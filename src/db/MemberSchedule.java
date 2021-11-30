package db;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class MemberSchedule {


    private Long id;

    private Member member;


    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;


    private Boolean finish;


    private Interest interest;

    int calScore(String score_per_minute) {
        return Integer.parseInt(Long.toString(ChronoUnit.MINUTES.between(startTime, endTime))) * Integer.parseInt(score_per_minute);
    }

    public MemberSchedule(){

    }
    public MemberSchedule(String content, LocalDateTime startTime, LocalDateTime endTime, Boolean finish, Long interest){
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.finish = finish;
        this.interest = new Interest(interest);
    }

    public void setStartTime(String startTime) {
        this.startTime = LocalDateTime.parse(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = LocalDateTime.parse(endTime);
    }
}
