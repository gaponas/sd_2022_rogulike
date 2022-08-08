package screen;

import com.googlecode.lanterna.TerminalPosition;

/**
 * Одна ячейка карты. Хранит ее основные характеристики:
 * - положение на карте
 * - статус (стена/тропинка)
 */
public class Cell {
    private final int rowNumber;
    private final int colNumber;
    private boolean isWall;

    /**
     * Функция инициализации ячейки карты.
     * По умолчанию статус ячейки -- стена
     * Положение задается относительно левой верхней ячейки.
     *
     * @param row вертикальная позиция ячейки на карте
     * @param col горизонтальная позиция ячейки на карте
     */
    public Cell(int row, int col) {
        rowNumber = row;
        colNumber = col;
        isWall = true;
    }

    /**
     * Функция инициализации ячейки карты.
     * Положение задается относительно левой верхней ячейки.
     *
     * @param row  вертикальная позиция ячейки на карте
     * @param col  горизонтальная позиция ячейки на карте
     * @param wall задает статус ячейки. true -- стена, false -- часть тропинки
     */
    public Cell(int row, int col, boolean wall) {
        rowNumber = row;
        colNumber = col;
        isWall = wall;
    }

    /**
     * Получение позиции ячейки на экране
     *
     * @return позицию на экране, упакованную в TerminalPosition
     */
    public TerminalPosition getPosition() {
        return new TerminalPosition(colNumber, rowNumber);
    }

    /**
     * Изменение статуса ячейки
     *
     * @param wall Новый статус ячейки. true -- стена, false -- тропинка
     */
    public void setWallStatus(boolean wall) {
        isWall = wall;
    }

    /**
     * Получении статуса ячейки
     *
     * @return текущий статус ячейки. true -- стена, false -- тропинка
     */
    public boolean isCellWall() {
        return isWall;
    }
}
