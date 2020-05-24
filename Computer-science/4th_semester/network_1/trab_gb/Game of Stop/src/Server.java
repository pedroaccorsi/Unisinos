import Scanner.*;
import java.net.*;

public class Server {

    public static void main(String argv[]) throws Exception {

        ServerSocket socketReceive = new ServerSocket(6789);
        Socket socketConnect_01 = socketReceive.accept();
        Socket socketConnect_02 = socketReceive.accept();

        while(socketConnect_01 == null) { socketConnect_01 = socketReceive.accept(); }
        while(socketConnect_02 == null) { socketConnect_01 = socketReceive.accept(); }

        IO_handler Client_IO_01 = new IO_handler_client(socketConnect_01);
        IO_handler Client_IO_02 = new IO_handler_client(socketConnect_02);

        System.out.println();

        Client_IO_01.write( "Are you ready 01?" + "\n" );
        Client_IO_02.write( "Are you ready 02?" + "\n" );

        if(Client_IO_01.read() == "Y" && Client_IO_02.read() == "Y"){
            Client_IO_01.write( "então bora carai" + "\n" );
            Client_IO_02.write( "então bora carai" + "\n" );
        }
    }
}


