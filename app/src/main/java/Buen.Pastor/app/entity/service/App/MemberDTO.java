package Buen.Pastor.app.entity.service.App;

public class MemberDTO {
    private int id;
    private String email;
    private String password;
    private boolean validity;
    private TeacherDTO teacher;

    // Constructor


    public MemberDTO(int id, String email, String password, boolean validity, TeacherDTO teacher) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.validity = validity;
        this.teacher = teacher;
    }

    // Constructor vacío
    public MemberDTO() {
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

    public boolean isValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}