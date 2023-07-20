package gad.doublehashing;


public class DoubleHashInt implements DoubleHashable<Integer> {
	private int size;

	public DoubleHashInt(int primeSize) {
	   size=primeSize;
	}

	@Override
	public int hash(Integer key) {
		if( size<=0){
			return 0;
		}
      int a = size>>1;
		int b = size>>2;
		int result=(a*key+b)% size;
		if(result<0){
			return -result;
		}

		return result;
	}

	@Override
	public int hashTick(Integer key) {
		if(key<0)
			key = -key;
		int result=(key%(size-1))+1;
		if(result<0){
			return -result;
		}
		return result;

	}
}