package Callables;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import Base.Result;
import Expressions.Expression;



public class First_Task  implements Callable{
	private int from;
	private int to;
	private Expression op;
	private  Result End;

	public First_Task(int _from, int _to,  ArrayList<Result> d, Expression expression,Semaphore _finished) {
		to=_to;
		from=_from;
		End=new Result(d,_finished);
		this.op =expression;
	}
	
	public Result get_Result(){
		return End;
	}


	@Override
	public Result call() throws Exception {
		for (int i =from; i <= to; i++) {
			 op.Submeet_In_Expression(i);
		}
		End.Submeet_Result(op.GetValue());
		return End;
	}

	@Override
	public String toString() {
		return "First_Task [from=" + from + ", to=" + to + ", op=" + op
				+ ", End=" + End + "]";
	}
	
	
	
	

}
