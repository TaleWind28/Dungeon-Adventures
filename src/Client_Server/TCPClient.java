package Client_Server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import Server_Task.Communication_Protocol;

public abstract class TCPClient extends Communication_Protocol{
    public int port;
    public String ip;
    public Scanner userInput;
    private Thread receiverThread;


    public TCPClient(String IP,int PORT){
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
            //imposto receiver, sender e senderThread
            setReceiver(sock.getInputStream());
            setSender(sock.getOutputStream());
            this.setReceiverThread();
            //attivo il thread di ricezione
            this.receiverThread.start();
            //faccio eseguire il comportamento di invio dal main thread
            this.sendBehaviour();
        } 
        catch (ConnectException e) {
            //System.out.println(e.getClass());
            System.out.println("Il server al momento non è disponibile :( , ci scusiamo per il disagio ");
            System.exit(0);
        }
        catch(IOException e){
            System.out.println("C'è stato un'errore sulla lettura dal socket");
            System.exit(0);;

        }
        catch(Exception e ){
            System.out.println("eccecion: "+e.getClass()+" : "+e.getStackTrace());
        }
        
    }

    //defattorizzare perchè incapsula troppo la logica del singolo client, salvo client MultiThread
    public void dial(){
        String message;
        try (Socket sock = new Socket(this.ip,this.port)) {
            setReceiver(sock.getInputStream());
            setSender(sock.getOutputStream());
            //stampa di benvenuto
            System.out.println(this.receiveMessage());
            //this.availabilityTask = this.availabilityChecker.schedule(()->{if(sock.isClosed()){System.out.println("Il socket è stato chiuso correttamente");System.exit(0);;};}, this.availableTime, TimeUnit.MILLISECONDS);
            while(true){
                //System.out.println("inserisci il messaggio da inviare al server");
                message = this.userInput.nextLine();
                if (message.equals("")){
                    break;
                }
                //invio il messaggio
                this.sendMessage(message);

                //System.out.println("messaggio inviato correttamente, attendiamo risposta");
                String serverResponse = this.receiveMessage();
                System.out.println(serverResponse);
                if(serverResponse.contains("Il mostro è stato sconfitto Congratulazioni!!")){
                    message = this.userInput.nextLine();
                    this.sendMessage(message);
                    if (!message.equals("si")){
                        return;
                    }
                }
                if(serverResponse.contains("Che peccato, Il mostro ti ha sconfitto!") || serverResponse.contains("Il mostro ti ha ucciso")){
                    System.out.println("partita terminata");
                    return;
                }
            }   
            
        
        } catch (SocketException e) {
            System.out.println("Connessione col Server Interrotta");
            return;
        }catch(Exception e){

        }
    }

    public abstract void sendBehaviour();

    public abstract void receiveBehaviour();

}
