package Base;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class PoolThread extends Thread {
	private boolean running=true;
	private boolean free=true;
	private boolean Ended=false;
	private int m;//max addition
	private int s;//max Multiplying
	private int id;
	private Callable starting;
	private Result r;
	private  Semaphore Wait;
	private  Semaphore busy;

	public PoolThread( int id,Semaphore b) {
		super("PoolThread number "+id);
		this.free=true;
		this.id = id;
		this.busy=b;
		Wait=new Semaphore(0,true);
	}

	public boolean IsFree(){
		return free;
	}

	public  void pushStartTask(Callable r){
		free=false;
		Ended=false;
		starting=r;
		ReturnCoin(Wait);
	}
	
	public  Result popEnedTask(){
		free=true;
		Ended=false;
		return new Result(r);
	}
	
	public  boolean Is_Ended(){
		return Ended && !free;
	}

	public void ShutDown(){
		running=false;
	}

	public void TakeCoin(Semaphore Sem){
		try {
			Sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void ReturnCoin(Semaphore Sem){
		Sem.release();
	}


	
	

	@Override
	public String toString() {
		return "PoolThread [running=" + running + ", free=" + free + ", Ended="
				+ Ended + ", id=" + id + ", starting=" + starting + ", busy="
				+ busy + "]";
	}

	public void run(){
		while (running){
			TakeCoin(Wait);
				try {
					r=(Result) starting.call();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Ended=true;
				ReturnCoin(busy);
			
			
			
		}
		System.out.println("PoolThread N "+id+" Ended ");
	}

}
