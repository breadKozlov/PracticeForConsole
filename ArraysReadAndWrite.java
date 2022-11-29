package streams;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArraysReadAndWrite {

    public final static String TEXT_FILE_ROAD_1 = "array1.txt";
    public final static String TEXT_FILE_ROAD_2 = "array2.txt";
    public final static String TEXT_FILE_ROAD_3 = "array3.txt";

    public static void main(String[] args) throws InterruptedException {

        ArraysReadAndWrite runner = new ArraysReadAndWrite();
        List<String> array1 = runner.createRandomArray();
        List<String> array2 = runner.createRandomArray();
        List<String> array3 = runner.createRandomArray();


        ExecutorService executor = Executors.newFixedThreadPool(3);
        Runnable worker1 = new WriteMyArraysRunnable(TEXT_FILE_ROAD_1,array1);
        Runnable worker2 = new WriteMyArraysRunnable(TEXT_FILE_ROAD_2,array2);
        Runnable worker3 = new WriteMyArraysRunnable(TEXT_FILE_ROAD_3,array3);

        executor.execute(worker1);
        executor.execute(worker2);
        executor.execute(worker3);
        executor.shutdown();
        Thread.sleep(2000);
        System.out.println("Close main thread " + Thread.currentThread().getName());
    }

    public List<String> createRandomArray() {

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

class WriteMyArraysRunnable implements Runnable {

   private final String name;
   private final List<String> inputList;

    public WriteMyArraysRunnable(String nameFile, List<String> inputList) {
        this.name = nameFile;
        this.inputList = inputList;
    }

    @Override
    public void run() {

        File file = new File(name);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(inputList.size() + "\n");
            for (String num: inputList) {
                bw.write(num + "\n");
            }
            bw.flush();
            System.out.println("Saved in " + file.getPath() + ", work in the - "
                    + Thread.currentThread().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
