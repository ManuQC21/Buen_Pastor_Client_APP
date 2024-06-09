package Buen.Pastor.app.entity.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TeacherPayment {

    private int id;
    private Teacher teacher;
    private Administrative administrative;
    private BigDecimal amount;
    private String paymentDate;
    private String paymentStatus;
    private String paymentReference;
    private int workDays;
    private String educationLevel;
    private String modularCode = "989873";
    private List<PaymentDetails> paymentDetails;

    public TeacherPayment() {
    }

    public TeacherPayment(int id) {
        this.id = id;
    }

    public TeacherPayment(int id, Teacher teacher, Administrative administrative, BigDecimal amount, String paymentDate, String paymentStatus, String paymentReference, int workDays, String educationLevel, String modularCode, List<PaymentDetails> paymentDetails) {
        this.id = id;
        this.teacher = teacher;
        this.administrative = administrative;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.paymentReference = paymentReference;
        this.workDays = workDays;
        this.educationLevel = educationLevel;
        this.modularCode = modularCode;
        this.paymentDetails = paymentDetails;
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

    public Administrative getAdministrative() {
        return administrative;
    }

    public void setAdministrative(Administrative administrative) {
        this.administrative = administrative;
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

    public List<PaymentDetails> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentDetails> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}

