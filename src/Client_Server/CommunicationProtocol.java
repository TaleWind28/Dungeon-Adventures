package Client_Server;

import java.net.Socket;

public interface CommunicationProtocol {
    //istanzio lo stream per la ricezione dei messaggi
    public void setReceiver(Socket input);
    //istanzio lo stream per l'invio dei messaggi
    public void setSender(Socket output);
    //definisco come invio i messaggi
    public int sendMessage(Message message);
    //definisco come ricevo i messaggi
    public Message receiveMessage();
}
