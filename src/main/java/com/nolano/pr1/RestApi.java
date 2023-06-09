/**
 *  pr1
 *
 *  Copyright (c) 2023 Antonino Nolano. Licensed under the MIT license, as
 * follows:
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package com.nolano.pr1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static spark.Spark.*;

public class RestApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApi.class);

    public void doMainLoop(final IEngine engine, String ipAddress, int port, int maxThreads) {

        // prepare serializer/deserializer
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Point.class, new Serializer());
        final Gson gson = gsonBuilder.create();

        // set ipAddress and port
        ipAddress(ipAddress);
        port(port);

        threadPool(maxThreads);

        // routes
        post("/point", (request, response) -> {
            try {
                engine.addPoint(gson.fromJson(request.body(), Point.class));
                response.status(200);
            } catch (JsonSyntaxException e) {
                LOGGER.error("bad request {}", request.body(), e);
                response.status(HTTP_BAD_REQUEST);
                return gson.toJson(new RestError(RestError.CODE_PAYLOAD, e.getMessage()));
            }
            return "";
        });

        get("/space", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return gson.toJson(engine.getPoints());
        });

        get("/lines/:npoints", (request, response) -> {
            try {
                response.status(200);
                response.type("application/json");
                return gson.toJson(engine.getLines(Integer.valueOf(request.params(":npoints"))));
            } catch (NumberFormatException e) {
                response.status(HTTP_BAD_REQUEST);
                return gson.toJson(new RestError(RestError.CODE_PAYLOAD, e.getMessage()));
            }
        });

        delete("/space", (request, response) -> {
            engine.clean();
            response.status(200);
            return "";
        });
    }

}
