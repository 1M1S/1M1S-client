package loginPage;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.Member;
import db.MemberInterest;
import main.MainFrame;
import mainPage.MainPage;
import myPage.MyPage;
import myPage.MyPageRequest;
import surveyPage.InterestSelectPanel;
import utils.Request;



public class LoginRequest {
    static public ObjectMapper objectMapper = new ObjectMapper();

    public static void Login(MainFrame mainFrame, Member loginInformation){
        var request = new Request<Member, Token>("/auth/login", loginInformation);
        try {
            Request.xAccessToken = request.POST(Token.class).getAccessToken();
            var checkMemberInterest = new Request<Void, MemberInterest[]>("/api/user/interest");
            var result = checkMemberInterest.GET(MemberInterest[].class);
            if(result.length == 0)LoginPage.mainFrame.change(LoginPage.mainFrame.interestSelectPanel, InterestSelectPanel.class);
            else {
                MyPage.me = MyPageRequest.getMe();
                LoginPage.mainFrame.change(LoginPage.mainFrame.mainPage, MainPage.class);
            };
        }catch (Exception e){
            Request.xAccessToken = "";
        }

    }

}
