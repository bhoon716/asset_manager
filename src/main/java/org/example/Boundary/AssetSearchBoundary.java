package org.example.Boundary;

import org.example.Control.MainControl;
import org.example.Entity.AssetType;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class AssetSearchBoundary extends JFrame {

    private final JPanel searchPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 검색 영역 레이아웃 개선
    private final JLabel assetTypeLabel = new JLabel("자산 종류:");
    private final JComboBox<AssetType> assetTypeComboBox; // 필터링된 AssetType 을 사용할 JComboBox
    private final JLabel symbolLabel = new JLabel("자산 코드:");
    private final JTextField symbolTextField = new JTextField(10);
    private final JTextArea assetInfoTextArea = new JTextArea(15, 40); // 결과 표시 영역
    private final JScrollPane resultScrollPane = new JScrollPane(assetInfoTextArea); // 스크롤 가능하게 설정
    private final JPanel buttonPanel = new JPanel(); // 검색 버튼은 별도 패널로 추가
    private final JButton searchButton = new JButton("검색");

    private final MainControl mainControl;

    public AssetSearchBoundary(MainControl mainControl) {
        this.mainControl = mainControl;

        // Tradable 한 자산만 필터링하여 JComboBox 에 추가
        assetTypeComboBox = new JComboBox<>(Arrays.stream(AssetType.values())
                .filter(this::isTradable)
                .toArray(AssetType[]::new));

        initUI();
        setUpSearchPanel();
        setUpButtonPanel();
        setUpResultPanel();
        setVisible(true);
    }

    private void initUI() {
        setTitle("자산 정보 검색");
        setSize(600, 500); // 창 크기 조정
        setLocationRelativeTo(null); // 화면 중앙에 배치
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // 여백 추가
    }

    private void setUpSearchPanel() {
        searchPanel.setBorder(BorderFactory.createTitledBorder("자산 검색")); // 테두리와 제목 추가
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 추가

        searchPanel.add(assetTypeLabel);
        searchPanel.add(assetTypeComboBox);
        searchPanel.add(symbolLabel);
        searchPanel.add(symbolTextField);

        add(searchPanel, BorderLayout.NORTH);
    }

    private void setUpResultPanel() {
        assetInfoTextArea.setEditable(false); // 텍스트 수정 불가
        assetInfoTextArea.setLineWrap(true); // 자동 줄바꿈
        assetInfoTextArea.setWrapStyleWord(true); // 단어 단위 줄바꿈
        assetInfoTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // 폰트 크기 조정

        resultScrollPane.setBorder(BorderFactory.createTitledBorder("검색 결과")); // 테두리와 제목 추가
        add(resultScrollPane, BorderLayout.CENTER); // 중앙에 결과 패널 추가
    }

    private void setUpButtonPanel() {
        searchButton.addActionListener(e -> searchAssetInfo());
        buttonPanel.add(searchButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void searchAssetInfo() {
        AssetType assetType = (AssetType) assetTypeComboBox.getSelectedItem();
        String symbol = symbolTextField.getText().trim();

        // 입력 검증
        if (assetType == null || symbol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "자산 종류와 종목 코드를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 검색 결과 가져오기
        JSONObject assetInfo = mainControl.searchAsset(assetType, symbol);
        if (assetInfo == null) {
            assetInfoTextArea.setText("검색된 자산 정보가 없습니다.");
        } else {
            assetInfoTextArea.setText(formatAssetInfo(assetInfo)); // JSON 데이터를 보기 좋게 포맷팅
        }
    }

    private String formatAssetInfo(JSONObject assetInfo) {
        StringBuilder formattedText = new StringBuilder();

        Iterator<String> keys = assetInfo.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            formattedText.append(key).append(": ").append(assetInfo.get(key)).append("\n");
        }

        return formattedText.toString();
    }

    // Tradable 한 자산인지 확인
    private boolean isTradable(AssetType assetType) {
        return assetType == AssetType.STOCK || assetType == AssetType.CRYPTO; // Tradable 자산만 포함
    }
}
