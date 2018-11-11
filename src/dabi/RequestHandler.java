package dabi;

import shared.Request;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestHandler implements Runnable{

    private LinkedBlockingQueue<Request> requestQueue;
    private boolean continueToRun;

    public RequestHandler(){
        this.requestQueue = new LinkedBlockingQueue<Request>();
        this.continueToRun = true;
    }

    @Override
    public void run() {
        while (continueToRun) {
            try {
                System.out.println("Waiting for next request");
                Request request = requestQueue.take();
                handleRequest(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRequest(Request request) {
        try {
            System.out.println("Adding request " + request.getId());
            requestQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(Request request) {
        try {
            DABIClient.getDABIClient().requestHandling(request);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DABIClient.getDABIClient().requestHandled(request);
    }

    public void setContinueToRun(boolean value) {
        this.continueToRun = value;
    }
}
