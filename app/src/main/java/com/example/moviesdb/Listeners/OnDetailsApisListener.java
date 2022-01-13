package com.example.moviesdb.Listeners;

import com.example.moviesdb.Models.DetailApiResponse;

public interface OnDetailsApisListener {
    void onResponse(DetailApiResponse response);
    void onError(String message);

}
