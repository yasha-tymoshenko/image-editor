import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author Yakiv Tymoshenko
 * @since 06-Aug-17
 */
@SuppressWarnings("WeakerAccess")
public class ImageEdit extends JPanel {

    private AffineTransform moveAT;
    private AffineTransform rotateAT;
    private AffineTransform zoomAT;

    private final double step = 10;
    private BufferedImage image;
    private boolean firstTime = true;
    private AffineTransform notRotatedAT = AffineTransform.getRotateInstance(0);

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        ImageEdit imageEdit = new ImageEdit();
        ImageControls imageControls = new ImageControls(imageEdit);
        mainPanel.add(imageControls);
        mainPanel.add(imageEdit);
        frame.add(mainPanel);
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int frameWidth = getWidth();
        int frameX0 = frameWidth / 2;
        int frameHeight = getHeight();
        int frameY0 = frameHeight / 2;

        if (firstTime) {
            image = ImageLoader.loadImage("/Lady.jpg");
            BufferedImage thumbnail = new BufferedImage(800 / 5, 700 / 5, BufferedImage.TYPE_INT_RGB);
            Graphics2D imageG2 = thumbnail.createGraphics();
            AffineTransform scalingTransfor = AffineTransform.getScaleInstance(0.2, 0.2);
            imageG2.drawRenderedImage(image, scalingTransfor);
            imageG2.dispose();
            image = thumbnail;
            final int x0 = image.getWidth() / 2;
            final int y0 = image.getHeight() / 2;
            moveAT = AffineTransform.getTranslateInstance(frameX0 - x0, frameY0 - y0);
            rotateAT = AffineTransform.getRotateInstance(0);
            zoomAT = AffineTransform.getScaleInstance(1, 1);
            firstTime = false;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, frameWidth, frameHeight);

        g2.drawRenderedImage(image, moveAT);

        g2.setColor(Color.BLUE);
        int lineNumber = 1;
        for (int i = 0; i < frameWidth; i += 2 * step) {
            g2.drawString(String.valueOf(lineNumber), i, 10);
            g2.drawLine(i, 0, i, frameHeight);
            lineNumber++;
        }
        lineNumber = 1;
        for (int i = 0; i < frameHeight; i += 2 * step) {
            g2.drawString(String.valueOf(lineNumber), 0, i + 10);
            g2.drawLine(0, i, frameWidth, i);
            lineNumber++;
        }
    }

    public void moveUp() {
        double[] matrix = new double[6];
        moveAT.getMatrix(matrix);
        matrix[5] = matrix[5] - step;
        moveAT = new AffineTransform(matrix);

        //        moveAT.translate(0, -step / moveAT.getScaleY());
        System.out.println("MoveAT:");
        System.out.println(moveAT.toString());
        repaint();
    }

    public void moveDown() {
        double[] matrix = new double[6];
        moveAT.getMatrix(matrix);
        matrix[5] = matrix[5] + step;
        moveAT = new AffineTransform(matrix);
//        moveAT.translate(0, step / moveAT.getScaleY());
        System.out.println("MoveAT:");
        System.out.println(moveAT.toString());
        repaint();
    }

    public void moveLeft() {
        double[] matrix = new double[6];
        moveAT.getMatrix(matrix);
        matrix[4] = matrix[4] - step;
        moveAT = new AffineTransform(matrix);
//        moveAT.translate(-step / moveAT.getScaleX(), 0);
        System.out.println("MoveAT:");
        System.out.println(moveAT.toString());
        repaint();
    }

    public void moveRight() {
        double[] matrix = new double[6];
        moveAT.getMatrix(matrix);
        matrix[4] = matrix[4] + step;
        moveAT = new AffineTransform(matrix);
//        moveAT.translate(step / moveAT.getScaleX(), 0);
        System.out.println("MoveAT:");
        System.out.println(moveAT.toString());
        repaint();
    }

    public void rotateLeft() {
        double angle = Math.toRadians(step);
        rotate(-angle);
    }

    public void rotateRight() {
        double angle = Math.toRadians(step);
        rotate(angle);
    }

    public void zoomIn() {
        double scale = 1 + step / 100;
        scale(scale);
    }

    public void zoomOut() {
        double scale = 1 / (1 + step / 100);
        scale(scale);
    }

    private void rotate(double angle) {
        int x0 = image.getWidth() / 2;
        int y0 = image.getHeight() / 2;
        rotateAT.rotate(angle, x0, y0);
        moveAT.concatenate(rotateAT);
        rotateAT = new AffineTransform(notRotatedAT);
        repaint();
    }

    private void scale(double scale) {
        int x0 = image.getWidth() / 2;
        int y0 = image.getHeight() / 2;

        zoomAT.translate(x0, y0);
        zoomAT.scale(scale, scale);
        zoomAT.translate(-x0, -y0);
        System.out.println("MoveAT:");
        System.out.println(moveAT.toString());
        moveAT.concatenate(zoomAT);
        System.out.println("MoveAT + zoom:");
        System.out.println(moveAT.toString());
        zoomAT = AffineTransform.getScaleInstance(1, 1);
        repaint();
    }

}
