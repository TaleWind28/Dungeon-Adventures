package Communication;

import java.util.concurrent.*;
import java.util.*;
import java.net.Socket;

public abstract class ProtocolServer extends CommunicationProtocol{
    public int PORT;
    public ExecutorService pool;
    //private int activeClients = 0;
    private final long CONNECTION_TIMEOUT = 10000;
    private ScheduledExecutorService timeoutScheduler;
    private List<Socket> activeClients;
    private ScheduledFuture<?> timeoutTask;

    public ProtocolServer(int port, int numThreads){
        this.PORT = port;
        this.pool = Executors.newFixedThreadPool(numThreads);
        this.activeClients = Collections.synchronizedList(new ArrayList<>());
        this.timeoutScheduler = Executors.newScheduledThreadPool(1);
    }

    public synchronized void onClientDisconnect(Socket client){
        System.out.println("Client disconnesso");
        activeClients.remove(client);
        try{
            client.close();
        }            
        catch (Exception e) {
            System.out.println(e.getClass()+": "+e.getStackTrace());   
        }
        if(activeClients.isEmpty()){
            System.out.println("Timeout Disconnessione iniziato");
            this.timeoutTask = timeoutScheduler.schedule(()->{System.out.println("Timeout scaduto, terminazione server");this.pool.shutdownNow();System.exit(0);}, CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        }
        
    }

    public synchronized void addClient(Socket clientSocket){
        activeClients.add(clientSocket);
        System.out.println("Client connesso. Client attivi: " + activeClients.size());

        // Cancella il timeout se ci sono client attivi
        if (timeoutTask != null) {
            timeoutTask.cancel(false);
            timeoutTask = null;
            System.out.println("Timeout Interrotto");
        }
    }

    //apre il socket e passa al threadpool i vari client
    public abstract void dial();
}
