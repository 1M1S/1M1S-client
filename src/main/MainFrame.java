package main;


import forumPage.ForumPage;
import loginPage.LoginPage;
import signUpPage.SignUpPage;
import surveyPage.InterestSelectPanel;
import utils.Font;

import javax.swing.*;
import java.util.Enumeration;



public class MainFrame extends JFrame {
    static public ForumPage forumPage;
    static public LoginPage loginPage;
    //static public MainFrame mainPage;
    static public SignUpPage joinPage;
    static public InterestSelectPanel interestSelectPanel;

    public <T extends JComponent> void  change(Object page, Class<T> q){
        getContentPane().removeAll();
        getContentPane().add(q.cast(page));
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public MainFrame(){
        setVisible(true);
        try {
            UIDefaults swingComponentDefaultTable = UIManager.getDefaults();
            Enumeration allDefaultKey = swingComponentDefaultTable.keys();
            while(allDefaultKey.hasMoreElements()) {
                String defaultKey = allDefaultKey.nextElement().toString();
                if(defaultKey.indexOf("font") != -1) {
                    UIManager.put(defaultKey, Font.mainFont);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(1115, 824);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        //mainFrame.mainPage = new mainPage(mainFrame);
        interestSelectPanel = new InterestSelectPanel(mainFrame);
        loginPage = new LoginPage(mainFrame);
        joinPage = new SignUpPage(mainFrame);
        //mainFrame.forumPage = new forumPage(mainFrame);
        mainFrame.change(loginPage, LoginPage.class);
    }

    public void paint(ImageIcon image){
        super.paint(getGraphics());
        getGraphics().drawImage(image.getImage(), 0, 0,this);
    }
}
