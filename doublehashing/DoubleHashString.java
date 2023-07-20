package gad.doublehashing;

import java.nio.charset.StandardCharsets;

public class DoubleHashString implements DoubleHashable<String> {
    private int size;
	public DoubleHashString(int primeSize) {
		size= primeSize;
	}

	@Override
	public int hash(String key) {
		if(size==0){
			return 0;
		}
	byte[] temp = key.getBytes(StandardCharsets.UTF_8);
	int prime = size>>1;
	int sum=0;
	for(int i=0;i< temp.length;i++){
		sum+=prime*temp[i];
		prime *= prime;
	}
	int result=sum% size;
	if(result<0){
	return 	-result;
	}
	return result;
	}

	@Override
	public int hashTick(String key) {
		int sum=0;
		for(int i =0;i<key.length();i++)
			sum+=key.charAt(i);
		return (sum%(size-1))+1;
	}
}