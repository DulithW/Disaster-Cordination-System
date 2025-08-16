package lk.disaster.service;

import lk.disaster.entity.DisasterReport;
import java.sql.SQLException;
import java.util.List;

public interface DisasterReportService {
    boolean addReport(DisasterReport disasterReport) throws SQLException;
    List<DisasterReport> getAllReports() throws SQLException;
    boolean updateReportStatus(int reportId, String status) throws SQLException;
    List<DisasterReport> getFilteredReports(String province,String district,String disasterType,String urgencyLevel) throws SQLException;
}