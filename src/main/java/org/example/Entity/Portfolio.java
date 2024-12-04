package org.example.Entity;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private String name;               // 포트폴리오 이름
    private final List<Asset> assetList; // 자산 리스트

    public Portfolio(String name) {
        setName(name);
        this.assetList = new ArrayList<>();
    }
    public Asset getAsset(int index) {return assetList.get(index);}
    public void removeAsset(int selectedRow) {
        assetList.remove(selectedRow);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Asset> getAssetList() {
        return assetList;
    }

    /**
     * 포트폴리오의 총 평가 금액
     * @return 총 평가 금액
     */
    public double getTotalEvaluationPrice() {
        return assetList.stream()
                .mapToDouble(Asset::getEvaluationPrice)
                .sum();
    }

    /**
     * 포트폴리오의 총 매입 금액
     * @return 총 매입 금액
     */
    private double getTotalInvestment() {
        return assetList.stream()
                .filter(asset -> asset instanceof Tradable)
                .mapToDouble(asset -> {
                    Tradable tradable = (Tradable) asset;
                    return tradable.getPurchasePrice() * asset.getQuantity();
                })
                .sum();
    }

    /**
     * 포트폴리오의 총 손익
     * @return 총 손익
     */
    public double getTotalProfitLoss() {
        return assetList.stream()
                .filter(asset -> asset instanceof Tradable)
                .mapToDouble(asset -> {
                    Tradable tradable = (Tradable) asset;
                    return (tradable.getCurrentPrice() - tradable.getPurchasePrice()) * asset.getQuantity();
                })
                .sum();
    }

    /**
     * 포트폴리오의 총 손익률 (%)
     * @return 총 손익률
     */
    public double getTotalProfitLossRate() {
        double totalInvestment = getTotalInvestment();
        if (totalInvestment == 0) {
            return 0.0;
        }
        return (getTotalProfitLoss() / totalInvestment) * 100;
    }

    @Override
    public String toString() {
        return name;
    }
}
