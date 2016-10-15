package cz.borec.demo;

import javax.swing.SwingUtilities;

import cz.borec.demo.gui.Frame;
 
public class Main
{
    public static void main(String[] args)
    {
        // schedule this for the event dispatch thread (edt)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                displayJFrame();
            }
        });
    }
 
    static void displayJFrame()
    {
        Frame frame = new Frame();
        frame.pack();
        frame.setVisible(true);
    }
}