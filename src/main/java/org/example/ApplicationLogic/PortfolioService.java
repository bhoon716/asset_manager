package org.example.ApplicationLogic;

import org.example.Entity.Asset;
import org.example.Entity.AssetType;
import org.example.Entity.Portfolio;

import java.util.List;

public interface PortfolioService {

    List<Portfolio> getPortfolioList(); // 모든 포트폴리오 반환

    boolean checkDuplicate(String name); // 이름 중복 확인

    void setPortfolioList(List<Portfolio> portfolioList); // 포트폴리오 리스트 설정

    void addPortfolio(String name); // 포트폴리오 추가

    void deletePortfolio(Portfolio portfolio); // 포트폴리오 삭제

    Portfolio getCurrentPortfolio(); // 현재 포트폴리오 반환

    void setCurrentPortfolio(Portfolio currentPortfolio); // 현재 포트폴리오 설정

    void addAsset(AssetType assetType, String symbol, double purchasePrice, double quantity); // 자산 추가

    void deleteAsset(int selectedRow); // 자산 삭제

    List<Object[]> getPortfolioDataList(); // 포트폴리오 데이터 반환

    Asset getDuplicatedAsset(AssetType assetType, String symbol); // 중복 자산 조회

    void editAsset(int selectedRow, AssetType assetType, String symbol, double purchasePrice, double quantity); // 자산 수정
}
