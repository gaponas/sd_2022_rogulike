package mainApplication;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import screen.GameMap;
import screen.UserListener;

import java.io.IOException;

/**
 * Объект GameWindow представляет из себя окно приложения и отвечает за его отображение
 */
public class GameWindow {
    private final DefaultTerminalFactory factory;
    private Terminal term;
    private Screen screen;

    /**
     * Инициализация окна приложения с заданными размерами
     *
     * @param rowsCount высота терминала. Измеряется в количестве строк
     * @param colsCount ширина терминала. Измеряется в количестве столбцов
     */
    public GameWindow(int rowsCount, int colsCount) {

        factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(colsCount, rowsCount));
        factory.setTerminalEmulatorTitle("RogueLike");
    }

    /**
     * Демонстрации окна приложения
     *
     * @throws IOException при ошибке отрисовки
     */
    public void start() throws IOException {
        term = factory.createTerminal();
        term.setCursorVisible(false);

        screen = new TerminalScreen(term);
        screen.startScreen();

        UserListener userListener = new UserListener();

        GameMap gameMap = new GameMap(screen);

        gameMap.drawMap(false);

        boolean isContinueGame = true;
        while (isContinueGame) {
            isContinueGame = userListener.handling(term, gameMap);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        screen.stopScreen();
    }

}
