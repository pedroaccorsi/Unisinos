import java.net.*;
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


        //ArrayList<Player> players = new ArrayList<Player>();
        //for(int i=0; i<4; i++){
        //    players.add( new Player(IO_user.read()) );
        //}
        //WinnerCalculator WinCalc = new WinnerCalculator(players);
        //System.out.println("O vencedor é: "+ WinCalc.getWinner());

        input = IO_server.read();

        //max player already playing
        if (input.equals(Messages.sorry)) {
            System.out.println(input);
            return;
        }

        //waiting for other players
        if (input.equals(Messages.waiting)) {
            System.out.println(Messages.waiting);
            input = IO_server.read();
        }

        //ask if player is ready to play
        System.out.println(Messages.ready);

        while (input.equals(Messages.yes) == false && input.equals(Messages.yes.toLowerCase()) == false)
            input = IO_user.read();

        //Let server know we're ready
        IO_server.write(input);

        //Get game from server
        GameOfStop Game = (GameOfStop) (IO_server.read_obj());
        Game.setName( IO_user.read("Type in your name: ") );

        System.out.println("OK! Bora jogar! Responda no padrão 'Categoria: Resposta'");
        System.out.println(Game.getRules());

        serverListener.listenString();

        while (serverListener.hasInputString() == false && Game.isStop() == false)
            Game.AddNewAnswer( IO_user.read() );

        System.out.println(Game.isStop() == true ? "Sending your answers!" : "Someone hit STOP! Sending your answers!");

        //Send final answers
        IO_server.write(Game.getAnswers());
        IO_server.write(Game.getAnswers());

        //Defining winner
        System.out.println("Calculating winner...");
        System.out.println(IO_server.read());

        socketClient.close();
    }

}