package IO_handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IO_handler{
    public static final String EOF  = "\n";
    public String read(String text)     throws IOException;
    public String read()                throws IOException;
    public Object read_obj(String text) throws IOException, ClassNotFoundException;
    public Object read_obj()            throws IOException, ClassNotFoundException;
    public void write(String message)   throws IOException;
    public void write(Object message)   throws IOException;
}
