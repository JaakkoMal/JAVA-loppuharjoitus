package com.JAVALoppuharjoitus.JAVA.loppuharjoitus;

import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.PSource;
import javax.naming.NamingEnumeration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

@RestController
public class MyController {

    /*List<Students> students = new ArrayList<>();
    List<Courses> courses = new ArrayList<>();*/
    Courses C  = new Courses();
    File courseFile = new File("C:\\Users\\jjmal\\Java loppuharjootus\\JAVA-loppuharjoitus\\src\\main\\java\\com\\JAVALoppuharjoitus\\JAVA\\loppuharjoitus\\info.txt");
    File studentFile = new File("C:\\Users\\jjmal\\Java loppuharjootus\\JAVA-loppuharjoitus\\src\\main\\java\\com\\JAVALoppuharjoitus\\JAVA\\loppuharjoitus\\studentinfo.txt");
    File dumpFile = new File("C:\\Users\\jjmal\\Java loppuharjootus\\JAVA-loppuharjoitus\\src\\main\\java\\com\\JAVALoppuharjoitus\\JAVA\\loppuharjoitus\\dump.txt");
    File studentsOnCourses = new File("C:\\Users\\jjmal\\Java loppuharjootus\\JAVA-loppuharjoitus\\src\\main\\java\\com\\JAVALoppuharjoitus\\JAVA\\loppuharjoitus\\studentsOnCourses.txt");


    /*@GetMapping("students")
    public List<Students> getAllStudents() {
        return students;
    }*/

   /* @PostMapping("addcourse")
    public String addCourse(@RequestParam String coursename, @RequestParam String courseteacher, @RequestParam String cid) {
        Courses c = new Courses(coursename, courseteacher, cid);
        courses.add(c);
        return "Course added";
    }*/

    /*@GetMapping("courses")
    public List<Courses> getAllCourses() {
        return courses;
    }*/
    @GetMapping("allcourses")
    public String getAllCourses() throws IOException {
        String contentToShow = Files.readString(Path.of("C:\\Users\\jjmal\\Java loppuharjootus\\JAVA-loppuharjoitus\\src\\main\\java\\com\\JAVALoppuharjoitus\\JAVA\\loppuharjoitus\\info.txt"));
        if(contentToShow.equals("")){
            return "There are no courses inserted in the system. You can add a course first to see if the application works.";
        }else {
            return "<h3> " + contentToShow.replaceAll("(\r\n|\r|\n|\n\r)", "<br>") + "</h3>";
        }
    }

    @GetMapping("coursesbyid")
    public String getCourseById(@RequestParam String id) throws FileNotFoundException {
        Scanner reader = new Scanner(courseFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] tokens = line.split(" ");
                //System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(id.equals(tokens[0])){
                System.out.println(line);
                reader.close();
                String randy = "<form method='POST' action='http://localhost:8080/addstudenttocourse'><label style='color:pink'>Course ID:</label><br><input type='text' name='id' value=" + tokens[0] + "><br><label style='color:pink'>Student ID:</label><br><input type='text' name='sid'><br><input type='submit' value='Submit' style='border-radius: 25px; background-color: pink; font-weight: bold; font-size: 16px;'></form>";
                return "<h3>" + line + "</h3>" + randy;
            }

        }
        reader.close();
        return "Antamallasi ID:llä ei löydy kurssia.";
    }

    @GetMapping("coursesbyname")
    public String getCourseByName(@RequestParam String coursename) throws FileNotFoundException {
        Scanner reader = new Scanner(courseFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] tokens = line.split(" ");
            System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(coursename.equals(tokens[1])){
                System.out.println(line);
                reader.close();
                return "<h3>" + line + "</h3>";
            }

        }
        reader.close();
        return "Antamallasi nimellä ei löydy kurssia.";
    }

    @GetMapping("coursesbyteacher")
    public String getCourseByTeacher(@RequestParam String teacher) throws IOException {
        Scanner reader = new Scanner(courseFile);
        PrintWriter dumpWriter = new PrintWriter(dumpFile);
        dumpWriter.print("");
        //List<String> coursesToShow = new ArrayList<>();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] tokens = line.split(" ");
            //System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(teacher.equals(tokens[2] + " " + tokens[3])){
                dumpWriter.print(line + System.lineSeparator());
                System.out.println(line);
            }else if(teacher.equals(tokens[2])) {
                dumpWriter.print(line + System.lineSeparator());
                System.out.println(line);
            } else if (teacher.equals(tokens[3])) {
                dumpWriter.print(line + System.lineSeparator());
                System.out.println(line);
            }
        }
        reader.close();
        dumpWriter.close();
        String contentToShow = Files.readString(Path.of("C:\\Users\\jjmal\\Java loppuharjootus\\JAVA-loppuharjoitus\\src\\main\\java\\com\\JAVALoppuharjoitus\\JAVA\\loppuharjoitus\\dump.txt"));
        if(contentToShow.equals("")){
            return "Teacher " + teacher + " doesn't have any courses. Try another teacher." ;
        }else {
            return "<h3> " + contentToShow.replaceAll("(\r\n|\r|\n|\n\r)", "<br>") + "</h3>";
        }
        /*reader.close();
        return "Antamallasi nimellä ei löydy kurssia.";*/
    }


    @PostMapping("addcourses")
    public String addCourse(@RequestParam String id, @RequestParam String coursename, @RequestParam String teacher) throws IOException {
        FileWriter fw = new FileWriter(courseFile, true);
        fw.write(id + " " + coursename + " " + teacher + System.lineSeparator());
        fw.close();
        return "Kurssi lisätty onnistuneesti.";
    }

    @PostMapping("addstudent")
    public String addStudent(@RequestParam String sid, @RequestParam String fname, @RequestParam String lname, @RequestParam String address) throws IOException {
        FileWriter fw = new FileWriter(studentFile, true);
        fw.write(sid + " " + fname + " " + lname + " " + address + System.lineSeparator());
        fw.close();
        return "Opiskelija lisätty onnistuneesti.";
    }

    @GetMapping("allstudents")
    public String getAllStudents() throws IOException {
        String contentToShow = Files.readString(Path.of("C:\\Users\\jjmal\\Java loppuharjootus\\JAVA-loppuharjoitus\\src\\main\\java\\com\\JAVALoppuharjoitus\\JAVA\\loppuharjoitus\\studentinfo.txt"));
        if(contentToShow.equals("")){
            return "There are no students inserted in the system. You can add a student first to see if the application works.";
        }else {
            return "<h3> " + contentToShow.replaceAll("(\r\n|\r|\n|\n\r)", "<br>") + "</h3>";
        }
    }

    @GetMapping("studentbyid")
    public String getStudentById(@RequestParam String sid) throws FileNotFoundException {
        Scanner reader = new Scanner(studentFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] tokens = line.split(" ");
            //System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(sid.equals(tokens[0])){
                System.out.println(line);
                reader.close();
                return "<h3>" + line + "</h3>";
            }

        }
        reader.close();
        return "Antamallasi ID:llä ei löydy kurssia.";
    }

    @PostMapping("addstudenttocourse")
    public String addStuToCou(@RequestParam String sid, @RequestParam String id) throws IOException {
        Scanner reader = new Scanner(studentFile);
        Scanner cReader = new Scanner(courseFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String cline = cReader.nextLine();
            String[] tokens = line.split(" ");
            String[] ctokens = line.split(" ");
            //System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(sid.equals(tokens[0])){
                System.out.println(line);
                    if(id.equals(ctokens[0])){
                        System.out.println(cline);
                    }
                reader.close();
                cReader.close();
                String studentLovesCourse = line + cline;
                FileWriter fw = new FileWriter(studentsOnCourses, true);
                fw.write(line + " " + cline + System.lineSeparator());
                fw.close();
                return "<h3>" + line + " lisätty kurssille " + cline + "</h#3>";
            }

        }
        reader.close();
        return "Pelle";
    }

}
