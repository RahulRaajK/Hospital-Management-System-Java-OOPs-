package com.hospital.service;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.impl.DoctorDAOImpl;
import com.hospital.model.Doctor;
import java.util.List;

public class DoctorService {
    private final DoctorDAO doctorDAO;

    public DoctorService() {
        this.doctorDAO = new DoctorDAOImpl();
    }

    public void addDoctor(Doctor doctor) {
        doctorDAO.addDoctor(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        doctorDAO.updateDoctor(doctor);
    }

    public void deleteDoctor(int id) {
        doctorDAO.deleteDoctor(id);
    }

    public Doctor getDoctorById(int id) {
        return doctorDAO.getDoctorById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorDAO.getAllDoctors();
    }
} 