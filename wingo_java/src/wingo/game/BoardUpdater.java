// BoardUpdater.java

package wingo.game;

import static wingo.go.GoColor.BLACK;
import static wingo.go.GoColor.EMPTY;
import static wingo.go.GoColor.WHITE;

import java.util.ArrayList;

import wingo.go.Board;
import wingo.go.ConstPointList;
import wingo.go.GoColor;
import wingo.go.GoPoint;
import wingo.go.Move;
import wingo.go.PointList;

/** Updates a go.Board to a node in a GameTree. */
public class BoardUpdater
{
    public BoardUpdater()
    {
        m_nodes = new ArrayList<ConstNode>(400);
    }

    public void update(ConstGameTree tree, ConstNode currentNode, Board board)
    {
        ConstGameInfo info = tree.getGameInfoConst(currentNode);
        int size = tree.getBoardSize();
        int handicap = info.getHandicap();
        NodeUtil.getPathToRoot(currentNode, m_nodes);
        board.init(size);
        boolean isFirstPlacement = true;
        for (int i = m_nodes.size() - 1; i >= 0; --i)
        {
            ConstNode node = (ConstNode)m_nodes.get(i);
            GoColor player = node.getPlayer();
            if (node.hasSetup())
            {
                ConstPointList setupBlack = node.getSetup(BLACK);
                ConstPointList setupWhite = node.getSetup(WHITE);
                ConstPointList setupEmpty = node.getSetup(EMPTY);
                if (isFirstPlacement && setupBlack.size() == handicap
                    && setupWhite.size() == 0 && setupEmpty.size() == 0)
                    board.setupHandicap(setupBlack);
                else
                {
                    PointList black = new PointList();
                    PointList white = new PointList();
                    for (GoPoint p : board)
                    {
                        if (board.getColor(p) == BLACK)
                            black.add(p);
                        else if (board.getColor(p) == WHITE)
                            white.add(p);
                    }
                    for (GoPoint p : setupBlack)
                    {
                        white.remove(p);
                        if (! black.contains(p))
                            black.add(p);
                    }
                    for (GoPoint p : setupWhite)
                    {
                        black.remove(p);
                        if (! white.contains(p))
                            white.add(p);
                    }
                    for (GoPoint p : setupEmpty)
                    {
                        black.remove(p);
                        white.remove(p);
                    }
                    board.setup(black, white, player);
                }
                isFirstPlacement = false;
            }
            else if (player != null)
                board.setToMove(player);
            Move move = node.getMove();
            if (move != null)
            {
                board.play(move);
                isFirstPlacement = false;
            }
        }
    }
    

    /** Local variable used in update.
        Member variable for avoiding frequent new memory allocations.
    */
    private final ArrayList<ConstNode> m_nodes;
}
