package controllers.university;

import controllers.BaseController;
import database.DB;
import dto.university._University;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import models.university.University;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.university.UniversityService;
import services.university.parsers.UniversityParser;

import java.io.IOException;
@Api(value = "/university", description = "Operations for parsing cist api")
public class UniversityController extends BaseController {
    @ApiOperation(
            value = "Get structure",
            httpMethod = "GET",
            response = University.class
    )
    @Transactional
    public Result getUniversityStructure(){
        return apply(()->{
            UniversityParser parser = new UniversityParser();
            University university = new University();
            try {
                university = parser.parseUniversity();
                DB.save(university);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ok(university);
        });
    }

    @ApiOperation(
            value = "Get university",
            httpMethod = "GET",
            response = _University.class
    )
    @Transactional
    public Result getUniversityDetails(){
        return apply(() -> {
            return ok(new _University().from(new UniversityService().getUniversity()));
        });
    }
}
