package Buen.Pastor.app.entity.service;

import java.time.LocalDate;
import java.util.List;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String dni;
    private String position;
    private String email;
    private String address;

    private LocalDate hiringDate;
    private boolean active;
    private List<TeacherPayment> teacherPayments;



    // Constructor vacío
    public Employee() {
    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String firstName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.position = position;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<TeacherPayment> getTeacherPayments() {
        return teacherPayments;
    }

    public void setTeacherPayments(List<TeacherPayment> teacherPayments) {
        this.teacherPayments = teacherPayments;
    }
}
