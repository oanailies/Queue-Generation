package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
   //parcurge server si calculeaza timpul de asteptare al fiecarui server si selecteaza serverul cu
    //cel mai mic timp de asteptare
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server selectedServer = null;
        int shortestWaitingPeriod = Integer.MAX_VALUE;//cel mai scurt timp de asteptare


        for (Server s : servers) {
            int waitingPeriod = s.getWaitingPeriod().get();
            if (waitingPeriod < shortestWaitingPeriod) {
                shortestWaitingPeriod = waitingPeriod;
                selectedServer = s;
            }
        }

        if (selectedServer == null) {
            throw new IllegalStateException("Server plin");
        }

        selectedServer.addTask(t);
    }

}
