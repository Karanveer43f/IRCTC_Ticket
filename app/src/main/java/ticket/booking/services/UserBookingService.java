package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.Entities.Ticket;
import ticket.booking.Entities.Train;
import ticket.booking.Entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDB/users.json";

    private List<User> userList;

    private final ObjectMapper objectMapper = new ObjectMapper();
    TrainService trainService;

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
        trainService = new TrainService();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    public void loadUsers() throws IOException {
        File users = new File(USERS_PATH);
        this.userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword() , user1.getHashedPassword());
        }).findFirst();

        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return true;
        }catch (IOException e){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile , userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId){

        Optional<Ticket> ticketToBeDeleted = user.getTicketsBooked()
                .stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId))
                .findFirst();
        ticketToBeDeleted.ifPresent(
                ticket -> user.getTicketsBooked().remove(ticket)
        );

        try{
            saveUserListToFile();
        }catch (IOException exception){
            System.out.println("Error while writing to file: " + exception);
        }

        return true;

    }

    public List<Ticket> fetchBookings(){
        return user.getTicketsBooked();
    }

    public List<Train> getTrains(String source, String dest) {
        return trainService.searchTrains(source , dest);
    }
}
