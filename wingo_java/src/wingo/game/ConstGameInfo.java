// ConstGameInfo.java

package wingo.game;

import wingo.go.GoColor;
import wingo.go.Komi;
import wingo.go.Score.ScoringMethod;

/** Const functions of game.GameInfo.
    @see GameInfo
*/
public interface ConstGameInfo
{
    String get(StringInfo type);

    String get(StringInfoColor type, GoColor c);

    int getHandicap();

    Komi getKomi();

    TimeSettings getTimeSettings();

    ScoringMethod parseRules();

    String suggestGameName();
}
