package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.Entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;



public class TrainService {
    private static final String TRAINS_PATH = "app/src/main/java/ticket/booking/localDB/trains.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Train> trainList;

    public TrainService() throws IOException {
        this.trainList =  loadTrains();
    }

    public List<Train> loadTrains() throws IOException {
        File trains = new File(TRAINS_PATH);
        return objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String src , String dst){
        return trainList.stream().filter(train -> validTrain(train , src , dst)).toList();
    }

    public Boolean validTrain(Train train , String src , String dst){
        List<String> stationOrder = train.getStations();

        int sourceIndex = stationOrder.indexOf(src.toLowerCase());
        int destIndex = stationOrder.indexOf(dst.toLowerCase() );

        return sourceIndex != -1 && destIndex != -1 && sourceIndex<destIndex;
    }

}
