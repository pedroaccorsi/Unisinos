import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.SortedMap;

import Constants.*;

public class Server {

    private static ArrayList<Client_handler> Clients = new ArrayList<Client_handler>();

    private static void set_clients(ServerSocket socketReceive) throws IOException {
        for(int i=1; i<=Connection.MAX_CLIENTS; i++){
            Socket socket = socketReceive.accept();
            Clients.add( new Client_handler(socket) );
        }
    }
    private static void set_players(){
        for (Client_handler client : Clients) {
            client.write("Are you ready?");
            client.set_player( client.read() );
        }
    }
    private static void say_hi_to_players(){
        for (Client_handler client : Clients) {
            client.write("Hi " + client.get_player() + "!");
        }
    }


    public static void main(String argv[]) throws Exception {

        ServerSocket socketReceive = new ServerSocket(Connection.PORT);

        set_clients(socketReceive);
        set_players();
        say_hi_to_players();
        while(true){}

    }
}


