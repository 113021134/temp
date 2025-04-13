package Models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import DataAccess.Guardiandao;
import DataAccess.Studentdao;
import Enums.Gender;
import Enums.Relationship;
import Utils.HandleError;

public class Classes {
    private static ArrayList<Student> students;

    public Classes(){
        getStudentsData();
    }

    private void getStudentsData(){
        try {
            students = new ArrayList<>();

            System.out.println("Reading db for students information");
    
            ArrayList<Student> tempdata = Studentdao.readStudentData();
    
            for(int i = 0; i < tempdata.size(); i++){
                students.add(tempdata.get(i));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    public static void addStudent(int studentNumber, String name, float height, float weight, LocalDate dob, String address, Gender gender, String profileImageLocation, String studentCardLocation, String familyCardLocation, String phoneNumber, String guardianName, String relationship, Relationship relationshipEnum){
        int studentsId = Studentdao.createStudent(studentNumber, name, height, weight, dob, address, gender, profileImageLocation, studentCardLocation, familyCardLocation);

        if(relationshipEnum == Relationship.PARENTS) relationship = "Parents";
        else if(relationshipEnum == Relationship.GRAND_PARENTS) relationship = "Grand Parents";

        Guardiandao.addGuardian(studentsId, phoneNumber, guardianName, relationshipEnum, relationship);

        BufferedImage profileImage = null;
        BufferedImage studentCardImage = null;
        BufferedImage familyCardImage = null;
        try {
            if(profileImageLocation != "")
                profileImage = ImageIO.read(new File(profileImageLocation));
            if(studentCardLocation != "")
                studentCardImage = ImageIO.read(new File(studentCardLocation));
            if(familyCardLocation != "")
                familyCardImage = ImageIO.read(new File(familyCardLocation));
        } catch (Exception e) {
            HandleError.handleError(e);
        }
        students.add(new Student(studentsId, studentNumber, name, height, weight, dob, address, gender, profileImage, studentCardImage, familyCardImage, phoneNumber, guardianName, relationship, relationshipEnum));
    }

    public static InputStream bufferedImageConvertToInputStream(BufferedImage image, String formatName) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, os);
        
        return new ByteArrayInputStream(os.toByteArray());
    }

    public static void updateStudent(int id, int studentNumber, String name, float height, float weight, LocalDate dob, String address, Gender gender, 
    BufferedImage profileImageLocation, String profileImageExtension, 
    BufferedImage studentCardLocation, String studentCardImageExtension, 
    BufferedImage familyCardLocation, String familyCardImageExtension,
    Integer index, String guardianName, String phoneNumber, Relationship relationshipEnum, String relationship,
    Sessions sessions)
    {
        InputStream profileInputStream = null;
        InputStream studentCardInputStream = null;
        InputStream familyCardInputStream = null;
        
        try {
            if(profileImageLocation != null) {
                if(profileImageExtension.isEmpty()) profileInputStream = Studentdao.getStudentProfileInputStream(id);
                else profileInputStream = bufferedImageConvertToInputStream(profileImageLocation, profileImageExtension);
            }
            
            if(studentCardLocation != null) {
                if(studentCardImageExtension.isEmpty()) studentCardInputStream = Studentdao.getStudentStudentCardInputStream(id);
                else studentCardInputStream = bufferedImageConvertToInputStream(studentCardLocation, studentCardImageExtension);
            }
            if(familyCardLocation != null) {
                if(familyCardImageExtension.isEmpty()) familyCardInputStream = Studentdao.getStudentFamilyCardInputStream(id);
                else familyCardInputStream = bufferedImageConvertToInputStream(familyCardLocation, familyCardImageExtension);
            }
        } catch (Exception e) {
            HandleError.handleError(e);
        }

        Studentdao.updateStudentById(id, studentNumber, name, height, weight, gender, dob, address, profileInputStream, studentCardInputStream, familyCardInputStream);

        Guardiandao.updateGuardian(id, phoneNumber, guardianName, relationshipEnum, relationship);

        students.set(index, Studentdao.readStudentDataById(id));
        students.get(index).setSession(sessions);
    }

    // public void editStudent(Student student, int index){
    //     students.set(index, student);
    // }

    public static Student searchStudentByStudentNumber(int studentNumber) { 
        int studentId = Studentdao.getStudentIdByStudentNumber(studentNumber);
        
        int l = 0;
        int r = students.size()-1;
        
        while(l <= r) {
            int mid = (l + r)/2;
            if(students.get(mid).getId() == studentId) { 
                return students.get(mid);
            }

            if(students.get(mid).getId() < studentId) {
                // System.out.println("masuk <");
                l = mid + 1;
            }
            else if(students.get(mid).getId() > studentId) {
                // System.out.println("masuk >");
                r = mid - 1;
            }
        }
        return null;
    }

    public static void decreaseSessionById(int id) {
        for(int i=0; i<students.size(); i++) {
            if(students.get(i).getId() == id) {
                students.get(i).getSession().decreaseSession();
                return;
            }
        }
    }
    public static void increaseSessionById(int id) {
        for(int i=0; i<students.size(); i++) {
            if(students.get(i).getId() == id) {
                students.get(i).getSession().increaseSession();
                return;
            }
        }
    }
    public static void decreaseSessionByStudentNumber(int studentNumber) {
        for(int i=0; i<students.size(); i++) {
            if(students.get(i).getStudentNumber() == studentNumber) {
                students.get(i).getSession().decreaseSession();
                return;
            }
        }
    }
    public static void increaseSessionByStudentNumber(int studentNumber) {
        for(int i=0; i<students.size(); i++) {
            if(students.get(i).getStudentNumber() == studentNumber) {
                students.get(i).getSession().increaseSession();
                return;
            }
        }
    }


    public static Student getStudent(int index){
        return students.get(index);
    }
    public ArrayList<Student> getStudents(){
        return students;
    }
    public void popStudent(int index) {
        students.remove(index);
    }
    public static void setStudents(ArrayList<Student> students) {
        Classes.students = students;
    }
}