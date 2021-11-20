package db;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {


    private Long id;


    private Interest interest;


    private String title;


    private String content;


    private Member member;


    private LocalDateTime writingDate;

    public Post(Long interest, String title, String content){
        this.interest = new Interest(interest);
        this.title = title;
        this.content = content;
    }
}
