package screen;

import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Класс обработки действий от пользователя
 */
public class UserListener {

    /**
     * Функция обработки конкретного действия пользователя
     *
     * @param terminal объект класса Terminal, в котором расположено приложение
     * @param map      карта игры
     * @return возвращает false если необходимо прекратить обработку действий пользователя. true -- продолжить обработку действий
     * @throws IOException если не удалось выполнить отрисовку отклика на действие пользователя
     */
    public boolean handling(Terminal terminal, GameMap map) throws IOException {
        return keyListening(terminal, map);
        // в дальнейшем сюда могут быть добавлены функции обработки других действий пользователя. Мышью, например
    }

    private boolean keyListening(Terminal terminal, GameMap map) throws IOException {
        var keyValue = terminal.pollInput();
        if (keyValue == null) {
            return true;
        }
        var keyType = keyValue.getKeyType();
        if (keyType == KeyType.EOF || keyType == KeyType.Escape) {
            return false;
        }
        if (keyType == KeyType.ArrowDown) {
            map.characterGoDown();
        } else if (keyType == KeyType.ArrowUp) {
            map.characterGoUp();
        } else if (keyType == KeyType.ArrowRight) {
            map.characterGoRight();
        } else if (keyType == KeyType.ArrowLeft) {
            map.characterGoLeft();
        } else if (keyType == KeyType.F1) {
            map.drawMap(true);
        }
        return true;
    }

}
