import Client_Server.ProtocolServer;

public class Server {
    public static void main(String[] args) throws Exception {
        ProtocolServer server = new ProtocolServer(20000,16);
        server.dial();
        System.out.println("passato pool");
        return;
    }
}
