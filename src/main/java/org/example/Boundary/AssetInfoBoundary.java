package org.example.Boundary;

import org.example.Control.MainControl;
import org.example.Entity.AssetDetail;
import org.example.Entity.AssetType;

import javax.swing.*;
import java.awt.*;

public class AssetInfoBoundary extends JFrame {

    private final JPanel searchPanel = new JPanel();
    private final JLabel assetTypeLabel = new JLabel("자산 종류:");
    private final JList<AssetType> assetTypeList = new JList<>(AssetType.values());
    private final JLabel symbolLabel = new JLabel("종목 코드:");
    private final JTextField symbolTextField = new JTextField(10);
    private final JButton searchButton = new JButton("검색");
    private final JTextArea assetDtoTextArea = new JTextArea(10, 30);

    private final MainControl mainControl;

    public AssetInfoBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        initUI();
        setUpSearchPanel();
        setUpResultPanel();
    }

    private void initUI() {
        setTitle("자산 정보 검색");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);
    }

    private void setUpSearchPanel() {
        searchButton.addActionListener(e -> searchAssetInfo());

        searchPanel.add(assetTypeLabel);
        searchPanel.add(assetTypeList);
        searchPanel.add(symbolLabel);
        searchPanel.add(symbolTextField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);
    }

    private void setUpResultPanel() {
        assetDtoTextArea.setEditable(false); // 수정 불가
        assetDtoTextArea.setLineWrap(true); // 자동 줄바꿈
        assetDtoTextArea.setWrapStyleWord(true); // 단어 단위 줄바꿈

        JScrollPane scrollPane = new JScrollPane(assetDtoTextArea); // 스크롤 추가
        add(scrollPane, BorderLayout.CENTER); // 스크롤 패널을 중앙에 추가
    }

    public void searchAssetInfo() {
        AssetType assetType = assetTypeList.getSelectedValue();
        String symbol = symbolTextField.getText();

        // 입력 검증
        if (assetType == null || symbol == null || symbol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "자산 종류와 종목 코드를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AssetDetail assetDetail = mainControl.searchAsset(assetType, symbol);
        if (assetDetail == null) {
            assetDtoTextArea.setText("검색된 자산 정보가 없습니다.");
            return;
        }
        assetDtoTextArea.setText(assetDetail.toString());

        // UI 갱신
        revalidate();
        repaint();
    }
}
