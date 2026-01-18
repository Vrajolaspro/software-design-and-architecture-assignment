package software.design.and.architecture.usecase.port;

import software.design.and.architecture.domain.model.PlayerColor;

import java.util.OptionalInt;

public interface DiceRollSource {
    OptionalInt nextRoll(PlayerColor currentPlayer);
}