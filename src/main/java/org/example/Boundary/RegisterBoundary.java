package org.example.Boundary;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.example.Control.MainControl;
import org.example.Entity.User;

import javax.swing.*;
import java.awt.*;

public class RegisterBoundary extends JFrame {
    private final MainControl mainControl;
    private final JTextField idField = new JTextField(10);
    private final JPasswordField pwField = new JPasswordField(10);
    private final JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private final JLabel idLabel = new JLabel("ID: ");
    private final JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private final JLabel pwLabel = new JLabel("PW: ");
    private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private final JButton registerButton = new JButton("Register");
    private final JButton cancelButton = new JButton("Cancel");
    private final JList<User> userList = new JList<>();

    public RegisterBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        initUI();
    }

    public void initUI() {
        setSize(600, 400);
        setTitle("Register");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        idPanel.add(idLabel);
        idPanel.add(idField);

        pwPanel.add(pwLabel);
        pwPanel.add(pwField);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        registerButton.addActionListener(e -> {
            try {
                // ID와 비밀번호를 입력받아 처리
                String id = idField.getText().trim();
                String pw = String.valueOf(pwField.getPassword()).trim();
                addUser(id, pw);
            } catch (IllegalArgumentException ex) {
                // 예외 발생 시 경고 메시지 출력
                JOptionPane.showMessageDialog(this, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            dispose(); // 현재 창 닫기
            mainControl.showLoginBoundary();
        });

        add(idPanel, BorderLayout.NORTH);
        add(pwPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateUserList() {
        DefaultListModel<User> listModel = new DefaultListModel<>();
        for (User user : mainControl.getUserMap().values()) { // Map의 values()로 User 객체 접근
            listModel.addElement(user);
        }
        userList.setModel(listModel);
    }

    private void addUser(String id, String pw) {
        // ID와 PW 유효성 검사 및 사용자 추가 처리
        if (id.isEmpty()) {
            throw new IllegalArgumentException("올바르지 않은 아이디입니다.");
        }
        if (pw.isEmpty()) {
            throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
        }
        if (mainControl.checkDuplicatedUserId(id)) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
        mainControl.addUser(id, pw);
        JOptionPane.showMessageDialog(this, "회원가입 성공!", "Success", JOptionPane.INFORMATION_MESSAGE);
        updateUserList(); // 사용자 목록 갱신
    }
}
