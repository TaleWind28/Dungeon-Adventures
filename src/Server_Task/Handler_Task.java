package Server_Task;
import Game.GameFactory;
import Game.Monster;
import Game.Player;
import java.net.Socket;

import Client_Server.TCPServer;

public class Handler_Task extends Communication_Protocol implements Runnable{
    private Socket client;
    private GameFactory factory;
    private TCPServer disconnectBehaviour;
    
    public Handler_Task(Socket client_socket) throws Exception{
        super();
        this.client = client_socket;
        setSender(this.client.getOutputStream());
        setReceiver(this.client.getInputStream());
        this.factory = new GameFactory();
        this.disconnectBehaviour = null; 
    }
    //overloading
    public Handler_Task(Socket client_socket,TCPServer disconnection) throws Exception{
        super();
        this.client = client_socket;
        setSender(this.client.getOutputStream());
        setReceiver(this.client.getInputStream());
        this.factory = new GameFactory();
        this.disconnectBehaviour = disconnection; 
    }
    //overloading
    public Handler_Task(Socket client_socket,String delimiter) throws Exception{
        super();
        this.client = client_socket;
        setSender(this.client.getOutputStream());
        setReceiver(this.client.getInputStream());
        this.factory = new GameFactory(); 
    }
    //implementazione della task
    public void run(){
        System.out.println("client connesso");
        String client_response = new String("");
        //Scegliere se generare solo il mostro o anche il player ogni round, attualmente si generano entrambi ogni round
        //scelta effettuata: rimane il solito player per simulare un roguelike
        //genero il player
        Player p1 = factory.createPlayer();
        try{
            //finchè il player vuole giocare e non muore
            while(true){
                //genero il mostro
                Monster m1 = factory.createMonster();
                this.sendMessage("Benvenuto o mio eroe aiutami a sconfiggere il mostro che ti si para davanti agli occhi\nPer quanto ti riguarda hai "+p1.getHP()+" punti ferita e "+p1.getPotionLeft()+" pozioni rimanenti");
                System.out.println(m1.getHP());
                //ciclo finchè non muore il mostro
                while(m1.isAlive()){
                    this.sendMessage("Al mostro rimangono "+m1.getHP()+" punti ferita");
                    String client_input = this.receiveMessage();
                    System.out.println(client_input);
                    switch (client_input) {
                        case "Attacca":
                            int[] globalLoss = p1.attackMonster(m1);
                            client_response = "Hai attaccato il mostro ed inflitto "+globalLoss[1]+" danni ai suoi punti vita\n"+"Tuttavia nel farlo hai perso: "+globalLoss[0]+" HP";
                            break;
                        case "Bevi":
                            if(p1.getPotionLeft()<=0)client_response = "Hai terminato le pozioni";
                            else client_response = "Hai bevuto una pozione ed hai recuperato: "+p1.drinkPotion()+" HP";
                            break;
                        case "Termina":
                            client_response = "Che peccato, Il mostro ti ha impaurito!";
                            //System.out.println(client_response);
                            this.sendMessage(client_response);
                            this.disconnectBehaviour.onClientDisconnect(this.client);
                            return;
                        default:
                            client_response = "Comando non riconosciuto, riprova";
                            break;
                    }
                    if(p1.isAlive()){
                        client_response += "\nTi rimangono "+p1.getHP()+" punti ferita e "+p1.getPotionLeft()+" pozioni di cura"; 
                    }else{
                        client_response += "\nIl mostro ti ha ucciso";
                        this.sendMessage(client_response);
                        System.out.println(client_response);
                        this.disconnectBehaviour.onClientDisconnect(this.client);
                        return;
                    }
                    this.sendMessage(client_response);
                    
                }
                this.sendMessage(client_response+"\nIl mostro è stato sconfitto Congratulazioni!!\nPer continuare a giocare digitare si");
                client_response = this.receiveMessage();
                if(!client_response.equals("si")){
                    System.out.println(client_response+"risposta client");
                    this.disconnectBehaviour.onClientDisconnect(this.client);
                    return;
                }   
            }
        }
        catch(NullPointerException e){
            System.out.println("il client ha terminato la connessione con un sigint");
            this.disconnectBehaviour.onClientDisconnect(client);
        }
        catch(Exception e){
            System.err.println(e.getClass());
        }
    }
}
