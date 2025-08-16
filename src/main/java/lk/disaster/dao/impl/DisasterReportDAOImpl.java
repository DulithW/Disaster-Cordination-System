package lk.disaster.dao.impl;

import lk.disaster.dao.DisasterReportDAO;
import lk.disaster.entity.DisasterReport;
import lk.disaster.util.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisasterReportDAOImpl implements DisasterReportDAO {

    private static final Logger logger = LogManager.getLogger(DisasterReportDAOImpl.class);


    @Override
    public boolean addReport(DisasterReport disasterReport) throws SQLException {
        String sql = "INSERT INTO disaster_reports (full_name, contact_number, email_address, national_id_number, street_address, grama_niladhari_division, district, province, type_of_disaster, date_of_incident, impact_description, number_of_affected_individuals, urgency_level, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, disasterReport.getFullName());
            preparedStatement.setString(2, disasterReport.getContactNumber());
            preparedStatement.setString(3, disasterReport.getEmailAddress());
            preparedStatement.setString(4, disasterReport.getNationalIdNumber());
            preparedStatement.setString(5, disasterReport.getStreetAddress());
            preparedStatement.setString(6, disasterReport.getGramaNiladhariDivision());
            preparedStatement.setString(7, disasterReport.getDistrict());
            preparedStatement.setString(8, disasterReport.getProvince());
            preparedStatement.setString(9, disasterReport.getTypeOfDisaster());
            preparedStatement.setTimestamp(10, Timestamp.valueOf(disasterReport.getDateOfIncident()));
            preparedStatement.setString(11, disasterReport.getImpactDescription());
            preparedStatement.setInt(12, disasterReport.getNumberOfAffectedIndividuals());
            preparedStatement.setString(13, disasterReport.getUrgencyLevel());
            preparedStatement.setString(14, disasterReport.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Report added successfully. Rows Affected: "+ rowsAffected);
            return rowsAffected > 0;
        }catch (SQLException e){
            logger.error("Error adding new report : "+e.getMessage());
            throw e;
        }
    }


    @Override
    public List<DisasterReport> getAllReports() throws SQLException {
        String sql = "SELECT * FROM disaster_reports";
        List<DisasterReport> disasterReports = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                DisasterReport disasterReport = mapResultSetToDisasterReport(resultSet);
                disasterReports.add(disasterReport);
            }

            logger.info("Retrieved all reports successfully.");
            return disasterReports;
        }catch (SQLException e){
            logger.error("Error retrieving reports: "+ e.getMessage());
            throw e;
        }
    }
    @Override
    public boolean updateReportStatus(int reportId, String status) throws SQLException {
        String sql = "UPDATE disaster_reports SET status = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, reportId);

            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Report "+reportId+" status updated to "+status+" . Rows Affected: "+rowsAffected);
            return rowsAffected > 0;
        }catch (SQLException e){
            logger.error("Error updating report status: "+ e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DisasterReport> getFilteredReports(String province, String district, String disasterType, String urgencyLevel) throws SQLException {
        List<DisasterReport> disasterReports = new ArrayList<>();
        String sql = "SELECT * FROM disaster_reports WHERE 1=1 ";
        StringBuilder queryBuilder = new StringBuilder(sql);
        if (province != null && !province.isEmpty()) {
            queryBuilder.append(" AND province = ?");
        }
        if (district != null && !district.isEmpty()) {
            queryBuilder.append(" AND district = ?");
        }
        if (disasterType != null && !disasterType.isEmpty()) {
            queryBuilder.append(" AND type_of_disaster = ?");
        }
        if (urgencyLevel != null && !urgencyLevel.isEmpty()) {
            queryBuilder.append(" AND urgency_level = ?");
        }
        sql = queryBuilder.toString();
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int parameterIndex = 1;

            if(province != null && !province.isEmpty()){
                preparedStatement.setString(parameterIndex++,province);
            }
            if(district != null && !district.isEmpty()){
                preparedStatement.setString(parameterIndex++,district);
            }
            if(disasterType != null && !disasterType.isEmpty()){
                preparedStatement.setString(parameterIndex++,disasterType);
            }
            if(urgencyLevel != null && !urgencyLevel.isEmpty()){
                preparedStatement.setString(parameterIndex++,urgencyLevel);
            }


            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    DisasterReport disasterReport = mapResultSetToDisasterReport(resultSet);
                    disasterReports.add(disasterReport);
                }
            }
            logger.info("Retrieved filtered reports successfully.");
            return disasterReports;

        } catch (SQLException e) {
            logger.error("Error retrieving filtered reports: "+ e.getMessage());
            throw e;
        }
    }


    private DisasterReport mapResultSetToDisasterReport(ResultSet resultSet) throws SQLException {
        DisasterReport disasterReport = new DisasterReport();
        disasterReport.setId(resultSet.getInt("id"));
        disasterReport.setFullName(resultSet.getString("full_name"));
        disasterReport.setContactNumber(resultSet.getString("contact_number"));
        disasterReport.setEmailAddress(resultSet.getString("email_address"));
        disasterReport.setNationalIdNumber(resultSet.getString("national_id_number"));
        disasterReport.setStreetAddress(resultSet.getString("street_address"));
        disasterReport.setGramaNiladhariDivision(resultSet.getString("grama_niladhari_division"));
        disasterReport.setDistrict(resultSet.getString("district"));
        disasterReport.setProvince(resultSet.getString("province"));
        disasterReport.setTypeOfDisaster(resultSet.getString("type_of_disaster"));
        disasterReport.setDateOfIncident(resultSet.getTimestamp("date_of_incident").toLocalDateTime());
        disasterReport.setImpactDescription(resultSet.getString("impact_description"));
        disasterReport.setNumberOfAffectedIndividuals(resultSet.getInt("number_of_affected_individuals"));
        disasterReport.setUrgencyLevel(resultSet.getString("urgency_level"));
        disasterReport.setStatus(resultSet.getString("status"));
        return disasterReport;
    }
}