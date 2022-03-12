package view;

import model.dao.FilePath;
import model.entities.enemies.Enemy;
import model.entities.enemies.Fly;
import model.map.GameMap;
import model.map.MapPosition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoard extends JPanel {
    private final GameMap map;
    private MapPosition playerPosition;
    private ArrayList<Enemy> enemies;
    private int cellWidth;
    private int cellHeight;
    private BufferedImage picture;

    public GameBoard(GameMap map, MapPosition playerPosition, ArrayList<Enemy> enemies) {
        this.map = map;
        this.playerPosition = playerPosition;
        this.enemies = enemies;
        this.picture = null;
    }

    /**
     * Function to paint the cells and player on the {@link View}.
     * @param g Instance of {@link Graphics}.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.cellWidth = this.getWidth() / this.map.getMaxCellsX();
        this.cellHeight = this.getHeight() / this.map.getMaxCellsY();

        for(int i = 0; i < this.map.getMaxCellsY(); i++) {
            for(int j = 0; j < this.map.getMaxCellsX(); j++) {
                char cell = this.map.getMap()[i][j];
                boolean isPlayerPosition = this.playerPosition.getX() == j && this.playerPosition.getY() == i;

                /* Render the cell */
                this.renderCell(g, cell, new MapPosition(j, i));

                /* Check if is the position of the player */
                if(isPlayerPosition) {
                    this.renderPlayer(g, new MapPosition(j, i));
                }

                /* Check if is the position of any enemy */
                for(Enemy enemy : this.enemies) {
                    if(enemy.getPosition().getX() == j && enemy.getPosition().getY() == i) {
                        this.renderEnemy(g, enemy);
                    }
                }
            }
        }
    }

    /**
     * Function to render each cell from the {@link GameMap}'s terrain.
     * @param g Instance of {@link Graphics}.
     * @param cell Character of the cell (type of cell) to render.
     * @param cellPosition MapPosition of the cell on the {@link GameMap}.
     */
    private void renderCell(Graphics g, char cell, MapPosition cellPosition) {
        String asset = "";

        /* Determine the asset for the cell */
        switch (cell) {
            case GameMap.START_PLAYER_CELL -> asset = FilePath.ASSET_CELL_START;
            case GameMap.START_FLY_CELL, GameMap.START_SPIDER_CELL, GameMap.EMPTY_CELL ->
                    asset = FilePath.ASSET_CELL_EMPTY;
            case GameMap.SPIKES_CELL -> asset = FilePath.ASSET_CELL_DAMAGE;
            case GameMap.END_CELL -> asset = FilePath.ASSET_CELL_END;
            case GameMap.WALL_CELL -> asset = FilePath.ASSET_CELL_WALL;
        }

        /* Get the cell's asset */
        this.getAsset(asset);

        /* Paint the cell */
        g.drawImage(picture, this.cellWidth * cellPosition.getX(), this.cellHeight * cellPosition.getY(),
                this.cellWidth, this.cellHeight, null);
    }

    /**
     * Function to render the {@link model.entities.player.Player} on the {@link GameMap}'s terrain.
     * @param g Instance of {@link Graphics}.
     * @param playerPosition {@link model.entities.player.Player}'s position on the map.
     */
    private void renderPlayer(Graphics g, MapPosition playerPosition) {
        /* Get player's asset */
        this.getAsset(FilePath.ASSET_PLAYER);

        /* Paint the player */
        g.drawImage(picture, this.cellWidth * playerPosition.getX(), this.cellHeight * playerPosition.getY(),
                this.cellWidth, this.cellHeight, null);
    }

    /**
     * Function to render an {@link Enemy} on the {@link GameMap}'s terrain.
     * @param g Instance of {@link Graphics}.
     * @param enemy {@link Enemy}'s position on the map.
     */
    private void renderEnemy(Graphics g, Enemy enemy) {
        /* Check the enemy type to get the corresponding asset */
        if(enemy instanceof Fly)  {
            this.getAsset(FilePath.ASSET_ENEMY_FLY);
        } else {
            this.getAsset(FilePath.ASSET_ENEMY_SPIDER);
        }

        /* Paint the player */
        g.drawImage(picture, this.cellWidth * enemy.getPosition().getX(),
                this.cellHeight * enemy.getPosition().getY(), this.cellWidth, this.cellHeight, null);
    }

    /**
     * Function to get the picture of a specified asset.
     * @param assetPath Path of the asset.
     */
    private void getAsset(String assetPath) {
        try {
            this.picture = ImageIO.read(new File(assetPath));
        } catch (IOException e) {
            System.out.println("ERROR: Cannot find the asset!");
        }
    }

    /**
     * Function to update the {@link model.entities.player.Player}'s position on move and repaint the {@link GameBoard}.
     * @param playerPosition New {@link model.entities.player.Player}'s {@link MapPosition}.
     */
    public void updatePlayerPosition(MapPosition playerPosition) {
        this.playerPosition = playerPosition;

        this.repaint();
    }

    /**
     * Function to update the list of {@link Enemy}'s position on move and repaint the {@link GameBoard}.
     * @param enemies List of {@link Enemy}.
     */
    public void updateEnemiesPosition(ArrayList<Enemy> enemies) {
        this.enemies = enemies;

        this.repaint();
    }
}
