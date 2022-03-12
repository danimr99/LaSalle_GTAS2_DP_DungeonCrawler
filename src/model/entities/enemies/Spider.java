package model.entities.enemies;

import model.map.MapPosition;

public class Spider extends Enemy {
    public Spider(MapPosition position) {
        super(position, Enemy.SPIDER_DAMAGE, Enemy.SPIDER_MOVEMENT_TIME_MILLIS, EnemyDirection.HORIZONTAL);
    }
}
