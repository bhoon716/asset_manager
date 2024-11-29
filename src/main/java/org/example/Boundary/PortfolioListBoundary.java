package org.example.Boundary;

import org.example.Control.MainControl;
import org.example.Entity.Portfolio;

import javax.swing.*;
import java.awt.*;

public class PortfolioListBoundary extends JFrame {

    private final MainControl mainControl;

    private final JLabel portfolioListLabel = new JLabel("포트폴리오 목록");
    private final JPanel buttonPanel = new JPanel();
    private final JButton addPortfolioButton = new JButton("포트폴리오 추가");
    private final JButton viewPortfolioButton = new JButton("포트폴리오 보기");
    private final JButton editPortfolioNameButton = new JButton("포트폴리오 이름 수정");
    private final JButton deletePortfolioButton = new JButton("포트폴리오 삭제");
    private final JList<Portfolio> portfolioList = new JList<>();

    public PortfolioListBoundary(MainControl mainControl) {
        this.mainControl = mainControl;
        initUI();
        updatePortfolioList();
    }

    private void initUI() {
        setTitle("포트폴리오 리스트");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Button Panel 설정
        setUpButtonPanel();

        // Layout 설정
        portfolioListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(portfolioListLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(portfolioList), BorderLayout.CENTER);

        setVisible(true);
    }

    private void setUpButtonPanel() {
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addPortfolioButton);
        buttonPanel.add(viewPortfolioButton);
        buttonPanel.add(editPortfolioNameButton);
        buttonPanel.add(deletePortfolioButton);

        // 버튼 액션 리스너 설정
        addPortfolioButton.addActionListener(e -> handleAddPortfolio());
        viewPortfolioButton.addActionListener(e -> handleViewPortfolio());
        editPortfolioNameButton.addActionListener(e -> handleEditPortfolioName());
        deletePortfolioButton.addActionListener(e -> handleDeletePortfolio());
    }

    // 포트폴리오 목록 업데이트
    private void updatePortfolioList() {
        DefaultListModel<Portfolio> listModel = new DefaultListModel<>();
        for (Portfolio portfolio : mainControl.getPortfolioList()) {
            listModel.addElement(portfolio);
        }
        portfolioList.setModel(listModel);
    }

    private void handleAddPortfolio() {
        String name = JOptionPane.showInputDialog(this, "포트폴리오 이름 입력:");
        if (name == null || name.trim().isEmpty()) {
            showError("포트폴리오 이름을 입력하세요.");
            return;
        }
        if (mainControl.checkDuplicatedPortfolioName(name)) {
            showError("중복된 포트폴리오 이름입니다.");
            return;
        }
        mainControl.addPortfolio(name.trim());
        updatePortfolioList();
        showMessage("포트폴리오가 추가되었습니다.");
    }

    private void handleViewPortfolio() {
        Portfolio portfolio = getSelectedPortfolio();
        if (portfolio == null) {
            return;
        }
        mainControl.showPortfolioBoundary(portfolio);
    }

    private void handleEditPortfolioName() {
        Portfolio portfolio = getSelectedPortfolio();
        if (portfolio == null) {
            return;
        }

        String newName = JOptionPane.showInputDialog(this, "새로운 포트폴리오 이름을 입력하세요:", portfolio.getName());
        if (newName == null || newName.trim().isEmpty()) {
            showError("올바르지 않은 이름입니다.");
            return;
        }
        if (mainControl.checkDuplicatedPortfolioName(newName)) {
            showError("중복된 이름입니다.");
            return;
        }

        portfolio.setName(newName.trim());
        updatePortfolioList();
        showMessage("포트폴리오 이름이 수정되었습니다.");
    }

    private void handleDeletePortfolio() {
        Portfolio portfolio = getSelectedPortfolio();
        if (portfolio == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?", "포트폴리오 삭제", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            mainControl.deletePortfolio(portfolio);
            updatePortfolioList();
            showMessage("포트폴리오가 삭제되었습니다.");
        }
    }

    private Portfolio getSelectedPortfolio() {
        Portfolio portfolio = portfolioList.getSelectedValue();
        if (portfolio == null) {
            showError("포트폴리오를 선택하세요.");
        }
        return portfolio;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "알림", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
    }
}
