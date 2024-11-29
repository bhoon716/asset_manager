package org.example.Entity;

public interface Tradable {

    double getPurchasePrice();

    void setPurchasePrice(double purchasePrice);

    double getCurrentPrice();

    void setCurrentPrice(double currentPrice);

    /**
     * 손익 계산
     * @return 손익 금액
     */
    default double calculateProfitLoss(double currentPrice, double purchasePrice, double quantity) {
        return (currentPrice - purchasePrice) * quantity;
    }

    /**
     * 손익률 계산
     * @return 손익률 (%)
     */
    default double calculateProfitLossRate(double currentPrice, double purchasePrice) {
        if (purchasePrice == 0) {
            return 0.0;
        }
        return ((currentPrice - purchasePrice) / purchasePrice) * 100;
    }
}
