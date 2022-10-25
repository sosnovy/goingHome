package com.example.cominghome;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import static com.example.cominghome.Constants.PLAYER_DEFAULT_X;
import static com.example.cominghome.Constants.PLAYER_DEFAULT_Y;

public class Avatar extends GameObject {


    public Avatar() {
        super(new Polygon(
                50, 50,
                40, 75,
                60, 75
        ));


    }

    @Override
    public void rotateLeft() {
        this.getNode().setRotate(this.getNode().getRotate() - 1.5);
        double x = 0;
        double y = 1 * this.getSpeed();

        double newX = (Math.cos(Math.toRadians(getNode().getRotate())) * x) +
                (Math.sin(Math.toRadians(getNode().getRotate())) * y);
        double newY = (-Math.sin(Math.toRadians(getNode().getRotate())) * x) +
                (Math.cos(Math.toRadians(getNode().getRotate())) * y);
        this.setTranslationVector(new Point2D(newX, newY));
    }

    @Override
    public void rotateRight() {
        this.getNode().setRotate(this.getNode().getRotate() + 1.5);
        double x = 0;
        double y = 1 * this.getSpeed();

        double newX = (Math.cos(Math.toRadians(getNode().getRotate())) * x) +
                (Math.sin(Math.toRadians(getNode().getRotate())) * y);
        double newY = (-Math.sin(Math.toRadians(getNode().getRotate())) * x) +
                (Math.cos(Math.toRadians(getNode().getRotate())) * y);
        this.setTranslationVector(new Point2D(newX, newY));
    }

    @Override
    public void moveForward() {
        this.setY(this.getY() - this.getTranslationVector().getY());
        this.setX(this.getX() + this.getTranslationVector().getX());
        this.getNode().setTranslateY(this.getY());
        this.getNode().setTranslateX(this.getX());
    }

    @Override
    public void moveBackwards() {
    }

    @Override
    public void resetCoordinates() {
        x = PLAYER_DEFAULT_X;
        y = PLAYER_DEFAULT_Y;
        this.setLocation();
    }
}
