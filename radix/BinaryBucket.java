package gad.radix;

import java.util.Arrays;

public class BinaryBucket {

	private int[] bucket;
	private int lPointer;
	private int rPointer;

	public BinaryBucket(int size) {
       bucket = new int[size];
	   lPointer =0;
	   rPointer = size-1;
	}

	public void insertLeft(int number) {
    if(lPointer <=rPointer){
		bucket[lPointer++] = number;
	}
	}

	public void insertRight(int number) {
     if(rPointer>=lPointer){
		 bucket[rPointer--]=number;
	 }
	}

	public int getMid() {
		return lPointer;
	}
  public void setBucket(int[] bucket){
		this.bucket = bucket;
  }
  public int getElement(int index){
		return bucket[index];
  }
  public int getSize(){
		return bucket.length;
  }
   public void start(){
		lPointer = bucket.length;
	}
	public void copy(BinaryBucket to){
		bucket = Arrays.copyOf(to.getBucket(),to.getSize());
		lPointer = to.getMid();
	}
      public void reset(){
		lPointer = 0;
		rPointer = bucket.length-1;
	  }
	private int[] getBucket() {
		return bucket;
	}

	public void logArray(Result result) {
		result.logArray(bucket);
	}

}
