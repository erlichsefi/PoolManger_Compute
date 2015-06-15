package User;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import Callables.Secend_Task;
import Base.Feeder;
import Base.Result;
import Base.poolManger;
import Expressions.*;


//the op bettween to Step is the op of step2
public class TwoStep extends Thread{
	static int id=0;
	private int n;//run to
	private int n2;
	private Double Result;//the Result
	private Expression  Action1;
	private Expression  Action2;
	private int max;
	private int max2;
	private poolManger p;
	private  Semaphore MainWait;

	public TwoStep(int _n, Expression _op,int max_op,int _n2, Expression _op2,int max_op2) {
		super("TwoStep number "+id++);
		max=max_op;
		max2=max_op2;
		n = _n;
		n2=_n2;
		this.Action1=_op;
		this.Action1=_op2;
	}


	public int NextNumOfTask(int tor,int max){
		if (tor%max != 0 ) return tor/max+1;
		else return tor/max;

	}

	public void Exucte(poolManger _p,Semaphore _computeWaiting){
		p=_p;	
		MainWait=_computeWaiting;
		this.start();
	}

	public int getN() {
		return n;
	}

	public double getResult() {
		return Result;
	}

	public void DoubleJoin(Thread t,Thread t1){
		try {
			t.join();
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void SinalJoin(Thread t){
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	public void run() {
		//Computing (1.2)a
		final OneStep r1=new OneStep(n,new ExpressionB(),max);
		r1.setPrintable(false);
		r1.Exucte(p, MainWait);
	
		//Computing (1.2)b
		final OneStep r2=new OneStep(n2,new ExpressionC(),max2);
		r2.setPrintable(false);
		r2.Exucte(p, MainWait);
		
		DoubleJoin(r1,r2);
		
		//Computing 2 result op
		OneStep r3=new OneStep(r1.getFinalResult(),r2.getFinalResult(),new ExpressionC(), max2,n);
		r3.Exucte(p, MainWait);
		
		SinalJoin(r3);
		Result=r3.getFinalResult();
		MainWait.release();
	}






}
