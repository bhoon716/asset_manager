package org.example.Entity;

public class AssetDto {

    private String symbol;       // 종목 코드
    private String date;         // 날짜
    private double open;         // 시가
    private double high;         // 고가
    private double low;          // 저가
    private double close;        // 종가
    private double adjClose;     // 조정 종가
    private long volume;         // 거래량
    private double change;       // 변동 폭
    private double changePercent;// 변동률

    public AssetDto() {
    }

    public AssetDto(String symbol, String date, double open, double high, double low,
                    double close, double adjClose, long volume, double change, double changePercent) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
        this.change = change;
        this.changePercent = changePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    @Override
    public String toString() {
        return "종목 정보\n" +
                "  종목 코드 :  " + symbol + "\n" +
                "  기준 날짜 :  " + date + "\n" +
                "  시    가 :    " + open + " USD\n" +
                "  고    가 :    " + high + " USD\n" +
                "  저    가 :    " + low + " USD\n" +
                "  종    가 :    " + close + " USD\n" +
                "  조정 종가 :  " + adjClose + " USD\n" +
                "  거 래 량 :    " + volume + " 주\n" +
                "  변 동 폭 :    " + change + " USD\n" +
                "  변 동 률 :    " + changePercent + " %\n";
    }
}