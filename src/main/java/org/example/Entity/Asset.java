package org.example.Entity;

public abstract class Asset {

    private AssetType assetType;   // 자산종류
    private String symbol;         // 종목코드
    private double quantity;       // 보유수량

    public Asset(AssetType assetType, String symbol, double quantity) {
        setProperties(assetType, symbol, quantity);
    }

    public void setProperties(AssetType assetType, String symbol, double quantity) {
        this.assetType = assetType;
        this.symbol = symbol;
        this.quantity = quantity;
    }

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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public abstract Double getEvaluationPrice();

    public abstract String[] toStringArray();
}
