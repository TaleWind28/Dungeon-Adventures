package Server_Task;

import java.util.Scanner;
import Communication.Protocol;

public class ReceiverTask implements Runnable{
    Protocol proto;
    Scanner userInput; 
    
    public ReceiverTask(Protocol proto, Scanner userInput){
        this.proto = proto;
        this.userInput = userInput;
    }

    public void run(){
        String serverResponse = this.proto.receiveMessage().payload;
                System.out.println(serverResponse);
                //if(serverResponse.contains("Il mostro è stato sconfitto Congratulazioni!!"))
                if(serverResponse.contains("Che peccato, Il mostro ti ha sconfitto!") || serverResponse.contains("Il mostro ti ha ucciso")){
                    System.out.println("partita terminata");
                    return;
                }
    }
}
