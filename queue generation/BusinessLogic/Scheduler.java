package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
   //planificare pt servere
    private List<Server> servers;
    private int maxNoServers;
    private int maxTaskPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer)
    {
        this.servers = new ArrayList<>();//lista servere
        this.maxNoServers = maxNoServers;
        this.maxTaskPerServer = maxTasksPerServer;
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(maxTasksPerServer);
            servers.add(server);
            Thread serverThread = new Thread(new Server(maxTasksPerServer).toString());
            serverThread.start();
        }
    }
    //schimba strategia
    public void changeStrategy(SelectionPolicy policy)
    {
        if(policy==SelectionPolicy.SHORTEST_QUEUE)
        {
            strategy=new ConcreteStrategyQueue();
        }
        if(policy==SelectionPolicy.SHORTEST_TIME)
        {
            strategy=new ConcreteStrategyTime();
        }
    }


    //primeste o noua sarcina si o trimite la unul dintre serverele disponibile in fct de strategoe
    public void dispatchTask(Task t)
    {
       strategy.addTask(servers, t);
    }
    //return server list
    public List<Server> getServers()
    {
        return servers;
    }


    public void addServer(Server server) {
        servers.add(server);
    }
}
