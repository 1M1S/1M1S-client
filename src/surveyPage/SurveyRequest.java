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
        if(InterestSelectPanel.interest.getId() == 3){
            if(score >= 2 && score < 6)level = "junior";
            else if(score >= 6)level = "expert";
        }else{
            if(score >= 4 && score < 8)level = "junior";
            else if(score >= 8)level = "expert";
        }
        System.out.println(level);
        System.out.println(InterestSelectPanel.interest.getSubject());
        System.out.println(score);
        return new MemberInterest(null,null,InterestSelectPanel.interest,level);
    }
    public static void getSurveys(Long interestId){
        var request = new Request<Void, RegisterSurvey[]>("/api/register-survey?interest_id="+interestId);
        try {
            RegisterSurvey[] result = request.GET(RegisterSurvey[].class);
            SurveyPanel.surveys =result;
            SurveyPanel.checked = new Integer[result.length];
            InterestSelectPanel.mainFrame.change(MainFrame.interestSelectPanel.surveyPanel, SurveyPanel.class);
            MainFrame.interestSelectPanel.surveyPanel.addQuestions();
            InterestSelectPanel.interest = result[0].getInterest();
        }catch (Exception e){

        }
    }

    public static void postSurveyResult(){
        MemberInterest memberInterest = createSurveyResult();
        System.out.println(memberInterest.getInterest().getSubject());
        var request = new Request<MemberInterest, MemberInterest>("/api/user/interest", memberInterest);
        request.POST(MemberInterest.class);
        InterestSelectPanel.mainFrame.change(MainFrame.loginPage, LoginPage.class);
        Request.xAccessToken = "";
        SurveyPanel.page = 1;
        InterestSelectPanel.surveyPanel = new SurveyPanel(MainFrame.interestSelectPanel);
        InterestSelectPanel.surveyPanel.addQuestions();

    }
}
