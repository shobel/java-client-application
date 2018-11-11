package network;

import shared.Request;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NetworkSender extends NetworkCommunicator {

    private ObjectOutputStream out;
    private NetworkManager networkManager;

    public NetworkSender(NetworkManager manager, ObjectOutputStream out) {
        this.out = out;
        this.networkManager = manager;
    }

    public void connectToServer(){
        networkManager.setEstablishConnectionRequestId(this.generateId());
        try {
            out.writeObject(new Request(networkManager.getEstablishConnectionRequestId(), "Initiate"));
        } catch (IOException e) {
            //TODO: Handle write error
            e.printStackTrace();
        }
    }

}
