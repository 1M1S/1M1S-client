package db;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Member {
    private Long id;
    private String username;
    private String password;
    public Member(){}
    public Member(Long id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public Member(String username, String password){
        this.username = username;
        this.password = password;
    }
}