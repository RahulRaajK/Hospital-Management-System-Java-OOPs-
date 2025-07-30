package com.hospital.service;

import com.hospital.dao.PatientDAO;
import com.hospital.dao.impl.PatientDAOImpl;
import com.hospital.model.Patient;
import java.util.List;

public class PatientService {
    private final PatientDAO patientDAO;

    public PatientService() {
        this.patientDAO = new PatientDAOImpl();
    }

    public void addPatient(Patient patient) {
        patientDAO.addPatient(patient);
    }

    public void updatePatient(Patient patient) {
        patientDAO.updatePatient(patient);
    }

    public void deletePatient(int id) {
        patientDAO.deletePatient(id);
    }

    public Patient getPatientById(int id) {
        return patientDAO.getPatientById(id);
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    public List<Patient> getPatientsByDoctorId(int doctorId) {
        return patientDAO.getPatientsByDoctorId(doctorId);
    }
} 