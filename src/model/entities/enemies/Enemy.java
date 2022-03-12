package model.entities.enemies;

import model.entities.Entity;
import model.entities.EntityDirection;
import model.map.MapPosition;

public abstract class Enemy extends Entity {
    public static final int FLY_DAMAGE = 3;
    public static final int FLY_MOVEMENT_TIME_MILLIS = 300;
    public static final int SPIDER_DAMAGE = 5;
    public static final int SPIDER_MOVEMENT_TIME_MILLIS = 200;
    protected final int damage;
    protected final int movementTime;
    protected final EnemyDirection enemyDirection;
    private EntityDirection lastMovement;

    public Enemy(MapPosition position, int damage, int movementTime, EnemyDirection enemyDirection) {
        super(position);
        this.damage = damage;
        this.movementTime = movementTime;
        this.enemyDirection = enemyDirection;

        if(this.enemyDirection.equals(EnemyDirection.HORIZONTAL)) {
            this.lastMovement = EntityDirection.LEFT;
        } else {
            this.lastMovement = EntityDirection.UP;
        }
    }

    /**
     * Getter of the damage that an {@link Enemy} causes to the {@link model.entities.player.Player}.
     * @return Number of damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Getter of the last movement of the {@link Enemy}.
     * @return An {@link EntityDirection}.
     */
    public EntityDirection getLastMovement() {
        return lastMovement;
    }

    /**
     * Getter of the delay time between movements of an {@link Enemy}.
     * @return Time in milliseconds.
     */
    public int getMovementTime() {
        return movementTime;
    }

    /**
     * Function to toggle the {@link EntityDirection} continuing the same {@link EnemyDirection}.
     */
    public void toggleDirection() {
        if(this.enemyDirection.equals(EnemyDirection.VERTICAL)) {
            if(this.lastMovement.equals(EntityDirection.UP)) {
                this.lastMovement = EntityDirection.DOWN;
            } else {
                this.lastMovement = EntityDirection.UP;
            }
        } else {
            if(this.lastMovement.equals(EntityDirection.LEFT)) {
                this.lastMovement = EntityDirection.RIGHT;
            } else {
                this.lastMovement = EntityDirection.LEFT;
            }
        }
    }
}
