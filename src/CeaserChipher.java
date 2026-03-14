import javax.swing.*;
import java.io.File;



public class CeaserChipher {


     static void main(String[] args) {


        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        // 1. Выбор исходного файла
        File inputFile = chooseFile("Выберите исходный текстовый файл");
        if (inputFile == null) return;

        // 2. Выбор режима (Шифрование, Дешифрование или Brute Force)
        Object[] options = {"Зашифровать", "Расшифровать", "Brute Force (в консоль)"};
        int mode = JOptionPane.showOptionDialog(null, "Выберите действие:", "Шифр Цезаря",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (mode == -1) return; // Закрыли окно

        try {
            // Читаем файл с буферизацией
            String content = FileManager.readBufferedToFile(inputFile.getPath());

            if (mode == 2) {
                // Режим Brute Force
                runBruteForce(content);
                JOptionPane.showMessageDialog(null, "Результаты Brute Force выведены в консоль IDE.");
            } else {
                // Режимы Шифрование/Дешифрование
                String keyStr = JOptionPane.showInputDialog("Введите ключ (смещение):");
                if (keyStr == null) return;
                int key = Integer.parseInt(keyStr);

                String result = (mode == 0) ? encrypt(content, key) : encrypt(content, -key);

                // Сохранение результата
                File outputFile = chooseFile("Куда сохранить результат?");
                if (outputFile != null) {
                    FileManager.writeBufferedToFile(outputFile.getPath(), result);
                    JOptionPane.showMessageDialog(null, "Файл успешно сохранен!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }


    }

    public static String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        String alphabetStr = new String(Alfavit.ALFAVIT);

        for (char symbol : text.toLowerCase().toCharArray()) {
            int index = alphabetStr.indexOf(symbol);
            if (index != -1) {
                // Вычисляем новый индекс с учетом сдвига
                int newIndex = (index + key) % Alfavit.ALFAVIT.length;
                // Обработка отрицательного ключа (для дешифровки)
                if (newIndex < 0) newIndex += Alfavit.ALFAVIT.length;
                result.append(Alfavit.ALFAVIT[newIndex]);
            } else {
                result.append(symbol); // Если символа нет в алфавите, оставляем, как есть
            }
        }
        return result.toString();
    }

    public static void runBruteForce(String text) {
        System.out.println("--- НАЧАЛО BRUTE FORCE ---");
        for (int i = 1; i < Alfavit.ALFAVIT.length; i++) {
            System.out.println("Ключ " + i + ": " + encrypt(text, -i));
        }
        System.out.println("--- КОНЕЦ ---");
    }

    private static File chooseFile(String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        int result = chooser.showOpenDialog(null);
        return (result == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
    }

}
