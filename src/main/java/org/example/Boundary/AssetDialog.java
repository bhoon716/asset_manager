package org.example.Boundary;

import org.example.Control.MainControl;
import org.example.Entity.Asset;
import org.example.Entity.AssetType;
import org.example.Entity.Tradable;

import javax.swing.*;
import java.awt.*;

public class AssetDialog extends JDialog {

    private final MainControl mainControl;

    private final JPanel formPanel = new JPanel(new GridLayout(4, 2));
    private final JLabel assetTypeLabel = new JLabel("자산 종류:");
    private final JComboBox<AssetType> assetTypeComboBox = new JComboBox<>(AssetType.values());
    private final JLabel symbolLabel = new JLabel("자산 코드:");
    private final JTextField symbolField = new JTextField();
    private final JLabel purchasePriceLabel = new JLabel("구매 단가:");
    private final JTextField purchasePriceField = new JTextField();
    private final JLabel quantityLabel = new JLabel("수량:");
    private final JTextField quantityField = new JTextField();
    private final JPanel buttonPanel = new JPanel();
    private final JButton confirmButton = new JButton("확인");
    private final JButton cancelButton = new JButton("취소");

    private int selectedRow = -1;      // 수정 모드의 선택된 행 (-1은 추가 모드)

    public AssetDialog(JFrame parent, MainControl mainControl, String title) {
        super(parent, title, true);
        this.mainControl = mainControl;

        initUI();
    }

    private void initUI() {
        setSize(400, 300);
        setLocationRelativeTo(null); // 화면 중앙 배치
        setLayout(new BorderLayout());

        displayFormPanel();
        displayButtonPanel();

        // AssetType 변경 시 동적으로 입력 필드 업데이트
        assetTypeComboBox.addActionListener(e -> updateFieldsBasedOnAssetType());
        updateFieldsBasedOnAssetType(); // 초기 필드 상태 설정
    }

    private void displayFormPanel() {
        formPanel.add(assetTypeLabel);
        formPanel.add(assetTypeComboBox);
        formPanel.add(symbolLabel);
        formPanel.add(symbolField);
        formPanel.add(purchasePriceLabel);
        formPanel.add(purchasePriceField);
        formPanel.add(quantityLabel);
        formPanel.add(quantityField);

        add(formPanel, BorderLayout.CENTER);
    }

    private void displayButtonPanel() {
        confirmButton.addActionListener(e -> confirm());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * AssetType 에 따라 입력 필드 표시 여부를 동적으로 업데이트
     */
    private void updateFieldsBasedOnAssetType() {
        AssetType selectedType = (AssetType) assetTypeComboBox.getSelectedItem();
        boolean isTradable = selectedType == AssetType.STOCK || selectedType == AssetType.CRYPTO;

        // Tradable 자산만 구매 단가 필드를 표시
        purchasePriceLabel.setVisible(isTradable);
        purchasePriceField.setVisible(isTradable);

        // 재배치
        formPanel.revalidate();
        formPanel.repaint();
    }

    private void confirm() {
        try {
            AssetType assetType = (AssetType) assetTypeComboBox.getSelectedItem();
            String symbol = symbolField.getText();
            double purchasePrice = purchasePriceField.isVisible() ? Double.parseDouble(purchasePriceField.getText()) : 0;
            double quantity = Double.parseDouble(quantityField.getText());

            if (symbol.isBlank() || quantity <= 0) {
                throw new IllegalArgumentException("유효한 데이터를 입력하세요.");
            }

            if (selectedRow < 0) {
                // 추가 모드
                mainControl.addAsset(assetType, symbol, purchasePrice, quantity);
            } else {
                // 수정 모드
                mainControl.editAsset(selectedRow, assetType, symbol, purchasePrice, quantity);
            }

            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "숫자를 정확히 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setEditMode(int selectedRow) {
        this.selectedRow = selectedRow;

        Asset selectedAsset = mainControl.getAssetByIndex(selectedRow);

        assetTypeComboBox.setSelectedItem(selectedAsset.getAssetType());
        symbolField.setText(selectedAsset.getSymbol());
        quantityField.setText(String.valueOf(selectedAsset.getQuantity()));

        // Tradable 자산인 경우 구매 단가 필드 활성화
        if (selectedAsset instanceof Tradable tradable) {
            purchasePriceField.setText(String.valueOf(tradable.getPurchasePrice()));
            purchasePriceLabel.setVisible(true);
            purchasePriceField.setVisible(true);
        } else {
            purchasePriceField.setText(""); // 비 Tradable 자산은 구매 단가 비활성화
            purchasePriceLabel.setVisible(false);
            purchasePriceField.setVisible(false);
        }

        formPanel.revalidate();
        formPanel.repaint();
    }
}
