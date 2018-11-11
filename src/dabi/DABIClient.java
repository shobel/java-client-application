package dabi;

import gui.ClientFrame;
import shared.Request;
import network.*;

public class DABIClient {

    private NetworkManager networkManager;
    private RequestHandler requestHandler;
    private ClientFrame clientFrame;
    private static DABIClient client = new DABIClient();
    private boolean connected;

    private DABIClient(){
        networkManager = new NetworkManager();
        requestHandler = new RequestHandler();
        clientFrame = new ClientFrame();
    }

    public static DABIClient getDABIClient(){
        return client;
    }

    public void establishConnectionToServer() {
        Thread thread = new Thread(networkManager);
        thread.start();
        //networkManager.connectToServer();
    }

    public void disconnectFromServer(){
        clientFrame.setConnected(false);
        clientFrame.addMessage("Disconnected from DABI server");
        networkManager.disconnectFromServer();
        connected = false;
    }

    public void handshaking(){
        clientFrame.handshaking();
    }

    public void connectionToServerEstablished(){
        connected = true;
        clientFrame.setConnected(true);
        clientFrame.addMessage("Connected to DABI server");

        Thread requestHandlerThread = new Thread(requestHandler);
        requestHandlerThread.start();
    }

    public void requestReceived(Request request){
        if (connected) {
            clientFrame.addMessage("Received request: " + request.getId() + " - " + request.getMessage());
            requestHandler.addRequest(request);
        }
    }

    public void requestHandling(Request request){
        if (connected) {
            clientFrame.addMessage("Handling request: " + request.getId() + " - " + request.getMessage());
        }
    }

    public void requestHandled(Request request){
        if (connected) {
            clientFrame.addMessage("Request complete: " + request.getId() + " - " + request.getMessage());
        }
    }

    public void logError(String error){
        clientFrame.addMessage(error);
    }

    public void updateDisconnectGui(){
        connected = false;
        clientFrame.setConnected(false);
    }

}
