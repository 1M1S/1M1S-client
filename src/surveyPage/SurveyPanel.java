package surveyPage;

import db.RegisterSurvey;
import loginPage.LoginPage;
import main.MainFrame;
import signUpPage.SignUpPage;
import utils.Font;
import utils.Images;
import javax.swing.*;
import java.awt.*;

public class SurveyPanel extends JPanel {
    public InterestSelectPanel interestSelectPanel;
    public static RegisterSurvey[] surveys = null;
    public static Integer[] checked = null;
    public static int page = 1;

    public void addQuestions(){
        //설문조사 생성
        removeAll();

        JButton RollBackButton = new JButton(Images.RollBackButton.getImageIcon());
        RollBackButton.addActionListener(e -> SignUpPage.mainFrame.change(MainFrame.loginPage, LoginPage.class));
        RollBackButton.setBounds(172, 105, 80, 80);
        RollBackButton.setContentAreaFilled(false);
        add(RollBackButton);

        //다음 버튼
        JButton NextButton = new JButton("Next");
        NextButton.addActionListener(e -> nextPage());
        NextButton.setBounds(700, 600, 80, 50);
        add(NextButton);

        JButton Index1Button = new JButton(Images.BookMarkImage1.getImageIcon());
        Index1Button.addActionListener(e -> {if(page == 2)prevPage();});
        Index1Button.setBounds(188, 185, 70, 50);
        Index1Button.setContentAreaFilled(false);
        add(Index1Button);

        //다음 페이지 index 버튼
        JButton Index2Button = new JButton(Images.BookMarkImage2.getImageIcon());
        Index2Button.addActionListener(e -> {if(page == 1)nextPage();});
        Index2Button.setBounds(188, 235, 70, 50);
        Index2Button.setContentAreaFilled(false);
        add(Index2Button);
        for(int i = (SurveyPanel.page-1)*3; i < SurveyPanel.page*3; i++){
            String[] answers = surveys[i].getChoices().split("\\|");
            JLabel question = new JLabel();
            question.setText(surveys[i].getProblemNumber() +". "+ surveys[i].getQuestion());
            question.setFont(Font.mainFont);
            question.setBounds(280, 120+150*(i%3), 800, 70);
            add(question);
            ButtonGroup  group = new ButtonGroup();
            for(int j = 0; j < answers.length; j++){
                JRadioButton answerButton = new JRadioButton();
                answerButton.setText(answers[j]);
                answerButton.setBounds(330+170*j, 190+150*(i%3), 180, 50);
                answerButton.setContentAreaFilled(false);
                if(checked[i] != null && checked[i] == j)answerButton.setSelected(true);
                int finalI = i;
                int finalJ = j;
                answerButton.addActionListener(e->{if(answerButton.isSelected())checked[finalI] = finalJ;});
                group.add(answerButton);
                add(answerButton);
            }

        }

    }
    
    public boolean checkSelect(){
        boolean flag = true;
        for(int i = 0; i < SurveyPanel.page*3; i++){
            if(SurveyPanel.checked[i] == null){
                JOptionPane.showMessageDialog(null, "질문"+(i+1)+"의 선택지를 체크해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
                flag = false;
                break;
            }
        }
        return flag;
    }
    
    public void nextPage() {
        if (checkSelect()) {
            switch (page) {
                case 1 -> {
                    page = 2;
                    InterestSelectPanel.surveyPanel = new SurveyPanel(interestSelectPanel);
                    InterestSelectPanel.mainFrame.change(InterestSelectPanel.surveyPanel, SurveyPanel.class);
                    InterestSelectPanel.surveyPanel.addQuestions();
                }
                case 2 -> SurveyRequest.postSurveyResult();
            }
        }
    }

    public void prevPage(){
        switch (page) {
            case 1 -> SignUpPage.mainFrame.change(MainFrame.loginPage, LoginPage.class);
            case 2 -> {
                page = 1;
                InterestSelectPanel.surveyPanel = new SurveyPanel(interestSelectPanel);
                InterestSelectPanel.mainFrame.change(InterestSelectPanel.surveyPanel, SurveyPanel.class);
                InterestSelectPanel.surveyPanel.addQuestions();
            }
        }
    }

    public SurveyPanel(InterestSelectPanel interestSelectPanel){
        this.interestSelectPanel = interestSelectPanel;
        setSize(800, 400);
        setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Images.SurveyBackGround.getImageIcon().getImage(), 0,0, null);
    }
}
