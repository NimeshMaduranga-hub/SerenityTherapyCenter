package lk.ijse.serenity.serenitytherapycenter.util;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Alerts {

    static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    static Alert errorAlert = new Alert(Alert.AlertType.ERROR);


    public Alerts(String title){

        infoAlert.setTitle(title);
        errorAlert.setTitle(title);
    }

    public Alert getSuccessAlert(String contentText){

        infoAlert.setContentText(contentText);

        Image image = new Image(getClass().getResourceAsStream("/images/successIcon.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        infoAlert.getDialogPane().setGraphic(imageView);

        return infoAlert;
    }

    public Alert getErrorAlert(String contentText){

        errorAlert.setContentText(contentText);

        Image image = new Image(getClass().getResourceAsStream("/images/errorIcon.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(50);
        errorAlert.getDialogPane().setGraphic(imageView);

        return errorAlert;
    }

    public Alert getInfoAlert (String contentText){

        infoAlert.setContentText(contentText);

        Image image = new Image(getClass().getResourceAsStream("/images/infoIcon.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        infoAlert.getDialogPane().setGraphic(imageView);

        return infoAlert;
    }

}
