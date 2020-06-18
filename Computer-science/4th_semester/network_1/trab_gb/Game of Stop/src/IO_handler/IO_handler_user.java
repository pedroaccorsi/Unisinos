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

    @Override
    public Object read_obj(String text) throws IOException {
        return null;
    }

    @Override
    public Object read_obj() throws IOException {
        return null;
    }

    public String read(String text) throws IOException {
        System.out.println(text);
        return this.Input.readLine();
    }

    public void write (String output) throws IOException {}

    @Override
    public void write(Object message) throws IOException {

    }

    ; //null implementation
}
