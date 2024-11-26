package org.example.ApplicationLogic;

import org.example.Entity.News;

import java.util.List;

public interface NewsApiService {

    List<News> getNewsList(int page, int size);
}
