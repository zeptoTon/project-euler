import java.util.concurrent.Callable;

public class Mod_check_callable implements Callable<Long>{
	private final long i;	
	private final long step;

	Mod_check_callable(long i, long step){
		this.i = i;
		this.step = step;
	}

	@Override
	public Long call() throws Exception {
		long a = 0;
		for (long i=this.i; i<this.i+this.step ; i++) {
			if (i%3==0 || i%5==0) {
				a += i;
			}
		}
		return a;
	}
}