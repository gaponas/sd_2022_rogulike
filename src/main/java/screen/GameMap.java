package screen;


import character.Character;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import labyrinth.Labyrinth;

import java.io.IOException;

/**
 * Класс, характеризующий игровое поле
 */
public class GameMap {

    private final Screen outerScreen;
    private final Labyrinth labyrinth;
    Character character;

    /**
     * Инициализация игрового поля
     *
     * @param screen на каком объекте класса Screen будет выполняться отрисовка карты
     */
    public GameMap(Screen screen) {
        outerScreen = screen;
        TerminalSize windowSize = outerScreen.getTerminalSize();
        int rows = windowSize.getRows();
        int cols = windowSize.getColumns();

        labyrinth = new Labyrinth(rows, cols);
    }

    /**
     * Функция отрисовки карты: поля и персонажей на нем
     *
     * @param isBuildNew нужно ли перегенерировать карту. true -- перегенерировать, false -- не перегенерировать
     * @throws IOException при ошибке отрисовки
     */
    public void drawMap(boolean isBuildNew) throws IOException {
        labyrinth.build(isBuildNew);
        character = new Character();
        labyrinth.drawLabyrinth(outerScreen);
        character.showCharacter(outerScreen);

        outerScreen.refresh();
    }

    /**
     * Функция перемещения персонажа вниз по карте
     *
     * @throws IOException при ошибке отрисовки перемещения
     */
    public void characterGoDown() throws IOException {
        character.moveDown(labyrinth, outerScreen);
        outerScreen.refresh();
    }

    /**
     * Функция перемещения персонажа вверх по карте
     *
     * @throws IOException при ошибке отрисовки перемещения
     */
    public void characterGoUp() throws IOException {
        character.moveUp(labyrinth, outerScreen);
        outerScreen.refresh();
    }

    /**
     * Функция перемещения персонажа вправо по карте
     *
     * @throws IOException при ошибке отрисовки перемещения
     */
    public void characterGoRight() throws IOException {
        character.moveRight(labyrinth, outerScreen);
        outerScreen.refresh();
    }

    /**
     * Функция перемещения персонажа влево по карте
     *
     * @throws IOException при ошибке отрисовки перемещения
     */
    public void characterGoLeft() throws IOException {
        character.moveLeft(labyrinth, outerScreen);
        outerScreen.refresh();
    }

}
