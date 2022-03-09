package com.JAVALoppuharjoitus.JAVA.loppuharjoitus;

import org.springframework.web.bind.annotation.RequestParam;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Students {
    private String fname;
    private String lname;
    private String address;
    private String sid;

    public Students(){}
    /*public Students(String fname, String lname, String address, String sid) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.sid = sid;
    }*/

//KATSO METODIEN TOIMINTA Courses-luokan kommentoinnista. Menee samalla kaavalla.
    public String getAllStudents() throws IOException {
        String contentToShow = Files.readString(Path.of("studentinfo.txt"));
        if(contentToShow.equals("")){
            return "There are no students inserted in the system. You can add a student first to see if the application works.";
        }else {
            return "<h3> " + contentToShow.replaceAll("(\r\n|\r|\n|\n\r)", "<br>") + "</h3>";
        }
    }

    public String getStudentById(String sid, File studentoFile) throws FileNotFoundException {
        Scanner reader = new Scanner(studentoFile);
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
        return "No student found with given ID.";
    }

    public String addStudent(String sid, String fname, String lname, String address, File studentoFile) throws IOException {
        FileWriter fw = new FileWriter(studentoFile, true);
        fw.write(sid + " " + fname + " " + lname + " " + address + System.lineSeparator());
        fw.close();
        return "Student added succesfully.";
    }

}
