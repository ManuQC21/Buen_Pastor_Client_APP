package Buen.Pastor.app.entity.service;

public class Member {
    private int id;
    private String email;
    private String password;
    private boolean validity;
    private Teacher teacher;

    public Member() {
    }

    public Member(int id) {
        this.id = id;
    }


    public Member(String email, String password, boolean validity, int teacherId) {
        this.email = email;
        this.password = password;
        this.validity = validity;
        this.teacher = new Teacher(teacherId);
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

}
