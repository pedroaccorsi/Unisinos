import IO_handler.*;
import Listener.*;

import java.io.IOException;
import java.net.*;

public class Client_handler {

    private IO_handler client_io        ;
    private Player     player           ;
    private Listener   serverListener   ;

    public Client_handler(Socket socket) throws IOException {
        this.client_io      = new IO_handler_client(socket);
        this.serverListener = new ClientListener(this.client_io);
    }

    public void write(String input){
        try {
            this.client_io.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(){
        try {
            return this.client_io.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void waitForInput(){
        this.serverListener.waitForInput();
    }

    public boolean hasInput(){
        return this.serverListener.hasInput();
    }

    public void listen(){
        this.serverListener.listen();
    }
    
    public void set_player(String name){
        this.player = new Player(name);
    }
    public String get_player(){ return this.player.get_name(); }
}
