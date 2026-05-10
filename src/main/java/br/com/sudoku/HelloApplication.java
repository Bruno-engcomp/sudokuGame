package br.com.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HelloApplication.class.getResource("Sudoku.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JavaFX Sudoku");
        stage.setScene(scene);
        stage.show();
    }
}
