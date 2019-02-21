package xavierdpt.kkfjp;

import java.util.concurrent.ForkJoinPool;

public class Main {
	public static void main(String[] args) {
		System.out.println("Processors: " + Runtime.getRuntime().availableProcessors());
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		System.out.println("Common pool parallelism: " + commonPool.getParallelism());
		System.out.println("Common parallelism: "+ForkJoinPool.getCommonPoolParallelism());
		
		ForkJoinPool pool = new ForkJoinPool(2);
		System.out.println("Pool parallelism: " + pool.getParallelism());
		


	}
}
