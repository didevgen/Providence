package services.university.parsers;

import models.university.*;
import play.libs.Json;
import services.university.IParser;
import services.university.UrlConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Eugne on 26.05.2016.
 */
public class UniversityParser {

    public University parseUniversity() throws IOException {
        University structuredUniversity = getUniversityStructure();
        University groupedUniversity = getUniversityGroupStructure();
        for (Faculty item : structuredUniversity.getFaculties()) {
            groupedUniversity.getFaculties()
                    .get(groupedUniversity.getFaculties().indexOf(item))
                    .setDepartments(item.getDepartments());
        }
        for (Faculty faculty : groupedUniversity.getFaculties()) {
            faculty.setUniversity(groupedUniversity);
            if (faculty.getDepartments() !=null) {
                for (Department department : faculty.getDepartments()) {
                    department.setFaculty(faculty);
                    for (Teacher teacher : department.getTeachers()) {
                        teacher.setDepartment(department);
                    }
                }
            }
            if (faculty.getDirections() !=null) {
                for (Direction direction : faculty.getDirections()) {
                    direction.setFaculty(faculty);
                    for (Group group : direction.getGroups()) {
                        group.setDirection(direction);
                    }
                    for (Speciality speciality : direction.getSpecialities()) {
                        speciality.setDirection(direction);
                        for (Group group : speciality.getGroups()) {
                            group.setSpeciality(speciality);
                        }
                    }
                }
            }
        }
        return groupedUniversity;
    }

    private University getUniversityStructure() throws IOException {
        URL obj = new URL(UrlConstants.P_API_PODR_JSON);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return Json.fromJson(Json.parse(response.toString()).get("university"), University.class);
    }

    private University getUniversityGroupStructure() throws IOException {
        URL obj = new URL(UrlConstants.P_API_GROUP_JSON);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        return Json.fromJson(Json.parse(response.toString()).get("university"), University.class);
    }

}
