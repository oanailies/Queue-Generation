package BusinessLogic;

import GUI.SimulationFrame;
import Model.Task;

import java.io.*;
import java.util.*;


public class SimulationManager implements Runnable {
    public int timeLimit=60; public int N; public int Q;
    public int minArrival; public int maxArrival;
    public int minService; public int maxService;
    public SelectionPolicy selectionPolicy =SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler; private SimulationFrame frame;
    private List<Task> generatedTasks= new LinkedList<Task>(); private Random random= new Random();

    public SimulationManager(String strategy, int N,int Q,int minArrival,int maxArrival,int minService,int maxService,int timeLimit)
    {
        this.timeLimit=timeLimit; this.N =N; this.Q =Q;
        this.minArrival=minArrival; this.maxArrival=maxArrival;
        this.minService=minService; this.maxService=maxService;
        scheduler=new Scheduler(Q,50);
        scheduler.changeStrategy(SelectionPolicy.valueOf(strategy));
        generateNRandomTask();
    }
    public void generateNRandomTask() {
        for (int i = 0; i < N; i++) {
            int arrTime = random.nextInt(maxArrival - minArrival + 1) + minArrival;
            int servTime = random.nextInt(maxService - minService + 1) + minService;
            System.out.println(arrTime + " " + servTime);
            generatedTasks.add(new Task(arrTime, servTime));
        }
        generatedTasks.sort(Comparator.comparing(Task::getArrivalTime));
    }
    int sTime = 0, sNumber = 0, peakHour = 0, wait = 0, totalWaitingTime = 0, currentTime = 0, stopGeneratedTask = -1;
    ArrayList<Integer> waitingTimes = new ArrayList<Integer>();@Override

    public void run() {try (BufferedWriter logOfEvents = new BufferedWriter(new FileWriter("filename.txt"));
                            BufferedWriter finalRezult = new BufferedWriter(new FileWriter("rezultat.txt"))) {
            while (currentTime < timeLimit && currentTime != stopGeneratedTask) { logOfEvents.write("Time:" + currentTime + "\n");
                if (generatedTasks.size() == 1) {Task task = generatedTasks.get(0); int arrivalTime = task.getArrivalTime(); int serviceTime = task.getServiceTime();
                    stopGeneratedTask = arrivalTime + serviceTime + 2;}
                logOfEvents.write("Waiting clients:");
                for (int i = 0; i < generatedTasks.size(); i++) {Task t = generatedTasks.get(i);
                    logOfEvents.write("(" + t.getId() + "," + t.getArrivalTime() + "," + t.getServiceTime() + ")" + ",");} logOfEvents.write("\n");
                for (int i = 0, count = 0; i < scheduler.getServers().size(); i++) { int aux_i=i+1; logOfEvents.write("Queue " + aux_i + ":");
                    Task[] tasks = scheduler.getServers().get(i).getTasks();
                    if (scheduler.getServers().get(i).getTasksQueue().size() == 0) {logOfEvents.write("closed ");} count = 0;
                    for (int j = 0; j < tasks.length; j++) {
                        if (tasks[j] != null) { count++;
                            logOfEvents.write("(" + tasks[j].getId() + "," + tasks[j].getArrivalTime() + "," + tasks[j].getServiceTime() + ") ");}}
                    if (peakHour <= count - 1) { peakHour = count;}
                    if (!scheduler.getServers().get(i).getTasksQueue().isEmpty()) {
                        Queue<Task> tasksQueue = scheduler.getServers().get(i).getTasksQueue();
                        if (!tasksQueue.isEmpty()) { Task t = tasksQueue.peek();
                            if (t.getServiceTime() == 1) { tasksQueue.remove();  t.setServiceStartTime(currentTime);
                                for (int j = 0; j < tasks.length; j++) {
                                    if (tasks[j] != null) { count++;try { logOfEvents.write("(" + tasks[j].getId() + "," + tasks[j].getArrivalTime() + "," + tasks[j].getServiceTime() + ") ");
                                        } catch (IOException exception) { throw new RuntimeException(exception);}
                                        totalWaitingTime += (currentTime - tasks[j].getArrivalTime());}}
                                sTime+= totalWaitingTime; sNumber++;} else { t.setServiceTime(t.getServiceTime() - 1);}} }logOfEvents.write("\n"); }
                for (int i = 0; i < generatedTasks.size(); i++) {Task g = generatedTasks.get(i);
                    if (g.getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(g);generatedTasks.remove(i);i--;}}currentTime++;
                synchronized (this) {try {wait(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}}}
            finalRezult.write("Peak hour:"+peakHour+"\n"+"Average waiting time:" + (double) totalWaitingTime / sNumber + "\n" +"Average service time:"+ (double)sTime/sNumber+"\n");
        } catch (IOException e) {throw new RuntimeException(e);}}
    /*
    otalWaitingTime += (currentTime - tasks[j].getArrivalTime()) - Aceasta calculează timpul de așteptare pentru o sarcină j care a sosit la tasks[j].getArrivalTime() și a așteptat până la currentTime
    sTime += totalWaitingTime - Aceasta adaugă timpul total de așteptare pentru toate sarcinile la variabila sTime
    sNumber numarul de saricini
    sTime depinde de timpul total de asteptare petru o sarcina
    Media de așteptare = Suma timpului de așteptare pentru toate sarcinile / Numărul total de sarcini.
    Media de servire = Suma timpului de servire pentru toate sarcinile / Numărul total de sarcini.
     */

    public static void main(String[] args)  {
        int timeLimit;
        int Q;
        int N;
        int minArrival,maxArrival;
        int minService,maxService;

        SimulationFrame frame= new SimulationFrame();
        while (frame.butonn == 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String strategy=frame.getStrategyDropdown();

            timeLimit=Integer.parseInt(frame.getTimeLimitField());
            N=Integer.parseInt(frame.getClientsField());
            Q=Integer.parseInt(frame.getQueueSizeField());
            minArrival=Integer.parseInt(frame.getMinArrivalField());
            maxArrival=Integer.parseInt(frame.getMaxArrivalField());
            minService=Integer.parseInt(frame.getInputMinService());
            maxService=Integer.parseInt(frame.getMaxServiceField());

        SimulationManager gen=new SimulationManager(strategy, N, Q,minArrival,maxArrival,minService,maxService,timeLimit);
        Thread t=new Thread(gen);
        t.start();

    }
}
