import java.util.*;
public class Main {
    static int nextPatientId = 1;
    static class Patient {
        int id;
        String name;
        int age;
        String gender;
        String disease;
        Patient(int id, String name, int age, String gender, String disease) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.disease = disease;
        }
    }

    static class Doctor {
        int id;
        String name;
        String specialization;
        Doctor(int id, String name, String specialization) {
            this.id = id;
            this.name = name;
            this.specialization = specialization;
        }
    }

    static class Appointment {
        int patientId;
        int doctorId;
        String date;
        String time;
        Appointment(int patientId, int doctorId, String date, String time) {
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.date = date;
            this.time = time;
        }
    }

    private static Patient findPatientById(List<Patient> patients, int id) {
        for (Patient p : patients) if (p.id == id) return p;
        return null;
    }

    private static Doctor findDoctorById(List<Doctor> doctors, int id) {
        for (Doctor d : doctors) if (d.id == id) return d;
        return null;
    }

    private static boolean deletePatientById(List<Patient> patients, int id) {
        Iterator<Patient> it = patients.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    private static boolean deleteDoctorById(List<Doctor> doctors, int id) {
        Iterator<Doctor> it = doctors.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    private static void addPatient(Scanner sc, List<Patient> patients) {
        try {
            int id = nextPatientId++;
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Enter Gender: ");
            String gender = sc.nextLine();
            System.out.print("Enter Disease: ");
            String disease = sc.nextLine();
            patients.add(new Patient(id, name, age, gender, disease));
            System.out.println("Patient added with ID: " + id);
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private static void viewPatients(List<Patient> patients) {
        int n = patients.size();
        if (n == 0) {
            System.out.println("No patients found");
        } else {
            for (int i = 0; i < n; i++) {
                Patient p = patients.get(i);
                System.out.println(p.id + ": " + p.name + ", Age: " + p.age + ", Gender: " + p.gender + ", Disease: " + p.disease);
            }
        }
    }

    private static void deletePatient(Scanner sc, List<Patient> patients, List<Appointment> appointments) {
        try {
            System.out.print("Enter Patient ID to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean ok = deletePatientById(patients, id);
            if (ok) {
                Iterator<Appointment> ita = appointments.iterator();
                while (ita.hasNext()) if (ita.next().patientId == id) ita.remove();
                System.out.println("Patient deleted");
            } else {
                System.out.println("Patient not found");
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private static void addDoctor(Scanner sc, List<Doctor> doctors) {
        try {
            System.out.print("Enter Doctor ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            if (findDoctorById(doctors, id) != null) {
                System.out.println("Doctor ID already exists");
                return;
            }
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Specialization: ");
            String specialization = sc.nextLine();
            doctors.add(new Doctor(id, name, specialization));
            System.out.println("Doctor added");
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private static void viewDoctors(List<Doctor> doctors) {
        int n = doctors.size();
        if (n == 0) {
            System.out.println("No doctors found");
        } else {
            for (int i = 0; i < n; i++) {
                Doctor d = doctors.get(i);
                System.out.println(d.id + ": " + d.name + ", Specialization: " + d.specialization);
            }
        }
    }

    private static void deleteDoctor(Scanner sc, List<Doctor> doctors, List<Appointment> appointments) {
        try {
            System.out.print("Enter Doctor ID to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean ok = deleteDoctorById(doctors, id);
            if (ok) {
                Iterator<Appointment> ita = appointments.iterator();
                while (ita.hasNext()) if (ita.next().doctorId == id) ita.remove();
                System.out.println("Doctor deleted");
            } else {
                System.out.println("Doctor not found");
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private static void bookAppointment(Scanner sc, List<Patient> patients, List<Doctor> doctors, List<Appointment> appointments) {
        try {
            System.out.print("Enter Patient ID: ");
            int pid = Integer.parseInt(sc.nextLine().trim());
            Patient p = findPatientById(patients, pid);
            if (p == null) {
                System.out.println("Patient not found");
                return;
            }
            System.out.print("Enter Doctor ID: ");
            int did = Integer.parseInt(sc.nextLine().trim());
            Doctor d = findDoctorById(doctors, did);
            if (d == null) {
                System.out.println("Doctor not found");
                return;
            }
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = sc.nextLine();
            System.out.print("Enter Time (HH:MM): ");
            String time = sc.nextLine();
            appointments.add(new Appointment(pid, did, date, time));
            System.out.println("Appointment booked");
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private static void viewAppointments(List<Patient> patients, List<Doctor> doctors, List<Appointment> appointments) {
        int n = appointments.size();
        if (n == 0) {
            System.out.println("No appointments found");
        } else {
            for (int i = 0; i < n; i++) {
                Appointment a = appointments.get(i);
                Patient p = findPatientById(patients, a.patientId);
                Doctor d = findDoctorById(doctors, a.doctorId);
                String pn = p == null ? ("Patient " + a.patientId) : p.name;
                String dn = d == null ? ("Doctor " + a.doctorId) : d.name;
                System.out.println("Patient: " + pn + ", Doctor: " + dn + ", Date: " + a.date + ", Time: " + a.time);
            }
        }
    }

    private static void patientMenu(Scanner sc, List<Patient> patients, List<Appointment> appointments) {
        while (true) {
            System.out.println("\nPatient Menu");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Delete Patient");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            int c;
            try {
                c = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            if (c == 1) addPatient(sc, patients);
            else if (c == 2) viewPatients(patients);
            else if (c == 3) deletePatient(sc, patients, appointments);
            else if (c == 4) break;
            else System.out.println("Invalid choice");
        }
    }

    private static void doctorMenu(Scanner sc, List<Doctor> doctors, List<Appointment> appointments) {
        while (true) {
            System.out.println("\nDoctor Menu");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Delete Doctor");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            int c;
            try {
                c = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            if (c == 1) addDoctor(sc, doctors);
            else if (c == 2) viewDoctors(doctors);
            else if (c == 3) deleteDoctor(sc, doctors, appointments);
            else if (c == 4) break;
            else System.out.println("Invalid choice");
        }
    }

    private static void appointmentMenu(Scanner sc, List<Patient> patients, List<Doctor> doctors, List<Appointment> appointments) {
        while (true) {
            System.out.println("\nAppointment Menu");
            System.out.println("1. Book Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");
            int c;
            try {
                c = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            if (c == 1) bookAppointment(sc, patients, doctors, appointments);
            else if (c == 2) viewAppointments(patients, doctors, appointments);
            else if (c == 3) break;
            else System.out.println("Invalid choice");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Patient> patients = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();
        while (true) {
            System.out.println("\nHospital Management System");
            System.out.println("1. Patient");
            System.out.println("2. Doctor");
            System.out.println("3. Appointment");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            if (choice == 1) {
                patientMenu(sc, patients, appointments);
            } else if (choice == 2) {
                doctorMenu(sc, doctors, appointments);
            } else if (choice == 3) {
                appointmentMenu(sc, patients, doctors, appointments);
            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}


