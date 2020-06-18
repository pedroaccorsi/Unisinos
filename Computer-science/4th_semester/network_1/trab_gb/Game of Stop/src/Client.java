import java.net.*;
import java.util.ArrayList;

import Listener.*;
import Constants.*;
import IO_handler.*;

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

        //Let server know we're ready
        IO_server.write(input);

        //Get game from server
        GameOfStop Game = (GameOfStop) (IO_server.read_obj());
        Game.setName(
            IO_user.read("Type in your name: ")
        );

        System.out.println("OK! Bora jogar! Responda no padrão 'Categoria: Resposta'");
        System.out.println(Game.toString());

        serverListener.listenString();

        while(serverListener.hasInputString() == false && Game.isStop() == false){
            Game.AddNewAnswer(
                IO_user.read()
            );
        }

        //Send final answers
        if(Game.isStop() == true){
            System.out.println("Sending your answers!");
        }else{
            System.out.println("Someone hit STOP! Sending your answers!");
        }

        IO_server.write(Game.getAnswers());
        IO_server.write(Game.getAnswers());

        while(true){}

    }

}
