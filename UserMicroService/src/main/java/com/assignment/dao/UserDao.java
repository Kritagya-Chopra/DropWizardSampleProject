package com.assignment.dao;

import com.assignment.db.UserEntity;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao extends SqlObject {



    @SqlUpdate("insert into Users values (default,:name,:email,:password,:dob,:address,:city,:pincode,:phoneno)")
    public int register(@BindBean UserEntity user);

    @SqlQuery("select * from Users where email=:email")
    public UserEntity getUserByEmail(@Bind("email") String email);

    @SqlQuery("select *  from Users where id=:id")
    public UserEntity getUserById(@Bind("id") Long id);

    @SqlUpdate("delete from Users where id=:id ")
    public int deleteUserbyId(@Bind("id") Long id) ;


    @SqlUpdate("update Users set name=:name where id=:id")
    int updateUser(@BindBean UserEntity u);
}
