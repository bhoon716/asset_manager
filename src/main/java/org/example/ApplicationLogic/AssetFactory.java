package org.example.ApplicationLogic;

import org.example.Entity.*;

public class AssetFactory {

    public AssetApiService getApiService(AssetType assetType) {
        return switch (assetType) {
            case STOCK -> new StockApiService();
            case CRYPTO -> new CryptoApiService();
            default -> {
                System.out.println("지원되지");
                throw new IllegalArgumentException("지원되지 않는 자산 종류");
            }
        };
    }

    public Asset createAsset(AssetType assetType, String symbol, Double purchasePrice, Double quantity) {
        switch (assetType) {
            case STOCK:
                return new Stock(symbol, purchasePrice, quantity);
            case CRYPTO:
                return new Crypto(symbol, purchasePrice, quantity);
            case CASH:
                return new Cash(symbol, quantity);
            default:
                System.out.println("지원되지1");
                throw new IllegalArgumentException("지원되지 않는 자산 종류");
        }
    }
}
