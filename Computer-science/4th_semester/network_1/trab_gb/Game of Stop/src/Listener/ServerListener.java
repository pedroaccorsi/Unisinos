package Listener;

import IO_handler.*;

import java.io.IOException;

public class ServerListener implements Listener{

     class ThreadServerListener implements Runnable{

        private IO_handler io_server;

        public ThreadServerListener(IO_handler io_server) throws IOException {
            this.io_server = io_server;
        }

        @Override
        public void run(){
            try {
                String dummy = this.io_server.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Thread serverListener;

    public ServerListener(IO_handler io_client) throws IOException {
        this.serverListener = new Thread(new  ThreadServerListener(io_client));
    }

    @Override
    public void listen(){
        this.serverListener.start();
    }

    @Override
    public void waitForInput(){
        if(this.serverListener.isAlive() == false){
            this.serverListener.start();
        }
        while(this.serverListener.isAlive()){}
    }

    @Override
    public boolean hasInput(){
        return this.serverListener.isAlive() == false ?  true : false;
    }

}
