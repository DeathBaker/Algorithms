package gad.radix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class BinaryRadixSort {

    private BinaryRadixSort() {
    }

    public static int key(int element, int binPlace) {
        if(binPlace>31){
            return 0;
        }
        int temp = 1;
        temp <<= binPlace;
        int result = element & temp;

        return (result>>binPlace)&0x1;
    }

    public static void kSort(BinaryBucket from, BinaryBucket to, int binPlace) {

        for(int i = 0;i<from.getMid();i++){
            int value = key(from.getElement(i),binPlace );
            if(value == 0){
                to.insertLeft(from.getElement(i));
            }else {
                to.insertRight(from.getElement(i));
            }
        }
        for( int i= from.getSize()-1;i>= from.getMid();i--){
            int value = key(from.getElement(i),binPlace );
            if(value == 0){
                to.insertLeft(from.getElement(i));
            }else {
                to.insertRight(from.getElement(i));
            }
        }
    }

    public static void lastSort(BinaryBucket from, int[] to) {
       int leftP = 0;
       int RPoint = to.length-1;
       for( int i = from.getSize()-1;i>=0;i--){
           if(from.getElement(i)<0){
               to[leftP++] = from.getElement(i);
           }else{
               to[RPoint--] = from.getElement(i);
           }
       }

    }

    public static void sort(int[] elements, Result result) {
        BinaryBucket from = new BinaryBucket(elements.length);
          from.setBucket(elements);
          from.start();
          BinaryBucket to = new BinaryBucket(elements.length);
        for (int decPlace = 0; decPlace < 32; decPlace++) {
            kSort(from, to,decPlace);
            to.logArray(result);
           from.copy(to);
           to.reset();
        }

        lastSort(from,elements);
    }

    public static void main(String[] args) {
       int[] test = new int[10_000_000];
        Random random = new Random();
        for (int i = 0; i < test.length; i++) {
            test[i] = random.nextInt(Integer.MAX_VALUE);
        }
        int[] testTwo = Arrays.copyOf(test, test.length);

        long start = System.nanoTime();
        sort(test, ignored -> {
        });
        long binaryTime = System.nanoTime() - start;

        start = System.nanoTime();
        RadixSort.sort(testTwo, ignored -> {
        });
        long decimalTime = System.nanoTime() - start;

        System.out.println("Korrekt sortiert:" + Arrays.equals(test, testTwo));
        System.out.println("Binary: " + binaryTime / 1_000_000);
        System.out.println("Decimal: " + decimalTime / 1_000_000);
      // sort(test,new StudentResult());

    }
}
