import controller.DirectionButtonsController;
import model.dao.MapDAO;

import javax.swing.*;

import model.map.GameMap;
import model.entities.player.Player;
import view.View;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapDAO mapDAO = new MapDAO();

            GameMap map = new GameMap(mapDAO.getMaxCellsX(), mapDAO.getMaxCellsY(), mapDAO.getMapFromFile());

            Player player = new Player(map.getPositionByCell(GameMap.START_PLAYER_CELL).get(0));

            View view = new View(map, player);

            /* Create a controller for the game direction buttons and attach it to view */
            DirectionButtonsController directionButtonsController = new DirectionButtonsController(view);
            view.addActionListener(directionButtonsController);

            view.setVisible(true);
        });
    }
}
