package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.Entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private final User user;
    private static final String USERS_PATH = "../localDB/users.json";

    private List<User> userList;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User user) throws IOException {
        this.user = user;
        File users = new File(USERS_PATH);

        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

//    public Boolean loginUser(){
//        Optional<User> foundUser = userList.stream().filter(user1 -> {return user1.getName().equals()})
//    }

}
