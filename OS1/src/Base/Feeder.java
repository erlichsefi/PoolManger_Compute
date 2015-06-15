package Base;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class Feeder extends Thread{
	static int count=0;
	static Semaphore MUTEX=new Semaphore(1);

	static AtomicInteger golb=new AtomicInteger(0);
	private poolManger p;
	private ArrayList<Callable> task;
	private int id;
	private boolean IsDone;

	
	public Feeder(ArrayList<Callable> n){ 
		super("Feeder number"+(count));
		IsDone=false;
		id=count++;
		this.task = new ArrayList<Callable>();
		while (n.size()!=0){
			task.add(n.remove(0));
		}


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


	public void SubmitManger( poolManger _p){
		p=_p;
		this.start();
	}



	public void run(){
		
					while (task.size() > 0){
						p.pushWaitingTask(task.remove(0));

					}
	}

	//System.out.println("feeder number "+id+" Ended with "+this.task.size());
}






