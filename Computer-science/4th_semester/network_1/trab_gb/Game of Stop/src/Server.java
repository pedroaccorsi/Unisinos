import IO_handler.*;
import java.net.*;
import Constants.*;

public class Server {

    public static void main(String argv[]) throws Exception {

        ServerSocket socketReceive = new ServerSocket(Connection.PORT);
        Socket socketConnect_01 = socketReceive.accept();

        while(true){
            IO_handler Client_IO_01 = new IO_handler_client(socketConnect_01);
            Client_IO_01.write(Client_IO_01.read().toUpperCase());
        }
    }
}


