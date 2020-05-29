package IO_handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class IO_handler_server implements IO_handler {

    private BufferedReader Input;
    private DataOutputStream Output;

    public IO_handler_server(Socket socketClient) throws IOException {
        this.Input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        this.Output = new DataOutputStream(socketClient.getOutputStream());
    }

    public String read() throws IOException {
        return this.Input.readLine();
    }

    public String read(String input) throws IOException {
        System.out.println(input);
        return this.Input.readLine();
    }
    public void write(String input) throws IOException {
        this.Output.writeBytes(input+ this.EOF);
    }

}