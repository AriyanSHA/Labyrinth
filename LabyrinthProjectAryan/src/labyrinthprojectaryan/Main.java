package labyrinthprojectaryan;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LabyrinthView game = new LabyrinthView();
            game.setVisible(true);
        });
    }
}
