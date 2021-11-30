package SchedulePage;

import db.MemberSchedule;
import utils.Request;

public class ScheduleRequest {

    public static MemberSchedule addMemberSchedule(MemberSchedule memberSchedule){
        var request = new Request<MemberSchedule,MemberSchedule>("/api/user/schedule",memberSchedule);
        return request.POST(MemberSchedule.class);
    }

    public static MemberSchedule modifyMemberSchedule(MemberSchedule memberSchedule, Long member_schedule_id){
        var request = new Request<MemberSchedule, MemberSchedule>("/api/user/schedule/" + member_schedule_id);
        return request.PUT(MemberSchedule.class);
    }

    public static MemberSchedule[] getMemberSchedules(String date){
        var request = new Request<Void, MemberSchedule[]>("/api/user/schedule?search_time=" + date);
        return request.GET(MemberSchedule[].class);
    }
}
