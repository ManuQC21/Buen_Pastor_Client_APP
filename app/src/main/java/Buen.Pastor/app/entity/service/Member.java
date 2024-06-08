package Buen.Pastor.app.entity.service;

public class Member {
    private int id;
    private String email;
    private String password;
    private boolean validity;
    private Employee employee;

    public Member() {
    }

    public Member(int id) {
        this.id = id;
    }

    public Member(int id, String email, String password, boolean validity, Employee employee) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.validity = validity;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
