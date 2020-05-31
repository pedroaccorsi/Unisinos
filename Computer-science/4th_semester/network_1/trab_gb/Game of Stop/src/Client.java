import java.net.*;
import Constants.*;
import IO_handler.*;
import Listener.Listener;
import Listener.ServerListener;


public class Client {

    public static void main(String argv[]) throws Exception {

        Socket socketClient     = new Socket(Connection.LOCAL_HOST, Connection.PORT);
        IO_handler IO_server    = new IO_handler_server(socketClient);
        Listener serverListener = new ServerListener(IO_server);
        IO_handler IO_user      = new IO_handler_user();
        String     input        = "";

        input = IO_server.read();

        //max player already playing
        if(input.equals(Messages.sorry)){
            System.out.println(input);
            return;
        }

        //waiting for other players
        if(input.equals(Messages.waiting)){
            System.out.println(Messages.waiting);
            input = IO_server.read();
        }

        //ask if player is ready to play
        System.out.println(Messages.ready);

        while (input.equals(Messages.yes)               == false &&
               input.equals(Messages.yes.toLowerCase()) == false)
            input = IO_user.read();

        //init player object with his/her name
        //Player player = new Player( IO_user.read("Type in your name: ") );

        IO_server.write("to pronto rapaiz");

        input = IO_server.read();

        System.out.println(input);


    }

}
