import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Arkanoid extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Menu game = new Menu("Game");
        game.setOnAction(event -> launch());
        //Menu file = new Menu("File");
        //file.getItems().add(game);
        MenuBar menu = new MenuBar();
        menu.getMenus().add(game);
        BorderPane root = new BorderPane();
        root.setTop(menu);
        VBox box = new VBox(10);
        Text ltf = new Text("Levels");
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
        
        Scene scene = new Scene(root, 768 , 1024);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main");
        primaryStage.show();
    }
    public static void main (String[] args){
        Application.launch();
    }
}
