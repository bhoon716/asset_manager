package org.example.Boundary;

import org.example.Control.MainControl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

public class PortfolioBoundary extends JFrame {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

    private final JPanel summaryPanel = new JPanel(new GridLayout(3, 1));
    private final JLabel totalEvaluationLabel = new JLabel("총 평가금액: ");
    private final JLabel totalProfitLossLabel = new JLabel("총 평가손익: ");
    private final JLabel totalProfitLossRateLabel = new JLabel("총 손익률: ");
    private final JTable assetTable = new JTable();
    private final JPanel buttonPanel = new JPanel();
    private final JButton addAssetButton = new JButton("자산 추가");
    private final JButton editAssetButton = new JButton("자산 수정");
    private final JButton deleteAssetButton = new JButton("자산 삭제");

    private final MainControl mainControl;

    public PortfolioBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        initUI();
        displaySummaryPanel();
        displayAssetTable();
        displayButtonPanel();
        updatePortfolioSummary();
        updateAssetTable();
        setVisible(true);
    }

    private void initUI() {
        setTitle("포트폴리오");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void displaySummaryPanel() {
        summaryPanel.add(totalEvaluationLabel);
        summaryPanel.add(totalProfitLossLabel);
        summaryPanel.add(totalProfitLossRateLabel);
        add(summaryPanel, BorderLayout.NORTH);
    }

    private void displayAssetTable() {
        add(new JScrollPane(assetTable), BorderLayout.CENTER);
    }

    private void displayButtonPanel() {
        addAssetButton.addActionListener(e -> addAsset());
        editAssetButton.addActionListener(e -> editAsset());
        deleteAssetButton.addActionListener(e -> deleteAsset());

        buttonPanel.add(addAssetButton);
        buttonPanel.add(editAssetButton);
        buttonPanel.add(deleteAssetButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updatePortfolioSummary() {
        double totalEvaluation = mainControl.getCurrentPortfolioEvaluationPrice();
        double totalProfitLoss = mainControl.getCurrentPortfolioProfitLoss();
        double totalProfitLossRate = mainControl.getCurrentPortfolioProfitLossRate();

        totalEvaluationLabel.setText("총 평가금액: " + DECIMAL_FORMAT.format(totalEvaluation));
        totalProfitLossLabel.setText("총 평가손익: " + DECIMAL_FORMAT.format(totalProfitLoss));
        totalProfitLossRateLabel.setText("총 손익률: " + DECIMAL_FORMAT.format(totalProfitLossRate) + "%");
    }

    private void updateAssetTable() {
        String[] columnNames = {"자산 종류", "종목 코드", "구매 가격", "현재 가격", "평가 금액", "손익", "손익률", "보유 수량"};
        Object[][] data = mainControl.getAssetTableData();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        assetTable.setModel(model);
    }

    private void addAsset() {
        mainControl.showAddAssetDialog();
        updatePortfolioSummary();
        updateAssetTable();
    }

    private void editAsset() {
        int selectedRow = assetTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "수정할 자산을 선택하세요.", "오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mainControl.showEditAssetDialog(selectedRow);
        updatePortfolioSummary();
        updateAssetTable();
    }

    private void deleteAsset() {
        int selectedRow = assetTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "삭제할 자산을 선택하세요.", "오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mainControl.deleteAsset(selectedRow);
        updatePortfolioSummary();
        updateAssetTable();
    }
}
