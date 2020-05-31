import Constants.Messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ClientPool implements Runnable{

    private ArrayList<Client_handler> Clients;

    public ClientPool(ArrayList<Client_handler> arr){
        this.Clients = arr;
    }


    @Override
    public void run(){
        ask_if_ready();
        start_listening();
        wait_for_answer();
        send_letter();
        while(true){}
    }

    private void ask_if_ready(){
        for(Client_handler client : this.Clients){
            client.write(Messages.ready);
        }

    }

    public void start_listening(){
        for(Client_handler client : this.Clients){
            client.listen();
        }
    }

    private void wait_for_answer(){
        int num_of_clients = 0;
        int num_of_answers = 0;

        num_of_clients = this.Clients.size();

        while(num_of_answers != num_of_clients) {
            num_of_answers = 0;
            for (Client_handler client : this.Clients) {
                if(client.hasInput() == true)
                    ++num_of_answers;
            }
        }

    }

    private void send_letter(){
        char letter = Character.toUpperCase(
                (char)(new Random().nextInt(26)+ 'a')
        );

        for(Client_handler client : this.Clients){
            client.write("Letter: " + letter);
        }
    }


}
