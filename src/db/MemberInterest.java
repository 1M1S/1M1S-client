package db;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberInterest {
    private Long id;
    private Member member;
    private Interest interest;
    private String level;
}
