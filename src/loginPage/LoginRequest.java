package loginPage;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.Member;
import main.MainFrame;
import utils.Request;



public class LoginRequest {
    static public ObjectMapper objectMapper = new ObjectMapper();

    public static void Login(MainFrame mainFrame, Member loginInformation){
        var request = new Request<Member, Token>("/auth/login", loginInformation);
        try {
            Request.xAccessToken = request.POST(Token.class).getAccessToken();
        }catch (Exception e){

        }

    }

}
