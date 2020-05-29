package IO_handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO_handler_user implements IO_handler {

    private BufferedReader Input;

    public IO_handler_user() throws IOException {
        this.Input = new BufferedReader(new InputStreamReader(System.in));
    }

    public String read() throws IOException {
        return this.Input.readLine();
    }

    public String read(String output) throws IOException {
        System.out.println(output);
        return this.Input.readLine();
    }

    public void write (String input) throws IOException {}; //null implementation
}
