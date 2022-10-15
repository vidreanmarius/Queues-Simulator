import Controller.Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        int clientsNumber = 0;
        int queuesNumber = 0;
        int simulationInterval = 0;
        int minArrival = 0;
        int maxArrival = 0;
        int minService = 0;
        int maxService = 0;
        try{
            File file = new File("D:/PT2022_30422_Vidrean_Marius_Assignment_2/src/main/resources/test3.txt");
            Scanner myReader = new Scanner (file);

             clientsNumber = myReader.nextInt();
             queuesNumber = myReader.nextInt();
             simulationInterval = myReader.nextInt();
             minArrival = myReader.nextInt();
             maxArrival = myReader.nextInt();
             minService = myReader.nextInt();
             maxService = myReader.nextInt();

             Simulation generare = new Simulation(clientsNumber, queuesNumber, simulationInterval, minArrival, maxArrival, minService, maxService);
             Thread t = new Thread(generare);
             t.start();
        }catch (FileNotFoundException ex){
            System.out.println("There is no file");
        }
    }
}
