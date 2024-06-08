package Buen.Pastor.app.entity.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public class TeacherPayment {

    private int id;
    private Employee employee;
    private Administrative administrative;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentStatus;
    private String paymentReference;
    private int workDays;
    private String educationLevel;
    private String modularCode = "989873";
    private List<PaymentDetails> paymentDetails;
}
