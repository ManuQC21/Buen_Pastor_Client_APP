package Buen.Pastor.app.entity.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TeacherPayment {

    private int id;
    private Teacher teacher;
    private BigDecimal amount;
    private String paymentDate;
    private String paymentStatus;
    private String paymentReference;
    private int workDays;
    private String educationLevel;
    private String modularCode = "989873";

    public TeacherPayment() {
    }

    public TeacherPayment(int id) {
        this.id = id;
    }

    public TeacherPayment(int id, Teacher teacher, BigDecimal amount, String paymentDate, String paymentStatus, String paymentReference, int workDays, String educationLevel, String modularCode) {
        this.id = id;
        this.teacher = teacher;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.paymentReference = paymentReference;
        this.workDays = workDays;
        this.educationLevel = educationLevel;
        this.modularCode = modularCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public int getWorkDays() {
        return workDays;
    }

    public void setWorkDays(int workDays) {
        this.workDays = workDays;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getModularCode() {
        return modularCode;
    }

    public void setModularCode(String modularCode) {
        this.modularCode = modularCode;
    }

}

