package Listener;

import IO_handler.*;

import java.io.IOException;

public class ClientListener implements Listener{


    class ThreadClientListenerString implements Runnable{

        private IO_handler io_server;

        public ThreadClientListenerString(IO_handler io_server) throws IOException{
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

    class ThreadClientListenerObject implements Runnable{

        private IO_handler io_server;

        public ThreadClientListenerObject(IO_handler io_server) throws IOException{
            this.io_server = io_server;
        }

        @Override
        public void run(){
            try {
                try {
                    Object dummy_obj = this.io_server.read_obj();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Thread clientListenerString;
    private Thread ClientListenerObject;
    private IO_handler io_client;

    public ClientListener(IO_handler io_client) throws IOException {
        this.io_client = io_client;
        this.clientListenerString = new Thread(new ThreadClientListenerString(io_client));
        this.ClientListenerObject = new Thread(new ThreadClientListenerObject(io_client));
    }

    private void resetStringListener(){
        try {
            this.clientListenerString = new Thread(new ThreadClientListenerString(io_client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetObjectListener(){
        try {
            this.ClientListenerObject = new Thread(new ThreadClientListenerObject(io_client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listenString() {
        if(this.clientListenerString.isAlive() == false){
            resetStringListener();
        }
        this.clientListenerString.start();

    }

    @Override
    public void waitForInputString() {
        if(this.clientListenerString.isAlive() == false){
            resetStringListener();
        }
        this.clientListenerString.start();
        while(this.clientListenerString.isAlive()){}
    }

    @Override
    public boolean hasInputString() {
        return  this.clientListenerString.isAlive() == false ?  true : false;
    }

    @Override
    public void listenObject() {
        if(this.ClientListenerObject.isAlive() == false){
            resetObjectListener();
        }
        this.ClientListenerObject.start();
    }

    @Override
    public void waitForInputObject() {
        if(this.ClientListenerObject.isAlive() == false){
            resetObjectListener();
        }
        this.ClientListenerObject.start();
        while(this.ClientListenerObject.isAlive()){}
    }

    @Override
    public boolean hasInputObject() {
        return  this.ClientListenerObject.isAlive() == false ?  true : false;
    }

}
