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
|   |   |   |   |- GameData.java
|   |   |   |   |- GameDataBuilder.java
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
- `RunGame` - dodawanie map i innych komponentów z których składa się `PacmanFrame` - jest to główny panel z
  którego wybiera się mapę na której będzie rozgrywka, lub przechodzi się do wyników
- `GhostMode` - enum z dostępnymi kierunkami duszka
- `Ghost` - ma listę z obrazkami duszka, zmienia kierunek pacmana a wraz z nim obrazek duszka,
- tworzy osobny wątek, który odpowiada za zmianę trybu (i czasem jego trwania) duszka wraz z jego obrazkiem
- `GhostService` - AI który przy pomocy dostępnej mapy określa poruszanie się duszka w zależnosci od trybu, w którym jest 
- `GameDataBuilder` - builder który pomaga stworzyć klasę `GameData`
- `GameData` - klasa, która zawiera wszystkie potrzebne inforamcje o mapie na której dzieje się gra
- `GamePanel` - panel który wyświetla panel gry, punktów, żyć i czasu gry
- `GameService` - panel, który po określeniu `GameData` oblicza wszystkie potrzebene dane do stworzenia mapy oraz ją tworzy
- `PacmanFrame` - frame na którym sie wszystko dzieje
- `PacmanPanel` - panel gry, tworzenie duszków i ich miejsca startowegp, updete położnia gracza i duszków oraz metoda
odopowiedzialna za ich malowanie
- `RunPacman` - klasa odpowiedzialna za wątek pacmana oraz posiadająca metody odpowiedzialne za: tracenie żyć i planszy 
bez punktów które gracz wcześniej zdobył, odpalenie nowego lewelu (duszki maja większą prędkość), zakończenie gry
