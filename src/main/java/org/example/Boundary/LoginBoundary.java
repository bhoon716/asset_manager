package org.example.Boundary;


import org.example.Control.MainControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class LoginBoundary extends JFrame {
    private final MainControl mainControl;
    private final JTextField idField = new JTextField(10);
    private final JPasswordField pwField = new JPasswordField(10);

    private final JPanel idPanel = new JPanel(new FlowLayout());//로그인
    private final JLabel idLabel = new JLabel("ID: ");
    private final JPanel pwPanel = new JPanel(new FlowLayout());
    private final JLabel pwLabel = new JLabel("PW: ");
    private final JPanel buttonPanel = new JPanel(new FlowLayout());
    private final JButton loginButton = new JButton("Login");
    private final JButton registerButton = new JButton("Register");

    public LoginBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        initUI();
    }

    public void initUI() {
        setTitle("Login");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        idPanel.add(idLabel);
        idPanel.add(idField);

        pwPanel.add(pwLabel);
        pwPanel.add(pwField);


        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        pwField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Enter 키 누르면 로그인 버튼을 누른 것과 같은 동작 수행
                    loginButton.doClick();
                }
            }
        });

        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String pw = String.valueOf(pwField.getPassword());

            try {
                if (mainControl.login(id, pw)) {
                    JOptionPane.showMessageDialog(this, "로그인 성공!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    mainControl.setPortfolioList();
                    mainControl.showMainBoundary(mainControl.getUser()); // 로그인 성공 후 메인 화면으로 이동
                    dispose(); // 현재 창 닫기
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
                dispose(); // 현재 창 닫기
                mainControl.showRegisterBoundary();
        });

        add(idPanel, BorderLayout.NORTH);
        add(pwPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
