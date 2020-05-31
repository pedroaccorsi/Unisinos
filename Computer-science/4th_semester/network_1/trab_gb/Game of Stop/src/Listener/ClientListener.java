package Listener;

import IO_handler.*;

import java.io.IOException;

public class ClientListener implements Listener {

    class ThreadClientListener implements Runnable{

        private IO_handler io_client;

        public ThreadClientListener(IO_handler io_client) throws IOException{
            this.io_client = io_client;
        }

        @Override
        public void run(){
            try {
                String dummy = this.io_client.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Thread clientListener;

    public ClientListener(IO_handler io_server) throws IOException {
        this.clientListener = new Thread(new ThreadClientListener(io_server));
    }

    @Override
    public void listen(){
        this.clientListener.start();
    }

    @Override
    public void waitForInput(){
        if(this.clientListener.isAlive() == false){
            this.clientListener.start();
        }
        while(this.clientListener.isAlive()){}
    }

    @Override
    public boolean hasInput(){
        return this.clientListener.isAlive() == false ?  true : false;
    }
}
