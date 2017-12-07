import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ArkaMVC extends Application {
    private int scoreP1 = 0;
    private boolean gameStarted;
    public static int cmp = 0;
    public static int cmppause = 0;
    Model m = new Model();

    public MenuBar topGui(Timeline tl) {
        MenuItem gl = new MenuItem("(Re)Launch");
        gl.setOnAction(e -> relaunch());
        MenuItem p = new MenuItem("Pause");
        p.setOnAction(e -> tl.pause());
        MenuItem q = new MenuItem("Quit");
        q.setOnAction(e -> System.exit(0));
        Menu game = new Menu("Game");
        game.getItems().addAll(gl, p, q);
        MenuBar menu = new MenuBar();
        menu.getMenus().add(game);
        return menu;
    }

    public VBox leftGui() {
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

    public HBox bottomGui() {
        HBox bottom = new HBox(2000);
        bottom.getChildren().add(new Text("Hello World"));
        return bottom;
    }

    public boolean hitWall() {
        return ((m.b.getBallXPos() >= m.getWidth()) || (m.b.getBallYPos() <= 0) || (m.b.getBallXPos() <= 0)) && !(m.b.getBallYPos() >= m.getHeight());
    }

    public boolean hitUpperWall() {
        return m.b.getBallYPos() <= 0;
    }

    public boolean hitpaddle() {
        return m.b.getBallXPos() < m.p.getPlayerOneXPos() + m.p.getPLAYER_WIDTH() && m.b.getBallXPos() > m.p.getPlayerOneXPos() && m.b.getBallYPos() > m.p.getPlayerOneYPos() - m.p.getPLAYER_HEIGHT() && m.b.getBallYPos() <= m.p.getPlayerOneYPos() + m.b.getBallR();
    }

    public boolean fallout() {
        return m.b.getBallYPos() > m.getHeight();
    }

    public void relaunch() {
        m.b.setBallXPos(m.b.getOriginballX());
        m.b.setBallYPos(m.b.getOriginballY());
        m.p.setPlayerOneXPos(m.p.getOriginpadposX());
        m.p.setPlayerOneYPos(m.p.getOriginpadposY());
        gameStarted = false;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(m.getWidth(), m.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());
        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case Q:
                    m.p.setPlayerOneXPos(m.p.getPlayerOneXPos() - 50);
                    break;
                case D:
                    m.p.setPlayerOneXPos(m.p.getPlayerOneXPos() + 50);
                    break;
            }
        });


        canvas.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case Q:
                    m.p.setPlayerOneXPos(m.p.getPlayerOneXPos());
                    break;
                case D:
                    m.p.setPlayerOneXPos(m.p.getPlayerOneXPos());
                    break;
            }
        });
        canvas.setOnMouseClicked(e -> {
            gameStarted = true;
            cmp += 1;
        });
        BorderPane root = new BorderPane();
        root.setTop(topGui(tl));
        root.setCenter(canvas);
        root.setLeft(leftGui());
        root.setBottom(bottomGui());
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        System.out.println("Ball Speed for X : " + m.b.getBallXSpeed() + " Ball speed for Y : " + m.b.getBallYSpeed());
        System.out.println("Ball Position for X : " + m.b.getBallXPos() + " Ball position for Y : " + m.b.getBallYPos());
        System.out.println("Player Position for X : " + m.p.getPlayerOneXPos() + " Player position for Y : " + m.p.getPlayerOneYPos());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, m.getWidth(), m.getHeight());
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
        if (gameStarted) {
            m.b.setBallXPos(m.b.getBallXPos() + m.b.getBallXSpeed());
            m.b.setBallYPos(m.b.getBallYPos() + m.b.getBallYSpeed());
        } else {
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to Start", m.getWidth() / 2, m.getHeight() / 2);
        }
        if (hitWall()) {
            m.b.setBallXSpeed(m.b.getBallXSpeed() * (-1));
        }

        if (hitUpperWall()) {
            m.b.setBallXSpeed(m.b.getBallXSpeed() * (-1));
            m.b.setBallYSpeed(m.b.getBallYSpeed() * (-1));
        }

        if (hitpaddle()) {
            //Decouper en trois coté gauche, milieu puis centre
            if (m.b.getBallXPos() > m.p.getPlayerOneXPos() + m.p.getPLAYER_WIDTH() / 2) {
                System.out.println("Second Half");


                if (m.b.getBallXSpeed() < 0) m.b.setBallXSpeed(m.b.getBallXSpeed() * (-1));
                m.b.setBallYSpeed(m.b.getBallYSpeed() * (-1));
            } else if (m.b.getBallXPos() < m.p.getPlayerOneXPos() + m.p.getPLAYER_WIDTH() / 2) {
                System.out.println("First Half");
                System.out.println("Ball Speed for X : " + m.b.getBallXSpeed() + " Ball speed for Y : " + m.b.getBallYSpeed());
                System.out.println("Ball Position for X : " + m.b.getBallXPos() + " Ball position for Y : " + m.b.getBallYPos());
                System.out.println("Player Position for X : " + m.p.getPlayerOneXPos() + " Player position for Y : " + m.p.getPlayerOneYPos());
                if (m.b.getBallXSpeed() > 0) m.b.setBallXSpeed(m.b.getBallXSpeed() * -1);
                m.b.setBallYSpeed(m.b.getBallYSpeed() * -1);
            }

        }

        if (fallout()) {
            relaunch();
            cmp = 0;

        }
        gc.fillOval(m.b.getBallXPos(), m.b.getBallYPos(), m.b.getBallR(), m.b.getBallR());
        gc.fillRect(m.p.getPlayerOneXPos(), m.p.getPlayerOneYPos(), m.p.getPLAYER_WIDTH(), m.p.getPLAYER_HEIGHT());
    }

    public static void main(String[] args) {
        Application.launch();
    }
}

