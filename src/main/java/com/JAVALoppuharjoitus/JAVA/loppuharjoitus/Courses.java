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

    //Haetaan kaikki kurssit
    public String getAllCourses() throws IOException {
        //Luodaan muuttuja ContentToShow, joka sisältää info.txt tiedoston string-muodossa. info.txt sisältää kurssitiedot.
        String contentToShow = Files.readString(Path.of("info.txt"));
        if(contentToShow.equals("")){
            //jos kursseja ei ole, eli tiedosto on tyhjä, palautetaan käyttäjälle tästä tieto.
            return "There are no courses inserted in the system. You can add a course first to see if the application works.";
        }else {
            //jos kursseja löytyy, palautetaan ne allekkain siinä järjestyksessä, kun ne on kirjattu
            return "<h3> " + contentToShow.replaceAll("(\r\n|\r|\n|\n\r)", "<br>") + "</h3>";
        }
    }
    //Haetaan kurssia kurssi-ID:n perusteella
    public String getCourseById(String id, File kurssifilu) throws FileNotFoundException {
        //Luodaan scanneri lukemaan kurssit sisältävää tiedostoa, controllerista on metodin kutsussa välitetty parametrina
        //kurssit sisältävä courseFile, jota vastaa tässä kurssifilu
        Scanner reader = new Scanner(kurssifilu);
        //while-loopissa luetaan kurssitietoja, niin kauan kun rivejä löytyy
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] tokens = line.split(" "); //Käytetään split() -funktiota erottelemaan kurssitiedoston tiedot erillisiksi "tokeneiksi"
            //System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(id.equals(tokens[0])){
                //Koska kurssitiedot ovat järjestyksessä "id", "kurssin nimi", "opettaja", verrataan ensimmäistä eroteltua tokenia
                //parametrina annettuun id:seen. Jos ovat sama, niin kurssi on löytynyt ja se palautetaan return -lausekkeella ja while loop katkeaa
                System.out.println(line);
                reader.close();
                String randy = "<form method='POST' action='http://localhost:8080/addstudenttocourse'><label style='color:pink'>Course ID:</label><br><input type='text' name='id' value=" + tokens[0] + "><br><label style='color:pink'>Student ID:</label><br><input type='text' name='sid'><br><input type='submit' value='Submit' style='border-radius: 25px; background-color: pink; font-weight: bold; font-size: 16px;'></form>";
                return "<h3>" + line + "<br><br>" + "Add your ass to this course by typing your student ID and clicking 'submit'.</h3>" + randy;
            }

        }
        //Jos annetulla id:llä ei löydy kurssia, palautetaan string, jossa kerrotaan se käyttäjälle.
        reader.close();
        return "No courses found with given ID.";
    }
    //Haetaan kurssia kurssin nimen perusteella
    public String getCourseByName(String coursename, File kurssiFilu) throws FileNotFoundException {
        //Sama periaate kuin id:llä haettaessa, mutta nyt verrataan toista tokenia (kurssin nimeä) parametrina annettuun
        //kurssinimeen ja palautetaan osuma käyttäjälle ruudulle. Jos osumaa ei tule, palautetaan siitä tieto.
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
    //Haetaan kurssia opettajan nimellä. Tämän voisi varmasti tehdä helpomminkin..
    public String getCourseByTeacher(String teacher, File kurssiFilu, File dumppiFile) throws IOException {
        //Jälleen luodaan scanneri lukemaan kurssitiedostoa. Sen lisäksi luodaan PrintWriter, jota käytetään tiedostoon
        //kirjoittamiseen.
        Scanner reader = new Scanner(kurssiFilu);
        PrintWriter dumpWriter = new PrintWriter(dumppiFile);
        dumpWriter.print("");
        //while-loopissa vertaillaan taas tokeneiksi hajotettua kurssitiedostoa annettuun parametriin. Iffittely sisältää
        //pari lisä iffittelyä, jotta voidaan hakea opettajaa etunimellä, sukunimellä tai koko nimellä.
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] tokens = line.split(" ");
            //System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            if(teacher.equals(tokens[2] + " " + tokens[3])){ //ensin katsotaan löytyykö kokonimellä
                dumpWriter.print(line + System.lineSeparator()); //kirjoitetaan tekstitiedostoon löytynyt tieto
                System.out.println(line);
            }else if(teacher.equals(tokens[2])) { //katsotaan löytyykö etunimellä
                dumpWriter.print(line + System.lineSeparator());
                System.out.println(line);
            } else if (teacher.equals(tokens[3])) { //löytyykö sukunimellä
                dumpWriter.print(line + System.lineSeparator());
                System.out.println(line);
            }
        }
        reader.close();
        dumpWriter.close();
        String contentToShow = Files.readString(Path.of("dump.txt")); //Muutetaan dump.txt -tiedosto String muotoon
        if(contentToShow.equals("")){ //Jos osumaa ei löydy, palautetaan tieto käyttäjälle
            return "Teacher " + teacher + " doesn't have any courses. Try another teacher." ;
        }else { //Jos osuma löytyy, palautetaan siitä tieto
            return "<h3> " + contentToShow.replaceAll("(\r\n|\r|\n|\n\r)", "<br>") + "</h3>";
        }
    }
    //Lisätään kurssi
    public String addCourse(String id, String coursename, String teacher, File kurssiFilu) throws IOException {
        //Luodaan FileWriter, jolla kirjoitetaan kurssitiedostoon parametrina annetut tiedot (id, kurssin nimi, opettaja)
        FileWriter fw = new FileWriter(kurssiFilu, true);
        fw.write(id + " " + coursename + " " + teacher + System.lineSeparator());
        fw.close();
        return "Course added succesfully.";
    }
}
