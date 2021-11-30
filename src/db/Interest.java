package db;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interest {
    private Long id;
    private String subject;
    public Interest(){};
    public Interest(Long id, String subject){
        this.id = id;this.subject = subject;
    }
    public Interest(Long id){
        this.id = id;this.subject = null;
    }
}
