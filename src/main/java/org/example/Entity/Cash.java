package org.example.Entity;

// Cash.java
public class Cash extends Asset {

    public Cash(String symbol, double quantity) {
        super(AssetType.CASH, symbol, 0, quantity);
    }

    @Override
    public double getEvaluationPrice() {
        return 0;
    }

    @Override
    public double getProfitLoss() {
        return 0;
    }

    @Override
    public double getProfitLossRate() {
        return 0;
    }

    @Override
    public String[] toStringArray(){
        return null;
    }
}
