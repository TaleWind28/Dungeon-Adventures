package Communication;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public abstract class ProtocolClient extends CommunicationProtocol{
    public int port;
    public String ip;
    public Scanner userInput;
    private Thread receiverThread;


    public ProtocolClient(String IP,int PORT){
        this.port = PORT;
        this.ip = IP;
        this.userInput = new Scanner(System.in);
    }

    public void setReceiverThread(){
        this.receiverThread = new Thread(){public void run(){receiveBehaviour();}};
    }

    //dialogo col server sfruttando il multithreading
    public void multiDial(){
        //apro il socket
        try (Socket sock = new Socket(this.ip,this.port)) {
            this.setProtocol(new TCP());
            //imposto receiver, sender e senderThread
            this.protocol.setReceiver(sock);
            this.protocol.setSender(sock);
            this.setReceiverThread();
            //attivo il thread di ricezione
            this.receiverThread.start();
            //faccio eseguire il comportamento di invio dal main thread
            this.sendBehaviour();
        }
        //disconnessione accidentale
        catch (SocketException e) {
            //System.out.println(e.getClass());
            System.out.println("Il server al momento non è disponibile :( , ci scusiamo per il disagio ");
            System.exit(0);
        }
        //input errato
        catch(IOException e){
            System.out.println("C'è stato un'errore sulla lettura dal socket");
            System.exit(0);;

        }
        //eccezione generica
        catch(Exception e ){
            System.out.println("eccezione: "+e.getClass()+" : "+e.getStackTrace());
        }
        
    }

    public abstract void sendBehaviour();

    public abstract void receiveBehaviour();

}
