package db;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInterest {
    private Long id;
    private Member member;
    private Interest interest;
    private String level;
    public MemberInterest(){}
    public MemberInterest(Long id, Member member, Interest interest, String level){
        this.id = id;
        this.member = member;
        this.interest = interest;
        this.level = level;
    }
    public MemberInterest(Long id, Member member, String level){
        this.id = id;
        this.member = member;
        this.level = level;
    }
}
