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

//Luodaan oliot luokista, sekä tekstitiedostot opiskelija- ja kurssitietojen tallentamista varten
    Courses C  = new Courses();
    Students S = new Students();
    File courseFile = new File("info.txt");
    File studentFile = new File("studentinfo.txt");
    File dumpFile = new File("dump.txt");
    File studentsOnCourses = new File("studentsOnCourses.txt");

//Alla GET- ja POST-mappaukset opiskelija- ja kurssitietojen lukemiseen ja lisäämiseen. Palautetaan vastaavissa luokissa
//määriteltyjen metodien tulokset ruudulle.

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
    //Lisätään oppilas kurssille. Tämä toiminnallisuus löytyy selaimella kun kurssia on etsitty kurssi-ID:llä.
    //Aikarajoitteiden takia, en ehdi hienosäätää ja siirtää tätä nyt etusivulle, mutta toiminta tullee esille silti.
    @PostMapping("addstudenttocourse")
    public String addStuToCou(@RequestParam String sid, @RequestParam String id) throws IOException {
        //Luodaan kaksi scanneria, joista toinen lukee kurssitietoja ja toinen opiskelijatietoja. Kts. Course -luokan
        //metodeja, koska toiminta on aika sama, paitsi nyt luetaan kahdet tiedot.
        Scanner reader = new Scanner(studentFile);
        Scanner cReader = new Scanner(courseFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine(); //opiskelijatietojen muuttaminen string -muotoon
            String cline = cReader.nextLine(); //kurssitietojen muuttaminen string -muotoon
            String[] tokens = line.split(" "); //erotellaan opiskelijatiedoston sanat tokeneiksi
            String[] ctokens = line.split(" "); //erotellaan kurssitiedoston sanat tokeneiksi
            //System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(sid.equals(tokens[0])){ //Verrataan opiskelijatiedoston ID:tä parametrina annettuun ID:seen
                System.out.println(line);
                    if(id.equals(ctokens[0])){ //Verrataan kurssitiedoston ID:tä annettuun ID:seen (hakukenttä täyttyy automaattisesti selaimessa)
                        System.out.println(cline);
                    }
                reader.close();
                cReader.close();
                //String studentLovesCourse = line + cline;
                FileWriter fw = new FileWriter(studentsOnCourses, true); //luodaan FileWriter kirjoittamaan löydetyt tiedot
                fw.write(line + " " + cline + System.lineSeparator()); //Kirjoitetaan studentsOnCourses -tiedostoon oppilaan ja kurssin tiedot
                fw.close();
                //HOX. Selain ei näytä muuta kuin tiedon, että oppilas on lisätty kurssille. Jos haluat tarkistaa, tsekkaa studentsOnCourses tekstitiedosto.
                return "<h3>" + line + "<br> added to course <br>" + cline + "</h3>";
            }

        }
        reader.close();
        //Jos annettua opiskelija ID:tä ei löydy, palautetaan tieto käyttäjälle. Kurssin kohdalla tätä ei voi käydä, koska
        //ainoa tapa mennä lisäämään oppilas kurssille on id:llä haetun kurssin kautta.
        return "Student ID does not exist.";
    }

}
