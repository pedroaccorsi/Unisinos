package Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO_handler_user implements IO_handler {

    private BufferedReader Scanner_from_user;

    public IO_handler_user() throws IOException {
        this.Scanner_from_user = new BufferedReader(new InputStreamReader(System.in));
    }

    public String read() throws IOException {
        return this.Scanner_from_user.readLine();
    }

    public void write (String input) throws IOException {}; //null implementation
}
