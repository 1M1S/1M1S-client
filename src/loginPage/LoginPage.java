package loginPage;

import main.MainFrame;

import javax.swing.*;

public class LoginPage extends JPanel{
    public static MainFrame mainFrame;
    private LoginPanel loginPanel;
    public LoginPage(MainFrame mainFrame){

        String path = System.getProperty("user.dir");
        System.out.println("Working Directory = " + path);
        LoginPage.mainFrame = mainFrame;
        setBounds(0,0,mainFrame.getWidth(),mainFrame.getHeight());
        loginPanel = new LoginPanel(this);
        //프레임 설정
        setLayout(null);
        add(loginPanel);
        setVisible(true);
    }


}
