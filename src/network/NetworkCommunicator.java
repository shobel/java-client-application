package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public abstract class NetworkCommunicator {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public long generateId(){
        return Math.abs(new Random().nextLong());
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
