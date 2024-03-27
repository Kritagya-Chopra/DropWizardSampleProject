package com.assignment.core;

import com.assignment.dao.UserDao;
import com.assignment.db.UserEntity;

import com.assignment.dto.AuthenticateDto;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserEntity user;


    @InjectMocks
    private UserService service;

    @Mock
    private UserDao dao;


    private AuthenticateDto dto;


    @BeforeEach
    void beforeEach() {
        user = new UserEntity();
        user.setName("test");
        user.setEmail("testt@gmail.com");
        user.setPassword("12345678");
        user.setDob(LocalDate.now());
        user.setId(10L);
        user.setAddress("Kolkata");
        user.setCity("jjj");
        user.setPhoneno("hjkbfjkhbsf");
        user.setPhoneno("88987");

        dto = new AuthenticateDto();
        dto.setEmail("test@gmail.com");
        dto.setPassword("12345678");

    }


    @DisplayName("Registration Fails when Name is not Provided")
    @Test
    void testcreateUser_whenNameIsNotProvided_RegistrationFailed() {

        user.setName(null);

        Response response = service.createUser(user);

        assertEquals("Please Fill All the Required Details", response.getEntity(), "Response is different");
        assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus(), "Status Should be 400");


    }

    @DisplayName("Registration Fails when Email is not Provided")
    @Test
    void testcreateUser_whenEmailIsNotProvided_RegistrationFailed() {

        user.setEmail(null);

        Response response = service.createUser(user);

        assertEquals("Please Fill All the Required Details", response.getEntity(), "Response is different");
        assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus(), "Status Should be 400");

    }

    @DisplayName("Registration Fails when DOB is not Provided")
    @Test
    void testcreateUser_whenDOBIsNotProvided_RegistrationFailed() {

        user.setDob(null);

        Response response = service.createUser(user);

        assertEquals("Please Fill All the Required Details", response.getEntity(), "Response is different");
        assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus(), "Status Should be 400");
    }

    @DisplayName("Registration Fails when Password is not Provided")
    @Test
    void testcreateUser_whenPasswordIsNotProvided_RegistrationFailed() {

        user.setPassword("");

        Response response = service.createUser(user);

        assertEquals("Please Fill All the Required Details", response.getEntity(), "Response is different");
        assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus(), "Status Should be 400");
    }

    @DisplayName("Registration Fails when Duplicate Email is Provided")
    @Test
    void testcreateUser_whenDuplicateEmailProvided_RegistrationFailed() {
        when(dao.register(Mockito.any(UserEntity.class))).thenThrow(RuntimeException.class);

        Response response = service.createUser(user);

        assertEquals("Registration Failed", response.getEntity(), "Registration should have been failed");
        assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus(), "Status Should be 400");
    }

    @DisplayName("When User Correctly Provides All the Details then Registration is SuccessFull")
    @Test
    void testcreateUser_whenUserDetailsAreProvided_RegistrationSuccessFull() {
        when(dao.register(Mockito.any(UserEntity.class))).thenReturn(1);

        Response response = service.createUser(user);

        assertEquals("Successfully Registered", response.getEntity(), "Registration should have been SuccessFull");
        assertEquals(HttpStatus.CREATED_201, response.getStatus(), "Status Should be 201");
    }


    @DisplayName("Authentication - When Email is not in Proper Format")
    @Test
    void testvalidateuser_whenEmailIsNotInProperFormat_AuthenticationFailed() {
        dto.setEmail("abc");

        Response res = service.validateUser(dto);

        assertEquals("Invalid Email format", res.getEntity(), "Response should be Invalid Format");
        assertEquals(HttpStatus.BAD_REQUEST_400, res.getStatus(), "Status Should be 400");
    }

    @DisplayName("Authentication - When Incorrect Password Provided is Given")
    @Test
    void testvalidateuser_whenIncorrectPasswordIsGiven_AuthenticationFailed() {
        dto.setPassword("1234");

        when(dao.getUserByEmail(Mockito.any(String.class))).thenReturn(user);

        Response res = service.validateUser(dto);

        assertEquals("Authentication Failed", res.getEntity(), "Response Should be Authentication Failed");
        assertEquals(HttpStatus.UNAUTHORIZED_401, res.getStatus(), "Status Should be 401");

    }


    @DisplayName("Authentication - When Correct Credentials are Provided")
    @Test
    void testvalidateuser_whenCorrectCredentialsAreProvided_AuthenticationSuccessFull() {
        when(dao.getUserByEmail(Mockito.any(String.class))).thenReturn(user);

        Response res = service.validateUser(dto);

        assertEquals(user, res.getEntity(), "User Should have been Returned");
        assertEquals(HttpStatus.OK_200, res.getStatus(), "Status Should be 200");

    }

    @DisplayName("Get User By Id When Given Id is Invalid")
    @Test
    void testgetUserById_whenGivenIdIsIncorrect_UserNotFound() {
        user.setId(-10l);
        when(dao.getUserById(user.getId())).thenReturn(null);

        Response response = service.getUserById(user.getId());

        assertEquals("User Does Not Exist", response.getEntity(), "User Should not Exist");
        assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus(), "Status should be 404");

    }

    @DisplayName("Get User By Id When Given Id is Valid")
    @Test
    void testgetUserById_whenGivenIdIsCorrect_UserFound() {

        when(dao.getUserById(user.getId())).thenReturn(user);

        Response response = service.getUserById(user.getId());

        assertEquals(user, response.getEntity(), "User Should Exist");
        assertEquals(HttpStatus.OK_200, response.getStatus(), "Status should be 200");

    }

    @DisplayName("Delete User By Id- When Given Id is Invalid")
    @Test
    void testdeleteUserById_whenGivenIdIsIncorrect_UserNotFound() {
        user.setId(-10l);
        when(dao.getUserById(user.getId())).thenReturn(null);

        Response response = service.deleteUserById(user.getId());

        assertEquals("User Does Not Exist", response.getEntity(), "User Should not Exist");
        assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus(), "Status should be 404");

    }


    @DisplayName("Delete User By Id - When Given Id is Valid")
    @Test
    void testdeleteUserById_whenGivenIdIsCorrect_UserDeleted() {

        when(dao.getUserById(user.getId())).thenReturn(user);
        when(dao.deleteUserbyId(user.getId())).thenReturn(1);

        Response response = service.deleteUserById(user.getId());

        assertEquals("Successfully Deleted", response.getEntity(), "Response Should be - Successfully Deleted");
        assertEquals(HttpStatus.OK_200, response.getStatus(), "Status should be 200");

    }

    @DisplayName("Update User - When Given Id is Not Valid")
    @Test
    void testupdateUser_whenGivenIdForUpdateIsNotValid_UserDoesNotExist() {
        user.setId(-10l);
        when(dao.getUserById(user.getId())).thenReturn(null);

        Response response = service.updateUser(user.getId(), user);

        assertEquals("User Does Not Exist", response.getEntity(), "User Should Not Exist");
        assertEquals(HttpStatus.NOT_FOUND_404,response.getStatus(),"Status should be 404");

    }

    @DisplayName("Update User - When ID is Valid but Name is not Given or Given name is in Invalid Format")
    @Test
    void testupdateUser_whenNameIsNotProvided_NameCannotBeEmpty() {
        user.setName(null);
        Response response = service.updateUser(user.getId(), user);

        assertEquals("Name Cannot be Empty", response.getEntity(), "Response Should be - Name Cannot be Empty");
        assertEquals(HttpStatus.BAD_REQUEST_400,response.getStatus(),"Status should be 400");

    }

    @DisplayName("Update User - When DOB is Empty")
    @Test
    void testupdateUser_whenDOBIsNotProvided_DOBCannotBeEmpty() {
        user.setDob(null);
        Response response = service.updateUser(user.getId(), user);

        assertEquals("DOB Cannot be Empty", response.getEntity(), "Response Should be - DOB Cannot be Empty");
        assertEquals(HttpStatus.BAD_REQUEST_400,response.getStatus(),"Status should be 400");
    }

    @DisplayName("Update User - All Details are Filled")
    @Test
    void testupdateUser_whenAllDetailsAreFilled_SuccessFullUpdate() {

        when(dao.getUserById(Mockito.any(Long.class))).thenReturn(user);
        when(dao.updateUser(Mockito.any(UserEntity.class))).thenReturn(1);
        Response response = service.updateUser(user.getId(), user);

        assertEquals("Successfully Updated", response.getEntity(), "Response Should be - Successfully Updated");
        assertEquals(HttpStatus.OK_200,response.getStatus(),"Status should be 200");
    }




}