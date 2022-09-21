package com.voto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }
    @GET
    @Path("/v2")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello2() {
        return "Hello from RESTEasy Reactive2";
    }
}