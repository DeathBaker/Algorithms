package gad.doublehashing;


import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


public class DoubleHashTable<K, V> {
private DoubleHashable<K> func;
private Pair<K,V> [] table;

private int Rehash;
private int collision;
	@SuppressWarnings("unchecked")
	public DoubleHashTable(int primeSize, HashableFactory<K> hashableFactory) {
		// Erstellt ein Array von Pairs: (Pair<K, V>[]) new Pair[primeSize];
		func = hashableFactory.create(primeSize);
		table = new Pair[primeSize];
		Rehash =0;
		collision=0;
	}

	public int hash(K key, int i) {
		if(table.length==0){
			return 0;
		}
		int k =(func.hash(key)+i* func.hashTick(key))% table.length;
		if(k<0){
			return -k;
		}
		return k;
	}

	public boolean insert(K k, V v) {
		int index;
       for(int i=0;i< table.length;i++) {
		   index = hash(k, i);
		   if (table[index] == null )  {
			   if(i>0){
				   collision++;
			   }
			   if(i>Rehash){
				   Rehash=i;
			   }
			   table[index]=new Pair<>(k,v);
			   return true;
		   }else if (table[index].one().equals(k)){
			   if(i>0){
				   collision++;
			   }
			   if(i>Rehash){
				   Rehash=i;
			   }
			   table[index]= new Pair<>(k,v);
			   return true;
		   }
	   }
		return false;
	}

	public Optional<V> find(K k) {
		return Arrays.stream(table).filter(Objects::nonNull).filter(n ->n.one().equals(k)).map(Pair::two).findFirst();

	}

	public int collisions() {

		return collision;}

	public int maxRehashes() {
		return Rehash;
	}
}