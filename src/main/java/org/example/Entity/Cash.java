package org.example.Entity;

public class Cash extends Asset {

    public Cash(String symbol, double quantity) {
        super(AssetType.CASH, symbol, quantity);
    }

    @Override
    public Double getEvaluationPrice() {
        return getQuantity(); // 현금의 평가 금액은 보유 수량 그대로 반환
    }

    @Override
    public String[] toStringArray() {
        return new String[]{
                getAssetType().name(),                                // 자산 종류
                getSymbol(),                                          // 종목 코드
                "-",                                                 // 구매 가격 없음
                "-",                                                 // 현재 가격 없음
                String.format("%.2f", getEvaluationPrice()),          // 평가 금액 (보유 수량 그대로 반환)
                "-",                                                 // 손익 없음
                "-",                                                 // 손익률 없음
                String.format("%.2f", getQuantity())                  // 보유 수량
        };
    }
}
