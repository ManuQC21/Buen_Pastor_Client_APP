package Buen.Pastor.app.entity.service;

import java.time.LocalDate;
import java.util.List;

public class Administrative {

    private int id;
    private String rol;
    private String fullName;
    private String position;
    private String dni;
    private String email;
    private String phone;
    private String address;
    private String hiringDate;
    private List<TeacherPayment> teacherPaymentsAdministered;

    public Administrative(int id) {
        this.id = id;
    }

    public Administrative() {
    }

    public Administrative(int id, String rol, String fullName, String position, String dni, String email, String phone, String address, String hiringDate, List<TeacherPayment> teacherPaymentsAdministered) {
        this.id = id;
        this.rol = rol;
        this.fullName = fullName;
        this.position = position;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.hiringDate = hiringDate;
        this.teacherPaymentsAdministered = teacherPaymentsAdministered;
    }

    public void setHiringDate(String hiringDate) {
        this.hiringDate = hiringDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<TeacherPayment> getTeacherPaymentsAdministered() {
        return teacherPaymentsAdministered;
    }

    public void setTeacherPaymentsAdministered(List<TeacherPayment> teacherPaymentsAdministered) {
        this.teacherPaymentsAdministered = teacherPaymentsAdministered;
    }
}
