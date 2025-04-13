package Models;

public class Attendance extends Student {
    public int id;
    public int studentNumber;
    public String name;

    public Attendance(int id, int studentNumber, String name) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getStudentNumber() {
        return studentNumber;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }
}
