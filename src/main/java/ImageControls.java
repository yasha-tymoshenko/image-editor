import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Yakiv Tymoshenko
 * @since 06-Aug-17
 */
@SuppressWarnings("WeakerAccess")
public class ImageControls extends JPanel implements ActionListener {
    private ImageEdit imageEdit;

    public ImageControls(final ImageEdit imageEdit) {
        super();
        this.imageEdit = imageEdit;

        setLayout(new GridLayout(8, 1));
        Command[] commands = Command.values();
        for (int i = 0; i < 8; i++) {
            JButton button = new JButton(commands[i].name());
            button.addActionListener(this);
            add(button);
        }
    }

    public void actionPerformed(ActionEvent e) {
        Command command = Command.valueOf(e.getActionCommand());
        switch (command) {
            case MOVE_UP:
                imageEdit.moveUp();
                break;
            case MOVE_DOWN:
                imageEdit.moveDown();
                break;
            case MOVE_LEFT:
                imageEdit.moveLeft();
                break;
            case MOVE_RIGHT:
                imageEdit.moveRight();
                break;
            case ROTATE_LEFT:
                imageEdit.rotateLeft();
                break;
            case ROTATE_RIGHT:
                imageEdit.rotateRight();
                break;
            case ZOOM_IN:
                imageEdit.zoomIn();
                break;
            case ZOOM_OUT:
                imageEdit.zoomOut();
                break;
        }
    }

    private enum Command {
        MOVE_UP, MOVE_LEFT, MOVE_RIGHT, MOVE_DOWN, ZOOM_IN, ZOOM_OUT, ROTATE_LEFT, ROTATE_RIGHT
    }
}
