package lk.disaster.service.impl;

import lk.disaster.dao.DisasterReportDAO;
import lk.disaster.dao.impl.DisasterReportDAOImpl;
import lk.disaster.entity.DisasterReport;
import lk.disaster.service.DisasterReportService;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DisasterReportServiceImpl implements DisasterReportService {

    private final DisasterReportDAO disasterReportDAO;
    private static final Logger logger = LogManager.getLogger(DisasterReportServiceImpl.class);

    public DisasterReportServiceImpl() {
        this.disasterReportDAO = new DisasterReportDAOImpl();
    }

    @Override
    public boolean addReport(DisasterReport disasterReport) throws SQLException {
        try{
            return disasterReportDAO.addReport(disasterReport);
        }catch (SQLException e){
            logger.error("Error in service while adding new report : " + e.getMessage());
            throw  e;
        }

    }


    @Override
    public List<DisasterReport> getAllReports() throws SQLException {
        try {


            return disasterReportDAO.getAllReports();
        } catch (SQLException e){
            logger.error("Error in service while retrieving all reports: "+e.getMessage());
            throw e;
        }
    }
    @Override
    public boolean updateReportStatus(int reportId, String status) throws SQLException {
        try{
            return disasterReportDAO.updateReportStatus(reportId,status);
        }catch (SQLException e){
            logger.error("Error in service while updating report "+reportId+" status to : "+status +" Error: " + e.getMessage());
            throw  e;
        }
    }
    @Override
    public List<DisasterReport> getFilteredReports(String province, String district, String disasterType, String urgencyLevel) throws SQLException {
        try {
            return disasterReportDAO.getFilteredReports(province,district,disasterType,urgencyLevel);
        } catch (SQLException e) {
            logger.error("Error in service while filtering report Error: "+ e.getMessage());
            throw e;
        }
    }
}