package org.example.Entity;

public abstract class TradableAsset extends Asset {

    private double currentPrice;

    public TradableAsset(AssetType assetType, String symbol, double purchasePrice, double quantity) {
        super(assetType, symbol, purchasePrice, quantity);
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double price) {
        this.currentPrice = price;
    }

    @Override
    public double getEvaluationPrice() {
        return currentPrice * getQuantity();
    }

    @Override
    public double getProfitLoss() {
        return getEvaluationPrice() - (getPurchasePrice() * getQuantity());
    }

    @Override
    public double getProfitLossRate() {
        double totalPurchase = getPurchasePrice() * getQuantity();
        return totalPurchase == 0 ? 0 : (getProfitLoss() / totalPurchase) * 100;
    }
}
