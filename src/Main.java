import controller.DirectionButtonsController;
import controller.EnemiesController;
import model.dao.MapDAO;

import javax.swing.*;

import model.entities.enemies.Enemy;
import model.entities.enemies.Fly;
import model.entities.enemies.Spider;
import model.map.GameMap;
import model.entities.player.Player;
import model.map.MapPosition;
import view.View;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapDAO mapDAO = new MapDAO();

            GameMap map = new GameMap(mapDAO.getMaxCellsX(), mapDAO.getMaxCellsY(), mapDAO.getMapFromFile());

            Player player = new Player(map.getPositionByCell(GameMap.START_PLAYER_CELL).get(0));

            /* Get all the enemies */
            ArrayList<Enemy> enemies = new ArrayList<>();

            /* Get all spiders */
            for(MapPosition spiderPosition : map.getPositionByCell(GameMap.START_SPIDER_CELL)) {
                enemies.add(new Spider(spiderPosition));
            }

            /* Get all flies */
            for(MapPosition flyPosition : map.getPositionByCell(GameMap.START_FLY_CELL)) {
                enemies.add(new Fly(flyPosition));
            }

            View view = new View(map, player, enemies);

            /* Create a controller for the game direction buttons and attach it to view */
            DirectionButtonsController directionButtonsController = new DirectionButtonsController(view);
            view.addActionListener(directionButtonsController);

            /* Create a controller for the enemies */
            EnemiesController enemiesController = new EnemiesController(view);

            view.setVisible(true);
        });
    }
}
