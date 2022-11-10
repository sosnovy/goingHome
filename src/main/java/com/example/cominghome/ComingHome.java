package com.example.cominghome;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;


public class ComingHome extends Application {

    long start = 0;
    long finish;
    int spawnTime = 1000;
    int lives = 0;
    int level = 1;
    private boolean left, right, up;
    private boolean isHome;
    private Pane root;
    private Text livesText = new Text();
    private Text levelText = new Text();
    private Avatar avatar;
    private Home home;
    private int deaths;
    private List<GameObject> debris = new ArrayList<>();
    private Alert alert;
    private Alert homeAlert;

    public static void main(String[] args) {

        launch();
    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 600);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lives > 0 && !isHome) {
                    gameLoop();
                } else if (isHome) {
                    homeAlert.show();
                    Button btn = (Button) homeAlert.getDialogPane().lookupButton(ButtonType.OK);
                    btn.setOnAction(actionEvent -> {
                        level++;
                        initialize();
                    });
                } else {
                    alert.show();
                    Button btn = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    btn.setOnAction(actionEvent -> {
                        System.exit(0);
                    });
                }

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
            isHome = true;
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
                } else {
                    d.move();
                }
            }

        }


    }

    @Override
    public void start(Stage stage) throws IOException {


        Scene scene = new Scene(createContent());
        initialize();


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

        Iterator<Node> itr = root.getChildren().iterator();
        if (!root.getChildren().isEmpty()) {
            for (Iterator<Node> it = itr; it.hasNext(); ) {
                Node n = it.next();
                itr.remove();
            }
        }


        home = new Home();

        deaths = 0;
        lives = 3;
        livesText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        levelText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        livesText.setText("Lives: " + lives);
        levelText.setText("Level: " + level);

        avatar = new Avatar();
        avatar.setSpeed(1.5);
        avatar.setTranslationVector(new Point2D(0, 1 * avatar.getSpeed()));
        avatar.resetCoordinates();

        isHome = false;


        livesText.setX(20);
        livesText.setY(30);

        levelText.setX(670);
        levelText.setY(30);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("You have lost");

        homeAlert = new Alert(Alert.AlertType.INFORMATION);
        homeAlert.setTitle("Home");
        homeAlert.setHeaderText("You have made it home!");

        root.getChildren().add(home.getNode());
        root.getChildren().add(avatar.getNode());
        root.getChildren().add(livesText);
        root.getChildren().add(levelText);
        up = false;
        right = false;
        left = false;
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
            long calcTime;
            if (spawnTime - (level * level * 4) <= 100) {
                calcTime = 10;
            } else {
                calcTime = (long) (spawnTime - (level * level * 1.5));
            }
            if (finish - start < calcTime) {
                finish = System.currentTimeMillis();
            } else {
                double rand = Math.random() * 10;
                boolean spawnLeft;
                spawnLeft = rand <= 5;

                rand = Math.random() * 1000;
                if (rand >= 800 || (rand <= 100 && rand >= 0)) {
                } else {
                    Debris d = new Debris();
                    d.setAlive(true);
                    d.setSpawnLeft(spawnLeft);
                    d.getNode().setTranslateY(rand);
                    if (d.isSpawnLeft()) {
                        d.getNode().setTranslateX(0);
                        d.setSpeed(2 * (Math.ceil(((double) level / 2))));
                    } else {
                        d.getNode().setTranslateX(800);
                        d.setSpeed(-2 * (Math.ceil(((double) level / 2))));
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