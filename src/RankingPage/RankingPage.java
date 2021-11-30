package RankingPage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Ranking;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RankingPage extends JFrame {
    private rankingPanel mainRankPanel = new rankingPanel();
    private JTable rankingTable;
    private DefaultTableModel rankingDtm;
    private JTable workoutTable;
    private DefaultTableModel workoutDtm;
    private JTable jobTable;
    private DefaultTableModel jobDtm;
    private JTable programTable;
    private DefaultTableModel programDtm;
    public static void main(String[] args) {
        new RankingPage();
    }

    public RankingPage(){
        setTitle("1M1S");
        setVisible(true);
        setLayout(null);
        setResizable(true);
        setSize(1115, 824);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainRankPanel);

        mainRankPanel.setVisible(true);
        mainRankPanel.setSize(1110, 824);
        mainRankPanel.setLayout(null);

        JLabel title = new JLabel("Ranking");
        title.setFont(new Font("나눔고딕", Font.BOLD, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(150, 90, 800, 70);
        mainRankPanel.add(title);

        JButton mainPageRollBackBtn = new JButton(new ImageIcon("C:\\1M1S-client\\src\\RankingPage\\rollback.png"));
        mainPageRollBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                //main화면 setVisible(true);                                         ///준화꺼랑 합친 후에 추가하기
            }
        });
        mainPageRollBackBtn.setBounds(0, 0, 82, 82);
        mainRankPanel.add(mainPageRollBackBtn);
/******************************************************************************************************************/

        // 랭킹 순서 테이블
        rankingDtm = new DefaultTableModel(0, 0);
        rankingTable = new JTable(rankingDtm);
        rankingTable.setRowHeight(80);
        rankingTable.setFont(new Font("나눔고딕", Font.PLAIN, 15));

        //칼럼 만들기
        rankingDtm.setColumnIdentifiers(new String[]{"등수"});
        rankingTable.getColumn("등수").setWidth(50);
        rankingTable.getColumn("등수").setMinWidth(50);
        rankingTable.getColumn("등수").setMaxWidth(50);


        //컬럼 크기, 위치 조절 불가
        rankingTable.getTableHeader().setReorderingAllowed(false);

        //스크롤팬 설정
        JScrollPane rankingScroll = new JScrollPane(rankingTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rankingScroll.setVisible(true);
        rankingScroll.setBounds(195, 250, 70, 280);

        for(int i=1;i<=3;i++)
            rankingDtm.addRow(new String[]{"  "+i+"등"});

        mainRankPanel.add(rankingScroll);

/******************************************************************************************************************/

        // 랭킹 "운동" 테이블 설정
        workoutDtm = new DefaultTableModel(0, 0);
        workoutTable = new JTable(workoutDtm);
        workoutTable.setRowHeight(80);
        workoutTable.setFont(new Font("나눔고딕", Font.PLAIN, 15));

        //칼럼 만들기
        workoutDtm.setColumnIdentifiers(new String[]{"운동"});
        workoutTable.getColumn("운동").setWidth(235);
        workoutTable.getColumn("운동").setMinWidth(235);
        workoutTable.getColumn("운동").setMaxWidth(235);

        //컬럼 크기, 위치 조절 불가
        workoutTable.getTableHeader().setReorderingAllowed(false);

        //스크롤팬 설정
        JScrollPane rankingScroll2 = new JScrollPane(workoutTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rankingScroll2.setVisible(true);
        rankingScroll2.setBounds(240, 250, 230, 280);

        getRanking(workoutDtm, 1L);

        mainRankPanel.add(rankingScroll2);

/******************************************************************************************************************/

        // 랭킹 "취업" 테이블 설정
        jobDtm = new DefaultTableModel(0, 0);
        jobTable = new JTable(jobDtm);
        jobTable.setRowHeight(80);
        jobTable.setFont(new Font("나눔고딕", Font.PLAIN, 15));

        //칼럼 만들기
        jobDtm.setColumnIdentifiers(new String[]{"취업"});
        jobTable.getColumn("취업").setWidth(235);
        jobTable.getColumn("취업").setMinWidth(235);
        jobTable.getColumn("취업").setMaxWidth(235);


        //컬럼 크기, 위치 조절 불가
        jobTable.getTableHeader().setReorderingAllowed(false);

        //스크롤팬 설정
        JScrollPane rankingScroll3 = new JScrollPane(jobTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rankingScroll3.setVisible(true);
        rankingScroll3.setBounds(470, 250, 230, 280);

        getRanking(jobDtm, 2L);

        mainRankPanel.add(rankingScroll3);

/******************************************************************************************************************/

        // 랭킹 "프로그래밍" 테이블 설정
        programDtm = new DefaultTableModel(0, 0);
        programTable = new JTable(programDtm);
        programTable.setRowHeight(80);
        programTable.setFont(new Font("나눔고딕", Font.PLAIN, 15));

        //칼럼 만들기
        programDtm.setColumnIdentifiers(new String[]{"프로그래밍"});
        programTable.getColumn("프로그래밍").setWidth(235);
        programTable.getColumn("프로그래밍").setMinWidth(235);
        programTable.getColumn("프로그래밍").setMaxWidth(235);

        //컬럼 크기, 위치 조절 불가
        programTable.getTableHeader().setReorderingAllowed(false);

        //스크롤팬 설정
        JScrollPane rankingScroll4 = new JScrollPane(programTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rankingScroll4.setVisible(true);
        rankingScroll4.setBounds(700, 250, 230, 280);

        getRanking(programDtm, 3L);

        mainRankPanel.add(rankingScroll4);

    }

/******************************************************************************************************************/

    // 관심분야별 상위 3명 랭커 출력 [GET]
    void getRanking(DefaultTableModel dtm,Long interest_id) {
        dtm.setRowCount(0);
        try {
            HttpClient client = HttpClient.newHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            //request보내기
            String uri = "http://localhost:8080/api/ranking/top3?interest_id="+Long.toString(interest_id);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();
            System.out.println("getUserRanking request : " + request);

            // 위에서 생성한 request를 보내고, 받은 response를 저장
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("getUserRanking response : " + response);
            System.out.println("getUserRanking body : " + response.body());

            // responseBody to Post Class Array
            Iterable<Ranking> posts = mapper.readValue(response.body(), new TypeReference<Iterable<Ranking>>(){});

            // JTable에 관심분야별 3명의 랭커들 추가
            for(Ranking p : posts) {
                String result = "         "+"[ID: "+p.getMember().getUsername()+", Score: "+p.getScore()+"]";
                dtm.addRow(new String[]{result});
                System.out.println(p);
            }
        } catch (Exception e) {
            System.out.println("오류 발생");
            e.printStackTrace();
        }
    }


/******************************************************************************************************************/
    class rankingPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Dimension d = this.getSize();
            ImageIcon image = new ImageIcon("C:\\1M1S-client\\src\\RankingPage\\rankImg.png");
            g.drawImage(image.getImage(), 0, 0, d.width, d.height, (ImageObserver)null);
        }
    }
}

