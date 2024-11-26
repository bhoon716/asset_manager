package org.example.Entity;

import java.text.DecimalFormat;
import java.time.Month;
import java.util.List;

public class Stock extends TradableAsset implements DividendBearing {

    public Stock(String symbol, double purchasePrice, double quantity) {
        super(AssetType.STOCK, symbol, purchasePrice, quantity);
    }

    @Override
    public double getExpectedDividend() {
        return 0;
    }

    @Override
    public List<Month> getExpectedDividendMonth() {
        return List.of();
    }

    @Override
    public String[] toStringArray() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00"); // 소수점 둘째 자리로 포맷

        return new String[]{
                getAssetType().toString(),
                getSymbol(),
                decimalFormat.format(getEvaluationPrice()),
                decimalFormat.format(getTotalPurchasePrice()),
                decimalFormat.format(getProfitLoss()),
                decimalFormat.format(getProfitLossRate()),
                decimalFormat.format(getCurrentPrice()),
                decimalFormat.format(getPurchasePrice()),
                decimalFormat.format(getQuantity()),
        };
    }
}
