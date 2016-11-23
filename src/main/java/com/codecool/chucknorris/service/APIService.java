package com.codecool.chucknorris.service;

import com.codecool.chucknorris.controller.ChuckNorrisAPIController;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Service class with actual business logic.
 */
public class APIService {

    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

    private static APIService INSTANCE;

    public static APIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new APIService();
        }
        return INSTANCE;
    }

    /**
     * Gets a random Chuck Norris fact, with category filter, if given
     * @param category - if not {@link StringUtils#isEmpty(Object)} acts as a filter.
     * @return - JSON received from the API as it is.
     * @throws IOException
     * @throws URISyntaxException
     */
    public String random(String category) throws IOException, URISyntaxException {

        URIBuilder builder = new URIBuilder("https://api.chucknorris.io/jokes/random");

        if(!StringUtils.isEmpty(category)){
            logger.debug("Adding category filter: {}", category);
            builder.addParameter(ChuckNorrisAPIController.CATEGORY_PARAM_KEY, category);
        }

        logger.info("Getting a random fact");

        return execute(builder.build());

    }

    /**
     * Listing fact categories
     * @return - JSON from the API
     * @throws URISyntaxException
     * @throws IOException
     */
    public String categories() throws URISyntaxException, IOException {

        logger.info("Listing categories");

        return execute(
                new URIBuilder("https://api.chucknorris.io/jokes/categories")
                    .build()
        );

    }

    /**
     * Executes the actual GET request against the given URI
     * @param uri - obj containing path and params.
     * @return
     * @throws IOException
     */
    private String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .execute()
                .returnContent()
                .asString();
    }

}
