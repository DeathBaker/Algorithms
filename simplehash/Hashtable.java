package gad.simplehash;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Hashtable<K, V> {
    private List<Pair<K, V>>[] table;
    private int[] function;

    @SuppressWarnings("unchecked")
    public Hashtable(int minSize, int[] a) {
        table = (List<Pair<K, V>>[]) new List[getNextPowerOfTwo(minSize)];
        for(int i=0;i< table.length;i++){
            table[i]=new ArrayList<>();
        }
        function=a;
    }

    public List<Pair<K, V>>[] getTable() {
        return table;
    }

    public static int getNextPowerOfTwo(int i) {
        if(i==0){
            return 1;
        }
        int temp=Integer.highestOneBit(i);
        if(i==temp){
            return i;
        }
        return temp<<1;
    }

    public static int fastModulo(int i, int divisor) {

        return i & (divisor-1);
    }

    private byte[] bytes(K k) {
        return k.toString().getBytes(StandardCharsets.UTF_8);
    }

    public int h(K k, ModuloHelper mH) {
        byte[] x = bytes(k);
        int sum=0;
        for(int i=0;i<x.length;i++){
            sum+=x[i]*function[i%function.length];
        }

        return mH.doTheMagic(sum, table.length);
    }

    public void insert(K k, V v, ModuloHelper mH) {
        if(k==null ){
            return;
        }
       int key = h(k,mH);
       table[key].add(new Pair<>(k,v));
    }

    public boolean remove(K k, ModuloHelper mH) {
        int key = h(k,mH);
       List<Pair<K,V>> temp= table[key].stream().filter(n->n.one().equals(k)).toList();
       if(temp.isEmpty()){
           return false;
       }
        return table[key].removeAll(temp);
    }

    public Optional<V> find(K k, ModuloHelper mH) {
        int key = h(k,mH);
       Optional<V> result= table[key].stream().filter(n -> n.one().equals(k)).map(Pair::two).findFirst();
        return result;
    }

    public List<V> findAll(K k, ModuloHelper mH) {
        int key = h(k,mH);
        List<V>result=table[key].stream().filter(n->n.one().equals(k)).map(n->n.two()).toList();
        return result;
    }

    public Stream<Pair<K, V>> stream() {
        return Stream.of(table).filter(Objects::nonNull).flatMap(List::stream);
    }

    public Stream<K> keys() {
        return stream().map(Pair::one).distinct();
    }

    public Stream<V> values() {
        return stream().map(Pair::two);
    }
}