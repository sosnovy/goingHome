package com.example.cominghome;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ComingHome extends Application {

    private boolean left, right, up;
    private Pane root;
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
        //System.out.println("Y: "+avatar.getY() + ",X: "+avatar.getX());

      /*  if(avatar.checkCollision(home)){
            //insert what happens when you win
        }


        //check collision of debris and player
        for(GameObject deb : debris){
            if(deb.checkCollision(avatar)){
                deaths++;
                avatar.resetPosition();
            }

        }*/

    }

    @Override
    public void start(Stage stage) throws IOException {


        Scene scene = new Scene(createContent());
        root.getChildren().add(avatar.getNode());

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

    public void init() {
        deaths = 0;
        avatar = new Avatar();
        avatar.setSpeed(1.5);
        avatar.setTranslationVector(new Point2D(0, 1 * avatar.getSpeed()));
        avatar.resetCoordinates();

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


}