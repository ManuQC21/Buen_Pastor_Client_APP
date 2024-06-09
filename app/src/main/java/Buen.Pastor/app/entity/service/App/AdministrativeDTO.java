package Buen.Pastor.app.entity.service.App;


import java.time.LocalDate;

public class AdministrativeDTO {
    private int id;
    private String fullName;
    private String position;
    private String dni;
    private String email;
    private String phone;
    private String address;
    private LocalDate hiringDate;
    private boolean active;

    public AdministrativeDTO() {
    }

    public AdministrativeDTO(int id) {
        this.id = id;
    }

    public AdministrativeDTO(int id, String fullName, String position, String dni, String email, String phone, String address, LocalDate hiringDate, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.hiringDate = hiringDate;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
