package org.example.ApplicationLogic;

import org.example.Entity.News;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NewsApiServiceImpl implements NewsApiService{

    private static final String BASE_URL = "https://financialmodelingprep.com/api/v3/fmp/articles";
    private static final String API_KEY = "QeQqFFHgy2R6keFPHezAdxNJaeZGVmpM";

    // 뉴스 데이터를 가져와 News 객체 리스트로 반환합니다.
    public List<News> getNewsList(int page, int size) {
        String url = buildUrl(page, size);
        String jsonResponse = fetchDataFromApi(url);

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            System.err.println("API 응답이 비어 있습니다.");
            return new ArrayList<>();
        }

        return parseJsonToNewsList(jsonResponse);
    }


    // API 호출 URL 생성
    private String buildUrl(int page, int size) {
        return String.format("%s?page=%d&size=%d&apikey=%s", BASE_URL, page, size, API_KEY);
    }


    // API 호출하여 JSON 데이터를 가져옴
    private String fetchDataFromApi(String url) {
        try {
            URL requestUrl = new URL(url);
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestUrl.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            System.out.println("뉴스 데이터 성공적으로 가져옴");
            return response.toString();
        } catch (MalformedURLException e) {
            System.err.println("잘못된 URL 입니다: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("API 요청 중 오류 발생: " + e.getMessage());
        }

        return null;
    }

    // JSON 응답 데이터를 List<News>로 변환
    private List<News> parseJsonToNewsList(String jsonResponse) {
        List<News> newsList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray contentArray = jsonObject.getJSONArray("content");

        for (int i = 0; i < contentArray.length(); i++) {
            JSONObject newsObject = contentArray.getJSONObject(i);

            String title = newsObject.optString("title", "No Title");
            String link = newsObject.optString("link", "No Link");

            newsList.add(new News(title, link));
        }

        return newsList;
    }
}
