package org.example;

import org.example.ApplicationLogic.AssetApiService.AssetApiServiceFactory;
import org.example.ApplicationLogic.NewsApiService;
import org.example.Control.MainControl;

public class Main {

    public static void main(String[] args) {
        AssetApiServiceFactory assetApiServiceFactory = new AssetApiServiceFactory();
        NewsApiService newsApiService = new NewsApiService();

        MainControl mainControl = new MainControl(assetApiServiceFactory, newsApiService);
        mainControl.showMainBoundary();
    }
}