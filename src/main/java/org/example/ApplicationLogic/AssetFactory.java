package org.example.ApplicationLogic;

import org.example.Entity.*;

public class AssetFactory {

    /**
     * AssetType 에 따라 적절한 Asset 객체를 생성합니다.
     */
    public Asset createAsset(AssetType assetType, String symbol, double purchasePrice, double quantity) {
        switch (assetType) {
            case STOCK:
                return new Stock(symbol, purchasePrice, quantity);
            case CRYPTO:
                return new Crypto(symbol, purchasePrice, quantity);
            case CASH:
                return new Cash(symbol, quantity);
            default:
                throw new IllegalArgumentException("지원되지 않는 자산 타입: " + assetType);
        }
    }
}
