import Client_Server.TCPClient;

public class Client extends TCPClient {
    public volatile boolean canSend;
    
    public Client(String IP, int PORT){
        super(IP,PORT);
        this.canSend = true;
    }
    
    public static void main(String args[]) throws Exception{
        TCPClient client = new Client("127.0.0.1", 20000);
        client.multiDial();
        System.out.println("Grazie per aver Giocato");
    }

    public void receiveBehaviour(){
        try{
            while(true){
                String serverAnswer = this.receiveMessage();
                if(serverAnswer!=null)System.out.println(serverAnswer);
                
                if(serverAnswer.contains("Che peccato, Il mostro ti ha impaurito!") || serverAnswer.contains("Il mostro ti ha ucciso")){
                    System.out.println("partita terminata");
                    //disconnettere client
                    System.exit(0);
                    return;
                }
                this.canSend = true;

            }
        }catch(NullPointerException e){
            System.out.println("Stiamo riscontrando dei problemi sul server, procederemo a chiudere la connessione, ci scusiamo per il disagio");
            System.exit(0);
        }
        
    }

    public void sendBehaviour(){
        while(true){
            if(this.canSend){    
                this.sendMessage(this.userInput.nextLine());
                this.canSend = false;
            }
        }
    }
}
