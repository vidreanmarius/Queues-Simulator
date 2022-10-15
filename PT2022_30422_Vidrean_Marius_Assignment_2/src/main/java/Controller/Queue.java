package Controller;

import Model.Client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {
    BlockingQueue<Client> clients;
    private AtomicBoolean isRunning; //without having to use synchronized block
    private Integer queueNumber;
    private AtomicInteger waitingTime;
    private AtomicInteger numberClients;

    public Queue(Integer id) {
        this.clients = new LinkedBlockingQueue<>();
        this.isRunning = new AtomicBoolean(false);
        this.queueNumber = id;
        this.waitingTime = new AtomicInteger(0); //how much does a new client has to wait
        this.numberClients = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (isRunning.get()) {

            try {

                if(clients.isEmpty()){
                    continue;
                }

                Thread.sleep(1000);
                Client current = clients.peek();
                current.decreaseServiceTime();
                waitingTime.decrementAndGet();

                if (current.getServiceTime() == 0) {
                    clients.remove();
                    numberClients.decrementAndGet();
                }

            } catch (InterruptedException e) {
                System.out.println("ERROR");
            }

        }
    }

    public void addClient(Client c){
        if(isRunning.get() == false){
            start();
        }
        numberClients.addAndGet(1);
        c.setWaitingTime(waitingTime.get());
        waitingTime.addAndGet(c.getServiceTime());
        clients.add(c);
    }

    public void stopT(){
        isRunning.set(false);
    }

    public void start(){
        isRunning.set(true);
        Thread m = new Thread(this);
        m.start();
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Client> clients) {
        this.clients = clients;
    }

    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(AtomicBoolean isRunning) {
        this.isRunning = isRunning;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Integer getWaitingTime() {
        return waitingTime.get();
    }

    public void setWaitingTime(AtomicInteger waitingTime) {
        this.waitingTime = waitingTime;
    }

    public AtomicInteger getNumberClients() {
        return numberClients;
    }

    public void setNumberClients(AtomicInteger numberClients) {
        this.numberClients = numberClients;
    }

    @Override
    public String toString() {
        if (getNumberClients().get() != 0) {
            return "Queue " + queueNumber + ":" + clients.toString() + "\n";
        } else {
            return "Queue " + queueNumber + ": " + "closed";
        }
    }
}
