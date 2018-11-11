package network;

import common.Constants;
import dabi.DABIClient;
import shared.Request;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.spec.ECField;

public class NetworkManager implements Runnable{

    private Socket clientSocket;
    private NetworkReceiver networkReceiver;
    private NetworkSender networkSender;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Long establishConnectionRequestId;
    private int retries = 0;

    public void run(){
        connectToServer();
    }

    public void connectToServer() {
        try {
            DABIClient.getDABIClient().handshaking();
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(Constants.DABIServerIP, Constants.DABIServerPort), 1000);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            retries = 0;
            System.out.println("Connected to server socket");
            networkReceiver = new NetworkReceiver(this, in);
            networkSender = new NetworkSender(this, out);

            networkSender.connectToServer();
            listenForRequests();
        } catch (SocketTimeoutException e) {
            DABIClient.getDABIClient().logError("ERROR: DABI Server not responding");
        } catch (IOException e) {
            if (e.getMessage().equals("Connection reset")) {
                DABIClient.getDABIClient().logError("ERROR: Connection reset, retrying in a second...");
                if (retries < 3) {
                    retries++;
                    ActionListener listener = new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            connectToServer();
                        }
                    };
                    Timer timer = new Timer(1000, listener);
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    DABIClient.getDABIClient().logError("retries: " + retries);
                }
            } else {
                connectionError("ERROR: Unable to connect to DABI. Contact administrators.");
            }
        }
    }

    public void connectionError(String error) {
        disconnectFromServer();
        DABIClient.getDABIClient().logError(error);
        DABIClient.getDABIClient().updateDisconnectGui();
    }

    public void disconnectFromServer(){
        try {
            clientSocket.close();
        } catch (Exception e) {}
        try {
            in.close();
        } catch (Exception e){}
        try {
            out.close();
        } catch (Exception e) {}
    }

    public void listenForRequests(){
        Thread receiverThread = new Thread(networkReceiver);
        receiverThread.start();
    }

    public void requestReceived(Request request){
        DABIClient.getDABIClient().requestReceived(request);
    }

    public void connectionToServerEstablished(){
        DABIClient.getDABIClient().connectionToServerEstablished();
    }

    public long getEstablishConnectionRequestId(){
        return establishConnectionRequestId;
    }

    public void setEstablishConnectionRequestId(Long id){
        this.establishConnectionRequestId = id;
    }
}
