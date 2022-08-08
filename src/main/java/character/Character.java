package character;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import labyrinth.Labyrinth;

/**
 * Класс персонажа
 */
public class Character {

    private int currentRow;
    private int currentCol;

    private TextColor characterBackgroundColor = TextColor.ANSI.WHITE_BRIGHT;
    private TextColor characterForegroundColor = TextColor.ANSI.RED;
    private String characterName = "me";

    /**
     * Инициализация персонажа.
     * Начальная позиция на поле -- левый верхний угол.
     * Имя персонажа по умолчанию -- "me"
     */
    public Character() {
        currentRow = 0;
        currentCol = 0;
    }

    /**
     * Инициализация персонажа с заданием начальной позиции на поле
     * Положение клетки задается относительно левой верхней ячейки.
     * Имя персонажа по умолчанию -- "me"
     *
     * @param row вертикальная позиция на поле
     * @param col горизонтальная позиция на поле
     */
    public Character(int row, int col) {
        setCharacterPosition(row, col);
    }

    /**
     * Инициализация персонажа с заданием начальной позиции на поле b имени.
     * Положение клетки задается относительно левой верхней ячейки.
     *
     * @param row  вертикальная позиция на поле
     * @param col  горизонтальная позиция на поле
     * @param name имя персонажа. Отображаться будут только первые два символа
     */
    public Character(int row, int col, String name) {
        if (name.length() >= 2) {
            characterName = name;
        } else {
            var nameLength = name.length();
            characterName = name + new String(new char[2 - nameLength]).replace('\0', ' ');
        }
        setCharacterPosition(row, col);
    }

    /**
     * Установка цвета фона для версонажа
     *
     * @param characterBackgroundColor цвет фона
     */
    public void setCharacterBackgroundColor(TextColor characterBackgroundColor) {
        this.characterBackgroundColor = characterBackgroundColor;
    }

    /**
     * Установка цвета текста имени персонажа
     *
     * @param characterForegroundColor цвет текста имени персонажа
     */
    public void setCharacterForegroundColor(TextColor characterForegroundColor) {
        this.characterForegroundColor = characterForegroundColor;
    }

    /**
     * Установка позиции персонажа на поле.
     * Положение клетки задается относительно левой верхней ячейки.
     *
     * @param row вертикальная позиция на поле
     * @param col горизонтальная позиция на поле
     */
    public void setCharacterPosition(int row, int col) {
        currentRow = row;
        currentCol = col;
    }

    /**
     * Отрисовка персонажа
     *
     * @param screen -- объект класса Screeen, на котором будет выполняться отрисовка
     */
    public void showCharacter(Screen screen) {
        drawCharacter(screen);
    }

    private void drawCharacter(Screen screen) {
        screen.setCharacter(currentCol * 2, currentRow, TextCharacter.fromCharacter(characterName.charAt(0), characterBackgroundColor, characterForegroundColor)[0]);
        screen.setCharacter(currentCol * 2 + 1, currentRow, TextCharacter.fromCharacter(characterName.charAt(1), characterBackgroundColor, characterForegroundColor)[0]);
    }

    /**
     * Функция перемещения персонажа влево по полю
     *
     * @param labyrinth поле, по которому перемещается персонаж
     * @param screen    объект класса Screeen, на котором будет выполняться отрисовка
     */
    public void moveLeft(Labyrinth labyrinth, Screen screen) {
        if (currentCol == 0) {
            return;
        }
        int newCol = currentCol - 1;
        if (labyrinth.isCellWall(currentRow, newCol)) {
            return;
        }
        labyrinth.backToTrackCell(currentRow, currentCol, screen);
        currentCol = newCol;
        drawCharacter(screen);
    }

    /**
     * Функция перемещения персонажа вправо по полю
     *
     * @param labyrinth поле, по которому перемещается персонаж
     * @param screen    объект класса Screeen, на котором будет выполняться отрисовка
     */
    public void moveRight(Labyrinth labyrinth, Screen screen) {
        if (currentCol >= labyrinth.getColsCount() - 1) {
            return;
        }
        int newCol = currentCol + 1;
        if (labyrinth.isCellWall(currentRow, newCol)) {
            return;
        }
        labyrinth.backToTrackCell(currentRow, currentCol, screen);
        currentCol = newCol;
        drawCharacter(screen);
    }

    /**
     * Функция перемещения персонажа вверх по полю
     *
     * @param labyrinth поле, по которому перемещается персонаж
     * @param screen    объект класса Screeen, на котором будет выполняться отрисовка
     */
    public void moveUp(Labyrinth labyrinth, Screen screen) {
        if (currentRow == 0) {
            return;
        }
        int newRow = currentRow - 1;
        if (labyrinth.isCellWall(newRow, currentCol)) {
            return;
        }
        labyrinth.backToTrackCell(currentRow, currentCol, screen);
        currentRow = newRow;
        drawCharacter(screen);
    }

    /**
     * Функция перемещения персонажа вниз по полю
     *
     * @param labyrinth поле, по которому перемещается персонаж
     * @param screen    объект класса Screeen, на котором будет выполняться отрисовка
     */
    public void moveDown(Labyrinth labyrinth, Screen screen) {
        if (currentRow >= labyrinth.getRowsCount() - 1) {
            return;
        }
        int newRow = currentRow + 1;
        if (labyrinth.isCellWall(newRow, currentCol)) {
            return;
        }
        labyrinth.backToTrackCell(currentRow, currentCol, screen);
        currentRow = newRow;
        drawCharacter(screen);
    }

}
