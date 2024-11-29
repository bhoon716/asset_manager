package org.example.Boundary;

import org.example.Control.MainControl;

import javax.swing.*;
import java.awt.*;

public class LoginBoundary extends JFrame {

    private final MainControl mainControl;

    private final JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    private final JLabel idLabel = new JLabel("ID: ");
    private final JTextField idField = new JTextField(15);
    private final JLabel pwLabel = new JLabel("PW: ");
    private final JPasswordField pwField = new JPasswordField(15);
    private final JPanel buttonPanel = new JPanel(new FlowLayout());
    private final JButton loginButton = new JButton("로그인");
    private final JButton registerButton = new JButton("회원가입");

    public LoginBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        initUI();

    }

    private void initUI() {
        setTitle("로그인");
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
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void login() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword()).trim();

        if (id.isEmpty() || pw.isEmpty()) {
            showMessage("아이디와 비밀번호를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!mainControl.login(id, pw)) {
            showMessage("아이디 또는 비밀번호가 올바르지 않습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            return;
        }

        showMessage("로그인 성공!", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        mainControl.showMainBoundary();
    }

    private void register() {
        dispose();
        mainControl.showRegisterBoundary();
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
