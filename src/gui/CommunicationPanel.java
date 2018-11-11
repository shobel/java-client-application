package gui;

import javax.swing.*;
import java.awt.*;

public class CommunicationPanel extends JPanel {

    private Dimension panelSize = new Dimension(600, 350);
    private JTextArea textArea;
    private boolean isFirstMessage = true;

    public CommunicationPanel(){

        this.setSize(panelSize);
        this.setPreferredSize(panelSize);
        this.setMaximumSize(panelSize);

        textArea = new JTextArea();
        //textArea.setSize(panelSize);
        //textArea.setPreferredSize(panelSize);
        textArea.setEditable(false);
        textArea.setText("Data sharing traffic will appear here...");
        textArea.setBackground(ClientColors.backgroundColor);
        textArea.setForeground(ClientColors.textColor);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        textArea.setMargin( new Insets(20,20,20,20) ); // tt is JTextArea instance

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setSize(panelSize);
        scrollPane.setPreferredSize(panelSize);
        scrollPane.setMaximumSize(panelSize);
        TextLineNumber tln = new TextLineNumber(textArea);
        tln.setBackground(ClientColors.backgroundColor);
        tln.setForeground(ClientColors.textColor);
        tln.setBorder(null);
        scrollPane.setRowHeaderView(tln);
        scrollPane.setBorder(null);
        this.add(scrollPane);
    }

    public void addMessage(String message) {
        if (isFirstMessage) {
            textArea.setText("");
        }
        textArea.insert(message + "\n", 0);
        isFirstMessage = false;
    }
}
