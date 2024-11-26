package Client_Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface CommunicationProtocol {
    //istanzio lo stream per la ricezione dei messaggi
    public void setReceiver(InputStream input);
    //istanzio lo stream per l'invio dei messaggi
    public void setSender(OutputStream output);
    //definisco come invio i messaggi
    public int sendMessage(Message message);
    //definisco come ricevo i messaggi
    public Message receiveMessage();
}
