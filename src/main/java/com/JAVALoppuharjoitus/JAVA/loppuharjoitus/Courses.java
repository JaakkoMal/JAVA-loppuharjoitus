package com.JAVALoppuharjoitus.JAVA.loppuharjoitus;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Courses {
    private String coursename;
    private String courseteacher;
    private String cid;

    public Courses(){}
    /*public Courses(String coursename, String courseteacher, String cid) {
        this.coursename = coursename;
        this.courseteacher = courseteacher;
        this.cid = cid;
    }*/


    public String getAllCourses() throws IOException {
        String contentToShow = Files.readString(Path.of("info.txt"));
        if(contentToShow.equals("")){
            return "There are no courses inserted in the system. You can add a course first to see if the application works.";
        }else {
            return "<h3> " + contentToShow.replaceAll("(\r\n|\r|\n|\n\r)", "<br>") + "</h3>";
        }
    }

    public String getCourseById(String id, File kurssifilu) throws FileNotFoundException {
        Scanner reader = new Scanner(kurssifilu);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] tokens = line.split(" ");
            //System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(id.equals(tokens[0])){
                System.out.println(line);
                reader.close();
                String randy = "<form method='POST' action='http://localhost:8080/addstudenttocourse'><label style='color:pink'>Course ID:</label><br><input type='text' name='id' value=" + tokens[0] + "><br><label style='color:pink'>Student ID:</label><br><input type='text' name='sid'><br><input type='submit' value='Submit' style='border-radius: 25px; background-color: pink; font-weight: bold; font-size: 16px;'></form>";
                return "<h3>" + line + "<br><br>" + "Add your ass to this course by typing your student ID and clicking 'submit'.</h3>" + randy;
            }

        }
        reader.close();
        return "No courses found with given ID.";
    }

    public String getCourseByName(String coursename, File kurssiFilu) throws FileNotFoundException {
        Scanner reader = new Scanner(kurssiFilu);
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
        return "No courses found with given course name.";
    }

    public String getCourseByTeacher(String teacher, File kurssiFilu, File dumppiFile) throws IOException {
        Scanner reader = new Scanner(kurssiFilu);
        PrintWriter dumpWriter = new PrintWriter(dumppiFile);
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
        String contentToShow = Files.readString(Path.of("dump.txt"));
        if(contentToShow.equals("")){
            return "Teacher " + teacher + " doesn't have any courses. Try another teacher." ;
        }else {
            return "<h3> " + contentToShow.replaceAll("(\r\n|\r|\n|\n\r)", "<br>") + "</h3>";
        }
    }

    public String addCourse(String id, String coursename, String teacher, File kurssiFilu) throws IOException {
        FileWriter fw = new FileWriter(kurssiFilu, true);
        fw.write(id + " " + coursename + " " + teacher + System.lineSeparator());
        fw.close();
        return "Course added succesfully.";
    }
}
