package db;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberInformation {
    private Long id;
    private Member member;
    private String name;
    private String nickname;
    private String gender;
    private String phone;
    private String email;
    private Long memberId;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime register_date;
    public MemberInformation(){};
    public MemberInformation(String name, String nickname, String email, String gender, String username, String password, Long memberId){
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.memberId = memberId;
        member = new Member(memberId, username, password);
    }
    public MemberInformation(String name, String nickname, String email, String gender, String username, String password){
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        member = new Member(username, password);
    }
}
