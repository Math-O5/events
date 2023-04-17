package com.event.backevents.domain.service;

import com.event.backevents.api.assembler.UserAssembler;
import com.event.backevents.api.controllers.UserController;
import com.event.backevents.common.googleGeoLocation.ApiKeyConfig;
import com.event.backevents.common.googleGeoLocation.GeoCoding;
import com.event.backevents.common.googleGeoLocation.GeoLocationServiceImpl;
import com.event.backevents.domain.model.Location;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogoUserServiceTest {

    public static final String NAME = "name";
//    @Mock
//    private UserController userController;

    @InjectMocks
    private CatalogoUserService catalogoUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserAssembler userAssembler;

    @Mock
    private GeoLocationServiceImpl geoLocationService;

    Location location;
    User returnUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.initMocks(this);

        location = Location.builder().number(0).state("Sao Paulo")
                .street("Vila").postalCode("0").city("Carapicuiba").build();

        returnUser = User.builder().id(1L).location(location).name(NAME).cnpj("1727373").build();
    }

    @Test
    void findAll() {

        List<User> returnUserList = List.of(
                User.builder().id(1L).location(location).name("ab").cnpj("1727373").build(),
                User.builder().id(2L).location(location).name("abc").cnpj("1727373").build()
        );

        when(userRepository.findAll()).thenReturn(returnUserList);

        List<User> users = this.userRepository.findAll();

        assertNotNull(users);
        verify(userRepository, times(1)).findAll();
        assertEquals(returnUserList.size(), users.size());

    }

    @Test
    void findById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnUser));

        User user = userRepository.findById(1L).get();

        assertNotNull(user);
    }

    @Test
    void add() {
        User newUser = User.builder().id(1L).name("name2").build();

        // when(geoLocationService.getGeoCodingForLoc(any())).thenReturn(Optional.of(location));
        when(userRepository.save(returnUser)).thenReturn(returnUser);

        User user = userRepository.save(returnUser);
//User user = catalogoUserService.add(returnUser);

        assertNotNull(user);

    }
}