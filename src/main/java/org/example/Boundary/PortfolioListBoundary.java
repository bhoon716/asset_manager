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

    public PortfolioListBoundary(MainControl mainControl){
        this.mainControl = mainControl;

        updatePortfolioList();
        initUI();
    }

    private void initUI(){
        setTitle("포트폴리오 리스트");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        addPortfolioButton.addActionListener(e -> addPortfolio());
        viewPortfolioButton.addActionListener(e -> viewPortfolio());
        editPortfolioNameButton.addActionListener(e -> editPortfolioName());
        deletePortfolioButton.addActionListener(e -> deletePortfolio());

        buttonPanel.add(addPortfolioButton);
        buttonPanel.add(viewPortfolioButton);
        buttonPanel.add(editPortfolioNameButton);
        buttonPanel.add(deletePortfolioButton);

        add(portfolioListLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(portfolioList), BorderLayout.CENTER);

        setVisible(true);
    }

    // 포트폴리오 목록 업데이트
    private void updatePortfolioList() {
        DefaultListModel<Portfolio> listModel = new DefaultListModel<>();
        for (Portfolio portfolio : mainControl.getPortfolioList()) {
            listModel.addElement(portfolio);
        }
        portfolioList.setModel(listModel);
    }

    private void addPortfolio(){
        String name = JOptionPane.showInputDialog(this, "포트폴리오 이름 입력:").trim();
        if(name.isEmpty()){
            throw new IllegalArgumentException("올바르지 않은 이름");
        }
        if(mainControl.checkDuplicatedPortfolioName(name)){
            throw new IllegalArgumentException("중복된 이름");
        }
        mainControl.addPortfolio(name);
        updatePortfolioList();
    }

    private void viewPortfolio(){
        Portfolio portfolio = portfolioList.getSelectedValue();
        if(portfolio == null){
            throw new IllegalArgumentException("포트폴리오를 선택해주세요");
        }
        mainControl.showPortfolioBoundary(portfolio);
    }

    private void editPortfolioName() {
        Portfolio portfolio = portfolioList.getSelectedValue();
        if (portfolio == null) {
            JOptionPane.showMessageDialog(this, "수정할 포트폴리오를 선택해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newName = JOptionPane.showInputDialog(this, "새로운 포트폴리오 이름을 입력하세요:", portfolio.getName()).trim();
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "올바르지 않은 이름입니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (mainControl.checkDuplicatedPortfolioName(newName)) {
            JOptionPane.showMessageDialog(this, "중복된 이름입니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        portfolio.setName(newName); // 포트폴리오 이름 변경
        updatePortfolioList(); // UI 갱신
        JOptionPane.showMessageDialog(this, "포트폴리오 이름이 수정되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deletePortfolio(){
        Portfolio portfolio = portfolioList.getSelectedValue();
        if(portfolio == null){
            throw new IllegalArgumentException("삭제할 포트폴리오를 선택해주세요");
        }
        int confirm = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?", "포트폴리오 삭제", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            mainControl.deletePortfolio(portfolio);
            updatePortfolioList();
        }
    }
}
