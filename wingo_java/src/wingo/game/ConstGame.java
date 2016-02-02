// ConstGame.java

package wingo.game;

import wingo.go.ConstBoard;
import wingo.go.GoColor;

/** Const functions of game.Game.
    @see Game
*/
public interface ConstGame
{
    ConstBoard getBoard();

    ConstClock getClock();

    ConstNode getCurrentNode();

    ConstGameInfo getGameInfo(ConstNode node);

    ConstNode getGameInfoNode();

    int getMoveNumber();

    ConstNode getRoot();

    int getSize();

    GoColor getToMove();

    ConstGameTree getTree();

    boolean isModified();
}
