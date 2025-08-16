package lk.disaster.controller;

import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);
    @FXML
    public void initialize(){
        logger.info("Main view initialized.");
    }
}