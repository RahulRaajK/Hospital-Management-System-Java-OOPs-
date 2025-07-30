package com.hospital.dao.impl;

import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Appointment;
import com.hospital.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDAOImpl implements AppointmentDAO {
    private static final Logger LOGGER = Logger.getLogger(AppointmentDAOImpl.class.getName());
    private final DatabaseConnection dbConnection;

    public AppointmentDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public void addAppointment(Appointment appointment) {
        String query = "INSERT INTO appointments (patient_id, doctor_id, appointment_time, status, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setTimestamp(3, appointment.getAppointmentTime());
            pstmt.setString(4, appointment.getStatus());
            pstmt.setString(5, appointment.getDescription());
            
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Appointment added successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding appointment", e);
            throw new RuntimeException("Failed to add appointment", e);
        }
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        String query = "UPDATE appointments SET patient_id=?, doctor_id=?, appointment_time=?, status=?, description=? WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setTimestamp(3, appointment.getAppointmentTime());
            pstmt.setString(4, appointment.getStatus());
            pstmt.setString(5, appointment.getDescription());
            pstmt.setInt(6, appointment.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                LOGGER.log(Level.WARNING, "No appointment found with ID: {0}", appointment.getId());
                throw new RuntimeException("Appointment not found with ID: " + appointment.getId());
            }
            LOGGER.log(Level.INFO, "Appointment updated successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating appointment", e);
            throw new RuntimeException("Failed to update appointment", e);
        }
    }

    @Override
    public void deleteAppointment(int id) {
        String query = "DELETE FROM appointments WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                LOGGER.log(Level.WARNING, "No appointment found with ID: {0}", id);
                throw new RuntimeException("Appointment not found with ID: " + id);
            }
            LOGGER.log(Level.INFO, "Appointment deleted successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting appointment", e);
            throw new RuntimeException("Failed to delete appointment", e);
        }
    }

    @Override
    public Appointment getAppointmentById(int id) {
        String query = "SELECT * FROM appointments WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Appointment appointment = new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getTimestamp("appointment_time"),
                    rs.getString("status"),
                    rs.getString("description")
                );
                LOGGER.log(Level.INFO, "Appointment retrieved successfully");
                return appointment;
            }
            LOGGER.log(Level.WARNING, "No appointment found with ID: {0}", id);
            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving appointment", e);
            throw new RuntimeException("Failed to retrieve appointment", e);
        }
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments";
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getTimestamp("appointment_time"),
                    rs.getString("status"),
                    rs.getString("description")
                ));
            }
            LOGGER.log(Level.INFO, "Retrieved {0} appointments", appointments.size());
            return appointments;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving appointments", e);
            throw new RuntimeException("Failed to retrieve appointments", e);
        }
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE patient_id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getTimestamp("appointment_time"),
                    rs.getString("status"),
                    rs.getString("description")
                ));
            }
            LOGGER.log(Level.INFO, "Retrieved {0} appointments for patient ID: {1}", new Object[]{appointments.size(), patientId});
            return appointments;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving appointments for patient", e);
            throw new RuntimeException("Failed to retrieve appointments", e);
        }
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE doctor_id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getTimestamp("appointment_time"),
                    rs.getString("status"),
                    rs.getString("description")
                ));
            }
            LOGGER.log(Level.INFO, "Retrieved {0} appointments for doctor ID: {1}", new Object[]{appointments.size(), doctorId});
            return appointments;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving appointments for doctor", e);
            throw new RuntimeException("Failed to retrieve appointments", e);
        }
    }
} 