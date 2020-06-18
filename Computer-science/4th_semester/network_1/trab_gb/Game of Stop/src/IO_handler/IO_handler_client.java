package IO_handler;

import java.io.*;
import java.net.Socket;

public class IO_handler_client implements IO_handler {

    private BufferedReader Input;
    private DataOutputStream Output;
    private ObjectInputStream Input_obj;
    private ObjectOutputStream Output_obj;

    public IO_handler_client(Socket socketConnection) throws IOException {
        this.Input  = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
        this.Output = new DataOutputStream(socketConnection.getOutputStream());
        this.Input_obj = new ObjectInputStream(socketConnection.getInputStream());
        this.Output_obj = new ObjectOutputStream(socketConnection.getOutputStream());
    }

    @Override
    public String read() throws IOException {
        return this.Input.readLine();
    }

    @Override
    public String read(String text) throws IOException {
        System.out.println(text);
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
    public void write(String output) throws IOException {
        this.Output.flush();
        this.Output.writeBytes(output + this.EOF);
        this.Output.flush();
    }

    @Override
    public void write(Object message) throws IOException {
        this.Output_obj.flush();
        this.Output_obj.writeObject(message);
        this.Output_obj.flush();
    }

}