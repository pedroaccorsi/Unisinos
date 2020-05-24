import Scanner.*;
import java.net.*;
import Constants.*;

public class Client {

    public static void main(String argv[]) throws Exception {

        Socket socketClient  = new Socket(Connection.LOCAL_HOST, Connection.PORT);
        IO_handler IO_server = new IO_handler_server(socketClient);
        IO_handler IO_user   = new IO_handler_user();

        while(true){
            IO_server.write(IO_user.read());
            System.out.println(IO_server.read());
        }
    }

}
