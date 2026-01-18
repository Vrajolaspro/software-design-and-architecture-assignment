package software.design.and.architecture.infrastructure.store;

import org.springframework.stereotype.Component;
import software.design.and.architecture.usecase.model.GameRecord;
import software.design.and.architecture.usecase.port.GameStore;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryGameStore implements GameStore {

    private final AtomicLong seq = new AtomicLong(1000);
    private final Map<String, GameRecord> store = new ConcurrentHashMap<>();

    @Override
    public String save(GameRecord record) {
        String id = record.id();
        if (id == null || id.isBlank()) {
            id = String.valueOf(seq.incrementAndGet());
        }
        store.put(id, new GameRecord(id, record.savedAt(), record.config(), record.rolls(), record.outcome()));
        return id;
    }

    @Override
    public Optional<GameRecord> load(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public java.util.List<String> listIds() {
        return store.keySet().stream()
                .sorted(Comparator.comparingLong(this::safeParseLong))
                .toList();
    }

    private long safeParseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return Long.MAX_VALUE;
        }
    }
}
