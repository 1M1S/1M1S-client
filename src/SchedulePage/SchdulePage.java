package SchedulePage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import db.MemberSchedule;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


public class SchdulePage extends JFrame {
    private mainSchedulePanel mainPanel = new mainSchedulePanel();
    private dailyPanel DayPanel = new dailyPanel();
    private writePanel Add_panel = new writePanel();
    private writePanel Modify_panel = new writePanel();

    //패널별로 요일 분류
    private String choosePanelDate;

    private String[] header = new String[]{"id", "Content", "StartTime", "EndTime","Finish"};
    private JTable todoTable;

    public static void main(String[] args) {
        new SchdulePage();
    }

    public SchdulePage() {
        setTitle("1M1S");
        setVisible(true);
        setLayout(null);
        setResizable(true);
        setSize(1115, 824);
        setLocationRelativeTo((Component)null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        add(DayPanel);
        add(Add_panel);
        add(Modify_panel);

        mainPanel.setVisible(true);
        mainPanel.setSize(1110, 824);
        mainPanel.setLayout((LayoutManager)null);

        JLabel pageTitle = new JLabel("What do you want?");
        pageTitle.setHorizontalAlignment(0);
        pageTitle.setFont(new Font("나눔고딕", 1, 40));
        pageTitle.setBounds(304, 220, 521, 85);
        mainPanel.add(pageTitle);

        JButton mainPageRollBackBtn = new JButton(new ImageIcon("C:\\1M1S-client\\src\\SchedulePage\\rollback.png"));
        mainPageRollBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                //main화면 setVisible(true);                                         ///준화꺼랑 합친 후에 추가하기
            }
        });
        mainPageRollBackBtn.setBounds(0, 0, 82, 82);
        mainPanel.add(mainPageRollBackBtn);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JLabel title = new JLabel("To Do List");
        title.setFont(new Font("나눔고딕", 1, 50));
        title.setHorizontalAlignment(0);
        title.setBounds(170, 35, 750, 70);
        DayPanel.add(title);


        JButton chooseDayPageRollBackBtn = new JButton(new ImageIcon("C:\\1M1S-client\\src\\SchedulePage\\rollback.png"));
        chooseDayPageRollBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DayPanel.setVisible(false);
                mainPanel.setVisible(true);
            }
        });
        chooseDayPageRollBackBtn.setBounds(0, 0, 82, 82);
        DayPanel.add(chooseDayPageRollBackBtn);


        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        todoTable = new JTable(dtm);
        dtm.setColumnIdentifiers(header);
        todoTable.removeColumn(todoTable.getColumnModel().getColumn(0));
        todoTable.getColumn("Content").setWidth(400);
        todoTable.getColumn("Content").setMinWidth(400);
        todoTable.getColumn("Content").setMaxWidth(400);
        todoTable.getColumn("StartTime").setWidth(130);
        todoTable.getColumn("StartTime").setMinWidth(130);
        todoTable.getColumn("StartTime").setMaxWidth(130);
        todoTable.getColumn("EndTime").setWidth(130);
        todoTable.getColumn("EndTime").setMinWidth(130);
        todoTable.getColumn("EndTime").setMaxWidth(130);
        todoTable.getColumn("Finish").setWidth(80);
        todoTable.getColumn("Finish").setMinWidth(80);
        todoTable.getColumn("Finish").setMaxWidth(80);

        todoTable.getTableHeader().setReorderingAllowed(false);

        // table scroll 추가
        JScrollPane scroll = new JScrollPane(todoTable, 22, 31);
        scroll.setVisible(true);
        scroll.setBounds(130, 125, 787, 617);
        DayPanel.add(scroll);

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("나눔고딕", 1, 15));
        addButton.setBounds(936, 143, 105, 35);
        DayPanel.add(addButton);
        addButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                DayPanel.setVisible(false);
                Add_panel.setVisible(true);
            }
        });

        JButton editButton = new JButton("Modify");
        editButton.setFont(new Font("나눔고딕", 1, 15));
        editButton.setBounds(936, 198, 105, 35);
        DayPanel.add(editButton);
        editButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                DayPanel.setVisible(false);
                Modify_panel.setVisible(true);
            }
        });

        JButton delButton = new JButton("Delete");
        delButton.setFont(new Font("나눔고딕", 1, 15));
        delButton.setBounds(936, 252, 105, 35);
        DayPanel.add(delButton);
        //일정 삭제 기능
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int delRow = todoTable.getSelectedRow();
                if (delRow >= 0) {
                    Long delId = (Long)todoTable.getModel().getValueAt(delRow, 0);

                    String uri = "http://localhost:8080";
                    uri = uri + "/api/user/" + Long.toString(1L) + "/schedule/" + Long.toString(delId);

                    try {
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(uri))
                                .DELETE()
                                .build();

                        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                        System.out.println("delete response: "+(String)response.body());

                    } catch (Exception ex) {
                        System.out.println("오류 발생");
                        ex.printStackTrace();
                    }
                    //update
                    getTodoList(dtm);
                    JOptionPane.showMessageDialog((Component)null, "Row Deleted");
                } else {JOptionPane.showMessageDialog((Component)null, "Unable To Delete");
                }
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton btnMonday = new JButton("Monday");
        btnMonday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choosePanelDate = "월";
                mainPanel.setVisible(false);
                DayPanel.setVisible(true);
                getTodoList(dtm);
            }
        });
        btnMonday.setFont(new Font("나눔고딕", 0, 15));
        btnMonday.setBounds(352, 344, 136, 37);
        mainPanel.add(btnMonday);

        JButton btnTuesday = new JButton("Tuesday");
        btnTuesday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choosePanelDate = "화";
                mainPanel.setVisible(false);
                DayPanel.setVisible(true);
                getTodoList(dtm);
            }
        });
        btnTuesday.setFont(new Font("나눔고딕", 0, 15));
        btnTuesday.setBounds(500, 344, 136, 37);
        mainPanel.add(btnTuesday);

        JButton btnWednesday = new JButton("Wednesday");
        btnWednesday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choosePanelDate = "수";
                mainPanel.setVisible(false);
                DayPanel.setVisible(true);
                getTodoList(dtm);
            }
        });
        btnWednesday.setFont(new Font("나눔고딕", 0, 15));
        btnWednesday.setBounds(648, 344, 136, 37);
        mainPanel.add(btnWednesday);

        JButton btnThursday = new JButton("Thursday");
        btnThursday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choosePanelDate = "목";
                mainPanel.setVisible(false);
                DayPanel.setVisible(true);
                getTodoList(dtm);
            }
        });
        btnThursday.setFont(new Font("나눔고딕", 0, 15));
        btnThursday.setBounds(270, 408, 136, 37);
        mainPanel.add(btnThursday);

        JButton btnFriday = new JButton("Friday");
        btnFriday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choosePanelDate = "금";
                mainPanel.setVisible(false);
                DayPanel.setVisible(true);
                getTodoList(dtm);
            }
        });
        btnFriday.setFont(new Font("나눔고딕", 0, 15));
        btnFriday.setBounds(418, 408, 136, 37);
        mainPanel.add(btnFriday);

        JButton btnSaturday = new JButton("Saturday");
        btnSaturday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choosePanelDate = "토";
                mainPanel.setVisible(false);
                DayPanel.setVisible(true);
                getTodoList(dtm);
            }
        });
        btnSaturday.setFont(new Font("나눔고딕", 0, 15));
        btnSaturday.setBounds(566, 408, 136, 37);
        mainPanel.add(btnSaturday);

        JButton btnSunday = new JButton("Sunday");
        btnSunday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choosePanelDate = "일";
                mainPanel.setVisible(false);
                DayPanel.setVisible(true);
                getTodoList(dtm);
            }
        });
        btnSunday.setFont(new Font("나눔고딕", 0, 15));
        btnSunday.setBounds(717, 408, 136, 37);
        mainPanel.add(btnSunday);

 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DayPanel.setLayout(null);
        DayPanel.setBounds(0, 0, 1100, 824);
        DayPanel.setVisible(false);

        Add_panel.setLayout((LayoutManager)null);
        Add_panel.setBounds(0, 0, 1100, 824);
        Add_panel.setVisible(false);

        Modify_panel.setLayout((LayoutManager)null);
        Modify_panel.setBounds(0, 0, 1100, 824);
        Modify_panel.setVisible(false);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //일정 추가 패널

        JLabel addPanelTitle = new JLabel("Plan");
        addPanelTitle.setFont(new Font("나눔고딕", 1, 40));
        addPanelTitle.setHorizontalAlignment(0);
        addPanelTitle.setBounds(339, 174, 452, 67);
        Add_panel.add(addPanelTitle);

        JPanel panel = new JPanel();
        panel.setBounds(330, 264, 452, 322);
        Add_panel.add(panel);panel.setLayout((LayoutManager)null);

        JLabel lblPlan = new JLabel("Plan");
        lblPlan.setFont(new Font("나눔고딕", 0, 25));
        lblPlan.setHorizontalAlignment(0);
        lblPlan.setBounds(12, 50, 83, 48);
        panel.add(lblPlan);

        JTextArea txtPlan = new JTextArea(3, 10);
        txtPlan.setBounds(95, 34, 328, 77);
        panel.add(txtPlan);
        txtPlan.setColumns(10);
        txtPlan.requestFocus();
        txtPlan.setFocusable(true);
        txtPlan.setFont(new Font("나눔고딕", 0, 15));

        JLabel lblStartTime = new JLabel("Start_Time");
        lblStartTime.setFont(new Font("나눔고딕", 0, 25));
        lblStartTime.setHorizontalAlignment(0);
        lblStartTime.setBounds(12, 136, 163, 37);
        panel.add(lblStartTime);

        JTextField txtStart = new JTextField();
        txtStart.setText("ex) 2021-11-25 01:30:00");
        txtStart.setEnabled(true);txtStart.setColumns(10);
        txtStart.setBounds(204, 136, 219, 37);
        panel.add(txtStart);

        JLabel lblEndTime = new JLabel("End_Time");
        lblEndTime.setHorizontalAlignment(0);
        lblEndTime.setFont(new Font("나눔고딕", 0, 25));
        lblEndTime.setBounds(12, 183, 163, 37);
        panel.add(lblEndTime);

        JTextField txtEnd = new JTextField();
        txtEnd.setColumns(10);
        txtEnd.setBounds(204, 183, 219, 37);
        panel.add(txtEnd);

        // 관심사 입력
        JLabel lblInterest = new JLabel("Interest");
        lblInterest.setFont(new Font("나눔고딕", 0, 25));
        lblInterest.setHorizontalAlignment(0);
        lblInterest.setBounds(28, 252, 135, 37);
        panel.add(lblInterest);

        // 버튼 그룹 객체 생성
        ButtonGroup g = new ButtonGroup();
        JRadioButton interestRadio[] = new JRadioButton[3];
        String radio_name[] = {"workout","job","programming"};

        interestRadio[0] = new JRadioButton(radio_name[0]);
        interestRadio[0].setFont(new Font("나눔고딕", Font.PLAIN, 15));
        interestRadio[0].setBounds(209, 234, 119, 23);
        panel.add(interestRadio[0]);

        interestRadio[1] = new JRadioButton(radio_name[1]);
        interestRadio[1].setFont(new Font("나눔고딕", Font.PLAIN, 15));
        interestRadio[1].setBounds(209, 264, 119, 23);
        panel.add(interestRadio[1]);

        interestRadio[2] = new JRadioButton(radio_name[2]);
        interestRadio[2].setFont(new Font("나눔고딕", Font.PLAIN, 15));
        interestRadio[2].setBounds(209, 293, 119, 23);
        panel.add(interestRadio[2]);

        g.add(interestRadio[0]);
        g.add(interestRadio[1]);
        g.add(interestRadio[2]);


        JButton btnSave = new JButton("Save");
        btnSave.setFont(new Font("나눔고딕", 0, 25));
        btnSave.setBounds(643, 615, 136, 31);
        Add_panel.add(btnSave);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String startTimeStr = txtStart.getText();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
                LocalDateTime startTime = LocalDateTime.parse(startTimeStr, dateTimeFormatter);
                System.out.println(startTime);

                String endTimeStr = txtEnd.getText();
                LocalDateTime endTime = LocalDateTime.parse(endTimeStr, dateTimeFormatter);
                System.out.println(endTime);

                Boolean isSelected=false;
                int i=1;
                for(;i<=interestRadio.length;i++){
                    if(interestRadio[i-1].isSelected()){  //true인 경우
                        isSelected = true;
                        break;
                    }
                }

                Long interest_id = null;
                if(isSelected){
                    interest_id = Long.valueOf(i+"");
                }

                if (txtPlan.getText().equals("")) {
                    JOptionPane.showMessageDialog((Component)null, "계획을 입력해주세요.", "Message", 0);
                } else if (txtStart.getText().equals("")) {
                    JOptionPane.showMessageDialog((Component)null, "시작시간을 입력해주세요.", "Message", 0);
                } else if (txtEnd.getText().equals("")) {
                    JOptionPane.showMessageDialog((Component)null, "종료시간을 입력해주세요.", "Message", 0);
                } else if (isSelected == false ) {
                    JOptionPane.showMessageDialog((Component)null, "관심사를 선택해주세요.", "Message", 0);
                } else {
                    addTable(dtm, txtPlan.getText(), startTime, endTime, interest_id );
                    txtPlan.setText("");
                    txtStart.setText("");
                    txtEnd.setText("");
                    interestRadio[i-1].setSelected(false);
                }
                DayPanel.setVisible(true);
                Add_panel.setVisible(false);
            }
        });
        JButton planPageRollBackBtn = new JButton(new ImageIcon("C:\\1M1S-client\\src\\SchedulePage\\rollback.png"));
        planPageRollBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Add_panel.setVisible(false);
                DayPanel.setVisible(true);
                //null로 처리
                txtPlan.setText("");
                txtStart.setText("");
                txtEnd.setText("");
                for(int i=0;i< interestRadio.length;i++)
                    interestRadio[i].setSelected(false);
            }
        });
        planPageRollBackBtn.setBounds(0, 0, 82, 82);
        Add_panel.add(planPageRollBackBtn);

 /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //일정 수정 패널

        JLabel modifyPanelTitle = new JLabel("Plan");
        modifyPanelTitle.setFont(new Font("나눔고딕", 1, 40));
        modifyPanelTitle.setHorizontalAlignment(0);
        modifyPanelTitle.setBounds(339, 148, 452, 67);
        Modify_panel.add(modifyPanelTitle);

        JPanel panel2 = new JPanel();
        panel2.setBounds(325, 225, 466, 418);
        Modify_panel.add(panel2);
        panel2.setLayout((LayoutManager)null);

        JLabel lblPlan2 = new JLabel("Plan");
        lblPlan2.setFont(new Font("나눔고딕", 0, 25));
        lblPlan2.setHorizontalAlignment(0);
        lblPlan2.setBounds(12, 50, 83, 48);
        panel2.add(lblPlan2);

        JTextArea txtPlan2 = new JTextArea(3, 10);
        txtPlan2.setBounds(95, 34, 328, 77);
        panel2.add(txtPlan2);
        txtPlan2.setColumns(10);
        txtPlan2.requestFocus();
        txtPlan2.setFocusable(true);
        txtPlan2.setFont(new Font("나눔고딕", 0, 15));

        JLabel lblStartTime2 = new JLabel("Start_Time");
        lblStartTime2.setFont(new Font("나눔고딕", 0, 25));
        lblStartTime2.setHorizontalAlignment(0);lblStartTime2.setBounds(12, 136, 163, 37);
        panel2.add(lblStartTime2);

        JTextField txtStart2 = new JTextField();txtStart2.setText("ex) 2021-11-25 01:30:00");
        txtStart2.setEnabled(true);
        txtStart2.setColumns(10);
        txtStart2.setBounds(204, 136, 219, 37);
        panel2.add(txtStart2);

        JLabel lblEndTime2 = new JLabel("End_Time");
        lblEndTime2.setHorizontalAlignment(0);
        lblEndTime2.setFont(new Font("나눔고딕", 0, 25));
        lblEndTime2.setBounds(12, 183, 163, 37);
        panel2.add(lblEndTime2);

        JTextField txtEnd2 = new JTextField();
        txtEnd2.setColumns(10);
        txtEnd2.setBounds(204, 183, 219, 37);
        panel2.add(txtEnd2);

        // 관심사 입력
        JLabel lblInterest2 = new JLabel("Interest");
        lblInterest2.setFont(new Font("나눔고딕", 0, 25));
        lblInterest2.setHorizontalAlignment(0);
        lblInterest2.setBounds(28, 266, 135, 37);
        panel2.add(lblInterest2);

        // 버튼 그룹 객체 생성
        ButtonGroup g2 = new ButtonGroup();
        JRadioButton interestRadio2[] = new JRadioButton[3];
        String radio_name2[] = {"workout","job","programming"};

        interestRadio2[0] = new JRadioButton(radio_name2[0]);
        interestRadio2[0].setFont(new Font("나눔고딕", Font.PLAIN, 15));
        interestRadio2[0].setBounds(209, 252, 119, 23);
        panel2.add(interestRadio2[0]);

        interestRadio2[1] = new JRadioButton(radio_name2[1]);
        interestRadio2[1].setFont(new Font("나눔고딕", Font.PLAIN, 15));
        interestRadio2[1].setBounds(209, 280, 119, 23);
        panel2.add(interestRadio2[1]);

        interestRadio2[2] = new JRadioButton(radio_name2[2]);
        interestRadio2[2].setFont(new Font("나눔고딕", Font.PLAIN, 15));
        interestRadio2[2].setBounds(209, 310, 119, 23);
        panel2.add(interestRadio2[2]);

        g2.add(interestRadio2[0]);
        g2.add(interestRadio2[1]);
        g2.add(interestRadio2[2]);


        JLabel lblFinish = new JLabel("Finish");
        lblFinish.setHorizontalAlignment(SwingConstants.CENTER);
        lblFinish.setFont(new Font("나눔고딕", Font.PLAIN, 25));
        lblFinish.setBounds(28, 346, 135, 37);
        panel2.add(lblFinish);

        ButtonGroup g3 = new ButtonGroup();
        JRadioButton rdbtnYes = new JRadioButton("Yes");
        rdbtnYes.setFont(new Font("나눔고딕", Font.PLAIN, 15));
        rdbtnYes.setBounds(209, 356, 72, 25);
        panel2.add(rdbtnYes);

        JRadioButton rdbtnNo = new JRadioButton("No");
        rdbtnNo.setFont(new Font("나눔고딕", Font.PLAIN, 15));
        rdbtnNo.setBounds(295, 356, 72, 25);
        panel2.add(rdbtnNo);

        g3.add(rdbtnYes);
        g3.add(rdbtnNo);

        JButton btnSave2 = new JButton("Save");
        btnSave2.setFont(new Font("나눔고딕", 0, 25));
        btnSave2.setBounds(651, 653, 136, 31);
        Modify_panel.add(btnSave2);
        btnSave2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String startTimeStr2 = txtStart2.getText();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
                LocalDateTime startTime2 = LocalDateTime.parse(startTimeStr2, dateTimeFormatter);
                System.out.println(startTime2);

                String endTimeStr2 = txtEnd2.getText();
                LocalDateTime endTime2 = LocalDateTime.parse(endTimeStr2, dateTimeFormatter);
                System.out.println(endTime2);

                Boolean isSelected2=false;
                int i=1;
                for(;i<=interestRadio2.length;i++){
                    if(interestRadio2[i-1].isSelected()){  //true인 경우
                        isSelected2 = true;
                        break;
                    }
                }

                Long interest_id2 = null;
                if(isSelected2){
                    interest_id2 = Long.valueOf(i+"");
                }


                Boolean isFinishSelected = false;
                Boolean isFinish = false;
                if(rdbtnYes.isSelected()){
                    isFinishSelected=true;
                    isFinish = true;
                } else if(rdbtnNo.isSelected()){
                    isFinishSelected=true;
                }

                if (txtPlan2.getText().equals("")) {
                    JOptionPane.showMessageDialog((Component)null, "계획을 입력해주세요.", "Message", 0);
                } else if (txtStart2.getText().equals("")) {
                    JOptionPane.showMessageDialog((Component)null, "시작시간을 입력해주세요.", "Message", 0);
                } else if (txtEnd2.getText().equals("")) {
                    JOptionPane.showMessageDialog((Component)null, "종료시간을 입력해주세요.", "Message", 0);
                } else if (isSelected2 == false ) {
                    JOptionPane.showMessageDialog((Component)null, "관심사를 선택해주세요.", "Message", 0);
                } else if (isFinishSelected == false ) {
                    JOptionPane.showMessageDialog((Component)null, "완료여부를 선택해주세요.", "Message", 0);
                } else {
                    modifyTable(dtm, txtPlan2.getText(), startTime2, endTime2, isFinish, interest_id2);  //user_id, member_schedule_id,
                    txtPlan2.setText("");
                    txtStart2.setText("");
                    txtEnd2.setText("");
                    rdbtnYes.setSelected(false);
                    rdbtnNo.setSelected(false);
                    interestRadio2[i-1].setSelected(false);
                }
                DayPanel.setVisible(true);
                Modify_panel.setVisible(false);
            }
        });
        JButton planPageRollBackBtn2 = new JButton(new ImageIcon("C:\\1M1S-client\\src\\SchedulePage\\rollback.png"));
        planPageRollBackBtn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Modify_panel.setVisible(false);
                DayPanel.setVisible(true);
                //null로 처리
                txtPlan2.setText("");
                txtStart2.setText("");
                txtEnd2.setText("");
                for(int i=0;i< interestRadio2.length;i++)
                    interestRadio2[i].setSelected(false);
                rdbtnYes.setSelected(false);
                rdbtnNo.setSelected(false);
            }
        });
        planPageRollBackBtn2.setBounds(0, 0, 82, 82);
        Modify_panel.add(planPageRollBackBtn2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //일정 추가 기능
    void addTable(DefaultTableModel dtm, String Content, LocalDateTime startTime, LocalDateTime endTime, Long interest_id) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            ObjectMapper objectMapper = new ObjectMapper();

            //user id DB에서 받아오기
            Long user_id = Long.valueOf("1");

            //request 보내기
            String uri = "http://localhost:8080";
            uri = uri + "/api/user/" + Long.toString(1L) + "/schedule";

            //request body 생성
            MemberSchedule addpost = new MemberSchedule(Content, startTime, endTime, null , interest_id); ////새로 생성 DB Post 클래스

            //request
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String requestBody = objectMapper.writeValueAsString(addpost);
            System.out.println("add requestBody : " + requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json; charset=UTF-8")  // content type, 인코딩형식 지정.
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))  // HTTP 메소드, body 지정(위에서 만든 JSON 전달)
                    .build();

            //response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("add response : " + response);
            System.out.println("add response body : " + response.body());

            //update
            getTodoList(dtm);

        } catch (Exception ex) {
            System.out.println("오류 발생");
            ex.printStackTrace();
        }
    }

    //일정 수정 기능
    void modifyTable(DefaultTableModel dtm, String Content, LocalDateTime startTime, LocalDateTime endTime, Boolean finish, Long interest_id) {
        int editRow = todoTable.getSelectedRow();
        if (editRow >= 0) {
            Long editId = (Long)todoTable.getModel().getValueAt(editRow, 0);  //Long user_id, Long member_schedule_id,

            String uri = "http://localhost:8080";
            uri = uri + "/api/user/" + Long.toString(1L) + "/schedule/" + Long.toString(editId);

            try {
                MemberSchedule modifypost = new MemberSchedule(Content, startTime, endTime, finish , interest_id);
                HttpClient client = HttpClient.newHttpClient();

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                String requestBody = objectMapper.writeValueAsString(modifypost);
                System.out.println("modify requestBody : " + requestBody);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

                //response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("modify response : " + response);
                System.out.println("modify response body : " + response.body());

            } catch (JsonProcessingException var17) {
                System.out.println("서버 요청 오류");
                var17.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //update
            getTodoList(dtm);
            JOptionPane.showMessageDialog((Component)null, "Edited");
        } else {
            JOptionPane.showMessageDialog((Component)null, "Unable To Edit");
        }
    }

    String getDayByDate(String choosePanelDate) {
        LocalDateTime today = LocalDateTime.now();
        // 월~일 : 1~7
        HashMap<String, Integer> m = new HashMap<>();
        m.put("월", 1);  m.put("화", 2); m.put("수", 3);
        m.put("목", 4); m.put("금", 5); m.put("토", 6); m.put("일", 7);
        // 오늘 요일
        System.out.println(today.getDayOfWeek().getValue());
        // 해당 요일과 오늘과 날짜 차이
        System.out.println(m.get(choosePanelDate) - today.getDayOfWeek().getValue());
        // 해당 요일의 날짜
        System.out.println(today.plusDays(m.get(choosePanelDate) - today.getDayOfWeek().getValue()));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
        return today.plusDays(m.get(choosePanelDate) - today.getDayOfWeek().getValue()).format(dateTimeFormatter);
    }

    // 일정 읽어오는 기능(새로고침)
    void getTodoList(DefaultTableModel dtm) {
        dtm.setRowCount(0);
        try {
            HttpClient client = HttpClient.newHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            //request보내기
            String uri = "http://localhost:8080";
            uri = uri + "/api/user/" + Long.toString(1L) + "/schedule";
            uri += "?search_time=" + getDayByDate(choosePanelDate);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();
            System.out.println("getTodoList request : " + request);

            // 위에서 생성한 request를 보내고, 받은 response를 저장
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println("getTodoList response : " + response);
            System.out.println("getTodoList body : " + response.body());

            // responseBody to Post Class Array
            Iterable<MemberSchedule> posts = mapper.readValue(response.body(), new TypeReference<Iterable<MemberSchedule>>(){});

            // Jtable에 일정 추가
            for(MemberSchedule p : posts) {
                dtm.addRow(new Object[] {
                        p.getId(), p.getContent(), p.getStartTime(), p.getEndTime(), p.getFinish()});
                System.out.println(p);
            }
        } catch (Exception e) {
            System.out.println("오류 발생");
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    class mainSchedulePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Dimension d = this.getSize();
            ImageIcon image = new ImageIcon("C:\\1M1S-client\\src\\SchedulePage\\schImg.png");
            g.drawImage(image.getImage(), 0, 0, d.width, d.height, (ImageObserver)null);
        }
    }

    class dailyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Dimension d = this.getSize();
            ImageIcon image = new ImageIcon("C:\\1M1S-client\\src\\SchedulePage\\dailyImg.png");
            g.drawImage(image.getImage(), 0, 0, d.width, d.height, (ImageObserver)null);
        }
    }

    class writePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Dimension d = this.getSize();
            ImageIcon image = new ImageIcon("C:\\1M1S-client\\src\\SchedulePage\\writeImg.png");
            g.drawImage(image.getImage(), 0, 0, d.width, d.height, (ImageObserver)null);
        }
    }
}