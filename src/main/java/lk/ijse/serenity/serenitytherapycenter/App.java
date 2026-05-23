package lk.ijse.serenity.serenitytherapycenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(App.class.getResource(
                "/lk/ijse/serenity/serenitytherapycenter/view/programs_form.fxml"
        ));
        stage = primaryStage;

        stage.setTitle("Serenity Mental Health Therapy Center");
        stage.setResizable(false);
        stage.centerOnScreen();

        Scene scene = new Scene(loadFXML("login_form"), 1360, 700);

        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {

        Scene scene;

        if (fxml.equals("login_form")) {
            scene = new Scene(loadFXML(fxml), 1360, 700);
        } else {
            scene = new Scene(loadFXML(fxml), 1360, 700);
        }

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static Parent loadFXML(String fxml) throws IOException {

        String path = "/lk/ijse/serenity/serenitytherapycenter/view/" + fxml + ".fxml";

        URL url = App.class.getResource(path);

        if (url == null) {
            throw new RuntimeException("FXML NOT FOUND: " + path);
        }

        return new FXMLLoader(url).load();
    }
}