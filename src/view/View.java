package view;

import controller.KeyboardController;
import model.dao.FilePath;
import model.entities.Entity;
import model.entities.EntityDirection;
import model.map.GameMap;
import model.map.MapPosition;
import model.entities.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private static final int ICON_WIDTH = 25;
    private static final int ICON_HEIGHT = 25;
    public static final String CONTROL_UP = "CONTROL_UP";
    public static final String CONTROL_DOWN = "CONTROL_DOWN";
    public static final String CONTROL_LEFT = "CONTROL_LEFT";
    public static final String CONTROL_RIGHT = "CONTROL_RIGHT";
    private final GameMap map;
    private Player player;
    private HealthBar healthBar;
    private GameBoard gameBoard;
    private JButton controlUpButton;
    private JButton controlDownButton;
    private JButton controlLeftButton;
    private JButton controlRightButton;

    public View(GameMap map, Player player) {
        this.map = map;
        this.player = player;

        this.configureWindow();
        this.renderHealthBar();
        this.renderGame();
        this.renderDirectionButtons();
        this.configureKeyboardListener();
    }

    /**
     * Function to configure basic settings for the {@link View}.
     */
    private void configureWindow() {
        this.setTitle("AC2 - Daniel Muelle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1080, 720));
        this.setLocationRelativeTo(null);
    }

    /**
     * Function to render the {@link HealthBar}.
     */
    private void renderHealthBar() {
        this.healthBar = new HealthBar(Entity.MAX_PLAYER_HEALTH);
        this.add(this.healthBar, BorderLayout.NORTH);
    }

    /**
     * Function to render the {@link GameBoard}.
     */
    private void renderGame() {
        this.gameBoard = new GameBoard(this.map, this.player.getPosition());

        this.add(this.gameBoard, BorderLayout.CENTER);
    }

    /**
     * Function to get the direction buttons' icons of a specific size.
     * @param path Path of the icon resource.
     * @return Instance of {@link ImageIcon} to set on a direction button.
     */
    private ImageIcon setImageIcon(String path) {
        return new ImageIcon(((new ImageIcon(path)).getImage()).getScaledInstance(View.ICON_WIDTH,
                View.ICON_HEIGHT, Image.SCALE_SMOOTH));
    }

    /**
     * Function to render the direction buttons.
     */
    private void renderDirectionButtons() {
        /* Configure icons for the control buttons */
        Icon upButtonIcon = this.setImageIcon(FilePath.ASSET_BUTTON_UP);
        Icon downButtonIcon = this.setImageIcon(FilePath.ASSET_BUTTON_DOWN);
        Icon leftButtonIcon = this.setImageIcon(FilePath.ASSET_BUTTON_LEFT);
        Icon rightButtonIcon = this.setImageIcon(FilePath.ASSET_BUTTON_RIGHT);

        /* Configure buttons and set them their corresponding icon */
        this.controlUpButton = new JButton(upButtonIcon);
        this.controlDownButton = new JButton(downButtonIcon);
        this.controlLeftButton = new JButton(leftButtonIcon);
        this.controlRightButton = new JButton(rightButtonIcon);

        /* Add buttons to a JPanel */
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        /* Set spacing between buttons */
        gbc.insets = new Insets(5, 10, 10, 5);

        /* Set position for each button */
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonsPanel.add(controlUpButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonsPanel.add(controlDownButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonsPanel.add(controlLeftButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        buttonsPanel.add(controlRightButton, gbc);

        /* Add JPanel containing buttons to MainView */
        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Function to add an {@link ActionListener} to direction buttons.
     * @param listener Instance of {@link ActionListener}.
     */
    public void addActionListener(ActionListener listener) {
        this.controlUpButton.setActionCommand(CONTROL_UP);
        this.controlUpButton.addActionListener(listener);

        this.controlDownButton.setActionCommand(CONTROL_DOWN);
        this.controlDownButton.addActionListener(listener);

        this.controlLeftButton.setActionCommand(CONTROL_LEFT);
        this.controlLeftButton.addActionListener(listener);

        this.controlRightButton.setActionCommand(CONTROL_RIGHT);
        this.controlRightButton.addActionListener(listener);

        /* Remove focus to buttons */
        this.controlUpButton.setFocusable(false);
        this.controlDownButton.setFocusable(false);
        this.controlLeftButton.setFocusable(false);
        this.controlRightButton.setFocusable(false);
    }

    /**
     * Function to add a {@link java.awt.event.KeyListener} to the {@link View}.
     */
    private void configureKeyboardListener() {
        this.addKeyListener(new KeyboardController(this));
        this.setFocusable(true);
    }

    /**
     * Function to check whether an {@link Entity} can move to a cell or not (is a wall).
     * @param futureEntityPosition The {@link MapPosition} where an {@link Entity} wants to move.
     * @return Result of the checkup.
     */
    private boolean isPlayableCell(MapPosition futureEntityPosition) {
        return this.map.getCellByPosition(futureEntityPosition) != GameMap.WALL_CELL;
    }

    /**
     * Function to check whether the {@link Player} is on a spikes cell or not.
     * @param playerPosition The {@link Player}'s position.
     * @return Result of the checkup.
     */
    private boolean isSpikesCell(MapPosition playerPosition) {
        return this.map.getCellByPosition(playerPosition) == GameMap.SPIKES_CELL;
    }

    /**
     * Function to check whether an {@link Entity} can move to a desired {@link EntityDirection}.
     * @param entityDirection {@link Entity}'s desired next move direction.
     * @return Result of the checkup.
     */
    public boolean canEntityMove(MapPosition entityPosition, EntityDirection entityDirection) {
        if(this.player.isAlive()) {
            switch (entityDirection) {
                case UP -> {
                    if(entityPosition.getY() > 0 &&
                            this.isPlayableCell(new MapPosition(entityPosition.getX(), entityPosition.getY() - 1))) {
                        return true;
                    }
                }
                case DOWN -> {
                    if(entityPosition.getY() < (this.map.getMaxCellsY() - 1) &&
                            this.isPlayableCell(new MapPosition(entityPosition.getX(), entityPosition.getY() + 1))) {
                        return true;
                    }
                }
                case LEFT -> {
                    if(entityPosition.getX() > 0 &&
                            this.isPlayableCell(new MapPosition(entityPosition.getX() - 1, entityPosition.getY()))) {
                        return true;
                    }
                }
                case RIGHT -> {
                    if(entityPosition.getX() < (this.map.getMaxCellsX() - 1) &&
                            this.isPlayableCell(new MapPosition(entityPosition.getX() + 1, entityPosition.getY()))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Function to cause damage to the {@link Player}.
     * @param damage Amount of damage to cause to the {@link Player}.
     */
    public void damagePlayer(int damage) {
        this.player.setHealth(this.player.getHealth() - damage);
    }

    /**
     * Function to move the {@link Player} to a desired {@link EntityDirection}.
     * @param playerDirection {@link Player}'s desired move direction.
     */
    public void movePlayer(EntityDirection playerDirection) {
        MapPosition playerPosition = this.player.getPosition();

        if(this.canEntityMove(this.player.getPosition(), playerDirection)) {
            switch (playerDirection) {
                case UP -> playerPosition.setY(playerPosition.getY() - 1);
                case DOWN -> playerPosition.setY(playerPosition.getY() + 1);
                case RIGHT -> playerPosition.setX(playerPosition.getX() + 1);
                case LEFT -> playerPosition.setX(playerPosition.getX() - 1);
            }

            /* Check if player has moved to a spikes cell */
            if(this.isSpikesCell(playerPosition)) {
                this.damagePlayer(GameMap.SPIKES_CELL_DAMAGE);
            }

            /* TODO Check if player has been attacked by an enemy */
        }
    }

    /**
     * Function to check whether the {@link Player} is on the {@link GameMap#END_CELL}.
     * @return Result of the checkup.
     */
    public boolean hasPlayerWon() {
        return this.map.getCellByPosition(this.player.getPosition()) == GameMap.END_CELL;
    }

    /**
     * Function to update the {@link HealthBar}.
     */
    private void updateHealthBar() {
        this.healthBar.updateHealthBar(this.player.getHealth());
    }

    /**
     * Function to update the {@link GameBoard}.
     */
    private void updatePlayerPosition() {
        this.gameBoard.updatePlayerPosition(this.player.getPosition());
    }

    /**
     * Function to update the whole {@link View} and check if a dialog has to be displayed (winning dialog or death
     * dialog).
     */
    public void updateView() {
        this.updateHealthBar();
        this.updatePlayerPosition();

        /* Check if player has dead */
        if(!this.player.isAlive()) {
            this.showDialog("YOU DIED", "Oh, no! You ran out of HP!", false);
        }

        /* Check if player has won */
        if(this.hasPlayerWon()) {
            this.showDialog("YOU WON", "Congratulations for reaching the end!", true);
        }
    }

    /**
     * Function to reset all and play again.
     */
    public void resetGame() {
        this.player.setHealth(Entity.MAX_PLAYER_HEALTH);
        this.player.setPosition(this.map.getPositionByCell(GameMap.START_PLAYER_CELL).get(0));
        this.updateHealthBar();
        this.updatePlayerPosition();

        /* Enable direction control buttons */
        this.controlUpButton.setEnabled(true);
        this.controlDownButton.setEnabled(true);
        this.controlLeftButton.setEnabled(true);
        this.controlRightButton.setEnabled(true);
    }

    /**
     * Function to display a {@link JDialog} when the player wins or dies.
     * @param title Title of the dialog.
     * @param message Message of the dialog.
     * @param isWinnerDialog Boolean whether is winner or dead dialog.
     */
    public void showDialog(String title, String message, boolean isWinnerDialog) {
        final JDialog dialog = new JDialog(this, title, false);
        JOptionPane optionPane = new JOptionPane(message, isWinnerDialog ?
                JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        optionPane.addPropertyChangeListener(e -> {
            String prop = e.getPropertyName();

            if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                /* Reset everything to play again */
                this.resetGame();

                dialog.setVisible(false);
            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(null);

        dialog.setVisible(true);
        dialog.requestFocus();

        /* Disable direction control buttons to prevent duplicating the dialog */
        this.controlUpButton.setEnabled(false);
        this.controlDownButton.setEnabled(false);
        this.controlLeftButton.setEnabled(false);
        this.controlRightButton.setEnabled(false);
    }
}