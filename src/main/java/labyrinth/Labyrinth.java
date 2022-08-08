package labyrinth;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Поле игры, состоящее из стен и тропинок
 */
public class Labyrinth {

    private final String PATH_TO_LABYRINTH_FILE = "./src/main/java/labyrinth/lab.txt";

    private ArrayList<ArrayList<LabyrinthCell>> labyrinthCellsMatrix;

    private int rowsCount;
    private int colsCount;

    private TextColor wallColor = TextColor.ANSI.BLACK;
    private TextColor trackColor = TextColor.ANSI.WHITE_BRIGHT;

    /**
     * Инициализация лабиринта для заданного размера окна приложения
     *
     * @param rows высота экрана приложения. Измеряется в количестве строк
     * @param cols ширина ээкрана приложения. Измеряется в количестве столбцов
     */
    public Labyrinth(int rows, int cols) {
        rowsCount = rows;
        colsCount = cols / 2;
    }

    /**
     * Изменение цвета стен
     *
     * @param color цвет стены
     */
    public void setWallColor(TextColor color) {
        wallColor = color;
    }

    /**
     * Получить высоту лабиринта в клетках
     *
     * @return высота в клетках
     */
    public int getRowsCount() {
        return rowsCount;
    }

    /**
     * Получить ширину лабиринта в клетках
     *
     * @return ширина в клетках
     */
    public int getColsCount() {
        return colsCount;
    }

    /**
     * Изменение цвета тропинок
     *
     * @param color цвет тропинки
     */
    public void setTrackColor(TextColor color) {
        trackColor = color;
    }

    private void initLabyrinth() {
        labyrinthCellsMatrix = new ArrayList<>(rowsCount);
        for (int r = 0; r < rowsCount; r++) {
            ArrayList<LabyrinthCell> helpRow = new ArrayList<>(colsCount);
            for (int c = 0; c < colsCount; c++) {
                helpRow.add(new LabyrinthCell(r, c));
            }
            labyrinthCellsMatrix.add(helpRow);
        }
    }

    /**
     * Функция создания поля игры
     *
     * @param buildNew нужно ли строить новое поле. true -- сгенерировать новое поле, false -- загрузить последнюю сгенерированную версию поля
     */
    public void build(boolean buildNew) {
        if (buildNew) {
            createNewLabyrinth();
        } else {
            if (!loadLabyrinth(PATH_TO_LABYRINTH_FILE)) {
                createNewLabyrinth();
            }
        }
    }

    private void createNewLabyrinth() {
        initLabyrinth();
        randomPrimeAlgo();
        saveLabyrinth();
    }

    private void randomPrimeAlgo() {
        ArrayList<LabyrinthCell> cellsToLook = new ArrayList<>();
        Random rand = new Random();

        var initCell = labyrinthCellsMatrix.get(0).get(0);
        addToArray(initCell, cellsToLook);
        cellsToLook.remove(0);

        initCell.setWallStatus(false);
        addNeighborsToArray(initCell, cellsToLook);

        while (!cellsToLook.isEmpty()) {
            int currElementIdx = rand.nextInt(cellsToLook.size());
            LabyrinthCell currentCell = cellsToLook.remove(currElementIdx);
            if (!checkCellToSaveItWall(currentCell)) {
                currentCell.setWallStatus(false);
                addNeighborsToArray(currentCell, cellsToLook);
            }
        }
    }

    private boolean checkCellToSaveItWall(LabyrinthCell cell) {
        TerminalPosition cellPosition = cell.getPosition();
        var row = cellPosition.getRow();
        var col = cellPosition.getColumn();

        int isTopNotWall = (row == 0) ? 0 : (labyrinthCellsMatrix.get(row - 1).get(col).isCellWall() ? 0 : 1);
        int isBottomNotWall = (row == rowsCount - 1) ? 0 : (labyrinthCellsMatrix.get(row + 1).get(col).isCellWall() ? 0 : 1);
        int isLeftNotWall = (col == 0) ? 0 : (labyrinthCellsMatrix.get(row).get(col - 1).isCellWall() ? 0 : 1);
        int isRightNotWall = (col == colsCount - 1) ? 0 : (labyrinthCellsMatrix.get(row).get(col + 1).isCellWall() ? 0 : 1);

        int countOfNotWallCellsAround = isTopNotWall + isBottomNotWall + isLeftNotWall + isRightNotWall;
        return countOfNotWallCellsAround > 1;
    }

    private void addNeighborsToArray(LabyrinthCell cell, ArrayList<LabyrinthCell> array) {
        TerminalPosition cellPosition = cell.getPosition();
        var row = cellPosition.getRow();
        var col = cellPosition.getColumn();
        if (row != 0) {
            addToArray(labyrinthCellsMatrix.get(row - 1).get(col), array);
        }
        if (row != rowsCount - 1) {
            addToArray(labyrinthCellsMatrix.get(row + 1).get(col), array);
        }
        if (col != 0) {
            addToArray(labyrinthCellsMatrix.get(row).get(col - 1), array);
        }
        if (col != colsCount - 1) {
            addToArray(labyrinthCellsMatrix.get(row).get(col + 1), array);
        }
    }

    private void addToArray(LabyrinthCell cell, ArrayList<LabyrinthCell> array) {
        if (cell.getVisitStatus()) {
            return;
        }
        array.add(cell);
        cell.setVisitStatus(true);
    }

    /**
     * Проверка статуса клетки поля
     * Положение клетки задается относительно левой верхней ячейки.
     *
     * @param row вертиакльная позиция клетки
     * @param col горизонттальная позиция клетки
     * @return возращает true, если клетка -- стена, false -- если тропинка
     */
    public boolean isCellWall(int row, int col) {
        return labyrinthCellsMatrix.get(row).get(col).isCellWall();
    }

    /**
     * Отрисовка лабиринта
     *
     * @param screen -- на каком объекте Screen выполнить отрисовку поля
     */
    public void drawLabyrinth(Screen screen) {
        for (int r = 0; r < rowsCount; r++) {
            for (int c = 0; c < colsCount; c++) {
                TextColor color = labyrinthCellsMatrix.get(r).get(c).isCellWall() ? wallColor : trackColor;
                drawMapCell(c, r, screen, color);
            }
        }
    }

    private void drawMapCell(int c, int r, Screen screen, TextColor color) {
        screen.setCharacter(c * 2, r, TextCharacter.fromCharacter('.', color, color)[0]);
        screen.setCharacter(c * 2 + 1, r, TextCharacter.fromCharacter('.', color, color)[0]);
    }

    /**
     * Перерисовка клетки поля в цвет тропинки.
     * Положение клетки задается относительно левой верхней ячейки.
     *
     * @param row    вертикальная позиция клетки
     * @param col    горизонтальная позиция клетки
     * @param screen объект класса Screen, на котором происходить перерисовка
     */
    public void backToTrackCell(int row, int col, Screen screen) {
        if (isCellWall(row, col)) {
            return;
        }
        screen.setCharacter(col * 2, row, TextCharacter.fromCharacter('.', trackColor, trackColor)[0]);
        screen.setCharacter(col * 2 + 1, row, TextCharacter.fromCharacter('.', trackColor, trackColor)[0]);
    }

    private void saveLabyrinth() {
        try (PrintWriter writer = new PrintWriter(PATH_TO_LABYRINTH_FILE, StandardCharsets.UTF_8)) {
            writer.println(rowsCount);
            writer.println(colsCount);
            for (int r = 0; r < rowsCount; r++) {
                for (int c = 0; c < colsCount; c++) {
                    writer.print(labyrinthCellsMatrix.get(r).get(c).isCellWall() ? 1 : 0);
                    writer.print(" ");
                }
                writer.print('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Can't save current map");
        }

    }

    private boolean loadLabyrinth(String path) {
        if (!Files.exists(Paths.get(path))) {
            return false;
        }
        int rows, cols;
        ArrayList<ArrayList<LabyrinthCell>> helpMatrix;
        try (Scanner scanLab = new Scanner(new File(PATH_TO_LABYRINTH_FILE))) {
            rows = scanLab.nextInt();
            cols = scanLab.nextInt();
            helpMatrix = new ArrayList<>(rowsCount);
            for (int r = 0; r < rowsCount; r++) {
                ArrayList<LabyrinthCell> helpRow = new ArrayList<>(colsCount);
                for (int c = 0; c < colsCount; c++) {
                    var newEl = scanLab.nextInt();
                    helpRow.add(new LabyrinthCell(r, c, (newEl == 1)));
                }
                helpMatrix.add(helpRow);
            }
        } catch (Exception e) {
            return false;
        }
        rowsCount = rows;
        colsCount = cols;
        labyrinthCellsMatrix = helpMatrix;
        return true;
    }
}
