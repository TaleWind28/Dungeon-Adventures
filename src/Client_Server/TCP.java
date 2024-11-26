package Client_Server;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class TCP implements CommunicationProtocol{
    protected Scanner receiver;
    protected PrintWriter sender;
    protected String delimiter;

    public TCP(){
        this.delimiter = "<END>";
    }

    public TCP(String delimiter){
        this.delimiter = delimiter;
    }

    public void setReceiver(InputStream receiver) {
        this.receiver = new Scanner(receiver);
        this.receiver.useDelimiter(this.delimiter);
    }

    //Pensare se preferire questo a Strategy per cambiare tipo di protocollo
    public void setReceiver(ByteArrayOutputStream receiver){
        System.out.println("ciao");
    }

    public void setSender(OutputStream sender){
        this.sender = new PrintWriter(sender,true);
    }

    //probilmente è un di più perchè se voglio settare un delimiter uso il costruttore
    public void setDelimiter(String delimiter){
        this.delimiter = delimiter;
    }

    public Scanner getReceiver() {
        return receiver;
    }

    public PrintWriter getSender() {
        return sender;
    }

    public String getDelimiter(){
        return this.delimiter;
    }

    

    public Message receiveMessage(){
        if (this.receiver!=null && receiver.hasNext()){
            Message msg = new Message(this.receiver.next().replaceAll(this.delimiter,"").trim());
            return msg;
        }else{
            return null;
        }
    }
    
    public String receiveStringMessage(){
        if (this.receiver!=null && receiver.hasNext()){
            return this.receiver.next().replaceAll(this.delimiter,"").trim();
        }else{
            return null;
        }
    }

    public int sendMessage(String message){
        if (this.sender!=null){
            this.sender.println(message+this.delimiter);
        }
        //this.sender.println(message);
        return 1;
    }

    public int sendMessage(Message message){
        if (this.sender!=null){
            this.sender.println(message.payload+this.delimiter);
        }
        //this.sender.println(message);
        return 1;
    }

}
