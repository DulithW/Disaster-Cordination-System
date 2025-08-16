package lk.disaster.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.disaster.entity.DisasterReport;
import lk.disaster.service.DisasterReportService;
import lk.disaster.service.impl.DisasterReportServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class AdminViewController {

    private static final Logger logger = LogManager.getLogger(AdminViewController.class);
    private final DisasterReportService disasterReportService;

    @FXML
    private TableView<DisasterReport> disasterReportsTable;
    @FXML
    private TableColumn<DisasterReport, Integer> requestIdColumn;
    @FXML
    private TableColumn<DisasterReport, String> reporterNameColumn;
    @FXML
    private TableColumn<DisasterReport, String> contactInfoColumn;
    @FXML
    private TableColumn<DisasterReport, String> locationColumn;
    @FXML
    private TableColumn<DisasterReport, String> disasterInfoColumn;
    @FXML
    private TableColumn<DisasterReport, String> impactSummaryColumn;
    @FXML
    private TableColumn<DisasterReport, Integer> affectedIndividualsColumn;
    @FXML
    private TableColumn<DisasterReport, String> statusColumn;
    @FXML
    private ComboBox<String> filterProvinceComboBox;
    @FXML
    private ComboBox<String> filterDistrictComboBox;
    @FXML
    private ComboBox<String> filterDisasterTypeComboBox;
    @FXML
    private ComboBox<String> filterUrgencyLevelComboBox;


    public AdminViewController() {
        this.disasterReportService = new DisasterReportServiceImpl();
    }

    @FXML
    public void initialize() {
        // Initialize the combo boxes
        filterProvinceComboBox.getItems().addAll("Western Province","Central Province","Southern Province","Northern Province","Eastern Province","North Western Province","North Central Province", "Uva Province","Sabaragamuwa Province");
        filterDistrictComboBox.getItems().addAll("Colombo","Gampaha","Kalutara","Kandy", "Matale", "Nuwara Eliya","Galle","Matara","Hambantota","Jaffna","Kilinochchi","Mannar","Vavuniya","Mullaitivu","Batticaloa", "Ampara", "Trincomalee","Kurunegala","Puttalam", "Anuradhapura","Polonnaruwa","Badulla","Monaragala","Ratnapura","Kegalle");
        filterDisasterTypeComboBox.getItems().addAll("Flood","Landslide","Cyclone", "Drought", "Tsunami", "Wildfire","Epidemic");
        filterUrgencyLevelComboBox.getItems().addAll("Low","Medium","Critical");

        // Initialize the table columns
        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        reporterNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        contactInfoColumn.setCellValueFactory(cellData -> {
            String contactInfo = cellData.getValue().getContactNumber();
            String emailAddress = cellData.getValue().getEmailAddress();
            if(emailAddress != null && !emailAddress.isEmpty()){
                contactInfo = contactInfo + " / " + emailAddress;
            }
            return new SimpleStringProperty(contactInfo);
        });

        locationColumn.setCellValueFactory(cellData -> {
            String address = cellData.getValue().getStreetAddress() + ", " + cellData.getValue().getGramaNiladhariDivision() + ", " + cellData.getValue().getDistrict() + ", " + cellData.getValue().getProvince();
            return new SimpleStringProperty(address);
        });
        disasterInfoColumn.setCellValueFactory(cellData -> {
            String disasterInfo = cellData.getValue().getTypeOfDisaster() + " " + cellData.getValue().getDateOfIncident().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ", Urgency : " + cellData.getValue().getUrgencyLevel();
            return new SimpleStringProperty(disasterInfo);
        });
        impactSummaryColumn.setCellValueFactory(new PropertyValueFactory<>("impactDescription"));
        affectedIndividualsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAffectedIndividuals"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<String> statusComboBox = new ComboBox<>(FXCollections.observableArrayList("pending", "reviewed", "in progress", "resolved"));

            {
                statusComboBox.setOnAction(event -> {
                    DisasterReport report = getTableRow().getItem();
                    if (report != null) {
                        String selectedStatus = statusComboBox.getValue();
                        try {
                            if (disasterReportService.updateReportStatus(report.getId(), selectedStatus)) {
                                report.setStatus(selectedStatus);
                                logger.info("Report "+report.getId()+" status updated to "+selectedStatus);
                                disasterReportsTable.refresh();
                            }else{
                                logger.error("Failed to update the report "+ report.getId()+" status");
                            }

                        } catch (SQLException e) {
                            logger.error("Error updating report status : "+ e.getMessage());
                            e.printStackTrace();
                        }
                    }

                });
            }

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    statusComboBox.setValue(status);
                    setGraphic(statusComboBox);
                }
            }
        });
        loadReports();
    }

    public void loadReports() {
        try {
            List<DisasterReport> reports = disasterReportService.getAllReports();
            ObservableList<DisasterReport> observableReports = FXCollections.observableArrayList(reports);
            disasterReportsTable.setItems(observableReports);
            logger.info("All reports loaded successfully.");
        } catch (SQLException e) {
            logger.error("Error loading reports "+e.getMessage());
            e.printStackTrace();
            showErrorAlert("Error","Error loading data from database");
        }
    }

    @FXML
    public void handleFilterButton(ActionEvent actionEvent) {
        try {
            String selectedProvince = filterProvinceComboBox.getValue();
            String selectedDistrict = filterDistrictComboBox.getValue();
            String selectedDisasterType = filterDisasterTypeComboBox.getValue();
            String selectedUrgencyLevel = filterUrgencyLevelComboBox.getValue();
            List<DisasterReport> reports = disasterReportService.getFilteredReports(selectedProvince, selectedDistrict, selectedDisasterType, selectedUrgencyLevel);
            ObservableList<DisasterReport> observableReports = FXCollections.observableArrayList(reports);
            disasterReportsTable.setItems(observableReports);
            logger.info("Filtered reports loaded successfully.");
        } catch (SQLException e) {
            logger.error("Error while filtering reports " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Error", "Error filtering data from database");
        }
    }

    @FXML
    public void handleClearFilterButton(ActionEvent actionEvent) {
        filterProvinceComboBox.setValue(null);
        filterDistrictComboBox.setValue(null);
        filterDisasterTypeComboBox.setValue(null);
        filterUrgencyLevelComboBox.setValue(null);
        loadReports();
        logger.info("Filters cleared");

    }

    private void showErrorAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            alert.close();
        }
    }
}