# Software Design and Architecture Assignment – Simple Frustration Console Simulation

This project has been developed to demonstrate the development of a console game with the use of Java 21, Spring and it follows a clean architecture style.

---

## Table of Contents
- [Overview](#overview)
- [How to Run](#how-to-run)
- [Features Implemented](#features-implemented)
    - [Basic Game](#basic-game)
    - [Functional Variations](#functional-variations)
    - [Advanced Features](#advanced-features)
- [Architecture](#architecture)
    - [Clean Architecture / Ports and Adapters](#clean-architecture--ports-and-adapters)
    - [Folder Structure](#folder-structure)
- [Key Design Patterns Used](#key-design-patterns-used)

---

## Overview

The goal of this simulation is to model how the board game works showcasing how the different variations can have an affect on the game outcome.

The simulation starts the game placing the correct positions players and board routes, each turn the player rolls a dice which then the input applied to the player movement. The console will provide the stats for each player movement, and at the end a winner will be announced. 

---

## How to Run

### Prerequisites
- **Java 21+**
- **Maven**

### Run using IntelliJ
- Run the main class:
    - `software.design.and.architecture.GameApplication`

### Run using Maven (terminal)
- Run tests:
    - `mvn test`
- Run the application:
    - `mvn spring-boot:run`

### Console usage
When the program starts you will see a menu:
- **1) Play (and save when finished)**
- **2) Replay a saved game**
- **q) Quit**

Play flow:
1. Choose number of players (2 or 4).
2. Choose dice mode (1 die or 2 dice total).
3. Choose rule variations:
    - exact end required?
    - forfeit on hit?
4. Enter roll number. Type `q` to quit/cancel.

Replay flow:
1. Program lists saved game ids.
2. Enter an id to replay the saved game using the recorded rolls.

### Running each Variation at a time
The game was developed in a way so that the user can choose the settings himself for his play, however for testing purposes and evidence we can start the game by deciding which variation we would like to play for this:
1. Navigate to the `ConsoleRunner.java`
2. Locate the boolean variable `TEST_SINGLE_CONFIG` - we can set this to true and false
   - true - We would like to decide which variation to play. 
   - false - The game will played with its flow.
3. Locate the method `testConfig` within this we can decide which type of variation we would like to run.

---

## Features Implemented

### Basic Game
The simulation meets the functional requirement:
- Players take turns until the game is won.
- Each turn the console prints:
    - current player
    - roll total
    - position before and after
    - running total of turns taken by that player
- End of game the console prints:
    - winning player
    - total turns taken by all players

### Functional Variations
The game supports all the variations that were specified in the specification, also allowing us to combine these variations

- **Single Die**
    - Implemented via `DiceMode.ONE_DIE (1-6)`
    - Still supports the default `DiceMode.TWO_DICE_TOTAL (2-12)`

- **Exact End Required**
    - Implemented via `ExactEndRule`
    - If a player would overshoot, the move is cancelled and their turn is forfeited

- **Forfeit on HIT**
    - Implemented via `ForfeitOnHitRule`
    - If a move would land on an occupied position, the move is cancelled and the player remains where they are

### Advanced Features
The following advanced features were implemented:

- **4 Player Mode**
    - Supported through `GameConfig.playerCount()`
    - Supported players: **Red, Green, Blue, Yellow**
    - Turn ordering for 4 players: `RED -> GREEN -> BLUE -> YELLOW`
    - Each player has:
        - a unique tail prefix (`R`, `G`, `B`, `Y`)
        - a unique home position label for display formatting

- **Game Save and Replay**
    - Completed games are saved into an in-memory store and assigned a unique id
    - Saved data includes:
        - game configuration
        - sequence of rolls
        - outcome
    - Replays are deterministic: the game engine is executed again using the stored roll sequence

- **Unit Testing**
    - JUnit tests were developed to the tests of the functionalities allow me to debug any issues occurred within the program

---

## Architecture

### Clean Architecture / Ports and Adapters
The code follows a Clean Architecture style:
- **Domain layer** contains the logic of the game specifying its models and rules
- **Use case layer** showcases game play and defines the interfaces for external dependencies.
- **Infrastructure layer** implements those interfaces using console I/O and an in-memory store.

### Folder Structure
High-level structure:

- `src/main/java/software/design/and/architecture/domain`
    - `model` – data structures
    - `rules` – rule strategies

- `src/main/java/software/design/and/architecture/usecase`
    - `port` – interfaces
    - `service` – use cases
    - `model` – snapshot and record data models

- `src/main/java/software/design/and/architecture/infrastructure`
    - `console` – console adapters
    - `dice` – dice adapters for recording/replay
    - `store` – in-memory adapter

---

## Key Design Patterns Used

### Strategy Pattern (Variations)
Variations are implemented as interchangeable strategies rather than if/else logic:
- **End rule strategy**
    - `EndRule` interface
    - `OvershootAllowedEndRule` (basic game)
    - `ExactEndRule` (exact end variation)

- **Hit rule strategy**
    - `HitRule` interface
    - `AllowHitRule` (basic game)
    - `ForfeitOnHitRule` (forfeit on hit variation)

**Why this pattern was used:**
This works because variations are swappable algorithms, adding a new rule does not require modifying the game engine

### Ports & Adapters (Hexagonal Architecture)
Ports are interfaces defined in the use case layer:
- `DiceRollSource`
- `GamePresenter`
- `GameStore`

Infrastructure implements these ports:
- Console adapters: `ConsoleDiceRollSource`, `ConsoleGamePresenter`
- Storage adapter: `InMemoryGameStore`

**Why this pattern was used:**
This pattern presents the use cases from depending on the console, enables replay and persistence without changing the logic

### Decorator (RecordingDiceRollSource)
`RecordingDiceRollSource` wraps another `DiceRollSource` and records the rolls.

**Why this pattern was used:**
We are able to add the recording behavior without changing the underlying dice implementation, it keeps the save feature separate and composable.