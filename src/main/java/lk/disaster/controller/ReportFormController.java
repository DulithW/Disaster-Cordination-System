package lk.disaster.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.disaster.entity.DisasterReport;
import lk.disaster.service.DisasterReportService;
import lk.disaster.service.impl.DisasterReportServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ReportFormController {
    private static final Logger logger = LogManager.getLogger(ReportFormController.class);
    private final DisasterReportService disasterReportService;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField contactNumberTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField nationalIdTextField;
    @FXML
    private TextField streetAddressTextField;
    @FXML
    private TextField gramaNiladhariDivisionTextField;
    @FXML
    private ComboBox<String> districtComboBox;
    @FXML
    private ComboBox<String> provinceComboBox;
    @FXML
    private ComboBox<String> disasterTypeComboBox;
    @FXML
    private DatePicker dateOfIncidentDatePicker;
    @FXML
    private TextArea impactDescriptionTextArea;
    @FXML
    private TextField numberOfAffectedIndividualsTextField;
    @FXML
    private ComboBox<String> urgencyLevelComboBox;
    @FXML
    private Label messageLabel;


    public ReportFormController() {
        this.disasterReportService = new DisasterReportServiceImpl();
    }

    @FXML
    public void initialize() {
        // Initialize combo box values for district, province, disaster type and urgency level
        districtComboBox.getItems().addAll("Colombo","Gampaha","Kalutara","Kandy", "Matale", "Nuwara Eliya","Galle","Matara","Hambantota","Jaffna","Kilinochchi","Mannar","Vavuniya","Mullaitivu","Batticaloa", "Ampara", "Trincomalee","Kurunegala","Puttalam", "Anuradhapura","Polonnaruwa","Badulla","Monaragala","Ratnapura","Kegalle");
        provinceComboBox.getItems().addAll("Western Province","Central Province","Southern Province","Northern Province","Eastern Province","North Western Province","North Central Province", "Uva Province","Sabaragamuwa Province");
        disasterTypeComboBox.getItems().addAll("Flood","Landslide","Cyclone", "Drought", "Tsunami", "Wildfire","Epidemic");
        urgencyLevelComboBox.getItems().addAll("Low","Medium","Critical");
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) {
        try {

            String fullName = fullNameTextField.getText();
            String contactNumber = contactNumberTextField.getText();
            String emailAddress = emailAddressTextField.getText();
            String nationalIdNumber = nationalIdTextField.getText();
            String streetAddress = streetAddressTextField.getText();
            String gramaNiladhariDivision = gramaNiladhariDivisionTextField.getText();
            String district = districtComboBox.getValue();
            String province = provinceComboBox.getValue();
            String typeOfDisaster = disasterTypeComboBox.getValue();

            LocalDateTime dateTime = null;
            if(dateOfIncidentDatePicker.getValue()!=null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String date = dateOfIncidentDatePicker.getValue().toString();
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                dateTime= LocalDateTime.parse(date + " " + time, formatter);
            }else{
                logger.error("Date of incident is mandatory");
                messageLabel.setText("Date of incident is mandatory");
                return;
            }

            String impactDescription = impactDescriptionTextArea.getText();
            String urgencyLevel = urgencyLevelComboBox.getValue();
            int numberOfAffectedIndividuals= 0;

            try {
                numberOfAffectedIndividuals = Integer.parseInt(numberOfAffectedIndividualsTextField.getText());

            } catch (NumberFormatException ex) {
                logger.error("Number of affected Individuals must be a number : " + ex.getMessage());
                messageLabel.setText("Number of affected Individuals must be a number");
                return;
            }

            if(Objects.equals(fullName, "") || Objects.equals(contactNumber, "") || Objects.equals(nationalIdNumber, "") || Objects.equals(streetAddress, "") || Objects.equals(gramaNiladhariDivision, "") || Objects.equals(district, "") || Objects.equals(province, "") || Objects.equals(typeOfDisaster, "") || Objects.equals(impactDescription, "") || Objects.equals(urgencyLevel, "")){
                logger.error("All fields with * are mandatory");
                messageLabel.setText("All fields with * are mandatory");
                return;
            }
            DisasterReport disasterReport = new DisasterReport(fullName, contactNumber, emailAddress, nationalIdNumber, streetAddress, gramaNiladhariDivision, district, province, typeOfDisaster,dateTime,impactDescription,numberOfAffectedIndividuals, urgencyLevel);
            boolean added = disasterReportService.addReport(disasterReport);

            if(added){
                clearFields();
                messageLabel.setText("Report added successfully!");
                logger.info("New report added successfully : "+ disasterReport);
            }else{
                messageLabel.setText("Report adding failed!");
                logger.error("Report adding failed!");
            }
        }catch (SQLException ex){
            messageLabel.setText("Report adding failed!");
            logger.error("Error while adding new report : "+ ex.getMessage());
        } catch (Exception ex){
            messageLabel.setText("Report adding failed!");
            logger.error("Error while adding new report : "+ ex.getMessage());
        }
    }
    private void clearFields(){
        fullNameTextField.clear();
        contactNumberTextField.clear();
        emailAddressTextField.clear();
        nationalIdTextField.clear();
        streetAddressTextField.clear();
        gramaNiladhariDivisionTextField.clear();
        districtComboBox.setValue(null);
        provinceComboBox.setValue(null);
        disasterTypeComboBox.setValue(null);
        dateOfIncidentDatePicker.setValue(null);
        impactDescriptionTextArea.clear();
        numberOfAffectedIndividualsTextField.clear();
        urgencyLevelComboBox.setValue(null);
    }

}