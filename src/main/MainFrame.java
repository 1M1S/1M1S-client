package main;

import forumPage.forumPage;
import loginPage.loginPage;
import mainPage.mainPage;
import signUpPage.signUpPage;

import javax.swing.*;

public class MainFrame extends JFrame {
    public mainPage mainPage;
    public signUpPage joinPage;
    public forumPage forumPage;
    public loginPage loginPage;

    public <Page extends JFrame> void change(Page page){
        getContentPane().removeAll();
        getContentPane().add(page);
        revalidate();
        repaint();
    }

    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        mainFrame.mainPage = new mainPage(mainFrame);
        mainFrame.loginPage = new loginPage(mainFrame);
        mainFrame.joinPage = new signUpPage(mainFrame);
        mainFrame.forumPage = new forumPage(mainFrame);
        mainFrame.add(mainFrame.loginPage);
        mainFrame.setVisible(true);
    }
}
