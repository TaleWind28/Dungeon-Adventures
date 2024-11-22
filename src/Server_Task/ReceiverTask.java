package Server_Task;
import java.util.Scanner;

import Client_Server.TCPClient;

public class ReceiverTask implements Runnable{
    Communication_Protocol proto;
    Scanner userInput; 
    public ReceiverTask(TCPClient proto, Scanner userInput){
        this.proto = proto;
        this.userInput = userInput;
    }
    public void run(){
        String serverResponse = this.proto.receiveMessage();
                System.out.println(serverResponse);
                if(serverResponse.contains("Il mostro è stato sconfitto Congratulazioni!!")){
                    //String message = this.userInput.nextLine();
                    //this.sendMessage(message);
                    // if (!message.equals("si")){
                    //     return;
                    // }
                }
                if(serverResponse.contains("Che peccato, Il mostro ti ha sconfitto!") || serverResponse.contains("Il mostro ti ha ucciso")){
                    System.out.println("partita terminata");
                    return;
                }
    }
}
