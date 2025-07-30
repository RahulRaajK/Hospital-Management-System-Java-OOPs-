package com.hospital;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.PatientService;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Hospital Management System ===");
            System.out.println("1. Doctor Management");
            System.out.println("2. Patient Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> doctorManagement();
                case 2 -> patientManagement();
                case 3 -> appointmentManagement();
                case 4 -> {
                    System.out.println("Thank you for using Hospital Management System!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void doctorManagement() {
        while (true) {
            System.out.println("\n=== Doctor Management ===");
            System.out.println("1. Add Doctor");
            System.out.println("2. Update Doctor");
            System.out.println("3. Delete Doctor");
            System.out.println("4. View All Doctors");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> updateDoctor();
                case 3 -> deleteDoctor();
                case 4 -> viewAllDoctors();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void patientManagement() {
        while (true) {
            System.out.println("\n=== Patient Management ===");
            System.out.println("1. Add Patient");
            System.out.println("2. Update Patient");
            System.out.println("3. Delete Patient");
            System.out.println("4. View All Patients");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addPatient();
                case 2 -> updatePatient();
                case 3 -> deletePatient();
                case 4 -> viewAllPatients();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void appointmentManagement() {
        while (true) {
            System.out.println("\n=== Appointment Management ===");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. Update Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. View All Appointments");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> scheduleAppointment();
                case 2 -> updateAppointment();
                case 3 -> cancelAppointment();
                case 4 -> viewAllAppointments();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void addDoctor() {
        System.out.println("\n=== Add Doctor ===");
        Doctor doctor = new Doctor();
        
        System.out.print("Enter doctor name: ");
        doctor.setName(scanner.nextLine());
        
        System.out.print("Enter specialization: ");
        doctor.setSpecialization(scanner.nextLine());
        
        System.out.print("Enter contact number: ");
        doctor.setContactNumber(scanner.nextLine());
        
        System.out.print("Enter email: ");
        doctor.setEmail(scanner.nextLine());

        doctorService.addDoctor(doctor);
        System.out.println("Doctor added successfully!");
    }

    private static void updateDoctor() {
        System.out.println("\n=== Update Doctor ===");
        System.out.print("Enter doctor ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.print("Enter new name (current: " + doctor.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) doctor.setName(name);

        System.out.print("Enter new specialization (current: " + doctor.getSpecialization() + "): ");
        String specialization = scanner.nextLine();
        if (!specialization.isEmpty()) doctor.setSpecialization(specialization);

        System.out.print("Enter new contact number (current: " + doctor.getContactNumber() + "): ");
        String contactNumber = scanner.nextLine();
        if (!contactNumber.isEmpty()) doctor.setContactNumber(contactNumber);

        System.out.print("Enter new email (current: " + doctor.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) doctor.setEmail(email);

        doctorService.updateDoctor(doctor);
        System.out.println("Doctor updated successfully!");
    }

    private static void deleteDoctor() {
        System.out.println("\n=== Delete Doctor ===");
        System.out.print("Enter doctor ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        doctorService.deleteDoctor(id);
        System.out.println("Doctor deleted successfully!");
    }

    private static void viewAllDoctors() {
        System.out.println("\n=== All Doctors ===");
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found!");
            return;
        }

        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    private static void addPatient() {
        System.out.println("\n=== Add Patient ===");
        Patient patient = new Patient();
        
        System.out.print("Enter patient name: ");
        patient.setName(scanner.nextLine());
        
        System.out.print("Enter age: ");
        patient.setAge(scanner.nextInt());
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter gender: ");
        patient.setGender(scanner.nextLine());
        
        System.out.print("Enter contact number: ");
        patient.setContactNumber(scanner.nextLine());
        
        System.out.print("Enter address: ");
        patient.setAddress(scanner.nextLine());

        patientService.addPatient(patient);
        System.out.println("Patient added successfully!");
    }

    private static void updatePatient() {
        System.out.println("\n=== Update Patient ===");
        System.out.print("Enter patient ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }

        System.out.print("Enter new name (current: " + patient.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) patient.setName(name);

        System.out.print("Enter new age (current: " + patient.getAge() + "): ");
        String ageStr = scanner.nextLine();
        if (!ageStr.isEmpty()) patient.setAge(Integer.parseInt(ageStr));

        System.out.print("Enter new gender (current: " + patient.getGender() + "): ");
        String gender = scanner.nextLine();
        if (!gender.isEmpty()) patient.setGender(gender);

        System.out.print("Enter new contact number (current: " + patient.getContactNumber() + "): ");
        String contactNumber = scanner.nextLine();
        if (!contactNumber.isEmpty()) patient.setContactNumber(contactNumber);

        System.out.print("Enter new address (current: " + patient.getAddress() + "): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) patient.setAddress(address);

        patientService.updatePatient(patient);
        System.out.println("Patient updated successfully!");
    }

    private static void deletePatient() {
        System.out.println("\n=== Delete Patient ===");
        System.out.print("Enter patient ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        patientService.deletePatient(id);
        System.out.println("Patient deleted successfully!");
    }

    private static void viewAllPatients() {
        System.out.println("\n=== All Patients ===");
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found!");
            return;
        }

        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private static void scheduleAppointment() {
        System.out.println("\n=== Schedule Appointment ===");
        Appointment appointment = new Appointment();
        
        System.out.print("Enter patient ID: ");
        appointment.setPatientId(scanner.nextInt());
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter doctor ID: ");
        appointment.setDoctorId(scanner.nextInt());
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        try {
            appointment.setAppointmentTime(new Timestamp(dateFormat.parse(dateTimeStr).getTime()));
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please use yyyy-MM-dd HH:mm");
            return;
        }
        
        System.out.print("Enter description: ");
        appointment.setDescription(scanner.nextLine());
        
        appointment.setStatus("SCHEDULED");
        
        appointmentService.addAppointment(appointment);
        System.out.println("Appointment scheduled successfully!");
    }

    private static void updateAppointment() {
        System.out.println("\n=== Update Appointment ===");
        System.out.print("Enter appointment ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            System.out.println("Appointment not found!");
            return;
        }

        System.out.print("Enter new date and time (yyyy-MM-dd HH:mm) or press Enter to skip: ");
        String dateTimeStr = scanner.nextLine();
        if (!dateTimeStr.isEmpty()) {
            try {
                appointment.setAppointmentTime(new Timestamp(dateFormat.parse(dateTimeStr).getTime()));
            } catch (ParseException e) {
                System.out.println("Invalid date format! Please use yyyy-MM-dd HH:mm");
                return;
            }
        }

        System.out.print("Enter new status (SCHEDULED/COMPLETED/CANCELLED) or press Enter to skip: ");
        String status = scanner.nextLine();
        if (!status.isEmpty()) {
            appointment.setStatus(status);
        }

        System.out.print("Enter new description or press Enter to skip: ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            appointment.setDescription(description);
        }

        appointmentService.updateAppointment(appointment);
        System.out.println("Appointment updated successfully!");
    }

    private static void cancelAppointment() {
        System.out.println("\n=== Cancel Appointment ===");
        System.out.print("Enter appointment ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            System.out.println("Appointment not found!");
            return;
        }

        appointment.setStatus("CANCELLED");
        appointmentService.updateAppointment(appointment);
        System.out.println("Appointment cancelled successfully!");
    }

    private static void viewAllAppointments() {
        System.out.println("\n=== All Appointments ===");
        List<Appointment> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found!");
            return;
        }

        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }
} 