package com.example.cominghome;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ComingHome extends Application {

    long start = 0;
    long finish;
    int spawnTime = 1000;
    int lives = 0;
    private boolean left, right, up;
    private Pane root;
    private Text livesText = new Text();
    private Avatar avatar;
    private Home home;
    private int deaths;
    private List<GameObject> debris = new ArrayList<>();

    public static void main(String[] args) {

        launch();
    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 600);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameLoop();
            }
        };
        timer.start();
        return root;
    }

    private void gameLoop() {
        checkMovement();
        generateDebris();

        //System.out.println("Y: "+avatar.getY() + ",X: "+avatar.getX());

        if (avatar.checkCollision(home)) {
            //insert what happens when you win
            System.out.println("Home is Reached");
        }



        Iterator<GameObject> iterator = debris.iterator();
        while (iterator.hasNext()) {
            GameObject d = iterator.next();
            if (!d.isAlive()) {
                iterator.remove();
                root.getChildren().remove(d.getNode());
            } else {
                if (d.checkCollision(avatar)) {
                    d.setAlive(false);
                    lives--;
                    livesText.setText("Lives:" + lives);
                    System.out.println(lives);
                } else {
                    d.move();
                }
            }

        }


    }

    @Override
    public void start(Stage stage) throws IOException {

        initialize();
        Scene scene = new Scene(createContent());

        root.getChildren().add(home.getNode());
        root.getChildren().add(avatar.getNode());
        root.getChildren().add(livesText);


        stage.setTitle("Coming Home");
        stage.setScene(scene);
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                up = true;

            } else if (e.getCode() == KeyCode.SPACE) {
                avatar.setSpeed(6);

            } else if (e.getCode() == KeyCode.A) {
                left = true;


            } else if (e.getCode() == KeyCode.D) {
                right = true;
            }
        });
        stage.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.W) {
                up = false;

            } else if (e.getCode() == KeyCode.SPACE) {
                avatar.setSpeed(1.5);

            } else if (e.getCode() == KeyCode.A) {
                left = false;


            } else if (e.getCode() == KeyCode.D) {
                right = false;
            }

        });

        //check movements


        stage.show();

    }

    public void initialize() {
        home = new Home();

        deaths = 0;
        lives = 3;
        livesText.setFont(Font.font("verdana", FontWeight.BOLD,FontPosture.REGULAR,20));
        livesText.setText("Lives: " + lives);
        avatar = new Avatar();
        avatar.setSpeed(1.5);
        avatar.setTranslationVector(new Point2D(0, 1 * avatar.getSpeed()));
        avatar.resetCoordinates();


        livesText.setX(20);
        livesText.setY(30);
    }

    public void checkMovement() {
        if (up) {
            if (left) {
                avatar.rotateLeft();
                avatar.moveForward();
            } else if (right) {
                avatar.rotateRight();
                avatar.moveForward();
            } else {
                avatar.moveForward();
            }
        }
        if (left) {
            if (!right) {
                avatar.rotateLeft();
                if (up) {
                    avatar.moveForward();
                }
            }
        }
        if (right) {
            if (!left) {
                avatar.rotateRight();
                if (up) {
                    avatar.moveForward();
                }
            }
        }
    }

    public void checkOutOfBound() {

    }

    public void generateDebris() {
        if (start == 0) {
            start = System.currentTimeMillis();
        } else {
            if (finish - start < spawnTime) {
                finish = System.currentTimeMillis();
            } else {
                double rand = Math.random() * 10;
                boolean spawnLeft;
                spawnLeft = rand <= 5;

                rand = Math.random() * 1000;
                if (rand >= 800 || (rand <= 100 && rand >= 0)) {
                    System.out.println("no spawn");
                } else {
                    System.out.println("spawn");
                    Debris d = new Debris();
                    d.setAlive(true);
                    d.setSpawnLeft(spawnLeft);
                    d.getNode().setTranslateY(rand);
                    if (d.isSpawnLeft()) {
                        d.getNode().setTranslateX(0);
                        d.setSpeed(2);
                    } else {
                        d.getNode().setTranslateX(800);
                        d.setSpeed(-2);
                    }
                    debris.add(d);
                    root.getChildren().add(d.getNode());
                }
                start = System.currentTimeMillis();
            }
        }
        finish = System.currentTimeMillis();


    }


}