package lk.disaster.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import lk.disaster.entity.DisasterReport;
import lk.disaster.service.DisasterReportService;
import lk.disaster.service.impl.DisasterReportServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController {

    private static final Logger logger = LogManager.getLogger(DashboardController.class);
    private final  DisasterReportService disasterReportService;

    @FXML
    private Label totalDisastersLabel;
    @FXML
    private Label pendingDisastersLabel;
    @FXML
    private Label inProgressDisastersLabel;
    @FXML
    private Label resolvedDisastersLabel;

    @FXML
    private PieChart disasterTypePieChart;

    public DashboardController() {
        this.disasterReportService = new DisasterReportServiceImpl();
    }

    @FXML
    public void initialize() {
        loadDashboardData();
    }
    private void loadDashboardData(){
        try {
            List<DisasterReport> reports = disasterReportService.getAllReports();
            for (DisasterReport report : reports) {
                System.out.println(report);
            }
            int total = reports.size();
            int pending = 0;
            int inProgress = 0;
            int resolved = 0;

            Map<String,Integer> disasterTypeCounts = new HashMap<>();
            for (DisasterReport report: reports){
                if(report.getStatus().equals("pending")){
                    pending++;
                } else if (report.getStatus().equals("in progress")) {
                    inProgress++;
                } else if (report.getStatus().equals("resolved")) {
                    resolved++;
                }
                String disasterType = report.getTypeOfDisaster();
                disasterTypeCounts.put(disasterType,disasterTypeCounts.getOrDefault(disasterType, 0)+1);
            }


            totalDisastersLabel.setText(String.valueOf(total));
            pendingDisastersLabel.setText(String.valueOf(pending));
            inProgressDisastersLabel.setText(String.valueOf(inProgress));
            resolvedDisastersLabel.setText(String.valueOf(resolved));
            loadDisasterTypePieChart(disasterTypeCounts);
            logger.info("Dashboard data loaded successfully.");
        } catch (SQLException e) {
            logger.error("Error loading dashboard data : "+e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadDisasterTypePieChart(Map<String,Integer> disasterTypeCounts){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String,Integer> entry: disasterTypeCounts.entrySet()){
            pieChartData.add(new PieChart.Data(entry.getKey(),entry.getValue()));
        }
        disasterTypePieChart.setData(pieChartData);
    }
}