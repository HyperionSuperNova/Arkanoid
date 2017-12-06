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
    private double playerOneXPos = width/2 - width/10;
    private static final int PLAYER_HEIGHT = 15;
    private static final int PLAYER_WIDTH = 100;
    private static final double BALL_R = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double playerOneYPos = height - 15;
    private final double originpadposX = width/2 - width/10;
    private final double originpadposY = height - 15;
    private double playerTwoYPos = height / 2;
    private final double originballX = width/2;
    private final double originballY = height - PLAYER_HEIGHT - BALL_R;
    private double ballXPos = width/2;
    private double ballYPos = height - PLAYER_HEIGHT - BALL_R;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted;
    public static int cmp = 0;
    //gc.fillRect(width/2,height-20,100,15);

    public MenuBar topGui(){
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
        return menu;
    }

    public VBox leftGui(){
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
        return box;
    }

    public HBox bottomGui(){
        HBox bottom = new HBox(2000);
        bottom.getChildren().add(new Text("Hello World"));
        return bottom;
    }

    public boolean hitWall(){
        return (ballXPos >= width || ballYPos <= 0 || ballXPos <= 0) && !(ballYPos >= height);
    }

    public boolean hitpaddle(){
        return ballXPos < playerOneXPos + PLAYER_WIDTH && ballYPos >= playerOneYPos && ballYPos < playerOneYPos + PLAYER_HEIGHT;
    }

    public boolean fallout(){

        return ballYPos > height;
    }

    public void relaunch(){
        ballXPos = originballX;
        ballYPos = originballY;
        playerOneXPos = originpadposX;
        playerOneYPos = originpadposY;
        gameStarted = false;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());
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
        canvas.setOnMouseClicked(e ->  {gameStarted = true; cmp+=1;});
        BorderPane root = new BorderPane();
        root.setTop(topGui());
        root.setCenter(canvas);
        root.setLeft(leftGui());
        root.setBottom(bottomGui());
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
            if(ballXPos < width - width  / 4) {
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
            }  else {
                playerTwoYPos =  ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
            }
            //gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
        } else {
            //ballXPos = width/2;
            //ballYPos = height - 100;
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to Start", width / 2, height / 2);


            ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
        }
        if(hitWall()){
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }




        //if(ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
        if((ballXPos == playerOneXPos + PLAYER_HEIGHT) && (ballYPos <= playerOneYPos) && (ballYPos >= playerOneYPos + PLAYER_WIDTH)){
            System.out.println("here");

        }
        /*gameStarted && (ballXPos >= playerOneXPos - PLAYER_WIDTH/2 && ballXPos <= playerOneXPos + PLAYER_WIDTH/2)*/

        if(hitpaddle()){
            System.out.println("here2");
            System.out.println("Ball Position for X : "  + ballXPos + " Ball position for Y : " + ballYPos);
            System.out.println("Player Position for X : "  + playerOneXPos + " Player position for Y : " + playerOneYPos);
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }

        if(fallout()){
            relaunch();
        }
        /*if(ballXPos > playerOneXPos + PLAYER_HEIGHT && ballYPos > playerOneYPos + PLAYER_WIDTH) {
            //scoreP2++;

            gameStarted = false;
        }*/


        /*if(((ballXPos == playerOneXPos + PLAYER_WIDTH) && ballYPos < playerOneYPos && ballYPos > playerOneYPos + PLAYER_WIDTH)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
            System.out.println("here");
        }*/

        //gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
        gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        cmp = 0;
    }
    public static void main (String[] args){
        Application.launch();
    }
}
