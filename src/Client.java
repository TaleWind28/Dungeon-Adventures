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
        //unreached code
        //System.out.println("Grazie per aver Giocato");
    }

    public void receiveBehaviour(){
        try{
            while(true){
                String serverAnswer = this.receiveMessage().payload;
                if(serverAnswer!=null)System.out.println(serverAnswer);
                
                if(serverAnswer.contains("Che peccato, Il mostro ti ha impaurito!") || serverAnswer.contains("Il mostro ti ha ucciso")){
                    System.out.println("partita terminata");
                    //disconnettere client
                    System.exit(0);
                    return;
                }
                if (serverAnswer.contains("Il mostro è stato sconfitto Congratulazioni!!")){
                    this.canSend = true;
                    serverAnswer = this.receiveMessage().payload;
                    if (serverAnswer.contains("Grazie per aver giocato!")){
                        System.out.println(serverAnswer);
                        System.exit(0);
                    }   
                }
                this.canSend = true;

            }
        }catch(NullPointerException e){
            try {
                if(!this.canSend){
                    long sleep = 10000;
                    Thread.sleep(sleep);
                }   
            } catch (InterruptedException e1) {
                // TODO: handle exception
            }
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
