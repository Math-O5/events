package com.event.backevents.api.model;

import com.event.backevents.api.assembler.UserAssembler;
import com.event.backevents.domain.model.User;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void userToUserDto() {
        User user = new User();
        user.setId(1L);
        user.setName("Daniel");

        UserAssembler userAssembler = new UserAssembler(new ModelMapper());

        UserDto userDto = userAssembler.toModel(user);

        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getId(), userDto.getId());
    }

}