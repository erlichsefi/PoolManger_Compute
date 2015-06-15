package User;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import Base.Feeder;
import Base.poolManger;
import Expressions.*;
import tests.*;

public class Main {
	static final int NUMBER_POOLS=20;
	public static void Solutin(int k,int r,ArrayList<Integer> nk,ArrayList<Integer> lr,ArrayList<Integer> mr,int t,int s,int m) {
		poolManger p1= new poolManger(t,NUMBER_POOLS);
		p1.start();
		

		Semaphore ComputeWaiting=new Semaphore(0);

		//Perform type (1.1)
		ExpressionA first=new ExpressionA();
		for (int i = 0; i < k; i++) {
			final OneStep r1=new OneStep(nk.get(i),first,m);
			r1.Exucte(p1, ComputeWaiting);
		}

		//Perform type (1.2)

		ExpressionB secend=new ExpressionB();
		ExpressionC third=new ExpressionC();
		for (int i = 0; i < r; i++) {
			final TwoStep r1=new TwoStep(lr.get(i),secend,m,mr.get(i),third,s);
			r1.Exucte(p1, ComputeWaiting);
		}

		try {
			ComputeWaiting.acquire(k+4*r);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
		p1.ShutDown();
		
	}


	public static void main(String[] args) {
		int k=10; //number of (1.1)
		int r=1;//number of (1.2)
		int max_mul=2;
		int max_sum=2;
		int t=1;
		ArrayList<Integer> nk=new ArrayList<Integer>(k);//(1.1);
		ArrayList<Integer> lr=new ArrayList<Integer>(r);//(1.2)a
		ArrayList<Integer> nr=new ArrayList<Integer>(r);//(1.2)b
		for (int i = 0; i <10; i++) {
			nk.add(1);
		}
		
		lr.add(1);
		nr.add(2);

		Solutin(k,r,nk,lr,nr,t,max_sum,max_mul);


	}









}
