package MediaCollectie;

import MediaCollectie.data.MediaObject;
import MediaCollectie.util.HandlerLocation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class UIMain extends Application {
    public static Stage UIStage;
    public static ArrayList<MediaObject> mediaList;
    public static double latitude;
    public static double longitude;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        UIStage = primaryStage;
        mediaList = new ArrayList<>();

        String[] location = HandlerLocation.getLatitudeAndLongitude("");
        latitude = HandlerLocation.D2R(Double.parseDouble(location[0]));
        longitude = HandlerLocation.D2R(Double.parseDouble(location[1]));

        Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));

        primaryStage.setScene(new Scene(root, 585, 395));
        primaryStage.setTitle("Datastructuren");
        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
