package view;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class GUI implements UI{
    public static void main(String[] args){
        GUI gui = new GUI();
        gui.run();
    }

    @Override
    public void run() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        JLabel usernameLabel = new JLabel("Username: ");
        JTextField usernameInput = new JTextField("", 20);
        JButton button = new JButton("Submit");
        setDefaultSize(24);
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        panel.add(usernameLabel);
        panel.add(usernameInput);
        panel.add(button);
        frame.add(panel);

        //Display the window.
        frame.setVisible(true);
    }

    public static void setDefaultSize(int size) {

        Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
        Object[] keys = keySet.toArray(new Object[keySet.size()]);

        for (Object key : keys) {

            if (key != null && key.toString().toLowerCase().contains("font")) {

                System.out.println(key);
                Font font = UIManager.getDefaults().getFont(key);
                if (font != null) {
                    font = font.deriveFont((float)size);
                    UIManager.put(key, font);
                }

            }

        }

    }

}