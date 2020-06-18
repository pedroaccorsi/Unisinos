import java.net.*;
import java.util.ArrayList;
import Constants.*;
public class Server {
    public static void main(String argv[]) throws Exception {
        ArrayList<Client_handler> Clients = new ArrayList<Client_handler>();
        ServerSocket socketReceive        = new ServerSocket(Connection.PORT);
        Thread clientPool                 = null;
        System.out.println("Starting server...");
        System.out.println("Waiting for players...");

        //infinitely waits for all players to join
        while(true){
            Socket socket = null;
            while( socket == null){ socket = socketReceive.accept(); }

            Clients.add( new Client_handler(socket) );

            int num_of_clients = Clients.size();
            //clients still can join
            if( num_of_clients < Connection.MAX_CLIENTS ){
                Clients.get(num_of_clients-1).client_io.write(Messages.waiting);
                System.out.println("One player just joined");
            }
            //last client to join
            else if(num_of_clients == Connection.MAX_CLIENTS){
                System.out.println("One player just joined");
                //max clients already joined
            } else {
                Clients.get(num_of_clients-1).client_io.write(Messages.sorry);
                Clients.remove(num_of_clients-1);
                System.out.println("One player was kicked due to server overload");
            }
            if(clientPool == null && num_of_clients == Connection.MAX_CLIENTS){
                ArrayList<Client_handler> arr = new ArrayList<Client_handler>();
                for(Client_handler client : Clients){
                    arr.add(client);
                }
                clientPool = new Thread(new ClientPool(arr));
                clientPool.start();
            }
        }
    }
}