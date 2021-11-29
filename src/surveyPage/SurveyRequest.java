package surveyPage;

import db.MemberInterest;
import db.RegisterSurvey;
import loginPage.LoginPage;
import main.MainFrame;
import utils.Request;

public class SurveyRequest {

    static public MemberInterest createSurveyResult(){
        int score = 0;
        String level = "beginner";
        for(int i = 0; i < SurveyPanel.surveys.length; i++){
            score += SurveyPanel.checked[i];
        }
        if(SurveyPanel.interest.getId() == 3){
            if(score >= 2 && score < 6)level = "junior";
            else if(score >= 6)level = "expert";
        }else{
            if(score >= 4 && score < 8)level = "junior";
            else if(score >= 8)level = "expert";
        }
        return MemberInterest.builder()
                .interest(SurveyPanel.interest)
                .level(level)
                .build();
    }
    public static void getSurveys(Long interestId){
        var request = new Request<Void, RegisterSurvey[]>("/api/register-survey?interest_id="+interestId);
        try {
            RegisterSurvey[] result = request.GET(RegisterSurvey[].class);
            SurveyPanel.surveys =result;
            if(result.length > 0)SurveyPanel.interest = result[0].getInterest();
            SurveyPanel.checked = new Integer[result.length];
            InterestSelectPanel.mainFrame.change(MainFrame.interestSelectPanel.surveyPanel, SurveyPanel.class);
            MainFrame.interestSelectPanel.surveyPanel.addQuestions();
        }catch (Exception e){

        }
    }

    public static void postSurveyResult(){
        var request = new Request<MemberInterest, MemberInterest>("/api/user/interest", createSurveyResult());
        try{
            request.POST(MemberInterest.class);
            InterestSelectPanel.mainFrame.change(MainFrame.loginPage, LoginPage.class);
        }catch (Exception e){

        }
    }
}
