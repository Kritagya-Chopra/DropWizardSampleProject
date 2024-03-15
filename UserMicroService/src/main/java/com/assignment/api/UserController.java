package com.assignment.api;


import com.assignment.authenticateDto;
import com.assignment.core.UserService;
import com.assignment.db.UserEntity;
import com.assignment.client.*;
import io.dropwizard.jersey.PATCH;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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




//    @POST
//    @Path("/register")
//    public Representation registerUser(@Valid UserEntity user)
//    {
//        return userservice.createUser(user);
//    }

    @PermitAll
    @POST
    @Path("/register")
    public Response registerUser(@Valid UserEntity user)
    {
        return userservice.createUser(user);
    }




//    @PermitAll
//    @POST
//    @Path("/authenticate")
//    public Representation<?> validateUser(@Valid UserEntity user)
//    {
//       return userservice.validateUser(user);
//
//    }


    @PermitAll
    @POST
    @Path("/authenticate")
    public Response validateUser(@Valid UserEntity user)
    {
        return userservice.validateUser(user);

    }



//    @GET
//    @Path("/get/{id}")
//    public Representation<?> getUserbyId(@PathParam("id") Long id)
//    {
//        System.out.println("Received request for user with ID: " + id);
//        return  userservice.getUserById(id);
//
//    }

    @GET
    @Path("/get/{id}")
    public Response getUserById(@PathParam("id") Long id)
    {
        System.out.println("Received request for user with ID: " + id);
        return  userservice.getUserById(id);

    }

//    @DELETE
//    @Path("{id}")
//    public Representation<?> deleteUser(@PathParam("id") Long id)
//    {
//
//        return userservice.deleteUserById(id);
//    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id)
    {

        return userservice.deleteUserById(id);
    }


//    @PATCH
//    @Path("/update/{id}")
//    public Representation<?> updateUser(@PathParam("id") Long id,@Valid UserEntity user)
//    {
//        return userservice.updateUser(id,user);
//    }

    @PATCH
    @Path("/update/{id}")
    public Response updateUser(@PathParam("id") Long id,@Valid UserEntity user)
    {
        return userservice.updateUser(id,user);
    }







}
