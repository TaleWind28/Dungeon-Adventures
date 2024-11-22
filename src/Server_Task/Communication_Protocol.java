package Server_Task;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.InputStream;

public class Communication_Protocol {
    protected Scanner receiver;
    protected PrintWriter sender;
    protected String delimiter;

    public Communication_Protocol(){
        this.delimiter = "<END>";
    }

    public Communication_Protocol(String delimiter){
        this.delimiter = delimiter;
    }

    public void setReceiver(InputStream receiver) {
        this.receiver = new Scanner(receiver);
        this.receiver.useDelimiter(this.delimiter);
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

    public String receiveMessage(){
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
}
