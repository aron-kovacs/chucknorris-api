package com.codecool.chucknorris;

import com.codecool.chucknorris.controller.ChuckNorrisAPIController;
import com.codecool.chucknorris.service.APIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ResponseTransformer;

import java.net.URISyntaxException;

import static spark.Spark.*;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private ChuckNorrisAPIController controller;

    public static void main(String[] args) {

        setup(args);

        Application application = new Application();

        application.controller = new ChuckNorrisAPIController(
                APIService.getInstance()
        );

        // --- MAPPINGS ---
        get("/api/random", application.controller::random);
        get("/api/categories", application.controller::categories);

        // --- EXCEPTION HANDLING ---
        exception(URISyntaxException.class, (exception, request, response) -> {
            response.status(500);
            response.body(String.format("URI building error, maybe wrong format? : %s", exception.getMessage()));
            logger.error("Error while processing request", exception);
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body(String.format("Unexpected error occurred: %s", exception.getMessage()));
            logger.error("Error while processing request", exception);
        });
    }

    /**
     * Setting up port
     * @param args - app args
     */
    private static void setup(String[] args){
        if(args == null || args.length == 0){
            logger.error("Port must be given as the first argument.");
            System.exit(-1);
        }

        try {
            port(Integer.parseInt(args[0]));
        } catch (NumberFormatException e){
            logger.error("Invalid port given '{}', it should be number.", args[0]);
            System.exit(-1);
        }
    }

}
