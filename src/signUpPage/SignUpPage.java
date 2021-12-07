package signUpPage;

import javax.swing.*;
import db.*;
import main.MainFrame;
import surveyPage.InterestSelectPanel;
import surveyPage.SurveyPanel;

public class SignUpPage extends JPanel {

    public static MemberInformation memberInformation = null;
    public static Interest interest = null;
    public static InterestSelectPanel interestSelectPanel = null;
    public static SurveyPanel surveyPanel = null;
    public static MemberInformationPanel memberInformationPanel = null;
    public static MainFrame mainFrame;

    public SignUpPage(MainFrame mainFrame) {
        SignUpPage.mainFrame = mainFrame;
        SignUpPage.memberInformationPanel = new MemberInformationPanel(this);
        add(memberInformationPanel);
        setLayout(null);
        setSize(1100, 824);
    }
}
