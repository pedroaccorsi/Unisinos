import IO_handler.*;

import java.io.IOException;
import java.net.Socket;

public class ServerListenerHandler {

     class ThreadServerListener implements Runnable {

        private Socket socket;
        private IO_handler Input;

        public ThreadServerListener(Socket socket) throws IOException {
            this.socket = socket;
            this.Input = new IO_handler_server(this.socket);
        }
        public void run(){
            try {
                String dummy = this.Input.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Thread serverListener;

    public ServerListenerHandler(Socket socket) throws IOException {
        this.serverListener = new Thread(new  ThreadServerListener(socket));
    }

    public void waitForInput(){
        if(this.serverListener.isAlive() == false){
            this.serverListener.start();
        }
        while(this.serverListener.isAlive()){}
    }

    public boolean hasInput(){
        return this.serverListener.isAlive() == false ?  true : false;
    }

}
