@echo off
echo Compiling Hospital Management System...

:: Create bin directory if it doesn't exist
if not exist bin mkdir bin

:: Compile all Java files
javac -d bin -cp "lib/*" src/main/java/com/hospital/model/*.java src/main/java/com/hospital/dao/*.java src/main/java/com/hospital/dao/impl/*.java src/main/java/com/hospital/service/*.java src/main/java/com/hospital/util/*.java src/main/java/com/hospital/HospitalManagementSystem.java

:: Run the application
echo Running Hospital Management System...
java -cp "bin;lib/*" com.hospital.HospitalManagementSystem

pause 