package com.example.cominghome;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Debris extends GameObject {
    public boolean spawnLeft;

    public Debris() {
        super(new Circle(0,0,10, Color.BROWN));
    }

    public boolean isSpawnLeft() {
        return spawnLeft;
    }

    public void setSpawnLeft(boolean spawnLeft) {
        this.spawnLeft = spawnLeft;
    }


}
