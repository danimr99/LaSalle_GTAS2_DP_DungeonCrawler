package model.entities.enemies;

import model.map.MapPosition;

public class Fly extends Enemy {
    public Fly(MapPosition position) {
        super(position, Enemy.FLY_DAMAGE, Enemy.FLY_MOVEMENT_TIME_MILLIS, EnemyDirection.VERTICAL);
    }
}
