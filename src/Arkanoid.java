import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Arkanoid extends Application{
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
        BorderPane root = new BorderPane();
        root.setTop(menu);
        VBox box = new VBox(20);
        Text ltf = new Text("Levels");
        HBox arka = new HBox(10);
        VBox box2 = new VBox(20);
        //box2.fillWidthProperty();
        //box2.getChildren().add(null);
        arka.setStyle("-fx-border-style: solid inside;");
        ltf.setTextAlignment(TextAlignment.CENTER);
        Button level1 = new Button("Level 1");
        Button level2 = new Button("Level 2");
        Button level3 = new Button("Level 3");
        Button level4 = new Button("Level 4");
        Button level5 = new Button("Level 5");
        Button level6 = new Button("Level 6");
        Button level7 = new Button("Level 7");
        box.getChildren().add(ltf);
        box.getChildren().add(level1);
        box.getChildren().add(level2);
        box.getChildren().add(level3);
        box.getChildren().add(level4);
        box.getChildren().add(level5);
        box.getChildren().add(level6);
        box.getChildren().add(level7);
        root.setLeft(box);
        root.setCenter(arka);
        root.setRight(box2);
        Scene scene = new Scene(root, 768 , 1024);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main");
        primaryStage.show();
    }
    public static void main (String[] args){
        Application.launch();
    }
}
