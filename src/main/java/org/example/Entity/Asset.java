package org.example.Entity;

public abstract class Asset {

    private AssetType assetType;   // 자산종류
    private String symbol;         // 종목코드
    private double purchasePrice;  // 구매단가
    private double quantity;       // 보유수량
    private double totalPurchasePrice;

    public Asset(AssetType assetType, String symbol, double purchasePrice, double quantity) {
        this.assetType = assetType;
        this.symbol = symbol;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.totalPurchasePrice = purchasePrice * quantity;
    }

    // Getters and Setters
    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(double totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    // 평가 금액 (현재가 * 보유 수량)
    public abstract double getEvaluationPrice();

    // 평가 손익 (평가 금액 - 총 구매 금액)
    public abstract double getProfitLoss();

    // 손익률 ((평가 손익 / 총 구매 금액) * 100)
    public abstract double getProfitLossRate();

    public abstract String[] toStringArray();
}
