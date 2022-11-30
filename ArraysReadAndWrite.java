package streams;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;

public record ArraysReadAndWrite(int randomOrigin, int randomBound
        , int originLengthArray, int boundLengthArray) {

    public final static String TEXT_FILE_ROAD_1 = "array1.txt";
    public final static String TEXT_FILE_ROAD_2 = "array2.txt";
    public final static String TEXT_FILE_ROAD_3 = "array3.txt";
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);
    private static final List<Callable<String>> listTasks = new ArrayList<>();
    private static List<Future<String>> outputFuture = new ArrayList<>();

    public static void main(String[] args){

        ArraysReadAndWrite runner = new ArraysReadAndWrite(10, 100, 3, 10);
        List<String> filesTxt = new ArrayList<>();
        Collections.addAll(filesTxt, TEXT_FILE_ROAD_1, TEXT_FILE_ROAD_2, TEXT_FILE_ROAD_3);
        runner.do_Exam10(filesTxt);
    }

    public void do_Exam10(List<String> filesTxt) {

        System.out.println("Открываю поток - " + Thread.currentThread().getName());
        System.out.println("Создаю массивы и вывожу их на экран: ");

        Map<String, List<String>> inputForWrite = new HashMap<>();

        for (String file : filesTxt) {
            inputForWrite.put(file, this.createRandomList());
        }

        System.out.println();
        System.out.println("Записываю их асинхронно в трех потоках:");

        this.runInExecutor(listTasks, inputForWrite);

        System.out.println();
        System.out.print("Считываю массивы асинхронно в трех потоках из трех файлов\n" +
                " и вывожу результаты на экран:");

        this.runInExecutor(listTasks, filesTxt);
        executor.shutdown();
        System.out.println("Закрываю поток - " + Thread.currentThread().getName());
    }

    public void runInExecutor(List<Callable<String>> tasks, Map<String, List<String>> input) {

        for (Map.Entry<String, List<String>> task : input.entrySet()) {
            tasks.add(new WriteMyListCallable(task.getKey(), task.getValue()));
        }
        try {
            outputFuture = executor.invokeAll(tasks);
            for (Future<String> fut : outputFuture) {
                System.out.println(fut.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tasks.clear();
        outputFuture.clear();
    }

    public void runInExecutor(List<Callable<String>> tasks, List<String> filesTxt) {

        for (String file : filesTxt) {
            tasks.add(new ReadMyListCallable(file));
        }
        try {
            outputFuture = executor.invokeAll(tasks);
            for (Future<String> fut : outputFuture) {
                System.out.println(fut.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tasks.clear();
        outputFuture.clear();
    }

    public List<String> createRandomList() {

        int n = (int) ((Math.random() * (boundLengthArray - originLengthArray + 1) + originLengthArray));
        int[] array = new int[n];
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            array[i] = new Random().nextInt(randomOrigin, randomBound);
            System.out.print(array[i] + " ");
            list.add(Integer.toString(array[i]));
        }
        System.out.println();
        return list;
    }
}

class WriteMyListCallable implements Callable<String> {

    private final String name;
    private final List<String> inputList;

    public WriteMyListCallable(String nameFile, List<String> inputList) {
        this.name = nameFile;
        this.inputList = inputList;
    }

    @Override
    public String call() {

        File file = new File(name);
        String result = null;

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(inputList.size() + "\n");
            for (String num: inputList) {
                bw.write(num + "\n");
            }
            bw.flush();
            result = "Файл сохранен в: " + file.getPath() + ", "
                    + Thread.currentThread().getName() + " отработал.";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}

record ReadMyListCallable(String nameFile) implements Callable<String> {

    @Override
    public String call() {

        File file = new File(nameFile);
        List<Integer> list = new ArrayList<>();
        int numOfElements;
        StringBuilder result = new StringBuilder("\n");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            numOfElements = Integer.parseInt(br.readLine());
            while ((line = br.readLine()) != null) {
                list.add(Integer.parseInt(line));
            }
            result.append("Зачитал массив из файла: ").append(file.getPath()).append(", и вывожу его на экран: \n");
            for (Integer i : list) {
                result.append(i).append(" ");
            }
            result.append("\n");
            int sum = list.stream().reduce(0, Integer::sum);
            result.append("Сумма всех элементов данного массива равна: ").append(sum).append("\n")
                    .append("Количество элементов в массиве: ").append(numOfElements).append("\n");
            double average = list.stream().mapToInt(s -> s).average().orElseThrow();
            String aver = new DecimalFormat("##.##").format(average);
            result.append("Среднее арифметическое: ").append(aver).append("\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        result.append(Thread.currentThread().getName()).append(" отработал.");
        return result.toString();
    }
}