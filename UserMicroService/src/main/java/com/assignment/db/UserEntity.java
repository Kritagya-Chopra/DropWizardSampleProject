package com.assignment.db;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

//
//    private  Long id;
//
//    @NotNull
//    @Length(min=3, max = 45)
//    private  String name;
//
//    @NotNull
//    private String email;
//
//    @NotNull
//    @Length(min=8, max = 20)
//    private String password;
//
//    @NotNull
//    private LocalDate dob;
//
//    private String address;

    // private String city;
//
//    private int pincode;
//
//    @Length(min = 10)
//    private String phoneno;



    private  Long id;



    private  String name;


    private String email;


    private String password;

    private String city;

    private LocalDate dob;

    private String address;

    private int pincode;


    private String phoneno;


}
