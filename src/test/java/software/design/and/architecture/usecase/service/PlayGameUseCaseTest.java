package software.design.and.architecture.usecase.service;

import org.junit.jupiter.api.Test;
import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.domain.model.MoveResult;
import software.design.and.architecture.domain.model.PlayerColor;
import software.design.and.architecture.usecase.port.DiceRollSource;
import software.design.and.architecture.usecase.port.GamePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class PlayGameUseCaseTest {

    @Test
    void basicScenarioBlueWinsIn2TurnsTotalTurns4() {
        GameConfig config = GameConfig.basicTwoPlayer();
        FakeDice dice = new FakeDice(List.of(12, 12, 7, 8));
        CapturingPresenter presenter = new CapturingPresenter();
        new PlayGameUseCase().playToCompletion(config, dice, presenter);
        assertEquals(PlayerColor.BLUE, presenter.winner);
        assertEquals(2, presenter.winnerTurns);
        assertEquals(4, presenter.totalTurns);
        assertFalse(presenter.turns.isEmpty());
    }

    @Test
    void exactEndOvershootMeansNoMoveForCurrentPlayer() {
        GameConfig config = GameConfig.exactEndTwoPlayer();
        FakeDice dice = new FakeDice(List.of(12, 2, 9, 2, 2, 2));
        CapturingPresenter presenter = new CapturingPresenter();
        new PlayGameUseCase().playToCompletion(config, dice, presenter);
        MoveResult redTurn2 = presenter.turns.stream()
                .filter(t -> t.player() == PlayerColor.RED && t.roll() == 9)
                .findFirst()
                .orElseThrow();
        assertTrue(redTurn2.overshoot());
        assertFalse(redTurn2.moved());
        assertEquals(redTurn2.from(), redTurn2.to());
    }

    @Test
    void forfeitOnHitPreventsMovementWhenDestinationOccupied() {
        GameConfig config = GameConfig.exactEndForfeitOnHitTwoPlayer();
        FakeDice dice = new FakeDice(List.of(8, 2, 3, 12, 9, 6));
        CapturingPresenter presenter = new CapturingPresenter();
        new PlayGameUseCase().playToCompletion(config, dice, presenter);
        MoveResult redHitTurn = presenter.turns.stream()
                .filter(t -> t.player() == PlayerColor.RED && t.roll() == 3)
                .findFirst()
                .orElseThrow();
        assertTrue(redHitTurn.hit());
        assertTrue(redHitTurn.forfeitedOnHit());
        assertFalse(redHitTurn.moved());
        assertEquals(redHitTurn.from(), redHitTurn.to());
        assertEquals("9", redHitTurn.from().label());
        assertEquals("12", redHitTurn.attemptedTo().label());
    }

    @Test
    void gameEndsWhenNoMoreRollsNoWinnerIsDeclared() {
        GameConfig config = GameConfig.basicTwoPlayer();
        FakeDice dice = new FakeDice(List.of(12)); // one roll then end
        CapturingPresenter presenter = new CapturingPresenter();
        new PlayGameUseCase().playToCompletion(config, dice, presenter);
        assertNull(presenter.winner);
        assertTrue(presenter.messages.stream().anyMatch(m -> m.contains("Game ended (no more rolls).")));
    }

    private static final class FakeDice implements DiceRollSource {
        private final List<Integer> rolls;
        private int idx = 0;

        private FakeDice(List<Integer> rolls) {
            this.rolls = rolls;
        }

        @Override
        public OptionalInt nextRoll(GameConfig config, PlayerColor currentPlayer) {
            if (idx >= rolls.size()) {
                return OptionalInt.empty();
            }
            return OptionalInt.of(rolls.get(idx++));
        }
    }

    private static final class CapturingPresenter implements GamePresenter {
        private final List<MoveResult> turns = new ArrayList<>();
        private final List<String> messages = new ArrayList<>();
        private PlayerColor winner;
        private int winnerTurns;
        private int totalTurns;

        @Override
        public void showBanner(GameConfig config) {
        }

        @Override
        public void showTurn(MoveResult move, int playerTurnCount) {
            turns.add(move);
        }

        @Override
        public void showWinner(PlayerColor winner, int winnerTurns, int totalTurns) {
            this.winner = winner;
            this.winnerTurns = winnerTurns;
            this.totalTurns = totalTurns;
        }

        @Override
        public void showMessage(String message) {
            messages.add(message);
        }
    }
}
