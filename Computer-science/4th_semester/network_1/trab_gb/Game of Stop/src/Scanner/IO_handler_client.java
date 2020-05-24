package Scanner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class IO_handler_client implements IO_handler {

    private BufferedReader   Scanner_from_client;
    private DataOutputStream Scanner_to_client;

    public IO_handler_client(Socket socketConnection) throws IOException {
        try {
            this.Scanner_from_client = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
            this.Scanner_to_client   = new DataOutputStream(socketConnection.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() throws IOException {
        return this.Scanner_from_client.readLine();
    }

    public void write(String input) throws IOException {
        this.Scanner_to_client.writeBytes(input);
    }

}