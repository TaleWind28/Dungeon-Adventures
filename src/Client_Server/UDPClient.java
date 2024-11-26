package Client_Server;

import java.util.Scanner;

public class UDPClient extends TCP{
    private String ip;
    private int port;
    private Scanner userInput;

    public UDPClient (String IP,int PORT){
        this.port = PORT;
        this.ip = IP;
        this.userInput = new Scanner(System.in);
    }
    

}
