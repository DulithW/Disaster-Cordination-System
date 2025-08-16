package lk.disaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.disaster.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class MainApp extends Application {

    private static final Logger logger = LogManager.getLogger(MainApp.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            URL U1=getClass().getResource("../MainView.fxml");
            if (U1==null){
                System.out.println("u1 null");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 800);
            primaryStage.setTitle("Disaster Coordination System");
            primaryStage.setScene(scene);
            primaryStage.show();
            logger.info("Application started.");

        } catch (Exception e) {
            logger.error("Error starting the application : "+e.getMessage());
            throw e;
        }
    }
    @Override
    public void stop() {
        DBConnection.shutdown();
        logger.info("Application stoped.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}