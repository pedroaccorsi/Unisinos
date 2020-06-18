package Listener;

import IO_handler.*;

import java.io.IOException;

public class ServerListener implements Listener{


    class ThreadServerListenerString implements Runnable{

        private IO_handler io_client;

        public ThreadServerListenerString(IO_handler io_client) throws IOException{
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

    class ThreadServerListenerObject implements Runnable{

        private IO_handler io_client;

        public ThreadServerListenerObject(IO_handler io_client) throws IOException{
            this.io_client = io_client;
        }

        @Override
        public void run(){
            try {
                try {
                    Object dummy_obj = this.io_client.read_obj();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Thread serverListenerString;
    private Thread serverListenerObject;
    private IO_handler io_client;

    public ServerListener(IO_handler io_client) throws IOException {
        this.io_client = io_client;
        this.serverListenerString = new Thread(new ThreadServerListenerString(io_client));
        this.serverListenerObject = new Thread(new ThreadServerListenerObject(io_client));
    }

    private void resetStringListener(){
        try {
            this.serverListenerString = new Thread(new ThreadServerListenerString(io_client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetObjectListener(){
        try {
            this.serverListenerObject = new Thread(new ThreadServerListenerObject(io_client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listenString() {
        if(this.serverListenerString.isAlive() == false){
            resetStringListener();
        }
        this.serverListenerString.start();

    }

    @Override
    public void waitForInputString() {
        if(this.serverListenerString.isAlive() == false){
            resetStringListener();
        }
        this.serverListenerString.start();
        while(this.serverListenerString.isAlive()){}
    }

    @Override
    public boolean hasInputString() {
        return  this.serverListenerString.isAlive() == false ?  true : false;
    }

    @Override
    public void listenObject() {
        if(this.serverListenerObject.isAlive() == false){
            resetObjectListener();
        }
        this.serverListenerObject.start();
    }

    @Override
    public void waitForInputObject() {
        if(this.serverListenerObject.isAlive() == false){
            resetObjectListener();
        }
        this.serverListenerObject.start();
        while(this.serverListenerObject.isAlive()){}
    }

    @Override
    public boolean hasInputObject() {
        return  this.serverListenerObject.isAlive() == false ?  true : false;
    }

}
