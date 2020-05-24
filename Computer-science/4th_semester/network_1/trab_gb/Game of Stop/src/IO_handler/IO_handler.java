package IO_handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IO_handler{
    public static final String EOF  = "\n";
    public String read()            throws IOException;
    public void write(String input) throws IOException;
}
