package model.entities.player;

import model.entities.Entity;
import model.map.MapPosition;

public class Player extends Entity{
    private int health;

    public Player(MapPosition position) {
        super(position);
        this.health = Entity.MAX_PLAYER_HEALTH;
    }

    /**
     * Function to check whether the {@link Player} is alive or not.
     * @return Result of the checkup.
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Getter of the {@link Player}'s health.
     * @return {@link Player}'s health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter of the {@link Player}'s health.
     * @param health {@link Player}'s health.
     */
    public void setHealth(int health) {
        this.health = health;
    }
}