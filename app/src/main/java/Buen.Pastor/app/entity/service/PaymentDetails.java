package Buen.Pastor.app.entity.service;

import java.math.BigDecimal;

public class PaymentDetails {
    private int id;
    private TeacherPayment teacherPayment;
    private String description;
    private String paymentCategory;
    private BigDecimal amount;

    public PaymentDetails() {
    }

    public PaymentDetails(int id) {
        this.id = id;
    }

    public PaymentDetails(int id, TeacherPayment teacherPayment, String description, String paymentCategory, BigDecimal amount) {
        this.id = id;
        this.teacherPayment = teacherPayment;
        this.description = description;
        this.paymentCategory = paymentCategory;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TeacherPayment getTeacherPayment() {
        return teacherPayment;
    }

    public void setTeacherPayment(TeacherPayment teacherPayment) {
        this.teacherPayment = teacherPayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(String paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
