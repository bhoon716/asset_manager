package org.example.Control;

import org.example.ApplicationLogic.AssetApiService.AssetApiService;
import org.example.ApplicationLogic.AssetApiService.AssetApiServiceFactory;
import org.example.ApplicationLogic.NewsApiService;
import org.example.Boundary.AssetBoundary;
import org.example.Boundary.MainBoundary;
import org.example.Entity.AssetDto;
import org.example.Entity.AssetType;
import org.example.Entity.News;

import java.util.List;

public class MainControl {

    private final AssetApiServiceFactory assetApiServiceFactory;
    private final NewsApiService newsApiService;

    public MainControl(AssetApiServiceFactory assetApiServiceFactory ,NewsApiService newsApiService){
        this.assetApiServiceFactory = assetApiServiceFactory;
        this.newsApiService = newsApiService;
    }

    public void showMainBoundary(){
        new MainBoundary(this);
    }

    public void showAssetBoundary(){
        new AssetBoundary(this);
    }

    public void showPortfolioBoundary() {
        System.out.println("show portfolio boundary");
    }

    public AssetDto searchAsset(AssetType type, String symbol) {
        AssetApiService apiService = assetApiServiceFactory.getApiService(type);
        return apiService.getAssetDto(symbol);
    }

    public List<News> fetchNewsHeadlines(int page, int size) {
        return newsApiService.getNewsList(page, size);
    }

    public void logout() {
        System.out.println("logout");
    }
}