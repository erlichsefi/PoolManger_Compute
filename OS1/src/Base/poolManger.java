package Base;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;



public  class  poolManger extends Thread {
	private static int MAX_POLLMANGER=0;
	private static int MAX_MUL=2;//must be >1
	private static  int MAX_ADD=2;//must be >1 
	private boolean running;
	private ArrayList<PoolThread> pools;
	private ArrayList<Callable> waitingTasks;
	private int t; //max num of tasks
	private int p;//number of pools
	private Semaphore RunningTask;
	private Semaphore PushWaiting;
	private Semaphore TakeWaiting;
	private Semaphore BustPool;
	private Semaphore mutex;
	
	
	public poolManger(int _t,int _p) {
		super("poolManger");
		assert(MAX_POLLMANGER==0); MAX_POLLMANGER++;
		pools = new ArrayList<PoolThread>(_p);
		waitingTasks = new ArrayList<Callable>(_t);
		t = _t;
		p=_p;
		running=true;
		RunningTask=new Semaphore(0,true);
		BustPool=new Semaphore(_p,true);
		
		PushWaiting=new Semaphore(_t,true);
		TakeWaiting=new Semaphore(0,true);
		mutex=new Semaphore(1,true);
	}


	public  void Set_max_add_mul(int a,int b){
		MAX_MUL=a;
		MAX_ADD=b;
	}

	public  void TakeCoin(Semaphore Sem){
		try {
			Sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	public void ReturnCoin(Semaphore Sem){
		Sem.release();
	}


	public  void pushWaitingTask(Callable r){
		TakeCoin(PushWaiting);
		TakeCoin(mutex);
		waitingTasks.add(r);
		ReturnCoin(mutex);
		ReturnCoin(TakeWaiting);
	}

	public Callable popWaitingTask(){
		TakeCoin(mutex);
		Callable c1=waitingTasks.remove(0);
		ReturnCoin(PushWaiting);
		return c1;
	}

	public  Result popEndedTask(){
		TakeCoin(RunningTask);
		int i=EndedTask();
		Result r1=pools.get(i).popEnedTask();
		ReturnCoin(BustPool);
		
		ReturnCoin(mutex);
		return r1;
	}

	public  void pushReportAt(){
		TakeCoin(BustPool);
		TakeCoin(TakeWaiting);

		int id=FreePool();
		pools.get(id).pushStartTask(popWaitingTask());


	}


	public  synchronized int  FreePool() {
		for (int i = 0; i < pools.size(); i++) {
			if (pools.get(i).IsFree())
				return i;
		}
		return -1;
	}

	public  synchronized int  EndedTask() {
		for (int i = 0; i < pools.size(); i++) {
			if (pools.get(i).Is_Ended())
				return i;
		}
		return -1;
	}



	public void ShutDown(){
		running=false;
		System.exit(0);
	}


	public void StartPools(){
		for (int i = 0; i <	p; i++) {
			PoolThread pl=new PoolThread(i,RunningTask);
			pools.add(pl);
			pools.get(i).start();
		}
	}

	public void KillPools(){
		for (int i = 0; i <	p; i++) {
			pools.get(i).ShutDown();
		}
	}


	public void run(){
		StartPools();
		
		//thread Responsible of returning task
		Thread th=new Thread(){
			public void run(){
				while (running){

						popEndedTask().Report();
						//System.out.println(Arrays.toString(tracker));
				}
				System.out.println("returns task Therad Ended ");
				KillPools();
			}	
			
		};
		th.start();

		while (running){
					pushReportAt();
			}
		
		
		System.out.println("PoolManger Therad Ended ");

	}

}




