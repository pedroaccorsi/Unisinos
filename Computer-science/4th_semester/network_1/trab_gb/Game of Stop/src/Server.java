import java.net.*;
import java.util.ArrayList;

import Constants.*;

public class Server {

    public static void main(String argv[]) throws Exception {

        ArrayList<Client_handler> Clients = new ArrayList<Client_handler>();
        ServerSocket socketReceive        = new ServerSocket(Connection.PORT);

        //infinitely waits for all players to join
        for(int i=1; i<=Connection.MAX_CLIENTS; i++){
            Socket socket = null;
            while( socket == null){ socket = socketReceive.accept(); }
            Clients.add( new Client_handler(socket) );
            if(i<Connection.MAX_CLIENTS){
                Clients.get(0).write("Waiting for other players to join...");
            }
        }

        //ask if all players are ready
        for (Client_handler client : Clients) {
            client.write(Messages.ready);
        }

        //start game?


    }
}


