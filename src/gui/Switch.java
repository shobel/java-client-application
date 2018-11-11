package gui;

import dabi.DABIClient;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;

public class Switch extends JComponent implements MouseListener {

    private boolean OnOff = false;
    private int MARGIN = 5;
    private final int BORDER = 4;
    private Color backgroundColor;
    private Color buttonColor;
    private Color DISABLED_COMPONENT_COLOR = new Color( 131,131,131);
    private Dimension size = new Dimension(100, 50);

    public Switch() {
        super();
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setVisible(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(Switch.this);
        // initial colors
        setBackgroundColor(new Color(75, 216, 101));
        setButtonColor(new Color(255, 255, 255));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isOpaque()) { // Paint the background of component
            g2.setColor(getBackground());
            g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        }

        if (isEnabled()) { // component enabled
            g2.setColor(((OnOff) ? getBackgroundColor() : new Color(216, 217, 219)));
            g2.fill(new RoundRectangle2D.Double((float) MARGIN, (float) MARGIN,
                    (float) getWidth() - MARGIN * 2, (float) getHeight() - MARGIN * 2,
                    getHeight() - MARGIN * 2, getHeight() - MARGIN * 2));
        } else { // component disabled
            g2.setColor(DISABLED_COMPONENT_COLOR);
            g2.draw(new RoundRectangle2D.Double((float) MARGIN, (float) MARGIN,
                    (float) getWidth() - MARGIN * 2, (float) getHeight() - MARGIN * 2,
                    getHeight() - MARGIN * 2, getHeight() - MARGIN * 2));
        }

        g2.setColor((isEnabled()) ? getButtonColor() : DISABLED_COMPONENT_COLOR);
        // circular button
        if (OnOff) { // ON to the left
            g2.fillOval(MARGIN + BORDER / 2, MARGIN + BORDER / 2,
                    getHeight() - MARGIN * 2 - BORDER, getHeight() - MARGIN * 2 - BORDER);
        } else { // OFF to the right
            g2.fillOval(getWidth() - getHeight() + MARGIN + BORDER / 2, MARGIN + BORDER / 2,
                    getHeight() - MARGIN * 2 - BORDER, getHeight() - MARGIN * 2 - BORDER);
        }
    }

    public boolean isOnOff() {
        return OnOff;
    }

    public void setOnOff(boolean OnOff) {
        this.OnOff = OnOff;
        if (OnOff) {
            DABIClient.getDABIClient().establishConnectionToServer();
        } else {
            DABIClient.getDABIClient().disconnectFromServer();
        }
    }

    public void setOnOffVisuals(boolean OnOff) {
        this.OnOff = OnOff;
        repaint();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isEnabled()) {
            setOnOff(!OnOff);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {  /*...*/ }

    @Override
    public void mouseReleased(MouseEvent e) {  /*...*/ }

    @Override
    public void mouseEntered(MouseEvent e) {  /*...*/ }

    @Override
    public void mouseExited(MouseEvent e) {  /*...*/ }

} // Switch: end