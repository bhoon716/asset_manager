package org.example.Entity;

public class Stock extends Asset implements Tradable {

    private double purchasePrice; // 구매 가격
    private double currentPrice;  // 현재 가격

    public Stock(String symbol, double purchasePrice, double quantity) {
        super(AssetType.STOCK, symbol, quantity);
        this.purchasePrice = purchasePrice;
    }

    @Override
    public double getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public double getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public Double getEvaluationPrice() {
        return currentPrice * getQuantity();
    }

    @Override
    public String[] toStringArray() {
        return new String[]{
                getAssetType().name(),                                // 자산 종류
                getSymbol(),                                          // 종목 코드
                String.format("%.2f", purchasePrice),                 // 구매 가격
                String.format("%.2f", currentPrice),                  // 현재 가격
                String.format("%.2f", getEvaluationPrice()),          // 평가 금액
                String.format("%.2f", calculateProfitLoss(currentPrice, purchasePrice, getQuantity())), // 손익
                String.format("%.2f%%", calculateProfitLossRate(currentPrice, purchasePrice)),           // 손익률
                String.format("%.2f", getQuantity())                  // 보유 수량
        };
    }
}
