package CurriculumPage;

import db.Curriculum;
import db.CurriculumSchedule;
import db.MemberCurriculum;
import utils.Request;

public class CurriculumRequest {

    public static Curriculum addCurriculum(Curriculum curriculum){
        var request = new Request<Curriculum, Curriculum>("/api/curriculum",curriculum);
        return request.POST(Curriculum.class);
    }

    public static Curriculum[] getCurriculums(){
        var request = new Request<Void, Curriculum[]>("/api/curriculum");
        return request.GET(Curriculum[].class);
    }

    public static CurriculumSchedule[] getCurriculumSchedules(Long curriculum_id){
        var request = new Request<Void, CurriculumSchedule[]>("/api/curriculum/"+curriculum_id +"/schedule");
        return request.GET(CurriculumSchedule[].class);
    }

    public static MemberCurriculum[] getMemberCurriculums(){
        var request = new Request<Void, MemberCurriculum[]>("/api/user/curriculum");
        return request.GET(MemberCurriculum[].class);
    }

    public static MemberCurriculum addMemberCurriculum(Long curriculum_id, MemberCurriculum memberCurriculum){
        var request = new Request<MemberCurriculum, MemberCurriculum>("/api/user/curriculum?curriculum_id=" + curriculum_id,memberCurriculum);
        return request.POST(MemberCurriculum.class);
    }

    public static MemberCurriculum deleteMemberCurriculum(Long curriculum_id){
        var request = new Request<Void, MemberCurriculum>("/api/user/curriculum/?curriculum_id=" + curriculum_id);
        return request.DELETE(MemberCurriculum.class);
    }
}
