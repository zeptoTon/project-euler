/*
P1 Multiples of 3 and 5
Find the sum of all the multiples of 3 or 5 below 1000.

We exec this question in multithreading way in java
There are different way to do multiThreading
using ThreadPool with Callable and Future object
p.s Runnable is not for return type function.
*/

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;


public class p1 {
	private static final int NTHREDS = 10;
	private static final int BOUND = 100000000;
	private static int STEP = BOUND/NTHREDS;
	
	public static long fastest_way(){
		long n = (BOUND-1)/3;
		long m = (BOUND-1)/5;
		long r = (BOUND-1)/(5*3);

		return (3*(n+1)*n/2) + (5*(m+1)*m/2) - (15*(r+1)*r/2);
	}

	public static long alternate_method_forloop(){
		long a = 0;
		for ( int i=0; i<BOUND; i++ ) {
			if (i%3==0 || i%5==0) {
				a += i;
			}
		}
		return a;
	}

	public static long alternate_method_multthreading(){
    List<Future<Long>> list = new ArrayList<Future<Long>>();

		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		for (int i=0; i<NTHREDS ; i++ ) {
		    Callable<Long> worker = new Mod_check_callable(i*STEP, STEP);
			  Future<Long> submit = executor.submit(worker);
		    list.add(submit);
  		}
	    long sum = 0;
	    // now retrieve the result
	    for (Future<Long> future : list) {
	      try {
	        sum += future.get();
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      } catch (ExecutionException e) {
	        e.printStackTrace();
	      }
	    }
	    executor.shutdown();
	    return sum;
	}

	public static void main(String[] args){
		long start = System.currentTimeMillis();
		System.out.println(fastest_way());
		double elapsedTimeInSec = (System.currentTimeMillis() - start);
		System.out.println("formula:"+elapsedTimeInSec);
		
		start = System.currentTimeMillis();
		System.out.println(alternate_method_forloop());
		elapsedTimeInSec = (System.currentTimeMillis()- start);
		System.out.println("forloop:"+elapsedTimeInSec);

		start = System.currentTimeMillis();
		System.out.println(alternate_method_multthreading());
		elapsedTimeInSec = (System.currentTimeMillis()- start);
		System.out.println("multithread:"+elapsedTimeInSec);


	}


}