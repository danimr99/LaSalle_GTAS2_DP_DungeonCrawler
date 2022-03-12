package model.entities.enemies;

import view.View;

public class EnemyWorker implements Runnable {
    private final View view;
    private final Enemy enemy;
    private final EnemyMovementListener listener;

    public EnemyWorker(View view, Enemy enemy, EnemyMovementListener listener) {
        this.view = view;
        this.enemy = enemy;
        this.listener = listener;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            /* Check if enemy can continue moving on the same direction as the last movement */
            if(this.view.canEntityMove(this.enemy.getPosition(), enemy.getLastMovement())) {
                this.view.moveEnemy(this.enemy);
            } else {
                this.enemy.toggleDirection();
            }

            /* Set delay between movements */
            try {
                Thread.sleep(this.enemy.getMovementTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /* Notify view that an enemy has moved */
            this.listener.enemyMoved();

            /* Check if enemy has attacked the player */
            if(this.view.getPlayer().isAlive() && this.view.isPlayerAttackedByEnemy() == this.enemy) {
                this.view.damagePlayer(enemy.getDamage());
                this.view.updateView();
            }
        }
    }
}
