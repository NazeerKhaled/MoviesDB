package com.example.moviesdb.Listeners;

import com.example.moviesdb.Models.SearchApiResponse;

public interface OnSearchApiListener {
    void onResponse(SearchApiResponse response);
    void onError(String message);

}
