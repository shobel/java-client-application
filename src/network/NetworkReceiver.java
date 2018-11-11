package network;

import dabi.DABIClient;
import shared.Request;
import java.io.*;

public class NetworkReceiver extends NetworkCommunicator implements Runnable {

    private ObjectInputStream in;
    private NetworkManager networkManager;

    public NetworkReceiver(NetworkManager manager, ObjectInputStream in) {
        this.in = in;
        this.networkManager = manager;
    }

    public void run(){
        Object inputObject;
        try {
            while ((inputObject = in.readObject()) != null) {
                Request inputRequest = (Request) inputObject;
                long id = inputRequest.getId();
                String message = inputRequest.getMessage();
                System.out.println("Received message: " + message);
                if (id == networkManager.getEstablishConnectionRequestId()) {
                    networkManager.connectionToServerEstablished();
                } else if (".".equals(message)) {
                    break;
                } else {
                    networkManager.requestReceived(inputRequest);
                }
            }
            stopConnection();
        } catch (Exception e) {
            if (e.getMessage() == null || !e.getMessage().equals("Socket closed")) {
                networkManager.connectionError("ERROR: Unable to reach DABI server");
            }
        }
    }
}
