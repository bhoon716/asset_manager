package org.example.ApplicationLogic;

import org.example.Entity.Asset;
import org.example.Entity.AssetType;
import org.example.Entity.Portfolio;
import org.example.Entity.Tradable;

import java.util.ArrayList;
import java.util.List;

public class PortfolioServiceImpl implements PortfolioService {

    private final AssetFactory assetFactory;

    private List<Portfolio> portfolioList = new ArrayList<>();
    private Portfolio currentPortfolio;

    public PortfolioServiceImpl(AssetFactory assetFactory) {
        this.assetFactory = assetFactory;
    }

    @Override
    public List<Portfolio> getPortfolioList() {
        return portfolioList;
    }

    @Override
    public boolean checkDuplicate(String name) {
        return portfolioList.stream().anyMatch(portfolio -> portfolio.getName().equalsIgnoreCase(name));
    }

    @Override
    public void setPortfolioList(List<Portfolio> portfolioList) {
        if (portfolioList == null) {
            throw new IllegalArgumentException("포트폴리오 리스트가 null 일 수 없습니다.");
        }
        this.portfolioList = portfolioList;
    }

    @Override
    public void addPortfolio(String name) {
        if (checkDuplicate(name)) {
            throw new IllegalArgumentException("이미 존재하는 포트폴리오 이름입니다.");
        }
        portfolioList.add(new Portfolio(name));
    }

    @Override
    public void deletePortfolio(Portfolio portfolio) {
        portfolioList.remove(portfolio);
        if (portfolio.equals(currentPortfolio)) {
            currentPortfolio = null;
        }
    }

    @Override
    public Portfolio getCurrentPortfolio() {
        return currentPortfolio;
    }

    @Override
    public void setCurrentPortfolio(Portfolio currentPortfolio) {
        if (currentPortfolio == null || !portfolioList.contains(currentPortfolio)) {
            throw new IllegalArgumentException("선택한 포트폴리오가 목록에 없습니다.");
        }
        this.currentPortfolio = currentPortfolio;
    }

    @Override
    public void addAsset(AssetType assetType, String symbol, double purchasePrice, double quantity) {
        validateCurrentPortfolio();
        Asset existingAsset = getDuplicatedAsset(assetType, symbol);
        if (existingAsset != null) {
            updateExistingAsset(existingAsset, purchasePrice, quantity);
        } else {
            Asset asset = assetFactory.createAsset(assetType, symbol, purchasePrice, quantity);
            currentPortfolio.getAssetList().add(asset);
        }
    }

    private void updateExistingAsset(Asset existingAsset, double purchasePrice, double quantity) {
        if (existingAsset instanceof Tradable tradable) {
            double totalPurchasePrice = tradable.getPurchasePrice() * existingAsset.getQuantity() + purchasePrice * quantity;
            double totalQuantity = existingAsset.getQuantity() + quantity;
            tradable.setPurchasePrice(totalPurchasePrice / totalQuantity);
            existingAsset.setQuantity(totalQuantity);
        }
    }

    @Override
    public void deleteAsset(int selectedRow) {
        validateAssetIndex(selectedRow);
        currentPortfolio.getAssetList().remove(selectedRow);
    }

    @Override
    public List<Object[]> getPortfolioDataList() {
        validateCurrentPortfolio();
        List<Object[]> dataList = new ArrayList<>();
        for (Asset asset : currentPortfolio.getAssetList()) {
            dataList.add(asset.toStringArray());
        }
        return dataList;
    }

    @Override
    public Asset getDuplicatedAsset(AssetType assetType, String symbol) {
        validateCurrentPortfolio();
        return currentPortfolio.getAssetList().stream()
                .filter(asset -> asset.getSymbol().equalsIgnoreCase(symbol) && asset.getAssetType() == assetType)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void editAsset(int index, AssetType assetType, String symbol, double purchasePrice, double quantity) {
        validateAssetIndex(index);

        Asset asset = currentPortfolio.getAssetList().get(index);
        asset.setAssetType(assetType);
        asset.setSymbol(symbol);
        asset.setQuantity(quantity);

        if (asset instanceof Tradable tradable) {
            tradable.setPurchasePrice(purchasePrice);
        }
    }

    private void validateCurrentPortfolio() {
        if (currentPortfolio == null) {
            throw new IllegalStateException("현재 선택된 포트폴리오가 없습니다.");
        }
    }

    private void validateAssetIndex(int index) {
        validateCurrentPortfolio();
        if (index < 0 || index >= currentPortfolio.getAssetList().size()) {
            throw new IndexOutOfBoundsException("유효하지 않은 자산 인덱스입니다.");
        }
    }
}
