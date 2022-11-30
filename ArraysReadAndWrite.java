package streams;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;

public class ArraysReadAndWrite {

    public final static String TEXT_FILE_ROAD_1 = "array1.txt";
    public final static String TEXT_FILE_ROAD_2 = "array2.txt";
    public final static String TEXT_FILE_ROAD_3 = "array3.txt";

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ArraysReadAndWrite runner = new ArraysReadAndWrite();
        List<String> array1 = runner.createRandomList();
        List<String> array2 = runner.createRandomList();
        List<String> array3 = runner.createRandomList();


        ExecutorService executor1 = Executors.newFixedThreadPool(3);
        ExecutorService executor2 = Executors.newFixedThreadPool(1);
        List<Callable<String>> listTasks = new ArrayList<>();
        Collections.addAll(listTasks, new WriteMyListCallable(TEXT_FILE_ROAD_1,array1)
                , new WriteMyListCallable(TEXT_FILE_ROAD_2,array2), new WriteMyListCallable(TEXT_FILE_ROAD_3, array3));

        List<Future<String>> outputFuture = executor1.invokeAll(listTasks);
        for(Future<String> fut: outputFuture) {
            System.out.println(fut.get());
        }
        executor1.shutdown();
        listTasks.clear();
        outputFuture.clear();

        Collections.addAll(listTasks,new ReadMyListCallable(TEXT_FILE_ROAD_1),new ReadMyListCallable(TEXT_FILE_ROAD_2),
                new ReadMyListCallable(TEXT_FILE_ROAD_3));

        outputFuture = executor2.invokeAll(listTasks);
        for(Future<String> fut: outputFuture) {
            System.out.println(fut.get());
        }
        executor2.shutdown();

        System.out.println("Close thread " + Thread.currentThread().getName());

    }

    public List<String> createRandomList() {

        int n = (int)((Math.random() * 8) + 3);
        int[] array = new int[n];
        List<String> list = new ArrayList<>();
        for(int i = 0; i < array.length; i++) {
            array[i] = new Random().nextInt(10,100);
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

class ReadMyListCallable implements Callable<String> {


    private final String nameFile;
    public ReadMyListCallable(String nameFile) {
        this.nameFile = nameFile;
    }

    @Override
    public String call() {

        File file = new File(nameFile);
        List<Integer> list = new ArrayList<>();
        int numOfElements = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            numOfElements = Integer.parseInt(br.readLine());
            while ((line = br.readLine()) != null) {
                list.add(Integer.parseInt(line));
            }
            System.out.println("Зачитал массив из файла: " + file.getPath() + ", и вывожу его на экран: ");
            for (Integer i: list) {
                System.out.print(i + " ");
            }

            System.out.println();
            int sum = list.stream().reduce(0, Integer::sum);
            System.out.println("Сумма всех элементов данного массива равна: " + sum + "\n"
                    + "Количество элементов в массиве: " + numOfElements);
            double average = list.stream().mapToInt(s -> s).average().orElseThrow();
            String aver = new DecimalFormat("##.##").format(average);
            System.out.printf("Среднее арифметическое: %s\n",aver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return Thread.currentThread().getName() + " отработал.";
    }
}
