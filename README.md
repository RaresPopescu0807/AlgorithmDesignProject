# THE BUG BUSTERS

A project where we worked in teams of 3 to create a chess engine.
We chose to work in Java and use OOP.
Part of the points for this assignment where given after competing versus the engines of the other teams, around 40 or 50. We finished 4th !!!

## 1. Compile, Run and Clean
```
make
make run
make clean
```

Sources can be found under the [src](src) folder. Class files will be generated in the same location as their respective
source file.

## 2. Project Structure
The code is split into three main parts:
* logic
* state
* xboard

### 2.1. Logic
The [logic](src/thebugbusters/pa/project/logic) packet holds the move making code.
  
### 2.2. State
The [state](src/thebugbusters/pa/project/state) packet holds the game state model (board, engine modes).
  
### 2.3. XBoard
The [xboard](src/thebugbusters/pa/project/xboard) packet holds the implementation of XBoard commands, along with input
and output utilities.

## 3. Implementation Details
### 3.1. Stage 1
At the beginning of each game, the [DummyMoveFinder](src/thebugbusters/pa/project/logic/move/DummyMoveFinder.java) picks
a random pawn and advances it until it either gets blocked or captured by an opponent's piece. Then, the game engine
resigns.

*Note*: capturing opponent's pieces is not implemented.

*Note*: the game engine does not check whether the King is safe before advancing the chosen pawn. If the opponent checks
the game engine, it will attempt to advance the pawn and therefore issue an illegal move. This also leads to another
issue when starting a new game immediately after and reusing the engine. Because the engine can't tell the game has
ended abruptly (due to the illegal move), it will notice the pawn chosen in the previous game is not where it left it
(due to the board resetting upon starting a new game) and therefore resign on its first move.

### 3.2. Stage 2
The chess engine, at this stage, can handle any legal move XBoard / WinBoard throws at it. It finds legal moves until
the game is over and can be reused for other games with the exception of situations where the game ends due to time (on
depth settings >4 or very slow hardware).

In our tests, out of 30 rounds against FairyMax 4.8 with depth 2, we found 10 losses, 8 victories and 12 draws.

For this stage, we implemented [MinimaxMoveFinder](src/thebugbusters/pa/project/logic/move/MinimaxMoveFinder.java). It
is a standard minimax algorithm with no optimizations.

In charge of finding legal moves for all pieces are:
* [BishopOptions](src/thebugbusters/pa/project/logic/move/BishopOptions.java)
* [KingOptions](src/thebugbusters/pa/project/logic/move/KingOptions.java)
* [KnightOptions](src/thebugbusters/pa/project/logic/move/KnightOptions.java)
* [PawnOptions](src/thebugbusters/pa/project/logic/move/PawnOptions.java)
* [QueenOptions](src/thebugbusters/pa/project/logic/move/QueenOptions.java)
* [RookOptions](src/thebugbusters/pa/project/logic/move/RookOptions.java)

Board evaluation is handled by [BoardEvaluator](src/thebugbusters/pa/project/logic/evaluation/BoardEvaluator.java) with
the help of other classes in the [evaluation](src/thebugbusters/pa/project/logic/evaluation) package.

### 3.3. Stage 3
The minimax algorithm has been enhanced with alpha-beta pruning:
[AlphaBetaMoveFinder](src/thebugbusters/pa/project/logic/move/AlphaBetaMoveFinder.java). Piece evaluation was slightly
tweaked - but apparently not enough :(. This allows for bigger seach depth than before, being mindful of available time.

## 4. Team Members' Responsibilities
### 4.1. Stage 1
* Rares-Bogdan Popescu - implementation of XBoard commands, game logic and game state.
* Mihai-Valentin Bita - implementation of XBoard commands and code design.
* Dan-Dumitru Tipa - implementation of XBoard commands and documentation.

### 4.2. Stage 2
* Rares-Bogdan Popescu - implementation of piece moves, tweaking and debugging.
* Mihai-Valentin Bita - evaluation and minimax, tweaking and debugging.
* Dan-Dumitru Tipa - evaluation and minimax, tweaking and debugging.

### 4.3 Stage 3
* Rares-Bogdan Popescu - tweaking and debugging.
* Mihai-Valentin Bita - tweaking and debugging, alpha-beta.
* Dan-Dumitru Tipa - tweaking and debugging, alpha-beta.