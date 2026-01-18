package software.design.and.architecture.usecase.port;

import software.design.and.architecture.usecase.model.GameRecord;

import java.util.List;
import java.util.Optional;

public interface GameStore {
    String save(GameRecord record);
    Optional<GameRecord> load(String id);
    List<String> listIds();
}
