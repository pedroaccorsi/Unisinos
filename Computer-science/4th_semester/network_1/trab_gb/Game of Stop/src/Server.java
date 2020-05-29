import java.net.*;
import java.util.ArrayList;

import Constants.*;

public class Server {

    public static void main(String argv[]) throws Exception {

        ArrayList<Client_handler> Clients = new ArrayList<Client_handler>();
        ServerSocket socketReceive        = new ServerSocket(Connection.PORT);

        System.out.println("Starting server...");

        //infinitely waits for all players to join
        while(true){

            Socket socket = null;
            while( socket == null){ socket = socketReceive.accept(); }

            int current_clients = Clients.size();

            //many clients still to join
            if( current_clients < Connection.MAX_CLIENTS - 1){
                Clients.add( new Client_handler(socket) );
                Clients.get(current_clients).write(Messages.waiting);
                System.out.println("One player just joined");
                continue;
            }

            //last client to join
            else if(current_clients < Connection.MAX_CLIENTS){
                Clients.add( new Client_handler(socket) );
                System.out.println("One player just joined");

            //max clients already joined
            } else {
                Clients.add( new Client_handler(socket) );
                Clients.get(current_clients).write(Messages.sorry);
                Clients.remove(current_clients);
                System.out.println("One player was kicked due to server overload");
                continue;
            }

            for (Client_handler client : Clients) {
                client.write(Messages.ready);
            }

            //start game probably in new thread
        }





    }
}


