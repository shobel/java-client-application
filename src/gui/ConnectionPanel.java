package gui;

import javax.swing.*;
import java.awt.*;

public class ConnectionPanel extends JPanel{

    private Dimension panelSize = new Dimension(800, 300);
    private Dimension statusPanelSize = new Dimension(600, 80);
    private Dimension titlePanelSize = new Dimension(600, 20);
    private Dimension switchPanelSize = new Dimension(600, 100);
    private Dimension switchSize = new Dimension(220, 80);

    JLabel statusLabel;
    Switch switchButton;

    public ConnectionPanel(){

        BoxLayout bl = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(bl);

        setSize(panelSize);
        setPreferredSize(panelSize);
        setMaximumSize(panelSize);

        setBackground(ClientColors.backgroundColor);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridBagLayout());
        statusPanel.setBackground(ClientColors.backgroundColor);
        statusPanel.setMaximumSize(statusPanelSize);
        statusLabel = new JLabel("DISCONNECTED");
        statusLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        statusLabel.setForeground(ClientColors.disconnectedColor);
        statusPanel.add(statusLabel);
        add(statusPanel);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(ClientColors.backgroundColor);
        titlePanel.setMaximumSize(titlePanelSize);
        JLabel connectLabel = new JLabel("Connection to DABI");
        connectLabel.setFont(new Font("Courier New", Font.PLAIN, 14));
        connectLabel.setForeground(ClientColors.backgroundColor);
        titlePanel.add(connectLabel);
        add(titlePanel);

        JPanel switchPanel = new JPanel();
        switchPanel.setMaximumSize(switchPanelSize);
        switchPanel.setBackground(ClientColors.backgroundColor);
        JLabel onLabel = new JLabel(" ON");
        JLabel offLabel = new JLabel("OFF");
        onLabel.setFont(new Font("Courier New", Font.BOLD, 24));
        offLabel.setFont(new Font("Courier New", Font.BOLD, 24));
        onLabel.setForeground(ClientColors.textColor);
        offLabel.setForeground(ClientColors.textColor);
        switchButton = new Switch();
        switchButton.setSize(switchSize);
        switchButton.setPreferredSize(switchSize);
        switchButton.setMaximumSize(switchSize);
        switchPanel.add(onLabel);
        switchPanel.add(switchButton);
        switchPanel.add(offLabel);
        add(switchPanel);
    }

    public void setConnectedStatus(boolean connected) {
        if (connected) {
            statusLabel.setText("CONNECTED");
            statusLabel.setForeground(ClientColors.connectedColor);
            switchButton.setOnOffVisuals(connected);
        } else {
            statusLabel.setText("DISCONNECTED");
            statusLabel.setForeground(ClientColors.disconnectedColor);
            switchButton.setOnOffVisuals(connected);
        }
    }

    public void handshaking(){
        statusLabel.setText("HANDSHAKING...");
        statusLabel.setForeground(ClientColors.handshakingColor);
    }
}
