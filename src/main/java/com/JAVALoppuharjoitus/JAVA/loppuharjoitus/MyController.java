package com.JAVALoppuharjoitus.JAVA.loppuharjoitus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

@RestController
public class MyController {

    List<Students> students = new ArrayList<>();
    List<Courses> courses = new ArrayList<>();
    File courseFile = new File("C:\\Users\\jjmal\\Java loppuharjootus\\JAVA-loppuharjoitus\\src\\main\\java\\com\\JAVALoppuharjoitus\\JAVA\\loppuharjoitus\\info.txt");


    @PostMapping("addstudent")
    public String addStudent(@RequestParam String fnames, @RequestParam String lnames, @RequestParam String addresss, @RequestParam String sid) throws IOException {
        Students s = new Students(fnames, lnames, addresss, sid);
        students.add(s);
        return "";
    }

    @GetMapping("students")
    public List<Students> getAllStudents() {
        return students;
    }

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

    @GetMapping("coursesbyid")
    public String getCourseById(@RequestParam String id) throws FileNotFoundException {
        Scanner reader = new Scanner(courseFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] tokens = line.split(" ");
                System.out.println(tokens[0] + " " + tokens[1]);
            if(id.equals(tokens[0])){
                System.out.println(line);
                reader.close();
                return "<h3>" + line + "</h3>";
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
            System.out.println(tokens[0] + " " + tokens[1]);
            if(coursename.equals(tokens[1])){
                System.out.println(line);
                reader.close();
                return "<h3>" + line + "</h3>";
            }

        }
        reader.close();
        return "Antamallasi nimellä ei löydy kurssia.";
    }


    @PostMapping("addcourses")
    public String addCourse(@RequestParam String id, @RequestParam String coursename) throws IOException {
        FileWriter fw = new FileWriter(courseFile, true);
        fw.write(id + " " + coursename + System.lineSeparator());
        fw.close();
        return "Kurssi lisätty onnistuneesti.";
    }

}
