package Scanner;

import java.io.IOException;

public interface IO_handler{
    public String read()            throws IOException;
    public void write(String input) throws IOException;
}
