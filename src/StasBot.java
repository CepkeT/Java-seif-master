import DataModel.TurnResult;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StasBot {
    private final Set<Number[]> _variants; // Множество всех возможных вариантов чисел
    private Number[] _currentTurn; // Текущий ход

    public StasBot() {
        _variants = new HashSet<>(); // Инициализация множества вариантов
        _currentTurn = null; // Инициализация текущего хода
    }

    public void InitGameStart() {
        // Инициализация всех возможных вариантов чисел
        for (int digit1 = 0; digit1 < 10; digit1++) {
            for (int digit2 = 0; digit2 < 10; digit2++) {
                if (digit2 == digit1) continue; // Пропустить повторяющиеся цифры
                for (int digit3 = 0; digit3 < 10; digit3++) {
                    if (digit3 == digit1 || digit3 == digit2) continue; // Пропустить повторяющиеся цифры
                    for (int digit4 = 0; digit4 < 10; digit4++) {
                        if (digit4 == digit1 || digit4 == digit2 || digit4 == digit3) continue; // Пропустить повторяющиеся цифры
                        Number[] variant = new Number[]{digit1, digit2, digit3, digit4}; // Создание варианта числа
                        _variants.add(variant); // Добавление варианта в множество
                    }
                }
            }
        }
    }

    public String getTurn() {
        _currentTurn = selectNextTurn(); // Выбор следующего хода
        _variants.remove(_currentTurn); // Удаление выбранного хода из множества вариантов

        return Utilites.TurnArrayToString(_currentTurn); // Преобразование хода в строку и возврат
    }

    public void setTurnResult(TurnResult result) {
        // Обновление списка вариантов на основе полученного результата
        _variants.removeIf(x -> Utilites.getRightCount(_currentTurn, x) != result.CorrectDigits ||
                Utilites.getInPlaceCount(_currentTurn, x) != result.DigitsInPlace);
    }

    private Number[] selectNextTurn() {
        Number[] nextTurn = null;
        int minRemainingVariants = Integer.MAX_VALUE;

        // Перебор всех вариантов и выбор варианта с минимальным количеством оставшихся вариантов
        for (Number[] variant : _variants) {
            int remainingVariants = countRemainingVariants(variant);
            if (remainingVariants < minRemainingVariants) {
                minRemainingVariants = remainingVariants;
                nextTurn = variant;
            }
        }

        return nextTurn; // Возврат выбранного хода
    }

    private int countRemainingVariants(Number[] variant) {
        int count = 0;
        for (Number[] otherVariant : _variants) {
            if (!Arrays.equals(otherVariant, variant)) {
                count++;
            }
        }
        return count; // Возврат количества оставшихся вариантов
    }
}