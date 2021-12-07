package main;


import db.MemberInformation;
import forumPage.ForumPage;
import loginPage.LoginPage;
import lombok.SneakyThrows;
import mainPage.MainPage;
import signUpPage.SignUpPage;
import surveyPage.InterestSelectPanel;
import utils.Font;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Timer;


public class MainFrame extends JFrame {
    static public LoginPage loginPage;
    static public MainPage mainPage;
    static public SignUpPage joinPage;
    static public InterestSelectPanel interestSelectPanel;
    static public QuitPage quitPage;
    static public MainFrame mainFrame;

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
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public static void main(String[] args){

        mainFrame = new MainFrame();
//        mainFrame.addWindowListener(new WindowAdapter() {
//            @SneakyThrows
//            public void windowClosing(WindowEvent e){
//                mainFrame.getContentPane().removeAll();
//                mainFrame.add(quitPage);
//                mainFrame.getContentPane().revalidate();
//                mainFrame.getContentPane().repaint();
//                mainFrame.wait(3000);
////                try {
////
////
////
////                } catch (InterruptedException ex) {
////                    System.out.println("Oh noes!");
////                }
//                mainFrame.dispose();
//            System.exit(0);
//            }
//        });
        quitPage = new QuitPage(mainFrame);
        mainFrame.mainPage = new MainPage(mainFrame);
        interestSelectPanel = new InterestSelectPanel(mainFrame);
        loginPage = new LoginPage(mainFrame);
        joinPage = new SignUpPage(mainFrame);
        mainFrame.change(loginPage, LoginPage.class);

    }

}
