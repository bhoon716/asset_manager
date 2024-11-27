package org.example.Boundary;

import org.example.Control.MainControl;
import org.example.Entity.Asset;
import org.example.Entity.AssetType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class PortfolioBoundary extends JFrame {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00"); // 소수점 두 자리로 포맷

    private final JTable assetTable = new JTable();
    private final JPanel buttonPanel = new JPanel();
    private final JButton addAssetButton = new JButton("자산 추가");
    private final JButton editAssetButton = new JButton("자산 수정");
    private final JButton deleteAssetButton = new JButton("자산 삭제");
    private final JLabel portfolioInfoLabel = new JLabel();

    private final JPanel assetPanel = new JPanel(new GridLayout(0, 2));
    private final JLabel typeLabel = new JLabel("타입:");
    private final JComboBox<AssetType> assetTypeComboBox = new JComboBox<>(AssetType.values());
    private final JLabel symbolLabel = new JLabel("종목 코드:");
    private final JTextField symbolField = new JTextField();
    private final JLabel purchasePriceLabel = new JLabel("구매단가:");
    private final JTextField purchasePriceField = new JTextField();
    private final JLabel quantityLabel = new JLabel("수량:");
    private final JTextField quantityField = new JTextField();

    private final MainControl mainControl;

    public PortfolioBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        initUI();
    }

    public void initUI() {
        setTitle("포트폴리오");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        addAssetButton.addActionListener(e -> addAsset());
        editAssetButton.addActionListener(e -> editAsset());
        deleteAssetButton.addActionListener(e -> deleteAsset());

        buttonPanel.add(addAssetButton);
        buttonPanel.add(editAssetButton);
        buttonPanel.add(deleteAssetButton);

        add(portfolioInfoLabel, BorderLayout.NORTH);
        add(new JScrollPane(assetTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        assetPanel.add(typeLabel);
        assetPanel.add(assetTypeComboBox);
        assetPanel.add(symbolLabel);
        assetPanel.add(symbolField);
        assetPanel.add(purchasePriceLabel);
        assetPanel.add(purchasePriceField);
        assetPanel.add(quantityLabel);
        assetPanel.add(quantityField);

        updatePortfolioInfoLabel();
        updateAssetTable();

        setVisible(true);
    }

    private void clearAssetForm() {
        symbolField.setText("");
        purchasePriceField.setText("");
        quantityField.setText("");
    }

    private void addAsset() {
        int response = JOptionPane.showConfirmDialog(this, assetPanel, "자산 추가", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (response != JOptionPane.YES_OPTION) {
            return;
        }

        AssetType assetType = (AssetType) assetTypeComboBox.getSelectedItem();
        String symbol = symbolField.getText();
        Asset asset = mainControl.getDuplicatedAsset(assetType, symbol);
        Double purchasePrice;
        Double quantity;
        try {
            purchasePrice = Double.parseDouble(purchasePriceField.getText());
            quantity = Double.parseDouble(quantityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "숫자를 정확히 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (asset == null) {
            mainControl.addAsset(assetType, symbol, purchasePrice, quantity);
            updatePortfolioInfoLabel();
            updateAssetTable();
            clearAssetForm();
            return;
        }
        Double totalPurchasePrice = asset.getTotalPurchasePrice() + (purchasePrice * quantity);
        Double totalQuantity = asset.getQuantity() + quantity;
        Double newPurchasePrice = totalPurchasePrice / totalQuantity;
        asset.setPurchasePrice(newPurchasePrice);
        asset.setQuantity(totalQuantity);

        updatePortfolioInfoLabel();
        updateAssetTable();
        clearAssetForm();
    }

    private void editAsset() {
        int selectedRow = assetTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "수정할 자산을 선택해주세요.", "수정 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int response = JOptionPane.showConfirmDialog(this, assetPanel, "자산 수정", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (response != JOptionPane.YES_OPTION) {
            return;
        }

        AssetType assetType = (AssetType) assetTypeComboBox.getSelectedItem();
        String symbol = symbolField.getText();
        double purchasePrice;
        double quantity;
        try {
            purchasePrice = Double.parseDouble(purchasePriceField.getText());
            quantity = Double.parseDouble(quantityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "숫자를 정확히 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Asset asset = mainControl.getAssetByIndex(selectedRow);
        asset.setAssetType(assetType);
        asset.setSymbol(symbol);
        asset.setPurchasePrice(purchasePrice);
        asset.setQuantity(quantity);

        updatePortfolioInfoLabel();
        updateAssetTable();
    }

    private void deleteAsset() {
        int selectedRow = assetTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "삭제할 자산을 선택해주세요.", "삭제 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mainControl.deleteAsset(selectedRow);
        updatePortfolioInfoLabel();
        updateAssetTable();
    }

    private void updatePortfolioInfoLabel() {
        Double totalValue = mainControl.getCurrentPortfolio().getTotalEvaluationPrice();
        portfolioInfoLabel.setText("Total Value: " + DECIMAL_FORMAT.format(totalValue));
    }

    private void updateAssetTable() {
        String[] columnNames = {"자산종류", "종목코드", "평가금액", "총 구매금액", "평가손익", "손익률", "현재가", "구매단가", "보유수량"};
        List<Object[]> rawData = mainControl.getPortfolioDataList();

        // 데이터 포맷팅
        Object[][] formattedData = rawData.stream()
                .map(row -> new Object[]{
                        row[0], // 자산종류 (STOCK, CRYPTO 등)
                        row[1], // 종목코드 (symbol)
                        formatDouble(row[2]), // 평가금액
                        formatDouble(row[3]), // 총 구매금액
                        formatDouble(row[4]), // 평가손익
                        formatDouble(row[5]), // 손익률
                        formatDouble(row[6]), // 현재가
                        formatDouble(row[7]), // 구매단가
                        formatDouble(row[8])  // 보유수량
                })
                .toArray(Object[][]::new);

        DefaultTableModel model = new DefaultTableModel(formattedData, columnNames);
        assetTable.setModel(model);
    }

    private String formatDouble(Object value) {
        if (value instanceof Double) {
            return DECIMAL_FORMAT.format((Double) value);
        }
        return value != null ? value.toString() : ""; // 값이 null이면 빈 문자열 반환
    }
}
