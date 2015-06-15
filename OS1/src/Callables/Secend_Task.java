package Callables;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import Base.Result;
import Expressions.Expression;
import Expressions.ExpressionC;



public class Secend_Task  implements Callable{
	private int size;
	private ArrayList<Double> D;
	private Expression op;
	private  Result End;
	

	public Secend_Task( ArrayList<Double> num,  ArrayList<Result> d, Expression op,Semaphore _finished) {
		this.size =num.size();
		D=new ArrayList<Double>(num);
		this.op =op;
		End=new Result(d,_finished);
		
	}
	
	public Secend_Task(double result2, double result3, ExpressionC expressionC,ArrayList<Result> d,Semaphore _finished) {
	size=2;
	D=new ArrayList<Double>();
	D.add(result2);
	D.add(result3);
	op=expressionC;
	End=new Result(d,_finished);
	}

	public Result get_Result(){
		return End;
	}

	public int getTaskSize(){
		return size;
	}
	

	public int getTo() {
		return size;
	}


	@Override
	public Result call() throws Exception {
		for (int i =0; i < size; i++) {
			op.Just_Submeet(D.get(i));
		}
		End.Submeet_Result(op.GetValue());
		return End;
	}

	@Override
	public String toString() {
		return "Secend_Task [size=" + size + ", D=" + D + ", op=" + op
				+ ", End=" + End + "]";
	}
	
	
	
	
	

}
