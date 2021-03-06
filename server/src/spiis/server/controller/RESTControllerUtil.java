package spiis.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class RESTControllerUtil {

    /**
     * Makes a ResponseObject for a successful HTTP POST
     * The status code is CREATED, which requires a Location: header pointing to the resource
     * A body containing the created resource can optionally be included
     *
     * @param object the optional body of the response, an object representing the resource
     * @param baseUrl the base url for the resource, e.g. "/users"
     * @param id the id of the new resource, can't be null
     * @return a ResponseEntity with the status code, Location header and body
     * @throws IllegalArgumentException if the Location URI is illegal
     * @throws NullPointerException if the id is null
     */
    public static <T> ResponseEntity<T> makePOSTResponse(@Nullable T object, String baseUrl, @Nullable Long id) {

        Assert.isTrue(baseUrl.startsWith("/"), "baseUrl must be relative to host");
        Assert.isTrue(!baseUrl.endsWith("/"), "baseUrl must be pretty (no trailing /)");
        Objects.requireNonNull(id);

        try {
            URI location = new URI(String.format("%s/%d", baseUrl, id));
            return ResponseEntity.created(location).body(object);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException();
        }
    }
}
