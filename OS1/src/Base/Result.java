package Base;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Result {
	private final ArrayList<Result> Father;
	private double R;
	private Semaphore finished;
	
	public Result(ArrayList<Result> r,Semaphore _finished) {
		finished=_finished;
		Father = r;
	}
	
	public Result(Result r) {
		R=r.R;
		Father=r.Father;
		finished=r.finished;
	
	}
	
	public void Submeet_Result(double d){
		R=new Double(d);
	}
	
	public void Report(){
		Father.add(this);
		finished.release();
		
	}
	@Override
	public String toString() {
		return "Result [Father=" + ", R=" + R + "]";
	}
	public Double getResuilt() {
		return R;
	}
	
	
	
	
	
	
}
