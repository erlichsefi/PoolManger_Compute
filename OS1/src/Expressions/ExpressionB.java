package Expressions;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import Callables.First_Task;
import Callables.Secend_Task;
import Base.Result;

public class ExpressionB implements Expression{
	double ans;
	String name;
	public ExpressionB(){
		ans=1;
		name="(1.2)";
	}
	public void Submeet_In_Expression(double i){
		ans= (ans*(Math.pow(-1,3*i)/(2*(i+1)+1)));

	}
	
	public void Just_Submeet(Double i) {
		ans*=i;
		
	}

	public double GetValue(){
		return ans;
	}
	
	public String getName(){
		return name;
	}

	public ArrayList<Callable> CreateTask(int n,int max_op,ArrayList<Result> D,Semaphore _finished){
		ArrayList<Callable> E=new ArrayList<Callable>();
		int i = 1;
		while(i<=n){
			E.add(new First_Task(i,Math.min((i+max_op-1),n),D,new ExpressionB(),_finished));
			i=i+max_op;
		}
		return E;
	}
	
	
	public  Callable Create_Secend(int max,  ArrayList<Result> D,Semaphore _finished){
		int s=Math.min(max, D.size());
		ArrayList<Double> Tosec=new ArrayList<Double>();
		for (int i = 0; i < s; i++) {
			Tosec.add(D.remove(0).getResuilt());
		}
		return new Secend_Task( Tosec,D,new ExpressionB(),_finished);
	}
}
