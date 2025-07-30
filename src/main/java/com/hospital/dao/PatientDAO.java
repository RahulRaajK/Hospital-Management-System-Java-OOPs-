package com.hospital.dao;

import com.hospital.model.Patient;
import java.util.List;

public interface PatientDAO {
    void addPatient(Patient patient);
    void updatePatient(Patient patient);
    void deletePatient(int id);
    Patient getPatientById(int id);
    List<Patient> getAllPatients();
    List<Patient> getPatientsByDoctorId(int doctorId);
} 