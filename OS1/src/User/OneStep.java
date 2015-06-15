package User;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import Callables.Secend_Task;
import Base.Feeder;
import Base.Result;
import Base.poolManger;
import Expressions.*;


public class OneStep extends Thread{
	static int id=0;
	private boolean printable=true;
	private int taskToDo;//number of task left to do
	private int n;//run to
	private Double Result;//the Result
	private  ArrayList<Callable> ReadyTasks;
	private final ArrayList<Result> Results;
	private Expression  Action;
	private int max;
	private poolManger p;
	private  Semaphore MainWait;
	private  Semaphore GetAllResults;

	public OneStep(int _n, Expression _op,int max_op) {
		super("OneStep number "+id++);
		max=max_op;
		Results= new ArrayList<Result>();
		ReadyTasks=new ArrayList<Callable>();
		this.n = _n;
		this.Action=_op;
		GetAllResults=new Semaphore(0,true);
		ReadyTasks=(( _op).CreateTask(n,max_op,Results,GetAllResults));
		taskToDo=ReadyTasks.size();
	}


	public OneStep(double result2, double result3, ExpressionC expressionC, int max2, int n2) {
		super("TaskDetails number "+id++);
		max=max2;
		Results= new ArrayList<Result>();
		ReadyTasks=new ArrayList<Callable>();
		this.n = n2;
		taskToDo=1;
		this.Action=expressionC;
		ReadyTasks=new ArrayList<Callable>();
		GetAllResults=new Semaphore(0,true);
		ReadyTasks.add(new Secend_Task(result2,result3,expressionC,Results,GetAllResults));
	}


	public boolean isPrintable() {
		return printable;
	}


	public void setPrintable(boolean printable) {
		this.printable = printable;
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

	public double getFinalResult() {
		return Result;
	}

	public void setResult() {
		Result=Results.get(0).getResuilt();
	}

	public  void TakeCoin(Semaphore Sem,int i){
		try {
			Sem.acquire(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void run() {
		int N=taskToDo;
		final Feeder f1=new Feeder(ReadyTasks);
		f1.SubmitManger(p);
		boolean flag;
		if (N==1) flag=false;
		else flag=true;
		while (flag){
			TakeCoin(GetAllResults,N);
			N=NextNumOfTask(N, max);
			GetAllResults=new Semaphore(0,true);
			while (!Results.isEmpty()){
				ReadyTasks.add(Action.Create_Secend(max, Results,GetAllResults));
			}

			Feeder f2=new Feeder(ReadyTasks);
			f2.SubmitManger(p);

			if (N==1) flag=false;

		}
		TakeCoin(GetAllResults,N);
		Result=new Double (Results.get(0).getResuilt());

		if (printable)
			System.out.println("Expr. type "+Action.getName()+", n = "+n+"  : "+Result);
		MainWait.release();
	}






}
