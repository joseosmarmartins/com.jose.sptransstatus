package com.jose;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.util.*;

class StudentService {

    void setStudentStatus(InputStream inputStream, String outputFileAddress) throws Exception {
        Map<String, String> studentsMap = new LinkedHashMap<>();

        String csv = FileUtils.convertXlsToCsv(inputStream);
        String[] students = csv.split("\\r?\\n");
        for (String student : students) {
            String[] columns = student.split(";");

            String value;
            if (columns.length == 2) value = columns[1];
            else {
                value = resultByConnectionWithSptrans(columns[0]);

                if ("SemMatricula".equals(value)) value = "não possui";
                else value = "possui";
            }

            studentsMap.put(columns[0], value);
        }
        FileUtils.mapToXls(studentsMap, outputFileAddress);
    }

    private static String resultByConnectionWithSptrans(String cpf) throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("usuarioSearch.rgEmittedState", "SP");
        parameters.put("usuarioSearch.cpfNumber", cpf);
        parameters.put("anoLetivo", calendar.get(Calendar.YEAR) + "");

        Document document = Jsoup.connect("https://scapub.sbe.sptrans.com.br/sa/consultaEstudante/consultarEstudante.action").data(parameters).header("Content-Type", "application/x-www-form-urlencoded").post();
        return document.getElementById("mensagem").attr("value");
    }
}
