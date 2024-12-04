package org.example.Boundary;

import org.example.Control.MainControl;

import javax.swing.*;
import java.awt.*;

public class RegisterBoundary extends JFrame {

    private final MainControl mainControl;

    private final JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    private final JLabel idLabel = new JLabel("ID: ");
    private final JTextField idField = new JTextField(15);
    private final JLabel pwLabel = new JLabel("PW: ");
    private final JPasswordField pwField = new JPasswordField(15);
    private final JPanel buttonPanel = new JPanel(new FlowLayout());
    private final JButton registerButton = new JButton("회원가입");
    private final JButton cancelButton = new JButton("Cancel");

    public RegisterBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        initUI();
    }

    private void initUI() {
        setTitle("회원가입");
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        setUpInputPanel();
        setUpButtonPanel();

        setVisible(true);
    }

    private void setUpInputPanel() {
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(pwLabel);
        inputPanel.add(pwField);

        add(inputPanel, BorderLayout.CENTER);
    }

    private void setUpButtonPanel() {
        registerButton.addActionListener(e -> registerUser());
        cancelButton.addActionListener(e -> cancel());

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void registerUser() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword()).trim();

        if (id.isEmpty() || pw.isEmpty()) {
            showError("ID와 PW를 입력해주세요.");
            return;
        }

        if (mainControl.checkDuplicatedUserId(id)) {
            showError("이미 존재하는 사용자 ID 입니다: " + id);
            return;
        }

        mainControl.addUser(id, pw);
        showMessage("회원가입 성공!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        mainControl.showLoginBoundary(); // 회원가입 후 로그인 창으로 이동
    }

    private void cancel() {
        dispose(); // 현재 창 닫기
        mainControl.showLoginBoundary(); // 로그인 창 표시
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
    }
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
