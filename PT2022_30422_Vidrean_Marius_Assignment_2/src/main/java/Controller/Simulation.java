package Controller;

import Controller.Queue;
import Model.Client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable{


    List<Client> waitingClients = new ArrayList<>();
    private ArrayList<Queue> queues = new ArrayList<>();
    private int simTime;
    int maxTime;
    int queuesNumber;
    int clientsNumber;

    double averageWaitingTime = 0;
    double averageServiceTime = 0;
    int peakHour = 0;
    int totalClientsInQueues = 0;
    int totalMax = 0;


    public Simulation(int clientsNumber, int queuesNumber, int simulationInterval, int minArrival, int maxArrival, int minService, int maxService) {
        this.maxTime = simulationInterval;
        this.queuesNumber = queuesNumber;
        this.clientsNumber = clientsNumber;
        this.queues = createQueues(this.queues, queuesNumber);
        this.waitingClients = createClients(this.waitingClients, clientsNumber, minArrival, maxArrival, minService, maxService);
        this.simTime = 0;
    }


    @Override
    public void run(){
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter("output3.txt"));
            myWriter.write("START\n");
            while (simTime < maxTime) {
                try {
                    simTime++;
                    if(totalMax < computeTotalClients()){
                        totalMax = computeTotalClients();
                        peakHour = simTime;
                    }
                    myWriter.append(toString());
                    System.out.println(simTime);
                    int i = 0;
                    while (i < waitingClients.size()) {
                        if (waitingClients.get(i).getArrivalTime() == simTime) {
                            sendClient(queues, waitingClients.get(i));
                            averageWaitingTime = averageWaitingTime + waitingClients.get(i).getWaitingTime();
                            waitingClients.remove(waitingClients.get(i));
                            i = 0;
                        } else {
                            i++;
                        }
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error");
                }
            }
            stopThreads();
            myWriter.append("\nAverage waiting time is: " + computeAverageWaitingTime());
            myWriter.append("\nAverage service time is: " + computeAverageServiceTime());
            myWriter.append("\nPeak hour is: " + peakHour);
            myWriter.close();
        }catch (IOException e){
            System.out.println("Problem when writing");
        }
    }

    public synchronized int computeTotalClients(){
        totalClientsInQueues = 0;
       for(Queue q: queues){
          totalClientsInQueues = totalClientsInQueues + q.getNumberClients().get();
       }
       return totalClientsInQueues;
    }

    public double computeAverageServiceTime(){
        return averageServiceTime = averageServiceTime / clientsNumber;
    }

    public double computeAverageWaitingTime(){
      return averageWaitingTime = averageWaitingTime / clientsNumber;
    }

    public void stopThreads(){
        for(int i = 0; i<queuesNumber; i++){
            queues.get(i).stopT();
        }
    }
    public void sendClient(ArrayList<Queue> queues, Client c){
        Integer maximumWait = -1;
        Queue chosenQue = null;
        averageServiceTime = averageServiceTime + c.getServiceTime();
           for(Queue q : queues){
               if(maximumWait == -1 || maximumWait > q.getWaitingTime()){
                   maximumWait = q.getWaitingTime();
                   chosenQue = q;
               }
           }
           chosenQue.addClient(c);
    }

    public ArrayList<Queue> createQueues(ArrayList<Queue> queues, int queuesNumber){
        for(int i = 0; i<queuesNumber; i++){
            Queue q = new Queue(i);
            queues.add(q);
        }
        return queues;
    }

    public List<Client> createClients(List<Client> c, int clientsNumber, int minArrival, int maxArrival, int minService, int maxService){
           Random random = new Random();
           for(int i = 0; i<clientsNumber; i++){
              int arrivalTime = random.nextInt(maxArrival - minArrival) + minArrival;
              int serviceTime = random.nextInt(maxService - minService) + minService;
               Client generated = new Client(i, arrivalTime, serviceTime);
               c.add(generated);
           }
           return c;
    }

    @Override
    public String toString() {
        return "Time: " + simTime + "\n" +
                "Waiting clients : " + waitingClients.toString() + "\n" +
                queues.toString() + "\n" + "=========================================================\n";
    }
}
