package com.event.backevents;

import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

// import org.junit.Assert;

public class UserTest {

    @Test
    public void canCreateEvent() {
        User user = new User();

        Event event = user.createNewEvent(new Event());


    }

}
