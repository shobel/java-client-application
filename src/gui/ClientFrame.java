package gui;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {

    private Dimension frameSize = new Dimension(600, 600);
    private ConnectionPanel connectionPanel;
    private CommunicationPanel communicationPanel;

    public ClientFrame(){
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameSize);
        setPreferredSize(frameSize);
        setResizable(false);
        setTitle("DABI CLIENT");

        BoxLayout bl = new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS);
        this.setLayout(bl);

        connectionPanel = new ConnectionPanel();
        communicationPanel = new CommunicationPanel();

        this.getContentPane().add(connectionPanel);
        this.getContentPane().add(communicationPanel);

        centerWindow();
        setVisible(true);
    }

    public void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    public void setConnected(boolean connected){
        connectionPanel.setConnectedStatus(connected);
    }

    public void handshaking(){
        connectionPanel.handshaking();
    }

    public void addMessage(String message) {
        communicationPanel.addMessage(message);
    }

}
