package signUpPage;

import db.MemberInformation;
import db.MemberInterest;
import db.RegisterSurvey;
import loginPage.LoginPage;
import main.MainFrame;
import utils.Request;

import javax.swing.*;

public class JoinRequest {

    static public MemberInformation createMemberInformation(MemberInformationPanel memberInformationPanel){

        if(!memberInformationPanel.passwdConfirmText.getText().equals(memberInformationPanel.passwdText.getText())){
            JOptionPane.showMessageDialog(null,  "비밀번호 확인이 동일하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(memberInformationPanel.gender.equals("")){
            JOptionPane.showMessageDialog(null,  "성별을 선택해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }


        return new MemberInformation(
                memberInformationPanel.nameText.getText(),
                memberInformationPanel.nickNameText.getText(),
                memberInformationPanel.emailText.getText(),
                memberInformationPanel.gender,
                memberInformationPanel.userIdText.getText(),
                memberInformationPanel.passwdText.getText());
    }

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
        return MemberInterest.builder().member(SignUpPage.memberInformation.getMember())
                .interest(SurveyPanel.interest)
                .level(level)
                .build();
    }

    public static void join(){
        MemberInformation memberInformation = createMemberInformation(MemberInformationPanel.signUpPage.memberInformationPanel);
        if(memberInformation == null)return;
        var request = new Request<MemberInformation, MemberInformation>("/auth/join", memberInformation);
        MemberInformation result = request.POST(MemberInformation.class);
        if(result == null)return;
        SignUpPage.memberInformation = result;
        SignUpPage.mainFrame.change(SignUpPage.interestSelectPanel,InterestSelectPanel.class);

    }

    public static void rollBack(SignUpPage signUpPage){
        var request = new Request<MemberInformation, Void>("/auth/rollback", SignUpPage.memberInformation);
        try {
            request.DELETE(Void.class);
            SignUpPage.memberInformation = null;
        }catch (Exception e){

        }
    }

    public static void getSurveys(Long interestId){
        var request = new Request<Void, RegisterSurvey[]>("/api/register-survey/?interest_id="+interestId);
        try {
           RegisterSurvey[] result = request.PUT(RegisterSurvey[].class);
           SurveyPanel.surveys =result;
           SurveyPanel.checked = new Integer[result.length];
        }catch (Exception e){

        }
    }

    public static void postSurveyResult(SurveyPanel surveyPanel){
        var request = new Request<MemberInterest, Void>("/auth/survey-result", createSurveyResult());
        try{
            request.POST(Void.class);
            surveyPanel.signUpPage.mainFrame.change(MainFrame.loginPage, LoginPage.class);
        }catch (Exception e){

        }
    }
}
