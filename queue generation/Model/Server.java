package Model;

//server care poate procesa sarcini primite in coada
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private BlockingQueue<Task> tasks;//o coada blocata de sarcini care asteapta sa fie procesate
    private AtomicInteger waitingPeriod;//perioada de asteptare(in milisecunde) a unei sarcini inainte de a fi procesata se server

    private int maxTasks;

    public Server()
    {
       tasks=new LinkedBlockingQueue<Task>();
       waitingPeriod=new AtomicInteger(0);
    }
    public Server(int maxTasks) {
        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
        this.maxTasks = maxTasks;
    }

    //adauga o sarcina noua in coada de sarcini si creste valoarea lui waitingPeriod cu 1
    public void addTask(Task newTask)
    {
        tasks.add(newTask);
        waitingPeriod.incrementAndGet();
        synchronized (tasks){
            tasks.notifyAll();
        }
    }
    //procesează task-urile din coadă în ordinea în care au fost adăugate și le elimină doar pe cele care au fost finalizate cu succes (cu durata de procesare 0)
    public void run() {
        while (true) {
            Task task = tasks.peek();
            if (task != null) {
                int serviceTime = task.getServiceTime();
                if (serviceTime > 0) {
                    try {
                        Thread.sleep(serviceTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tasks.remove();
                    waitingPeriod.getAndAdd(-serviceTime);
                } else {
                    System.out.println("end");
                    tasks.remove();
                    waitingPeriod.getAndDecrement();
                }
            }
        }
    }

    //returneaza un array de saricini care sunt stocate in asptare in coada de saricini
    public Task[] getTasks()
    {
        Task[] result = new Task[waitingPeriod.get()];
        tasks.toArray(result);
        return result;
    }

    //returneaza coada de saricni
    public LinkedBlockingQueue getTasksQueue()
    {
        return (LinkedBlockingQueue) tasks;
    }
    public AtomicInteger getWaitingPeriod()
    {
            return  waitingPeriod;
    }
    public void setWaitingPeriod(AtomicInteger waitingPeriod)
    {
        this.waitingPeriod=waitingPeriod;
    }

}

