package software.design.and.architecture.usecase.model;

import software.design.and.architecture.domain.model.PlayerColor;

public record GameOutcome(PlayerColor winner, int winnerTurns, int totalTurns, boolean completed) {
    public static GameOutcome notCompleted() {
        return new GameOutcome(null, 0, 0, false);
    }
}
