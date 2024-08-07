package fr.afpa.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DisplayQRCode {

    public static void showImage(String imagePath) {
        // Chargez l'image depuis le classpath
        Image image = null;
        try {
            image = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            System.err.println("Erreur de chargement de l'image : " + imagePath);
            e.printStackTrace();
            return;
        }

        if (image.isError()) {
            System.err.println("Erreur de chargement de l'image : " + imagePath);
            return;
        }

        // Créez un ImageView pour afficher l'image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(true);

        // Créez un conteneur pour l'ImageView
        Pane pane = new Pane();
        pane.getChildren().add(imageView);

        // Créez une scène avec le conteneur et une taille définie
        Scene scene = new Scene(pane, 250, 250);

        // Créez un nouveau Stage
        Stage stage = new Stage();
        stage.setTitle(" QRCode");
        Image icon = new Image(DisplayQRCode.class.getResourceAsStream("/logo_javaCard.PNG"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); // Bloc la scène principale
        stage.show();
    }
}