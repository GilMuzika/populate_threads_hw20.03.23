package org.example;

import com.sun.tools.javac.Main;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PopulateThread extends Thread {

    private int[] _arr;
    private Helper[] _helperArr;
    private Protection _protectionKind;
    private Random _rnd = new Random();
    private static int _simpleCounter = 0;
    private static AtomicInteger _atomicCounter = new AtomicInteger(0);

    public PopulateThread(int[] array, String name, Protection protection) {
        super(name);
        _arr = array;
        _protectionKind = protection;
    }
    public PopulateThread(Helper[] array, String name, Protection protection) {
        super(name);
        _helperArr = array;
        _protectionKind = protection;
    }

    public void populateArraySynchronized(int[] arr) {
        while (_simpleCounter < arr.length) {
            int index = _rnd.nextInt(arr.length);
            int value = _rnd.nextInt(10000);
            synchronized (Main.class) {
                if (arr[index] == 0) {
                    arr[index] = value;
                    _simpleCounter++;
                }
            }
        }
    }
    public void populateArraySynchronized(Helper[] arr) {

        while (_simpleCounter < arr.length) {
            int index = _rnd.nextInt(arr.length);
            int value = _rnd.nextInt(10000);
            synchronized (Main.class) {
                if (arr[index] == null) {
                    arr[index] = new Helper(Thread.currentThread().getName(), value);
                    _simpleCounter++;
                }
            }
        }
    }



    public void populateArrayAtomic(int[] arr) {
     while(_atomicCounter.get() < arr.length) {
         int index = _atomicCounter.get();
         int value = _rnd.nextInt(10000);
             arr[index] = value;
             _atomicCounter.incrementAndGet();
        }
    }

    public void populateArrayAtomic(Helper[] arr) {
        while(_atomicCounter.get() < arr.length) {
            int index = _atomicCounter.get();
            int value = _rnd.nextInt(10000);
            arr[index] = new Helper(Thread.currentThread().getName(), value);
            _atomicCounter.getAndIncrement();


        }
    }







    private void whichRunAtomics() {
     if(_arr == null)
        populateArrayAtomic(_helperArr);
     if(_helperArr == null)
         populateArrayAtomic(_arr);
    }
    private void whichRunSynchronized() {
        if(_arr == null)
            populateArraySynchronized(_helperArr);
        if(_helperArr == null)
            populateArraySynchronized(_arr);
    }

    @Override
    public void run() {
        if(_protectionKind == Protection.Atomics)
            whichRunAtomics();
        if(_protectionKind == Protection.Synchronized)
            whichRunSynchronized();
    }
}
