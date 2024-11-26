package org.example.Entity;

public class Crypto extends TradableAsset {

    private double currentPrice;

    public Crypto(String symbol, double purchasePrice, double quantity) {
        super(AssetType.CRYPTO, symbol, purchasePrice, quantity);
    }

    @Override
    public String[] toStringArray() {
        return new String[0];
    }
}
