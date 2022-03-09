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


    Courses C  = new Courses();
    Students S = new Students();
    File courseFile = new File("info.txt");
    File studentFile = new File("studentinfo.txt");
    File dumpFile = new File("dump.txt");
    File studentsOnCourses = new File("studentsOnCourses.txt");



    @GetMapping("allcourses")
    public String kaikKurssit() throws IOException {
        return C.getAllCourses();
    }

    @GetMapping("coursesbyid")
    public String kurssiIdlla(@RequestParam String id) throws FileNotFoundException {
        return C.getCourseById(id, courseFile);
    }

    @GetMapping("coursesbyname")
    public String kurssiNimella(@RequestParam String coursename) throws FileNotFoundException {
        return C.getCourseByName(coursename, courseFile);
    }


    @GetMapping("coursesbyteacher")
    public String kurssiOpella(@RequestParam String teacher) throws IOException {
        return C.getCourseByTeacher(teacher, courseFile, dumpFile);
    }


    @PostMapping("addcourses")
    public String lisaaKurssi(@RequestParam String id, @RequestParam String coursename, @RequestParam String teacher) throws IOException {
        return C.addCourse(id, coursename, teacher, courseFile);
    }

    @PostMapping("addstudent")
    public String lisaaOppilas(@RequestParam String sid, @RequestParam String fname, @RequestParam String lname, @RequestParam String address) throws IOException {
        return S.addStudent(sid, fname, lname, address, studentFile);
    }

    @GetMapping("allstudents")
    public String kaikOppilaat() throws IOException {
        return S.getAllStudents();
    }

    @GetMapping("studentbyid")
    public String oppilasIdlla(@RequestParam String sid) throws FileNotFoundException {
        return S.getStudentById(sid, studentFile);
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
                //String studentLovesCourse = line + cline;
                FileWriter fw = new FileWriter(studentsOnCourses, true);
                fw.write(line + " " + cline + System.lineSeparator());
                fw.close();
                return "<h3>" + line + "<br> added to course <br>" + cline + "</h3>";
            }

        }
        reader.close();
        return "Student ID does not exist.";
    }

}
