package com.event.backevents.api.assembler;

import com.event.backevents.api.model.UserDto;
import com.event.backevents.api.model.input.PublisherInput;
import com.event.backevents.domain.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class UserAssembler {
    private ModelMapper modelMapper;

    public UserDto toModel(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> toCollectionModel(List<User> users) {
        return users.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public UserDto toEntity(PublisherInput publisherInput) {
        return modelMapper.map(publisherInput, UserDto.class);
    }
}
