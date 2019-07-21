package com.jose;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        try {
            InputStream studentsFile = new FileInputStream("CadastroEstudante_2019.xlsx");

            StudentService studentService = new StudentService();
            studentService.setStudentStatus(studentsFile, "CadastroEstudante_2019.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
