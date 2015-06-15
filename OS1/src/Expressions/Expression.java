package Expressions;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import Base.Result;

public interface Expression {


	public  void Submeet_In_Expression(double i);
	
	public  void Just_Submeet(Double double1);

	public   double GetValue();

	public ArrayList<Callable> CreateTask(int n, int max_op,
			ArrayList<Base.Result> d,Semaphore _finished);
	
	public  Callable Create_Secend(int max,  ArrayList<Result> D,Semaphore _finished);

	public String getName();
}
