import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class Arkanoid extends Application{
    private static final int width = 400;
    private static final int height = 600;
    private int playerOneXPos = width/2 - width/10;
    private double playerTwoXPos = height - PLAYER_HEIGHT;
    private static final int PLAYER_HEIGHT = 15;
    private static final int PLAYER_WIDTH = 100;
    private static final double BALL_R = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double playerOneYPos = height - 20;
    private double playerTwoYPos = height / 2;
    private double ballXPos = playerOneXPos;
    private double ballYPos = playerOneYPos + 40;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted;

    //gc.fillRect(width/2,height-20,100,15);

    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuItem gl = new MenuItem("(Re)Launch");
        //Insert Event here
        MenuItem p = new MenuItem("Pause");
        //Insert Event here
        MenuItem q = new MenuItem("Quit");
        q.setOnAction(e -> System.exit(0));
        Menu game = new Menu("Game");
        game.getItems().addAll(gl,p,q);
        MenuBar menu = new MenuBar();
        menu.getMenus().add(game);
        /*BorderPane root = new BorderPane();

        //Pane arka = new Pane();
        Canvas arka = new Canvas(600,800);
        GraphicsContext gc = arka.getGraphicsContext2D();

        arka.setStyle("-fx-border-style: solid inside;");
        ltf.setTextAlignment(TextAlignment.CENTER);


        root.setCenter(arka);
        Scene scene = new Scene(root, 768 , 1024);
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()){
                case ESCAPE: System.exit(0);
            }

        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main");
        primaryStage.show();*/


        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        VBox box = new VBox(20);
        Button level1 = new Button("Level 1");
        Button level2 = new Button("Level 2");
        Button level3 = new Button("Level 3");
        Button level4 = new Button("Level 4");
        Button level5 = new Button("Level 5");
        Button level6 = new Button("Level 6");
        Button level7 = new Button("Level 7");
        Text ltf = new Text("Levels");
        box.getChildren().add(ltf);
        box.getChildren().add(level1);
        box.getChildren().add(level2);
        box.getChildren().add(level3);
        box.getChildren().add(level4);
        box.getChildren().add(level5);
        box.getChildren().add(level6);
        box.getChildren().add(level7);
        HBox bottom = new HBox(2000);
        bottom.getChildren().add(new Text("Hello World"));
        //run(gc);
        canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());
        canvas.setOnKeyPressed((a) -> System.out.println("hi"));
        //canvas.setOnMouseMoved(e ->  playerOneXPos  = e.getX());
        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case Q: playerOneXPos -= 100;
                break;
                case D: playerOneXPos += 100;
                break;
            }
        });


        canvas.setOnKeyReleased(e -> {
            switch(e.getCode()){
                case Q: playerOneXPos -= 0;
                    break;
                case D: playerOneXPos += 0;
                    break;
            }
        });
        canvas.setOnMouseClicked(e ->  gameStarted = true);
        BorderPane root = new BorderPane();
        root.setTop(menu);
        root.setCenter(canvas);
        root.setLeft(box);
        root.setBottom(bottom);
        primaryStage.setScene(new Scene(root,1024,768));
        primaryStage.show();
        tl.play();
    }
    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
        if(gameStarted) {
            ballXPos+=ballXSpeed;
            ballYPos+=ballYSpeed;
            /*if(ballXPos < width - width  / 4) {
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
            }  else {
                playerTwoYPos =  ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
            }*/
            gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
        } else {
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to Start", width / 2, height / 2);
            ballXPos = width / 2;
            ballYPos = height + 40;
            ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
        }
        if(ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
        if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }
        if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }
        if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||
                ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }
        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
        //gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        //gc.fillRect(width/2,height-20,100,15);
    }
    public static void main (String[] args){
        Application.launch();
    }
}
