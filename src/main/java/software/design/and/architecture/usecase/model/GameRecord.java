package software.design.and.architecture.usecase.model;

import java.time.Instant;
import java.util.List;

public record GameRecord(String id, Instant savedAt, GameConfigSnapshot config, List<Integer> rolls, GameOutcome outcome) {
}
