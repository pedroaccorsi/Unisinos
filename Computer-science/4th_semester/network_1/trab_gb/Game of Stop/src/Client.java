import Scanner.*;
import java.net.*;

public class Client {

    public static void main(String argv[]) throws Exception {

        Socket socketClient  = new Socket("127.0.0.1", 6789);
        IO_handler IO_server = new IO_handler_server(socketClient);
        IO_handler IO_user   = new IO_handler_user();

        if(IO_server.read().startsWith("Are you ready")){
            IO_server.write("Y");
        }

        System.out.println(IO_server.read());
        socketClient.close();

    }

}
