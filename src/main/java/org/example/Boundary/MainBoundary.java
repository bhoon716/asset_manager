package org.example.Boundary;

import org.example.Control.MainControl;
import org.example.Entity.News;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

public class MainBoundary extends JFrame {

    private final JPanel newsPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // 뉴스 버튼 레이아웃
    private final JLabel newsLabel = new JLabel("오늘의 경제 이슈 - " + LocalDate.now(), SwingConstants.CENTER); // 가운데 정렬
    private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 버튼 패널
    private final JButton assetButton = new JButton("자산 검색");
    private final JButton portfolioButton = new JButton("포트폴리오 관리");
    private final JButton logoutButton = new JButton("로그아웃");

    private final MainControl mainControl;

    public MainBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        initUI();
        setVisible(true);
        setUpNewsPanel();
        setUpButtonPanel();
        loadNewsList();
    }

    private void initUI() {
        setTitle(mainControl.getUser().getUsername() + "'s  포트폴리오 매니저");
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10)); // 간격 추가
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpNewsPanel() {
        // 뉴스 제목 스타일링
        newsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(newsLabel, BorderLayout.NORTH);

        // 뉴스 패널 스타일링
        newsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(newsPanel, BorderLayout.CENTER); // 스크롤 추가
    }

    // 뉴스 데이터를 가져오고 화면에 표시
    private void loadNewsList() {
        List<News> newsList = mainControl.fetchNewsHeadlines(0, 5);
        for (News news : newsList) {
            JButton newsButton = new JButton(news.getTitle());
            newsButton.setFocusPainted(false);
            newsButton.setHorizontalAlignment(SwingConstants.LEFT); // 텍스트 왼쪽 정렬
            newsButton.addActionListener(e -> openURLInBrowser(news.getLink()));
            newsPanel.add(newsButton);
        }
    }

    // 버튼 패널 설정
    private void setUpButtonPanel() {
        assetButton.addActionListener(e -> showAssetInfoBoundary());
        portfolioButton.addActionListener(e -> showPortfolioListBoundary());
        logoutButton.addActionListener(e -> logout());

        // 버튼 스타일링
        for (JButton button : new JButton[]{assetButton, portfolioButton, logoutButton}) {
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setPreferredSize(new Dimension(150, 40));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showAssetInfoBoundary(){
        mainControl.showAssetInfoBoundary();
    }

    private void showPortfolioListBoundary(){
        mainControl.showPortfolioListBoundary();
    }

    private void logout(){
        dispose();
        mainControl.logout();
    }

    // URL 을 기본 브라우저에서 열기
    private void openURLInBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "URL 을 열 수 없습니다: " + url, "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
}
