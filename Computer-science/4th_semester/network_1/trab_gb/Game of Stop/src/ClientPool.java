import Constants.Messages;

import java.io.IOException;
import java.util.ArrayList;
public class ClientPool implements Runnable{

    private ArrayList<Client_handler> Clients;
    public ClientPool(ArrayList<Client_handler> arr){
        this.Clients = arr;
    }


    @Override
    public void run() {
        ask_if_ready();
        start_listening_string();
        wait_for_answer();
        send_game();
        //start_listening_object();
        start_listening_string();
        wait_for_stop();
        read_answers();
        set_winner();
    }

    private void ask_if_ready(){
        for(Client_handler client : this.Clients){
            try {
                client.client_io.write(Messages.ready);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void start_listening_string(){
        for(Client_handler client : this.Clients){
            client.clientListener.listenString();
        }
    }

    public void start_listening_object(){
        for(Client_handler client : this.Clients){
            client.clientListener.listenObject();
        }
    }

    private void wait_for_answer(){
        int num_of_clients = 0;
        int num_of_answers = 0;

        num_of_clients = this.Clients.size();

        while(num_of_answers != num_of_clients) {
            num_of_answers = 0;
            for (Client_handler client : this.Clients) {
                if(client.clientListener.hasInputString() == true)
                    ++num_of_answers;
            }
        }
    }

    private void send_game(){
        GameOfStop game = new GameOfStop();
        for(Client_handler client : this.Clients){
            try {
                client.client_io.write(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void wait_for_stop(){
        Boolean stop = false;
        while(stop == false) {
            for (Client_handler client : this.Clients) {
                if (client.clientListener.hasInputString() == true) {
                    try {
                        client.setAnswers(client.client_io.read());
                        client.stop = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stop = true;
                    for (Client_handler client_2 : this.Clients) {
                        if (client.equals(client_2) == false) {
                            try {
                                client_2.client_io.write(Messages.stop);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private void read_answers(){
        for( Client_handler client : this.Clients){
            if(client.stop == false){
                try {
                    client.setAnswers(client.client_io.read());
                    client.stop = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void set_winner(){
        ArrayList<Player> players = new ArrayList<Player>();

        for( Client_handler client : this.Clients){
            String ans = client.getAnswers();
            if(client.getAnswers() != "") {
                players.add( new Player(ans) );
            }
        }

        String winner = new WinnerCalculator(players).getWinner();

        try {
            for(Client_handler client : this.Clients){
                System.out.println(client.getName() + " - " + client.getAnswers());
                if(client.getName().equals(winner))
                    client.client_io.write("You win!!!");
                client.client_io.write( client.getName().equals(winner) ? "You win!!!" : "You lose!!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}