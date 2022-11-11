package patnashki;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class FileSaveWriter {

    public static final String DATA_BASE_DIRECTORY = "database";

    public static void main(String[] args) {

        createDataBaseWithSaves(DATA_BASE_DIRECTORY);
        System.out.println("Do you want to start a new game or load the save? (S/L): ");
        String answer = new Scanner(System.in).nextLine();

        if (answer.equals("L")) {
            checkAndReaderFiles(DATA_BASE_DIRECTORY);
        } else {
            checkAnswer();
        }
    }

    public static String[] createNewArray() {

        String[] list = new String[10];

        for (int i = 0; i < list.length; i++) {

            list[i] = Integer.toString(new Random().nextInt(10, 100));
            System.out.print(list[i] + " ");
        }
        System.out.println();
        checkSaveAnswer(list);

        return list;
    }

    public static void checkSaveAnswer(String[] list) {

        System.out.println("Do you want to save your array? (Y/n): ");
        String anw = new Scanner(System.in).nextLine();

        if (anw.equals("Y")) {
            saver(list,DATA_BASE_DIRECTORY);
        } else if (anw.equals("n")){
            checkAnswer();
        } else {
            System.out.println("Wrong command");
        }

    }

    public static void createDataBaseWithSaves(String directory) {

        File dir = new File(directory);
        if (dir.mkdir()) {
            System.out.println("Directory created");
        }
    }

    public static void saver(String[] file, String directory) {

        System.out.print("Enter the save name file: ");
        String enter = new Scanner(System.in).nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(".\\" + DATA_BASE_DIRECTORY
                + "\\" + enter))) {

            for (String a : file) {
                bw.write(a + " ");
            }
            System.out.println("Saved");
            checkAnswer();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void checkAndReaderFiles(String name) {

        File file = new File(name);

        if(file.isDirectory() && Objects.requireNonNull(file.listFiles()).length > 0) {

            for (File f: Objects.requireNonNull(file.listFiles())) {

                if (f.isDirectory()) {
                    System.out.println(f.getName() + " \t folder");
                } else {
                    System.out.println(f.getName() + "\t file");
                }
            }
        } else {
            System.out.print("Is empty direct.");
            System.out.println();
            checkAnswer();
        }
    }

    public static void checkAnswer() {

        System.out.println("Do you want to start a new array? (Y/n): ");
        String anw = new Scanner(System.in).nextLine();

        if (anw.equals("Y")) {
            createNewArray();
        } else if (anw.equals("n")){
            System.out.println("Game finished");
        } else {
            System.out.println("Its wrong enter!");
            checkAnswer();
        }
    }
}

