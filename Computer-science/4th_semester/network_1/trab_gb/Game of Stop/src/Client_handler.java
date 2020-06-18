import IO_handler.*;
import Listener.*;

import java.io.IOException;
import java.net.*;
import java.util.Map;

public class Client_handler {

    public IO_handler client_io;
    private String name = "";
    public Listener clientListener;
    private GameOfStop Game;
    private String answers;
    public Boolean stop = false;

    public void setGame(GameOfStop Game) {
        this.Game = Game;
    }

    public GameOfStop getGame() {
        return this.Game;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
        this.name = answers.split(";")[0].split(":")[1];
    }

    public String getAnswers() {
        return this.answers;
    }

    public String getName(){
        return this.name;
    }

    public Client_handler(Socket socket) throws IOException {
        this.client_io = new IO_handler_client(socket);
        this.clientListener = new ClientListener(this.client_io);
    }

}
