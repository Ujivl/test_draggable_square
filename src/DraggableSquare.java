import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DraggableSquare extends JPanel {
    private int squareX = 50, squareY = 50;  // Initial position of the square
    private int squareSize = 60;             // Initial size of the square
    private boolean dragging = false;        // Flag for dragging state
    private int offsetX, offsetY;            // Mouse offset during dragging
    private Timer enlargeTimer;              // Timer for enlarging the square
    private final int maxSquareSize = 80;   // Maximum size the square can grow to

    public DraggableSquare() {
        // Mouse listeners to handle dragging and enlarging the square
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Check if the click is within the square
                if (e.getX() >= squareX && e.getX() <= squareX + squareSize &&
                    e.getY() >= squareY && e.getY() <= squareY + squareSize) {

                    dragging = true;
                    offsetX = e.getX() - squareX;
                    offsetY = e.getY() - squareY;

                    // Start enlarging the square when clicked
                    enlargeTimer = new Timer(100, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            // Enlarge the square gradually up to the max size
                            if (squareSize < maxSquareSize) {
                                squareSize += 10;
                                repaint();  // Repaint to reflect the new size
                            }
                        }
                    });
                    enlargeTimer.start();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;

                // Stop enlarging the square when mouse is released
                if (enlargeTimer != null) {
                    enlargeTimer.stop();
                }

                // Reset the square to its original size
                squareSize = 50;
                repaint();  // Repaint to reflect the reset size
            }
        });

        // Mouse motion listener for dragging the square
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    // Update square position based on mouse movement
                    squareX = e.getX() - offsetX;
                    squareY = e.getY() - offsetY;
                    repaint();  // Repaint the square at its new position
                }
            }
        });
    }

    // Paint the square onto the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);  // Set square color
        g.fillRect(squareX, squareY, squareSize, squareSize);  // Draw square
    }
}
