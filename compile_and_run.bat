@echo off
echo Compiling Hospital Management System...

:: Create bin directory if it doesn't exist
if not exist bin mkdir bin

:: Compile the Java files
javac -d bin -cp "lib/*" src/main/java/com/hospital/*.java src/main/java/com/hospital/*/*.java src/main/java/com/hospital/*/*/*.java

:: Run the application
echo Running Hospital Management System...
java -cp "bin;lib/*" com.hospital.HospitalManagementSystem

pause 