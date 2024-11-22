import Client_Server.TCPServer;

public class Server {
    public static void main(String[] args) throws Exception {
        TCPServer server = new TCPServer(20000,16);
        server.dial();
        System.out.println("passato pool");
        return;
    }
}
