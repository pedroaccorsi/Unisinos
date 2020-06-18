package IO_handler;

import java.io.*;
import java.net.Socket;

public class IO_handler_server implements IO_handler {

    private BufferedReader Input;
    private DataOutputStream Output;
    private ObjectInputStream Input_obj;
    private ObjectOutputStream Output_obj;

    public IO_handler_server(Socket socketClient) throws IOException {
        this.Input  = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        this.Output = new DataOutputStream(socketClient.getOutputStream());
        this.Output_obj = new ObjectOutputStream(socketClient.getOutputStream());
        this.Input_obj = new ObjectInputStream(socketClient.getInputStream());
    }

    @Override
    public String read() throws IOException {
        return this.Input.readLine();
    }

    @Override
    public Object read_obj(String text) throws IOException, ClassNotFoundException {
        System.out.println(text);
        return this.Input_obj.readObject();
    }

    @Override
    public Object read_obj() throws IOException, ClassNotFoundException {
        return this.Input_obj.readObject();
    }

    @Override
    public String read(String text) throws IOException {
        System.out.println(text);
        return this.Input.readLine();
    }

    @Override
    public void write(String output) throws IOException {
        this.Output.flush();
        this.Output.writeBytes(output + this.EOF);
        this.Output.flush();    }

    @Override
    public void write(Object message) throws IOException {
        this.Output_obj.flush();
        this.Output_obj.writeObject(message);
        this.Output_obj.flush();
    }

}