package db;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writingDate;
    public void setWritingDate(String writingDate) {
        this.writingDate = LocalDateTime.parse(writingDate);
    }
    public Post(){}
    public Post(Interest interest, String title, String content){
        this.interest = interest;
        this.title = title;
        this.content = content;
    }
    public Post(Long id, Interest interest, String title, String content){
        this.id = id;
        this.interest = interest;
        this.title = title;
        this.content = content;
    }
}
