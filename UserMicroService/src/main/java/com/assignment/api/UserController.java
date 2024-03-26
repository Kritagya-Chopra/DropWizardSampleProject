package com.assignment.api;


import com.assignment.core.UserService;
import com.assignment.db.UserEntity;
import com.assignment.dto.AuthenticateDto;
import io.dropwizard.jersey.PATCH;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

     private final UserService userservice;

     public UserController(UserService userService)
     {
         this.userservice=userService;
     }


    @PermitAll
    @POST
    @Path("/register")
    public Response registerUser( UserEntity user)
    {
        return userservice.createUser(user);
    }


    @PermitAll
    @POST
    @Path("/authenticate")
    public Response validateUser(AuthenticateDto authenticateDto)
    {
        return userservice.validateUser(authenticateDto);

    }


    @GET
    @Path("/get/{id}")
    public Response getUserById(@PathParam("id") Long id)
    {
        System.out.println("Received request for user with ID: " + id);
        return  userservice.getUserById(id);

    }



    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id)
    {

        return userservice.deleteUserById(id);
    }


    @PATCH
    @Path("/update/{id}")
    public Response updateUser(@PathParam("id") Long id,@Valid UserEntity user)
    {
        return userservice.updateUser(id,user);
    }







}
