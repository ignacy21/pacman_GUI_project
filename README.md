# PACMAN

Aplikacja pacman jest wiernym odwzorowaniem gry Pacman. Powstała jako projekt na studia z przedmiotu Java GUI.

## Spis treści
- [Uruchomienie aplikacji](#uruchomienie-aplikacji)
- [Opis Gry](#opis-gry)

## Opis gry
Naszym celem jest zdobycie jak największej ilości punktów przy jednoczesnym uciekaniu lub gonieniu duszków.
- __Poruszanie się:__ Gracz steruje Pac-Manem w czterech kierunkach (góra, dół, lewo, prawo).
- __Zbieranie punktów:__ Każda zjedzona kulka daje punkty. W rogach znajdują się większe kulki („power pellets”),
- które dają Pac-Manowi chwilową możliwość zjadania duchów.
- __Duchy:__ Duchy mają różne strategie poruszania się:
    - `CHASE` - Duszki gonią Pacmana
    - `SCATTER` - Każdy Duszek idzie do swojego delegowanego rogu i tam pozostaje aż do zmiany truby na `CHASE` lub `RUN`
    - `RUN` - Gdy Pac-Man zje power pellet, duchy zmieniają kolor i uciekają od Pacmana, (gracz wtedy może zjeść duszka).
    - `RESPAWN` - Gdy gracz zje Duszka ten zmienia obraz na same oczy i wraca na Respawn
- __Poziomy:__ Po zjedzeniu wszystkich kulek, gracz przechodzi do następnego, trudniejszego poziomu.
- __Koniec gry:__ Gra kończy się, gdy Pac-Man straci wszystkie życia.

## Uruchomienie aplikacji
  Aplikacja docelowo była pisana w Intellij'a, więc do jej uruchomienia potrzebujemy tego IDE przy czym nie ma różnicy czy orzystamy z wersji Community czy Ultimate.
  Po pobraniu ide, z githuba pobieramy zpi: `<>Code` -> `Download ZIP`, zipa rozpakowywujemy i uruchamiamy przy pomocy Intellij'a.


## Struktura projektu

```
| src\
|   |- pacman\
|   |   |- ghosts\
|   |   |   |- Ghost.java  
|   |   |   |- GhostMode.java  
|   |   |   |- GhostService.java  
|   |   |- mainPanel\
|   |   |   |- gameData\
|   |   |   |   \- GameData.java
|   |   |   |- GamePanel.java  
|   |   |   |- GameService.java  
|   |   |   |- PacmanFrame.java  
|   |   |   |- PacmanPanel.java  
|   |   |   |- RunPacman.java  
|   |   |- playerControl\
|   |   |   |- Direction.java
|   |   |   |- Entity.java  
|   |   |   |- Pacman.java  
|   |   |   |- Player.java  
|   |   |- tiles\
|   |   |   |- boards\
|   |   |   |   |- MirorBoard.java
|   |   |   |   |- board1.txt
|   |   |   |   \- board1_1.txt
|   |   |   |- collision\
|   |   |   |   |- PacmanAndGhostCollision
|   |   |   |   |- PacmanService
|   |   |   |- point\
|   |   |   |   |- HighScoresFrame
|   |   |   |   |- PointCounterService
|   |   |   |- BoardService.java  
|   |   |   |- Tile.java  
|   |   |   |- TileManager.java  
|   |   |- RunGame.java  
|   |   |- wyniki.txt
|   |- resources\
|   |   |- images\
|   |   |   |- ghosts\
|   |   |   |- maps\
|   |   |   |- pacman\
|   |   |   |- tiles\
```
   