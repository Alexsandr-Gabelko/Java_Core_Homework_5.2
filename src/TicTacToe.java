//Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3], и
// представляют собой, например, состояния ячеек поля для игры в крестикинолики,
// где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле с ноликом,
// 3 – резервное значение. Такое предположение позволит хранить в одном числе
// типа int всё поле 3х3. Записать в файл 9 значений так, чтобы они заняли три байта.

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class TicTacToe {
    public static void main(String[] args) {
        // Исходный массив (поле 3x3)
        int[] field = {
                1, 2, 3,
                0, 1, 2,
                2, 0, 1
        };

        // Запись поля в файл
        String fileName = "field.dat";
        writeFieldToFile(field, fileName);

        // Чтение поля из файла
        int[] readField = readFieldFromFile(fileName);
        System.out.println("Прочитанное поле:");
        for (int i = 0; i < readField.length; i++) {
            System.out.print(readField[i] + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }

    public static void writeFieldToFile(int[] field, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            int packedData = 0;
            for (int i = 0; i < field.length; i++) {
                packedData |= (field[i] & 0b11) << (i * 2); // Упаковываем каждое значение (2 бита на ячейку)
            }
            fos.write((packedData >> 0) & 0xFF); // Пишем первый байт
            fos.write((packedData >> 8) & 0xFF); // Пишем второй байт
            fos.write((packedData >> 16) & 0xFF); // Пишем третий байт
            System.out.println("Поле успешно записано в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    public static int[] readFieldFromFile(String fileName) {
        int[] field = new int[9];
        try (FileInputStream fis = new FileInputStream(fileName)) {
            int packedData = 0;
            packedData |= fis.read() << 0;  // Читаем первый байт
            packedData |= fis.read() << 8; // Читаем второй байт
            packedData |= fis.read() << 16; // Читаем третий байт

            for (int i = 0; i < field.length; i++) {
                field[i] = (packedData >> (i * 2)) & 0b11; // Извлекаем 2 бита для каждой ячейки
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла: " + e.getMessage());
        }
        return field;
    }
}
