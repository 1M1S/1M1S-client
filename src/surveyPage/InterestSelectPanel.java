package surveyPage;

import db.Interest;
import main.MainFrame;
import signUpPage.JoinRequest;
import signUpPage.SignUpPage;
import utils.Font;
import utils.Images;

import javax.swing.*;
import java.awt.*;





public class InterestSelectPanel extends JPanel {
    static public MainFrame mainFrame;
    static public SurveyPanel surveyPanel;
    static public Interest interest;
    public void submit(Long interest_id){
        SurveyRequest.getSurveys(interest_id);
    }

    public InterestSelectPanel(MainFrame mainFrame){
        InterestSelectPanel.mainFrame = mainFrame;
        surveyPanel= new SurveyPanel(this);
        //패널2에서 관심분야 선택
        setSize(1100, 824);
        setLayout(null);
        setVisible(true);
        //패널 2 - 관심분야 선택
        //안내 글
        JLabel text = new JLabel();
        text.setText("관심 분야를 선택해주세요!");
        text.setFont(Font.bigFont);
        text.setBounds(300 ,180, 500, 70);
        add(text);

        //운동 선택 버튼
        JButton exerciseButton = new JButton(Images.ExerciseCategory.getImageIcon());
        exerciseButton.addActionListener(e -> submit(1L));
        exerciseButton.setBounds(300, 310, 150, 150);
        exerciseButton.setContentAreaFilled(false);
        add(exerciseButton);
        JLabel exerciseLabel = new JLabel();
        exerciseLabel.setText("운동");
        exerciseLabel.setBounds(355, 470, 60, 30);
        add(exerciseLabel);

        //프로그래밍 선택 버튼
        JButton programingButton = new JButton(Images.ProgrammingCategory.getImageIcon());
        programingButton.addActionListener(e -> submit(2L));
        programingButton.setBounds(480, 310, 150, 150);
        programingButton.setContentAreaFilled(false);
        add(programingButton);
        JLabel programingLabel = new JLabel();
        programingLabel.setText("프로그래밍");
        programingLabel.setBounds(505, 470, 150, 30);
        add(programingLabel);

        //취업 선택 버튼
        JButton employButton = new JButton(Images.EmployCategory.getImageIcon());
        employButton.addActionListener(e -> submit(3L));
        employButton.setBounds(660, 310, 150, 150);
        employButton.setContentAreaFilled(false);
        add(employButton);
        JLabel employLabel = new JLabel();
        employLabel.setText("취업 준비");
        employLabel.setBounds(695, 470, 120, 30);
        add(employLabel);

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Images.InterestSelectBackGround.getImageIcon().getImage(), 0,0, null);
    }
}
