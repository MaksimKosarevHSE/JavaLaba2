package com.maksim.TrieProject;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class TrieProject {
    public static final String FILE_PATH = "trie.bin";
    private static final String mainMenu = """
            
            Выберите действие:
            1. Вставить слово
            2. Проверить наличие слова
            3. Проверить наличие слов с определенным префиксом
            4. Получить все слова с определенным префиксом
            5. Завершить программу
            """;
    private static final Scanner scanner = new Scanner(System.in);

    private static void printFrame() {
        System.out.println(mainMenu);
    }

    private static boolean processAction(Trie trie) {
        String action = scanner.nextLine();
        switch (action) {
            case "1" -> {
                System.out.println("Введите слово:");
                String word = scanner.nextLine();
                if (Objects.equals(word, "")) {
                    System.out.println("Вы не можете добавить пустое слово");
                } else {
                    try {
                        trie.insert(word);
                        System.out.println("Успешно!");
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                        return true;
                    }
                }
            }
            case "2" -> {
                System.out.println("Введите слово");
                String word = scanner.nextLine();
                if (Objects.equals(word, "")) {
                    System.out.println("Вы ввели пустое слово");
                    break;
                }
                boolean res = trie.contains(word);
                if (res) System.out.println("Слово содержится");
                else System.out.println("Слово отсутствует");
            }
            case "3" -> {
                System.out.println("Введите префикс");
                String word = scanner.nextLine();
                boolean res = trie.startsWith(word);
                if (!Objects.equals(word, "")) {
                    if (res) System.out.println("Слова с заданным префиксом содержатся");
                    else System.out.println("Слова с заданным префиксом отсутствуют");
                } else {
                    if (res) System.out.println("Дерево не пустое, найдено слово, начинающиеся с пустово префикса");
                    else
                        System.out.println("Дерево пустое, не найдено ни одного слова, начинающегося с пустого префикса");
                }
            }
            case "4" -> {
                System.out.println("Введите префикс");
                String word = scanner.nextLine();
                var res = trie.getByPrefix(word);
                if (!Objects.equals(word, "")) {
                    System.out.println("Список слов, начинающихся с заданного префикса:");
                    System.out.println(res);
                } else {
                    System.out.println("Список слов, начинающихся с пустого префикса(все слова в дереве):");
                    System.out.println(res);
                }
            }
            case "5" -> {
                return true;
            }
            case null, default -> System.out.println("Введите коорректное действие");
        }
        return false;
    }

    public static void main(String[] args) {
        Trie trie;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            trie = (Trie) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Файл с Trie не найден");
            trie = new Trie();
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                System.out.println("Создан новый");
            } catch (IOException ex2) {
                System.out.println("Произошла ошибка сохранения файла");
                return;
            }
        }

        do {
            printFrame();
        } while (!processAction(trie));

    }
}
