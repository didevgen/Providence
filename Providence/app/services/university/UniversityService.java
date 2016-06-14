package services.university;

import com.mysema.query.jpa.impl.JPAQuery;
import database.DB;
import models.university.QFaculty;
import models.university.QUniversity;
import models.university.University;

public class UniversityService {

    public University getUniversity() {
        return DB.query().from(QUniversity.university)
                .leftJoin(QUniversity.university.faculties,QFaculty.faculty).fetch()
                .orderBy(QFaculty.faculty.fullName.asc())
                .singleResult(QUniversity.university);
    }
}
