package edu.cmu.hcii.paint;

public class PaintApp {

    public static void main(String[] args) {

        // Run on the Event Dispatch Thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create the main paint window with 800x600 initial size
                new PaintWindow(800, 600);
            }
        });

    }
}