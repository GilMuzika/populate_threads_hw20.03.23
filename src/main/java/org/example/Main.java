package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {



    public static int _arrayLength = 30;






    public static void main(String[] args) {

        int[] arr = new int[_arrayLength];
        //Helper[] arr = new Helper[_arrayLength];



        ArrayList<Thread> allThreads = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            PopulateThread pt = new PopulateThread(arr, "Thread " + (i + 1), Protection.Atomics);
            allThreads.add(pt);
            pt.start();
        }
        allThreads.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(Arrays.toString(arr));







    }
}
