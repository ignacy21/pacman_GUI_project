package pacman.tiles.collision;

import pacman.ghosts.Ghost;
import pacman.ghosts.GhostMode;
import pacman.playerControl.Pacman;

import java.util.List;

import static pacman.ghosts.GhostMode.RUN;
import static pacman.mainPanel.PacmanPanel.TILE_SIZE;

public class PacmanAndGhostCollision {

    private final Pacman pacman;
    private final List<Ghost> ghosts;

    public PacmanAndGhostCollision(Pacman pacman, List<Ghost> ghosts) {
        this.pacman = pacman;
        this.ghosts = ghosts;
    }

    public boolean isGameOver() {
        int pacmanX = pacman.getCoordinateX();
        int pacmanY = pacman.getCoordinateY();

        for (Ghost ghost : ghosts) {
            int xDiff = Math.abs(pacmanX - ghost.getCoordinateX());
            int yDiff = Math.abs(pacmanY - ghost.getCoordinateY());
            if (xDiff < TILE_SIZE - 3 && yDiff < TILE_SIZE - 3) {
                if (!(ghost.getGhostMode() == RUN || ghost.getGhostMode() == GhostMode.RESPAWN)) {
                    return true;
                } else {
                    if (ghost.getGhostMode() == RUN) {
                        ghost.changeToRespawnMode();
                    }
                }
            }
        }
        return false;
    }
}
