package signUpPage;

import loginPage.LoginPage;
import main.MainFrame;
import utils.Images;
import javax.swing.*;
import java.awt.*;

public class MemberInformationPanel extends JPanel {
    static public SignUpPage signUpPage;
    public TextField nickNameText;
    public TextField emailText;
    public TextField userIdText;
    public TextField passwdText;
    public TextField passwdConfirmText;
    public TextField nameText;
    public String gender = "";
    public ButtonGroup group;

    public boolean check(){
        return passwdConfirmText.getText().equals(passwdText.getText());
    }

    public MemberInformationPanel(SignUpPage signUpPage){
        MemberInformationPanel.signUpPage = signUpPage;
        setSize(1100, 824);
        setLayout(null);
        setVisible(true);

        //패널1 되돌아가기 버튼
        JButton rollBackButton = new JButton(Images.RollBackButton.getImageIcon());
        rollBackButton.addActionListener(e -> SignUpPage.mainFrame.change(MainFrame.loginPage, LoginPage.class));
        rollBackButton.setBounds(175, 105, 80, 80);
        rollBackButton.setContentAreaFilled(false);
        add(rollBackButton);

        //이름 입력받기
        Label nameLabel = new Label();
        nameLabel.setText("Name");
        nameLabel.setBounds(320, 200, 60, 30);
        add(nameLabel);
        nameText = new TextField();
        nameText.setBounds(390, 200, 100, 30);
        add(nameText);

        //성별 입력받기
        group = new ButtonGroup();
        JRadioButton manBox = new JRadioButton("man");
        manBox.setBounds(580, 200, 70, 30);
        manBox.addActionListener(e->{if(manBox.isSelected())gender = "man";});
        add(manBox);
        group.add(manBox);
        JRadioButton womanBox = new JRadioButton("woman");
        womanBox.addActionListener(e->{if(womanBox.isSelected())gender = "woman";});
        womanBox.setBounds(660, 200, 97, 30);
        add(womanBox);
        group.add(womanBox);

        //닉네임 입력받기
        Label nicknameLabel = new Label("Nickname : ");
        nicknameLabel.setBounds(320, 260, 95, 30);
        add(nicknameLabel);
        nickNameText = new TextField();
        nickNameText.setBounds(430, 260, 100, 30);
        add(nickNameText);

        //이메일 입력받기
        Label emailIdLabel = new Label();
        emailIdLabel.setText("E-mail");
        emailIdLabel.setBounds(320, 320, 70, 30);
        add(emailIdLabel);
        emailText = new TextField();
        emailText.setBounds(400, 320, 300, 30);
        add(emailText);

        //아이디 입력받기
        Label userIdLabel = new Label();
        userIdLabel.setText("User ID");
        userIdLabel.setBounds(320, 380, 70, 30);
        add(userIdLabel);
        userIdText = new TextField();
        userIdText.setBounds(400, 380, 100, 30);
        add(userIdText);

        //비밀번호
        Label passwdLabel = new Label();
        passwdLabel.setText("PASSWORD");
        passwdLabel.setBounds(320, 440, 125, 30);
        add(passwdLabel);
        passwdText = new TextField();
        passwdText.setBounds(460, 440, 220, 30);
        passwdText.setEchoChar('*');
        add(passwdText);

        //비밀번호 확인
        Label passwd2Label = new Label();
        passwd2Label.setText("PASSWORD Check");
        passwd2Label.setBounds(320, 500, 190, 30);
        add(passwd2Label);
        passwdConfirmText = new TextField();
        passwdConfirmText.setBounds(525, 500, 220, 30);
        passwdConfirmText.setEchoChar('*');
        add(passwdConfirmText);

        //폼 제출
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            JoinRequest.join();
        });
        nextButton.setBounds(550, 600, 120, 50);
        add(nextButton);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Images.JoinFormBackGround.getImageIcon().getImage(), 0,0, null);
    }
}
