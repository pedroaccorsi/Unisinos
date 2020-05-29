import java.net.*;
import Constants.*;
import IO_handler.*;


public class Client {

    public static void main(String argv[]) throws Exception {

        Socket socketClient    = new Socket(Connection.LOCAL_HOST, Connection.PORT);
        ServerListenerHandler serverListenerHandlerHandler = new ServerListenerHandler(socketClient);
        IO_handler IO_server   = new IO_handler_server(socketClient);
        IO_handler IO_user     = new IO_handler_user();
        String     input       = "";

        input = IO_server.read();

        if(input.equals(Messages.sorry)){
            System.out.println(input);
            return;
        }

        if(input.equals(Messages.waiting)){
            System.out.println(Messages.waiting);
            input = IO_server.read();
        }

        System.out.println(Messages.ready);

        while (input.equals(Messages.yes)               == false &&
               input.equals(Messages.yes.toLowerCase()) == false)
            input = IO_user.read();

        Player player = new Player( IO_user.read("Type in your name: ") );

    }

}
