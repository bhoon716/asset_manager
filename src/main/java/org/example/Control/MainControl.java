package org.example.Control;

import org.example.ApplicationLogic.*;
import org.example.Boundary.*;
import org.example.Entity.*;

import java.util.List;
import java.util.Map;

public class MainControl {

    private final AssetFactory assetFactory;
    private final NewsApiService newsApiService;
    private final PortfolioServiceImpl portfolioService;
    private final UserServiceImpl userService;

    public MainControl(AssetFactory assetFactory, NewsApiService newsApiService, PortfolioServiceImpl portfolioService, UserServiceImpl userService){
        this.assetFactory = assetFactory;
        this.newsApiService = newsApiService;
        this.portfolioService = portfolioService;
        this.userService = userService;
    }

    public void showMainBoundary(User user){
        userService.setCurrentUser(user);
        new MainBoundary(this);
    }

    public void showAssetInfoBoundary(){
        new AssetInfoBoundary(this);
    }

    public void showPortfolioListBoundary() {
        new PortfolioListBoundary(this);
    }
    public void showPortfolioBoundary(Portfolio portfolio) {
        portfolioService.setCurrentPortfolio(portfolio);
        new PortfolioBoundary(this);
    }
    public void showLoginBoundary() {
        new LoginBoundary(this);
    }
    public void showRegisterBoundary() {
        new RegisterBoundary(this);
    }



    public AssetDetail searchAsset(AssetType type, String symbol) {
        AssetApiService apiService = assetFactory.getApiService(type);
        return null;
    }

    public List<News> fetchNewsHeadlines(int page, int size) {
        return newsApiService.getNewsList(page, size);
    }


    public List<Portfolio> getPortfolioList() {
        return portfolioService.getPortfolioList();
    }
    public List<Portfolio> getPortfolioListDataList() {
        return userService.getPortfolioListDataList();
    }

    public boolean checkDuplicatedPortfolioName(String name) {
        return portfolioService.checkDuplicate(name);
    }

    public void addPortfolio(String name) {
        portfolioService.addPortfolio(name);
    }
    public void setPortfolioList() {
        portfolioService.setPortfolioList(userService.getPortfolioListDataList());
    }


    public void deletePortfolio(Portfolio portfolio) {
        portfolioService.deletePortfolio(portfolio);
    }

    public Portfolio getCurrentPortfolio() {
        return portfolioService.getCurrentPortfolio();
    }

    public void addAsset(Asset newAsset) {
        portfolioService.addAsset(newAsset);
    }

    public void changeAsset(int selectedRow, Asset newAsset) {
        portfolioService.changeAsset(selectedRow, newAsset);
    }

    public void deleteAsset(int selectedRow) {
        portfolioService.deleteAsset(selectedRow);
    }

    public List<Object[]> getPortfolioDataList() {
        return portfolioService.getPortfolioDataList();
    }

    public Asset getDuplicatedAsset(AssetType assetType, String symbol){
        return portfolioService.getDuplicatedAsset( assetType,  symbol);
    }

    public void addAsset(AssetType assetType, String symbol, Double purchasePrice, Double quantity) {
        Asset asset = assetFactory.createAsset(assetType, symbol, purchasePrice, quantity);
        if(asset instanceof TradableAsset){
            double currentPrice = assetFactory.getApiService(assetType).getCurrentPrice(symbol);
            ((TradableAsset) asset).setCurrentPrice(currentPrice);
        }
        portfolioService.addAsset(asset);
    }

    public Asset getAssetByIndex(int index) {
        return portfolioService.getCurrentPortfolio().getAssetList().get(index);
    }

    public void fetchAllCurrentPrice(){
        for(Asset asset : portfolioService.getCurrentPortfolio().getAssetList()){
            if(asset instanceof TradableAsset){
                double currentPrice = assetFactory.getApiService(asset.getAssetType()).getCurrentPrice(asset.getSymbol());
                ((TradableAsset) asset).setCurrentPrice(currentPrice);
            }
        }
    }

    public User getUser() {
        return userService.getCurrentUser();
    }
    public String getUserId() {return userService.getUserId();}
    public void addUser(String id, String password) {
        userService.addUser(id, password);
    }
    public boolean checkDuplicatedUserId(String id) {
        return userService.checkDuplicate(id);
    }
    public Map<String,User> getUserMap() {
        return userService.getUserMap();
    }
    public boolean login(String id, String password) {
        return userService.login(id, password);
    }
    public void logout() {
        new LoginBoundary(this);
    }



}