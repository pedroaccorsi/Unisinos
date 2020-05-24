package Scanner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class IO_handler_server implements IO_handler {

    private BufferedReader Scanner_from_server;
    private DataOutputStream Scanner_to_server;

    public IO_handler_server(Socket socketClient) throws IOException {
        this.Scanner_from_server = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        this.Scanner_to_server   = new DataOutputStream(socketClient.getOutputStream());
    }

    public String read() throws IOException {
        return this.Scanner_from_server.readLine();
    }

    public void write(String input) throws IOException {
        this.Scanner_to_server.writeBytes(input+ this.EOF);
    }
}