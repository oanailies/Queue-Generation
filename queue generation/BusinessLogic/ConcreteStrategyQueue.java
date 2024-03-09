package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    //add task parcurge toate obiectele din Server din lista data, calculeaza dimeniunea cozii de sarcini si selecteaza
    //serverul cu cea mai mica nimensiune din coada
    //dupa ce serverul a fost selectat, metoda add sarcina prima
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server selectedServer = null;
        int shortestQueueSize = Integer.MAX_VALUE;//cea mai mica coada

        for (Server s : servers) {
            int queueSize = s.getTasks().length;
            if (queueSize < shortestQueueSize) {
                shortestQueueSize = queueSize;
                selectedServer = s;
            }
        }

        if (selectedServer == null) {
            throw new IllegalStateException("Server plin");
        }

        selectedServer.addTask(t);
    }

}
