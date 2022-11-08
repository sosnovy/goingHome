package com.example.cominghome;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public abstract class GameObject {


    public double x, y;
    public boolean isAlive;
    private Node node;
    private Point2D translationVector = new Point2D(0, 0);
    private double speed;


    public GameObject(Node node) {
        this.node = node;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setLocation() {
        getNode().setTranslateX(getX());
        getNode().setTranslateY(getY());

    }
    public void move(){
        getNode().setTranslateX(getNode().getTranslateX()+speed);
    }

    public Point2D getTranslationVector() {
        return translationVector;
    }

    public void setTranslationVector(Point2D translationVector) {
        this.translationVector = translationVector;
    }

    public Node getNode() {
        return node;
    }

    public void resetCoordinates() {
    }

    ;

    public boolean checkCollision(GameObject gameObject) {
        return getNode().getBoundsInParent().intersects(gameObject.getNode().getBoundsInParent());
    }


    public void rotateLeft() {

    }

    public void rotateRight() {

    }

    public void moveForward() {


    }

    public void moveBackwards() {

    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
