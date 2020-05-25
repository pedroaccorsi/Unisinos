import IO_handler.*;
import java.net.*;
import Constants.*;

public class Client {

    public static void main(String argv[]) throws Exception {

        Socket socketClient  = new Socket(Connection.LOCAL_HOST, Connection.PORT);
        IO_handler IO_server = new IO_handler_server(socketClient);
        IO_handler IO_user   = new IO_handler_user();

        while(true){
            while(IO_server.read().equals("Are you ready?") == false){}

            System.out.println("What's your name?");
            Player player = new Player(IO_user.read());

            IO_server.write(player.get_name());
            System.out.println(IO_server.read());

        }

    }

}
