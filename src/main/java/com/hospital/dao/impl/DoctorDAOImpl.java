package com.hospital.dao.impl;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;
import com.hospital.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorDAOImpl implements DoctorDAO {
    private static final Logger LOGGER = Logger.getLogger(DoctorDAOImpl.class.getName());
    private final DatabaseConnection dbConnection;

    public DoctorDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public void addDoctor(Doctor doctor) {
        String query = "INSERT INTO doctors (name, specialization, contact_number, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getSpecialization());
            pstmt.setString(3, doctor.getContactNumber());
            pstmt.setString(4, doctor.getEmail());
            
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Doctor added successfully: {0}", doctor.getName());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding doctor: {0}", doctor.getName());
            throw new RuntimeException("Failed to add doctor", e);
        }
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        String query = "UPDATE doctors SET name=?, specialization=?, contact_number=?, email=? WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getSpecialization());
            pstmt.setString(3, doctor.getContactNumber());
            pstmt.setString(4, doctor.getEmail());
            pstmt.setInt(5, doctor.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                LOGGER.log(Level.WARNING, "No doctor found with ID: {0}", doctor.getId());
                throw new RuntimeException("Doctor not found with ID: " + doctor.getId());
            }
            LOGGER.log(Level.INFO, "Doctor updated successfully: {0}", doctor.getName());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating doctor: {0}", doctor.getName());
            throw new RuntimeException("Failed to update doctor", e);
        }
    }

    @Override
    public void deleteDoctor(int id) {
        String query = "DELETE FROM doctors WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                LOGGER.log(Level.WARNING, "No doctor found with ID: {0}", id);
                throw new RuntimeException("Doctor not found with ID: " + id);
            }
            LOGGER.log(Level.INFO, "Doctor deleted successfully with ID: {0}", id);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting doctor with ID: {0}", id);
            throw new RuntimeException("Failed to delete doctor", e);
        }
    }

    @Override
    public Doctor getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("contact_number"),
                    rs.getString("email")
                );
                LOGGER.log(Level.INFO, "Doctor retrieved successfully: {0}", doctor.getName());
                return doctor;
            }
            LOGGER.log(Level.WARNING, "No doctor found with ID: {0}", id);
            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving doctor with ID: {0}", id);
            throw new RuntimeException("Failed to retrieve doctor", e);
        }
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctors";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                doctors.add(new Doctor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("contact_number"),
                    rs.getString("email")
                ));
            }
            LOGGER.log(Level.INFO, "Retrieved {0} doctors", doctors.size());
            return doctors;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all doctors", e);
            throw new RuntimeException("Failed to retrieve doctors", e);
        }
    }
} 