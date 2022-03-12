package controller;

import model.entities.enemies.Enemy;
import model.entities.enemies.EnemyMovementListener;
import model.entities.enemies.EnemyWorker;
import view.View;

import javax.swing.*;

public class EnemiesController implements EnemyMovementListener {
    private final View view;

    public EnemiesController(View view) {
        this.view = view;

        this.startEnemies();
    }

    private void startEnemies() {
        for(Enemy enemy : this.view.getEnemies()) {
            new Thread(new EnemyWorker(this.view, enemy, this)).start();
        }
    }

    @Override
    public void enemyMoved() {
        SwingUtilities.invokeLater(this.view::updateEnemies);
    }
}
