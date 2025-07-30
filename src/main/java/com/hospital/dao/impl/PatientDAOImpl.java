package com.hospital.dao.impl;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;
import com.hospital.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientDAOImpl implements PatientDAO {
    private static final Logger LOGGER = Logger.getLogger(PatientDAOImpl.class.getName());
    private final DatabaseConnection dbConnection;

    public PatientDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public void addPatient(Patient patient) {
        String query = "INSERT INTO patients (name, age, gender, contact_number, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, patient.getName());
            pstmt.setInt(2, patient.getAge());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getContactNumber());
            pstmt.setString(5, patient.getAddress());
            
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Patient added successfully: {0}", patient.getName());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding patient: {0}", patient.getName());
            throw new RuntimeException("Failed to add patient", e);
        }
    }

    @Override
    public void updatePatient(Patient patient) {
        String query = "UPDATE patients SET name=?, age=?, gender=?, contact_number=?, address=? WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, patient.getName());
            pstmt.setInt(2, patient.getAge());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getContactNumber());
            pstmt.setString(5, patient.getAddress());
            pstmt.setInt(6, patient.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                LOGGER.log(Level.WARNING, "No patient found with ID: {0}", patient.getId());
                throw new RuntimeException("Patient not found with ID: " + patient.getId());
            }
            LOGGER.log(Level.INFO, "Patient updated successfully: {0}", patient.getName());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating patient: {0}", patient.getName());
            throw new RuntimeException("Failed to update patient", e);
        }
    }

    @Override
    public void deletePatient(int id) {
        String query = "DELETE FROM patients WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                LOGGER.log(Level.WARNING, "No patient found with ID: {0}", id);
                throw new RuntimeException("Patient not found with ID: " + id);
            }
            LOGGER.log(Level.INFO, "Patient deleted successfully with ID: {0}", id);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting patient with ID: {0}", id);
            throw new RuntimeException("Failed to delete patient", e);
        }
    }

    @Override
    public Patient getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Patient patient = new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("contact_number"),
                    rs.getString("address")
                );
                LOGGER.log(Level.INFO, "Patient retrieved successfully: {0}", patient.getName());
                return patient;
            }
            LOGGER.log(Level.WARNING, "No patient found with ID: {0}", id);
            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving patient with ID: {0}", id);
            throw new RuntimeException("Failed to retrieve patient", e);
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("contact_number"),
                    rs.getString("address")
                ));
            }
            LOGGER.log(Level.INFO, "Retrieved {0} patients", patients.size());
            return patients;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all patients", e);
            throw new RuntimeException("Failed to retrieve patients", e);
        }
    }

    @Override
    public List<Patient> getPatientsByDoctorId(int doctorId) {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT p.* FROM patients p " +
                      "INNER JOIN appointments a ON p.id = a.patient_id " +
                      "WHERE a.doctor_id = ? AND a.status = 'SCHEDULED'";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("contact_number"),
                    rs.getString("address")
                ));
            }
            LOGGER.log(Level.INFO, "Retrieved {0} patients for doctor ID: {1}", new Object[]{patients.size(), doctorId});
            return patients;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving patients for doctor", e);
            throw new RuntimeException("Failed to retrieve patients", e);
        }
    }
} 