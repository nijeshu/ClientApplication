package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MovingRobot extends Application {

    private TextArea textarea;
    private String[] movements;
    private Gateway gateway;
    private TextField textField;
    private String handle;
    private ArrayList<CirclePane> circlesList;

    public MovingRobot() {

        movements = null;
        circlesList = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) {

        VBox vBox = new VBox();
        textarea = new TextArea();
        vBox.getChildren().add(textarea);
        textField = new TextField();
        vBox.getChildren().add(textField);
        Button submit = new Button("Submit");
        vBox.getChildren().add(submit);
        BorderPane borderPane = new BorderPane();
      //  borderPane.setTop(circlePane);
        borderPane.setBottom(vBox);

        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setTitle("ROBOT MOVEMENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setScene(scene);
        submit.setOnAction(e -> {

            movements = textarea.getText().split("\n");
            handle = textField.getText();
            gateway.sendHandle(handle);

            //for (int i = 0; i < movements.length; i++) {
            gateway.sendInstructions(movements);
            //CirclePane circlePane = new CirclePane();
            //circlePane.updatePosition(gateway.getNewPosition(1));
            /*
             if (movements[i].equals("left")) {
             circlePane.left();
             } else if (movements[i].equals("right")) {
             circlePane.right();
             } else if (movements[i].equals("up")) {
             circlePane.up();
             } else if (movements[i].equals("down")) {
             circlePane.down();
             }
             */

        });
    }

    class CirclePane extends Pane {

        private Circle circle = new Circle(15);

        public CirclePane(Color color, RobotPosition p) {
            getChildren().add(circle);
            circle.setStroke(Color.BLACK);
            circle.setFill(color);
            circle.setCenterX(300);
            circle.setCenterY(175);
        }
        /*
         public void left() {
         circle.setCenterX(circle.getCenterX() - 10);
         }

         public void right() {
         circle.setCenterX(circle.getCenterX() + 10);
         }

         public void up() {
         circle.setCenterY(circle.getCenterY() - 10);
         }

         public void down() {
         circle.setCenterY(circle.getCenterY() + 10);
         }*/

        public void updatePosition(RobotPosition p) {
            circle.setCenterX(p.getX());
            circle.setCenterY(p.getY());

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
