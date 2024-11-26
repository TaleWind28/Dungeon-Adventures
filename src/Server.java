import java.net.ServerSocket;
import java.net.Socket;

import Communication.ProtocolServer;
import Communication.TCP;
import Server_Task.Handler_Task;

public class Server extends ProtocolServer{
    public Server(int port, int numThreads){
        super(port,numThreads);

    }
    public static void main(String[] args) throws Exception {
        ProtocolServer server = new Server(20000,16);
        server.setProtocol(new TCP());
        server.dial();
        System.out.println("passato pool");
        return;
    }
    
    public void dial(){
            while(true){
            try (ServerSocket server = new ServerSocket(this.PORT)) {
                Socket client_Socket = server.accept();
                //realizzare con factory per miglior versatilità
                Handler_Task task = new Handler_Task(client_Socket,this,this.protocol);
                addClient(client_Socket);
                this.pool.execute(task);
            } catch (Exception e) {
                System.out.println(e.getClass()+": "+e.getStackTrace());
            }
        }
    }
}
