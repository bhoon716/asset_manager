package org.example.ApplicationLogic;

import org.example.Entity.Asset;
import org.example.Entity.AssetType;
import org.example.Entity.Portfolio;
import org.example.Entity.TradableAsset;

import java.util.ArrayList;
import java.util.List;

public class PortfolioService {

    private final List<Portfolio> portfolioList = new ArrayList<>();
    private Portfolio currentPortfolio;

    public List<Portfolio> getPortfolioList() {
        return this.portfolioList;
    }

    public boolean checkDuplicate(String name) {
        for(Portfolio portfolio : portfolioList){
            if(name.equals(portfolio.getName())){
                return true;
            }
        }
        return false;
    }

    public void addPortfolio(String name) {
        portfolioList.add(new Portfolio(name));
    }

    public void deletePortfolio(Portfolio portfolio) {
        portfolioList.remove(portfolio);
    }

    public Portfolio getCurrentPortfolio() {
        return currentPortfolio;
    }

    public void setCurrentPortfolio(Portfolio currentPortfolio) {
        this.currentPortfolio = currentPortfolio;
    }

    public void addAsset(Asset newAsset) {
        currentPortfolio.getAssetList().add(newAsset);
    }

    public void changeAsset(int index, Asset newAsset) {
        currentPortfolio.getAssetList().set(index, newAsset);
    }

    public void deleteAsset(int selectedRow) {
        currentPortfolio.getAssetList().remove(selectedRow);
    }

    public List<Object[]> getPortfolioDataList() {
        List<Object[]> dataList = new ArrayList<>();
        for(Asset asset : currentPortfolio.getAssetList()){
            dataList.add(asset.toStringArray());
        }
        return dataList;
    }

    public Asset getDuplicatedAsset(AssetType assetType, String symbol) {
        for(Asset asset : currentPortfolio.getAssetList()){
            if(symbol.equals(asset.getSymbol()) && assetType.equals(asset.getAssetType())){
                return asset;
            }
        }
        return null;
    }
}
