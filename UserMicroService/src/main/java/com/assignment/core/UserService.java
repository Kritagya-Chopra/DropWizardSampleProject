package com.assignment.core;


import com.assignment.authenticateDto;
import com.assignment.client.Representation;
import com.assignment.dao.UserDao;
import com.assignment.db.UserEntity;
import org.eclipse.jetty.http.HttpStatus;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.core.Response;

@Service
public class UserService {
private UserDao userDao;

    public UserService(UserDao userDao)
    {
        this.userDao=userDao;
    }


//    public Representation createUser(UserEntity user) {
//        int rows=userDao.register(user);
//
//        if(rows!=0)
//        {
//            return new Representation<String>(HttpStatus.CREATED_201,"Successfully Registered");
//        }
//        else
//            return new Representation<String>(HttpStatus.BAD_REQUEST_400,"Registration Failed");
//    }

    public Response createUser(UserEntity user) {

        try
        {
            int rows=userDao.register(user);

            if(rows!=0)
            {
                return Response.status(HttpStatus.CREATED_201).entity("Successfully Registered").build();
            }
            else
                return Response.status(HttpStatus.BAD_REQUEST_400).entity("Registration Failed").build();
        }
        catch (Exception e)
        {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Registration Failed").build();
        }

    }

//    public Representation<?> validateUser(UserEntity user) {
//
//        UserEntity dbUser = userDao.getUserByEmail(user.getEmail());
//
//        System.out.println(dbUser.getEmail() + " " + dbUser.getPassword() +" " + user.getPassword());
//
//        if(!dbUser.getPassword().equals(user.getPassword()))
//        {
//            return  new Representation<String>(HttpStatus.UNAUTHORIZED_401,"Authentication Failed");
//        }
//        else
//            return new Representation<Long>(HttpStatus.OK_200,dbUser.getId());
//
//    }

    public Response validateUser(UserEntity user) {

        UserEntity dbUser = userDao.getUserByEmail(user.getEmail());

        System.out.println(dbUser.getEmail() + " " + dbUser.getPassword() +" " + user.getPassword());

        if(!dbUser.getPassword().equals(user.getPassword()))
        {
            return  Response.status(HttpStatus.UNAUTHORIZED_401).entity("Authentication Failed").build();
        }
        else
        {
            String data= dbUser.getId()+"-"+dbUser.getEmail();

            String token=TokenEncyption.encrypt(data);
            System.out.println(TokenEncyption.decrypt(token));
            return Response.ok(token).build();
        }
//            return Response.ok(dbUser.getId()).build();

    }

//    public Representation<?> getUserById(Long id) {
//
//        System.out.println(id);
//        UserEntity user= userDao.getUserById(id);
//
//        if(user!=null)
//        {
//            return  new Representation<UserEntity>(HttpStatus.OK_200,user);
//
//        }
//        else
//            return  new Representation<String>(HttpStatus.BAD_REQUEST_400,"User Does Not Exist");
//
//    }

    public Response getUserById(Long id) {

        System.out.println(id);
        UserEntity user= userDao.getUserById(id);

        if(user!=null)
        {
            return  Response.ok(user).build();

        }
        else
            return  Response.status(HttpStatus.BAD_REQUEST_400).entity("User Does Not Exist"). build();

    }

//    public Representation<?> deleteUserById(Long id) {
//
//        int rows= userDao.deleteUserbyId(id);
//
//        if(rows!=0)
//        {
//            return new Representation<String>(HttpStatus.OK_200,"Successfully Deleted");
//        }
//        else
//            return new Representation<String>(HttpStatus.BAD_REQUEST_400,"Deletion Failed");
//    }

    public Response deleteUserById(Long id) {

        int rows= userDao.deleteUserbyId(id);

        if(rows!=0)
        {
            return  Response.ok("Successfully Deleted").build();
        }
        else
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Deletion Failed").build() ;
    }

//    public Representation<?> updateUser(Long id, UserEntity user) {
//
//        UserEntity u= userDao.getUserById(id);
//
//        if(u!=null)
//        {
//            u.setName(user.getName());
//            u.setDob(u.getDob());
//
//            int rows=userDao.updateUser(u);
//            if(rows!=0)
//                return new Representation<String>(HttpStatus.OK_200,"Successfully Updated");
//            else
//                return new Representation<String>(HttpStatus.BAD_REQUEST_400,"Update Failed");
//        }
//       return new Representation<String>(HttpStatus.BAD_REQUEST_400,"User Does Not Exist");
//
//    }

    public Response updateUser(Long id, UserEntity user) {

        UserEntity u= userDao.getUserById(id);

        if(u!=null)
        {
            u.setName(user.getName());
            u.setDob(u.getDob());

            int rows=userDao.updateUser(u);
            if(rows!=0)
                return  Response.ok(  "Successfully Updated").build();
            else
                return Response.status(HttpStatus.BAD_REQUEST_400).entity(  "Update Failed").build();
        }
        return Response.status(HttpStatus.BAD_REQUEST_400).entity(  "User Does not Exist").build();

    }
}
