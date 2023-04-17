package com.event.backevents.api.controller;

import com.event.backevents.api.assembler.UserAssembler;
import com.event.backevents.api.controllers.UserController;
import com.event.backevents.api.model.EventDto;
import com.event.backevents.api.model.UserDto;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Location;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.UserRepository;
import com.event.backevents.domain.service.CatalogoUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



//@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    public static final String NAME = "name";
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserAssembler userAssembler;

    @Mock
    private CatalogoUserService catalogoUserService;

    Location location;
    User returnUser;
    Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        location = Location.builder().number(0).state("Sao Paulo")
                .street("Vila").postalCode("0").city("Carapicuiba").build();

        returnUser = User.builder().location(location).name("Julius").cnpj("1727373").build();

        event = Event.builder().id(1L).user(returnUser).build();
    }
//    @BeforeEach
//    public void setup() {
//        RestAssuredMockMvc.standaloneSetup(this.userController);
//    }

//    @Test
//    public void createdOk_createUser () throws Exception {
//
//        User user = new User();
//
//        user.setName("Jorge Aragão");
//
//        given().accept(ContentType.JSON)
//                .when()
//                .post("/users")
//                .body();
//    }

    @Test
    public void createOk_createUser () throws Exception {

//        Event event = new Event();
//
//        event.setId(1L);
//        event.setName("Aulas judô");
//        event.setRate(0f);
//        event.setCodeName("Judo");
//        event.setIsActive(true);
//        event.setDescricao("Aulas judô");

        User user = User.builder().id(1L).location(location).name(NAME).cnpj("1727373").build();

        when(catalogoUserService.findById(1L)).thenReturn(user);

        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(user);

        final MockMvc mvcMock = MockMvcBuilders.standaloneSetup(this.userController).build();

        final MvcResult mvcResult = mvcMock
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isCreated())
                .andReturn();

        ModelMapper modelMapper = new ModelMapper();
        final UserDto userDtoResult = modelMapper.map(mvcResult.getResponse(), UserDto.class);
//
        assertEquals(user.getId(), userDtoResult.getId());
        assertEquals(user.getName(), userDtoResult.getName());
//        assertEquals(user.getCnpj(), userDtoResu);

    }

    @Test
    public void retrieveOk_getUser () throws Exception {

        when(catalogoUserService.findAllUsers()).thenReturn(List.of(new User(), new User()));

        final MockMvc mvcMock = MockMvcBuilders.standaloneSetup(this.userController).build();

        final MvcResult mvcResult = mvcMock
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

//        given().accept(ContentType.JSON)
//                .when()
//                .get("/users")
//                .then()
//                .statusCode(HttpStatus.OK.value());

    }

//    @Test
//    public void retrieveOk_getUserById () throws Exception {
//
//        when(catalogoUserService.findById(1L)).thenReturn(new User());
//
//        given().accept(ContentType.JSON)
//                .when()
//                .get("/users/{userId}")
//                .then()
//                .statusCode(HttpStatus.NOT_FOUND.value());
//
//    }
}
