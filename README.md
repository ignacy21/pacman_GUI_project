# PACMAN

Aplikacja pacman jest wiernym odwzorowaniem gry Pacman. Powstała jako projekt na studia z przedmiotu Java GUI.

## Spis treści
- [Uruchomienie aplikacji](#uruchomienie-aplikacji)


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