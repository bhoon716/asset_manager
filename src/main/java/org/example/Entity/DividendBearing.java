package org.example.Entity;

import java.time.Month;
import java.util.List;

public interface DividendBearing {

    double getExpectedDividend(); // 예상 배당금

    List<Month> getExpectedDividendMonth(); // 예상 배당 월
}