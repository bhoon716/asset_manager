package org.example.ApplicationLogic.AssetApiService;

import org.example.Entity.AssetType;

public class AssetApiServiceFactory {

    public AssetApiService getApiService(AssetType assetType) {
        switch (assetType){
            case STOCK:
                return new StockApiService();
            case CRYPTO:
                return new CryptoApiService();
            case CASH:
                return new CashApiService();
            default:
                throw new IllegalArgumentException();
        }
    }
}
