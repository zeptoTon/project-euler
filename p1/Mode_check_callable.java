import java.util.concurrent.Callable;

public class Mod_check_callable implements Callable<Long>{

	Mod_check_callable(long i, long step){
		this.i = i;
		this.step = step;
	}

	@Override
	public Long call() throws Exception {
		long a = 0;
		for (int i=this.i; i<this.i+this.step ; i++) {
			if (i%3==0 || i%5==0) {
				a += i;
			}
		}
		return a;
	}
}

// import java.util.concurrent.Callable;

// public class MyCallable implements Callable<Long> {
//   @Override
//   public Long call() throws Exception {
//     long sum = 0;
//     for (long i = 0; i <= 100; i++) {
//       sum += i;
//     }
//     return sum;
//   }

// } 