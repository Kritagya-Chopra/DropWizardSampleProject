package com.assignment.core;


import com.assignment.dao.UserDao;
import com.assignment.db.UserEntity;
import com.assignment.dto.AuthenticateDto;
import org.eclipse.jetty.http.HttpStatus;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.core.Response;

@Service
public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public Response createUser(UserEntity user) {

        if (user.getName() == null || user.getDob() == null || user.getEmail() == null || user.getPassword() == null)
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Please Fill All the Required Details").build();

        if (user.getName() == "" || user.getEmail() == "" || user.getPassword() == "")
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Please Fill All the Required Details").build();

        try {
            userDao.register(user);
            return Response.status(HttpStatus.CREATED_201).entity("Successfully Registered").build();

        } catch (Exception e) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Registration Failed").build();
        }

    }


    public Response validateUser(AuthenticateDto authenticateDto) {

        if (!authenticateDto.getEmail().contains("@") || !authenticateDto.getEmail().contains(".com"))
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Invalid Email format").build();


        UserEntity dbUser = userDao.getUserByEmail(authenticateDto.getEmail());

        System.out.println(dbUser.getEmail() + " " + dbUser.getPassword() + " " + authenticateDto.getPassword());

        if (!dbUser.getPassword().equals(authenticateDto.getPassword())) {
            return Response.status(HttpStatus.UNAUTHORIZED_401).entity("Authentication Failed").build();
        } else {
//            String data= dbUser.getId()+"-"+dbUser.getEmail();
//
//            String token=TokenEncyption.encrypt(data);
//            System.out.println(TokenEncyption.decrypt(token));
            return Response.ok(dbUser).build();
        }


    }


    public Response getUserById(Long id) {

        System.out.println(id);
        UserEntity user = userDao.getUserById(id);

        if (user != null) {
            return Response.ok(user).build();

        } else
            return Response.status(HttpStatus.NOT_FOUND_404).entity("User Does Not Exist").build();

    }


    public Response deleteUserById(Long id) {

        UserEntity user = userDao.getUserById(id);

        if (user != null) {
            userDao.deleteUserbyId(id);
            return Response.ok("Successfully Deleted").build();
        }
        return Response.status(HttpStatus.NOT_FOUND_404).entity("User Does Not Exist").build();


    }


    public Response updateUser(Long id, UserEntity user) {

        if(user.getName() =="" || user.getName()==null)
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Name Cannot be Empty").build();
        else if (user.getDob()==null) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("DOB Cannot be Empty").build();
        }


        UserEntity u = userDao.getUserById(id);

        if (u != null) {
            u.setName(user.getName());
            u.setDob(u.getDob());
            userDao.updateUser(u);
            return Response.ok("Successfully Updated").build();
        } else
            return Response.status(HttpStatus.NOT_FOUND_404).entity("User Does Not Exist").build();



    }
}
