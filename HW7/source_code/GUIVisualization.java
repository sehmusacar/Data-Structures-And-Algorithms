import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUIVisualization extends JFrame {

    private List<Integer> times;

    public GUIVisualization(List<Integer> times, String title) {
        this.times = times;
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGraph(g2d);
    }

    private void drawGraph(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int labelPadding = 25;
        int pointWidth = 4;

        double xScale = ((double) width - (2 * padding) - labelPadding) / (times.size() - 1);
        double yScale = ((double) height - 2 * padding - labelPadding) / getMaxTime();

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);

        // Create hatch marks and grid lines for y-axis.
        for (int i = 0; i < 10 + 1; i++) {
            int x0 = padding + labelPadding;
            int y0 = height - ((i * (height - padding * 2 - labelPadding)) / 10 + padding + labelPadding);
            int y1 = y0;
            if (times.size() > 0) {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawLine(padding + labelPadding + 1 + pointWidth, y0, width - padding, y1);
                g2d.setColor(Color.BLACK);
                String yLabel = ((int) ((getMaxTime() * ((i * 1.0) / 10)) * 100)) / 100 + "";
                FontMetrics metrics = g2d.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2d.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
        }

        // Create hatch marks and grid lines for x-axis.
        for (int i = 0; i < times.size(); i++) {
            if (times.size() > 1) {
                int x0 = i * (width - padding * 2 - labelPadding) / (times.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = height - padding - labelPadding;
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawLine(x0, height - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2d.setColor(Color.BLACK);
                String xLabel = i + "";
                FontMetrics metrics = g2d.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2d.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
        }

        // Create x and y axes 
        g2d.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding);
        g2d.drawLine(padding + labelPadding, height - padding - labelPadding, width - padding, height - padding - labelPadding);

        // Draw data points
        g2d.setColor(Color.RED);
        for (int i = 0; i < times.size(); i++) {
            int x = (int) (i * xScale + padding + labelPadding);
            int y = (int) ((getMaxTime() - times.get(i)) * yScale + padding);
            g2d.fillOval(x - pointWidth / 2, y - pointWidth / 2, pointWidth, pointWidth);
        }
    }

    private int getMaxTime() {
        int maxTime = Integer.MIN_VALUE;
        for (int time : times) {
            maxTime = Math.max(maxTime, time);
        }
        return maxTime;
    }

    public static void createAndShowGui(List<Integer> times, String title) {
        GUIVisualization frame = new GUIVisualization(times, title);
        frame.setVisible(true);
    }
}
