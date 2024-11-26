package Communication;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class TCP implements Protocol{
    protected Scanner receiver;
    protected PrintWriter sender;
    protected String delimiter;

    public TCP(){
        this.delimiter = "<END>";
    }

    public TCP(String delimiter){
        this.delimiter = delimiter;
    }

    //implementazione metodi interfaccia
    public void setReceiver(Socket receiver) {
        try{
            this.receiver = new Scanner(receiver.getInputStream());
            this.receiver.useDelimiter(this.delimiter);
        }
        catch(IOException e){
            System.out.println("Errore nel settare il receiver");
        }
    }

    public void setSender(Socket sender){
        try{
            this.sender = new PrintWriter(sender.getOutputStream(),true);
        }
        catch(IOException e){
            System.out.println("Errore nel settare il sender");
        }
        
    }

    public Message receiveMessage(){
        if (this.receiver!=null && receiver.hasNext()){
            Message msg = new Message(this.receiver.next().replaceAll(this.delimiter,"").trim());
            return msg;
        }else{
            return null;
        }
    }

    public int sendMessage(Message message){
        if (this.sender!=null){
            this.sender.println(message.payload+this.delimiter);
        }
        //this.sender.println(message);
        return 1;
    }
    
    //metodi classe concreta
    //probilmente è un di più perchè se voglio settare un delimiter uso il costruttore
    public void setDelimiter(String delimiter){
        this.delimiter = delimiter;
    }

    public String getDelimiter(){
        return this.delimiter;
    }

    public Scanner getReceiver() {
        return receiver;
    }

    public PrintWriter getSender() {
        return sender;
    }


}
