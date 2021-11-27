package CurriculumPage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Curriculum;
import db.CurriculumSchedule;
import db.MemberCurriculum;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class CurriculumPage extends JFrame {
    private mainCurriculumPanel mainPanel = new mainCurriculumPanel();
    private JTable curriMainTable;
    private JTable curriSubTable;
    private DefaultTableModel curriMainDtm = new DefaultTableModel(0, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel curriSubDtm = new DefaultTableModel(0, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public static void main(String[] args) {
        new CurriculumPage();
    }

    public CurriculumPage() {
        setTitle("1M1S");
        setVisible(true);
        setLayout(null);
        setResizable(true);
        setSize(1115, 824);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);

        mainPanel.setVisible(true);
        mainPanel.setSize(1110, 824);
        mainPanel.setLayout(null);

        // 커리큘럼 항목 선택 테이블 설정(왼쪽 테이블)
        curriMainTable = new JTable(curriMainDtm);
        curriMainTable.setRowHeight(40);
        curriMainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //하나의 행만 선택할 수 있도록 지정
        curriMainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getSubCurri(curriSubDtm);
            }
        });
        curriMainTable.setFont(new Font("나눔고딕", Font.PLAIN, 15));
        //칼럼 만들기
        String[] header = {"id", "interest","level","check"};
        curriMainDtm.setColumnIdentifiers(header);
        //id 안 보이게 설정
        curriMainTable.removeColumn(curriMainTable.getColumnModel().getColumn(0));
        curriMainTable.getColumn("interest").setWidth(100);
        curriMainTable.getColumn("interest").setMinWidth(100);
        curriMainTable.getColumn("interest").setMaxWidth(100);
        curriMainTable.getColumn("level").setWidth(130);
        curriMainTable.getColumn("level").setMinWidth(130);
        curriMainTable.getColumn("level").setMaxWidth(130);
        curriMainTable.getColumn("check").setWidth(75);
        curriMainTable.getColumn("check").setMinWidth(75);
        curriMainTable.getColumn("check").setMaxWidth(75);

        //컬럼 크기, 위치 조절 불가
        curriMainTable.getTableHeader().setReorderingAllowed(false);

        //스크롤팬 설정
        JScrollPane curriMainScroll = new JScrollPane(curriMainTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        curriMainScroll.setVisible(true);
        curriMainScroll.setBounds(130, 190, 322, 550);
        mainPanel.add(curriMainScroll);


        // 선택한 커리큘럼 항목에 대한 커리큘럼 테이블 설정(오른쪽 테이블)
        curriSubTable = new JTable(curriSubDtm);
        curriSubTable.setRowHeight(40);
        curriSubTable.setFont(new Font("나눔고딕", Font.PLAIN, 15));
        //칼럼 만들기
        String[] header2 = {"id", "Curriculum"}; //table header
        curriSubDtm.setColumnIdentifiers(header2);
        //id 안 보이게 설정
        curriSubTable.removeColumn(curriSubTable.getColumnModel().getColumn(0));
        curriSubTable.getColumn("Curriculum").setWidth(430);
        curriSubTable.getColumn("Curriculum").setMinWidth(430);
        curriSubTable.getColumn("Curriculum").setMaxWidth(430);

        //컬럼 크기, 위치 조절 불가
        curriSubTable.getTableHeader().setReorderingAllowed(false);

        //스크롤팬 설정
        JScrollPane curriSubScroll = new JScrollPane(curriSubTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        curriSubScroll.setVisible(true);
        curriSubScroll.setBounds(574, 190, 450, 550);
        mainPanel.add(curriSubScroll);


        JLabel title = new JLabel("Curriculum");
        title.setFont(new Font("나눔고딕", Font.BOLD, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(177, 45, 748, 73);
        mainPanel.add(title);

        getMainCurri(curriMainDtm);

        JButton addCurriBtn = new JButton("Add");
        addCurriBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUserCurri(curriMainDtm);
            }
        });
        addCurriBtn.setFont(new Font("나눔고딕", Font.BOLD, 15));
        addCurriBtn.setBounds(762, 128, 125, 35);
        mainPanel.add(addCurriBtn);

        JButton delCurriBtn = new JButton("Delete");
        //일정 삭제 기능
        delCurriBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delUserCurri(curriMainDtm);
            }
        });
        delCurriBtn.setFont(new Font("나눔고딕", Font.BOLD, 15));
        delCurriBtn.setBounds(899, 128, 125, 35);
        mainPanel.add(delCurriBtn);

        repaint();
        revalidate();
    }

    /******************************************************************************************************************/
    // Main 커리큘럼

    // 추가 시 해당 항목 색깔 변경 기능
//     void changeState() {
//         int editRow = curriMainTable.getSelectedRow();
//         if (editRow >= 0) {
//             //Long editId = (Long)curriMainTable.getModel().getValueAt(editRow, 0);
//
//         }
//     }

    //일정 추가 기능 => GET
    void addMainTable(DefaultTableModel dtm) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            ObjectMapper objectMapper = new ObjectMapper();

            //request 보내기
            String uri = "http://localhost:8080/api/curriculum";

            //request body 생성
            MemberCurriculum addpost = new MemberCurriculum();

            //request
            String requestBody = objectMapper.writeValueAsString(addpost);
            System.out.println("addMainTable requestBody : " + requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json; charset=UTF-8")  // content type, 인코딩형식 지정.
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))  // HTTP 메소드, body 지정(위에서 만든 JSON 전달)
                    .build();

            //response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("addMainTable response : " + response);
            System.out.println("addMainTable response body : " + response.body());

            //update
            getMainCurri(dtm);
            dtm.fireTableDataChanged();
        } catch (Exception ex) {
            System.out.println("오류 발생");
            ex.printStackTrace();
        }
    }

    // Main 커리큘럼 읽어오기
    void getMainCurri(DefaultTableModel dtm) {
        dtm.setRowCount(0);
        try {
            HttpClient client = HttpClient.newHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            //request보내기
            String uri = "http://localhost:8080/api/curriculum";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();
            System.out.println("getMainCurri request : " + request);

            // 위에서 생성한 request를 보내고, 받은 response를 저장
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("getMainCurri response : " + response);
            System.out.println("getMainCurri body : " + response.body());

            // responseBody to Post Class Array
            Iterable<Curriculum> posts = mapper.readValue(response.body(), new TypeReference<Iterable<Curriculum>>(){});

            // Jtable에 일정 추가
            for(Curriculum p : posts) {
                dtm.addRow(new Object[] {
                        p.getId(),p.getInterest().getSubject(),p.getLevel(), false});
                System.out.println(p);
            }
            dtm.fireTableDataChanged();
            checkUserCurriculum(dtm);
        } catch (Exception e) {
            System.out.println("오류 발생");
            e.printStackTrace();
        }
    }
    /******************************************************************************************************************/
    // Sub 커리큘럼

    // Sub 커리큘럼 읽어오기
    void getSubCurri(DefaultTableModel dtm) {

        // curriculum_id 가져오기
        int editRow = curriMainTable.getSelectedRow();
        if (editRow >= 0){
            Long curriculum_id = (Long)curriMainTable.getModel().getValueAt(editRow, 0);

            dtm.setRowCount(0);
            try {
                HttpClient client = HttpClient.newHttpClient();
                ObjectMapper mapper = new ObjectMapper();

                //request보내기
                String uri = "http://localhost:8080/api/curriculum/";
                uri = uri  + Long.toString(curriculum_id) + "/schedule";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .GET()
                        .build();
                System.out.println("getSubCurri request : " + request);

                // 위에서 생성한 request를 보내고, 받은 response를 저장
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("getSubCurri response : " + response);
                System.out.println("getSubCurri body : " + response.body());

                // responseBody to Post Class Array
                Iterable<CurriculumSchedule> posts = mapper.readValue(response.body(), new TypeReference<Iterable<CurriculumSchedule>>(){});

                // Jtable에 일정 추가
                for(CurriculumSchedule p : posts) {
                    dtm.addRow(new Object[] {
                            p.getId(),p.getContent()});
                    System.out.println(p);
                }
                dtm.fireTableDataChanged();
            } catch (Exception e) {
                System.out.println("오류 발생");
                e.printStackTrace();
            }
        }
    }


/******************************************************************************************************************/
    // 유저의 커리큘럼

    // 유저의 커리큘럼 읽어오기 => GET
    void checkUserCurriculum(DefaultTableModel dtm) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            // uri 생성
            String uri = "http://localhost:8080/api/user/" + Long.toString(1) + "/curriculum";

            // request 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();

            // 위에서 생성한 request를 보내고, 받은 response를 저장
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // responseBody to Iterable<MemberCurriculum>
            Iterable<MemberCurriculum> M = mapper.readValue(response.body(), new TypeReference<Iterable<MemberCurriculum>>(){});

            // MemberCurriculum에서 커리큘럼 id 가져와서 커리큘럼 목록 중에 id 일치하는 친구들 true로 변경.
            for(MemberCurriculum m : M) {
                // 모든 커리큘럼 목록들에 대해서 for문 돌려서 일치하는 id 검색
                System.out.println("row 개수 : " + dtm.getRowCount());
                for(int i=0; i<dtm.getRowCount(); i++) {
                    Long CurriId = (Long) curriMainTable.getModel().getValueAt(i, 0);
                    if(CurriId.equals(m.getCurriculum().getId())) {
                        curriMainTable.getModel().setValueAt(true, i, 3);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("오류 발생");
            e.printStackTrace();
        }
    }

    // 유저의 커리큘럼 추가
    void addUserCurri(DefaultTableModel dtm) {
        int addRow = curriMainTable.getSelectedRow();
        if (addRow >= 0) {
            Long addCurriId = (Long) curriMainTable.getModel().getValueAt(addRow, 0);

            String uri = "http://localhost:8080";
            uri += "/api/user/" + Long.toString(1L) + "/curriculum/" + "?curriculum_id=" + Long.toString(addCurriId);

            try {
                HttpClient client = HttpClient.newHttpClient();
                ObjectMapper mapper = new ObjectMapper();

                //request body 생성
                MemberCurriculum addpost = new MemberCurriculum();

                //request
                String requestBody = mapper.writeValueAsString(addpost);
                System.out.println("addUserCurri requestBody : " + requestBody);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .header("Content-Type", "application/json; charset=UTF-8")  // content type, 인코딩형식 지정.
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))  // HTTP 메소드, body 지정(위에서 만든 JSON 전달)
                        .build();

                //response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("addUserCurri response : " + response);
                System.out.println("addUserCurri response body : " + response.body());

                // 선택한 커리큘럼의 check여부 true로 변경.
                curriMainTable.getModel().setValueAt(true, addRow,3);
                dtm.fireTableDataChanged();

                JOptionPane.showMessageDialog((Component)null, "User-Curriculum added");
            } catch (Exception ex) {
                System.out.println("오류 발생");
                ex.printStackTrace();
                JOptionPane.showMessageDialog((Component)null, "Unable To add");
            }
        }
    }

    // 유저의 커리큘럼 삭제
    void delUserCurri(DefaultTableModel dtm) {

        int delRow = curriMainTable.getSelectedRow();
        if (delRow >= 0) {
            Long delCurriId = (Long)curriMainTable.getModel().getValueAt(delRow, 0);

            String uri = "http://localhost:8080";
            uri += "/api/user/" + Long.toString(1L) + "/curriculum/" + "?curriculum_id=" + Long.toString(delCurriId);

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .DELETE()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("delUserCurri response: "+(String)response.body());

                // 선택한 커리큘럼의 check여부 false로 변경.
                curriMainTable.getModel().setValueAt(false, delRow,3);

            } catch (Exception ex) {
                System.out.println("오류 발생");
                ex.printStackTrace();
            }
            dtm.fireTableDataChanged();
            JOptionPane.showMessageDialog((Component)null, "User-Curriculum Deleted");
        } else {
            JOptionPane.showMessageDialog((Component)null, "Unable To Delete");
        }
    }


/******************************************************************************************************************/

    class mainCurriculumPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Dimension d = this.getSize();
            ImageIcon image = new ImageIcon();///이미지 수정하기
            g.drawImage(image.getImage(), 0, 0, d.width, d.height, (ImageObserver)null);
        }
    }
}