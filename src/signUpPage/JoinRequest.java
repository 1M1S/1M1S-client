package signUpPage;

import db.MemberInformation;
import db.MemberInterest;
import db.RegisterSurvey;
import loginPage.LoginPage;
import main.MainFrame;
import surveyPage.InterestSelectPanel;
import surveyPage.SurveyPanel;
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



    public static void join(){
        MemberInformation memberInformation = createMemberInformation(MemberInformationPanel.signUpPage.memberInformationPanel);
        if(memberInformation == null)return;
        try{
            var request = new Request<MemberInformation, MemberInformation>("/auth/join", memberInformation);
            MemberInformation result = request.POST(MemberInformation.class);
            if(result == null)return;
            SignUpPage.mainFrame.change(MainFrame.loginPage,LoginPage.class);
        }catch (Exception e){

        }


    }




}
