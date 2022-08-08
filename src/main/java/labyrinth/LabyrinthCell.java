package labyrinth;

import screen.Cell;

/**
 * Класс- наследник клетки карты. Характеризует клетку поля
 */
public class LabyrinthCell extends Cell {

    private boolean isVisited;

    /**
     * Функция инициализации ячейки карты.
     * По умолчанию статус ячейки -- стена
     * По умолчанию ячейка не была посещена при построении лабиринта
     * Положение задается относительно левой верхней ячейки.
     *
     * @param row вертикальная позиция ячейки на карте
     * @param col горизонтальная позиция ячейки на карте
     */
    public LabyrinthCell(int row, int col) {
        super(row, col);
        isVisited = false;
    }

    /**
     * Функция инициализации ячейки карты.
     * Положение задается относительно левой верхней ячейки.
     * По умолчанию ячейка не была посещена при построении лабиринта
     *
     * @param row  вертикальная позиция ячейки на карте
     * @param col  горизонтальная позиция ячейки на карте
     * @param wall задает статус ячейки. true -- стена, false -- часть тропинки
     */
    public LabyrinthCell(int row, int col, Boolean wall) {
        super(row, col);
        isVisited = false;
        super.setWallStatus(wall);
    }

    /**
     * Установка индикатора посещения ячейки
     *
     * @param visit устанавливаемый индикатор посещения ячейки/ true -- ячейка посещена, false -- не посещена
     */
    public void setVisitStatus(boolean visit) {
        isVisited = visit;
    }

    /**
     * Получение индикатора посещения ячейки
     *
     * @return индикатор посещения ячейки/ true -- ячейка посещена, false -- не посещена
     */
    public boolean getVisitStatus() {
        return isVisited;
    }
}
