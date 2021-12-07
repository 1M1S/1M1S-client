package loginPage;

import db.Member;
import main.MainFrame;
import signUpPage.SignUpPage;
import utils.Images;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private LoginPage loginPage;
    public void login(String username, String password){
            Member loginInformation = new Member(username,password);
            LoginRequest.Login(loginPage.mainFrame, loginInformation);
    }

    private void join(){
        LoginPage.mainFrame.change(MainFrame.joinPage, SignUpPage.class);
    }
    public LoginPanel(LoginPage loginPage){
        this.loginPage = loginPage;

        setSize(1100, 824);
        setLayout(null);

        Label idLabel = new Label();
        idLabel.setText("USER ID");
        idLabel.setBounds(390, 230, 88, 30);
        add(idLabel);

        TextField idText = new TextField();
        idText.setBounds(390, 265, 370, 30);
        add(idText);

        Label pwdLabel = new Label();
        pwdLabel.setText("PASSWORD");
        pwdLabel.setBounds(390, 310, 125, 30);
        add(pwdLabel);

        TextField pwdText = new TextField();
        pwdText.setBounds(390, 345, 370, 30);
        pwdText.setEchoChar('*');
        add(pwdText);
        //로그인 버튼
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e ->login(idText.getText(), pwdText.getText()));
        loginButton.setBounds(390, 385, 180, 80);
        add(loginButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> join());
        signUpButton.setBounds(580, 385, 180, 80);
        add(signUpButton);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Images.DefaultBackGround.getImageIcon().getImage(), 0,0, null);
    }
}