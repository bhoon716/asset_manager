package org.example.ApplicationLogic;

import org.example.Entity.AssetType;

public class AssetApiServiceFactory {

    private final AssetApiService stockApiService;
    private final AssetApiService cryptoApiService;

    public AssetApiServiceFactory(AssetApiService stockApiService, AssetApiService cryptoApiService) {
        this.stockApiService = stockApiService;
        this.cryptoApiService = cryptoApiService;
    }

    public AssetApiService getApiService(AssetType assetType) {
        switch (assetType) {
            case STOCK:
                return stockApiService;
            case CRYPTO:
                return cryptoApiService;
            default:
                throw new IllegalArgumentException("지원되지 않는 자산 종류: " + assetType);
        }
    }
}
