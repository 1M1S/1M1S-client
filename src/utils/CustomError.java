package utils;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomError {
    Integer status;
    String timestamp;
    String error;
    String code;
    String message;

}
