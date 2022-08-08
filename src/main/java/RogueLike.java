import mainApplication.GameWindow;

import java.io.IOException;


public class RogueLike {

    private static final int DEFAULT_ROWS_COUNT = 50;
    private static final int DEFAULT_COLS_COUNT = 100;

    public static void main(String[] args) {
        GameWindow wind = new GameWindow(DEFAULT_ROWS_COUNT, DEFAULT_COLS_COUNT);
        try {
            wind.start();
        } catch (IOException e) {
            System.out.println("Ooops:" + e.getMessage());
        }
    }
}
