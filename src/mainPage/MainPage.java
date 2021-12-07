package mainPage;

import CurriculumPage.CurriculumPage;
import RankingPage.RankingPage;
import SchedulePage.SchdulePage;
import consultingPage.ConsultingPage;
import forumPage.ForumPage;
import main.MainFrame;
import myPage.MyPage;
import utils.Images;
import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel {
    private final Font mainFont = new Font("나눔고딕", Font.PLAIN, 20);
    public myPanel mainPanel = new myPanel();
    static public MainFrame mainFrame = null;
    public MainPage(MainFrame mainFrame) {
        MainPage.mainFrame = mainFrame;
        //프레임 설정
        setVisible(true);
        setLayout(null);
        setSize(1115, 824);
        add(mainPanel);

        //**************************************************************************************************************

        //패널 설정
        mainPanel.setSize(1100, 824);
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        //**************************************************************************************************************

        //마이페이지 버튼 설정
        JButton button1 = new JButton("회원 정보");
        button1.addActionListener(e -> {
            MyPage mypage = new MyPage(mainFrame);
        });
        button1.setFont(mainFont);
        button1.setBounds(0, 0, 200, 110);
        mainPanel.add(button1);

        //시간관리 버튼 설정
        JButton button2 = new JButton("시간 관리");
        button2.addActionListener(e -> new SchdulePage(mainFrame));
        button2.setFont(mainFont);
        button2.setBounds(0, 110, 200, 110);
        mainPanel.add(button2);

        //랭킹 버튼 설정
        JButton button3 = new JButton("랭킹");
        button3.addActionListener(e -> new RankingPage(mainFrame));
        button3.setFont(mainFont);
        button3.setBounds(0, 220, 200, 110);
        mainPanel.add(button3);

        //상담 버튼 설정
        JButton button4 = new JButton("상담");
        button4.addActionListener(e -> new ConsultingPage());
        button4.setFont(mainFont);
        button4.setBounds(0, 330, 200, 110);
        mainPanel.add(button4);

        //게시판 버튼 설정
        JButton button5 = new JButton("게시판");
        button5.addActionListener(e -> new ForumPage(mainFrame));
        button5.setFont(mainFont);
        button5.setBounds(0, 440, 200, 110);
        mainPanel.add(button5);

        //커리큘럼 버튼 설정
        JButton button6 = new JButton("커리큘럼");
        button6.addActionListener(e -> new CurriculumPage());
        button6.setFont(mainFont);
        button6.setBounds(0, 550, 200, 250);
        mainPanel.add(button6);
    }

    static class myPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g) {
            Dimension d = getSize();
            ImageIcon image = Images.MainPageBackGround.getImageIcon();
            g.drawImage(image.getImage(), 0, 0, d.width, d.height, null);
        }
    }
}

