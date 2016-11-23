package com.codecool.chucknorris.controller;

import com.codecool.chucknorris.service.APIService;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class ChuckNorrisAPIController {

    public static final String CATEGORY_PARAM_KEY = "category";

    private final APIService apiService;

    public ChuckNorrisAPIController(APIService apiService){
        this.apiService = apiService;
    }

    public String random(Request request, Response response) throws IOException, URISyntaxException {
        String category = request.queryParams(CATEGORY_PARAM_KEY);
        return apiService.random(category);
    }

    public String categories(Request request, Response response) throws IOException, URISyntaxException {
        return apiService.categories();
    }

}
