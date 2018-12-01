package ohtu;

import java.util.function.IntUnaryOperator;

public class TennisGame {
    private static boolean isAds(int maxScore) {
        return maxScore >= 4;
    }

    static class Player {
        private final String name;
        private final int points;

        public Player(String name) {
            this(name, 0);
        }

        public Player(String name, int points) {
            this.name = name;
            this.points = points;
        }

        public Player mapPoints(IntUnaryOperator f) {
            return new Player(name, f.applyAsInt(points));
        }
    }

    // These are static in a companion interface to ensure that the show functions are pure
    public interface Show {
        static String showAdvantage(Player p) {
            return "Advantage " + p.name;
        }

        static String showWin(Player p) {
            return "Win for " + p.name;
        }

        static String showPoints(Player p) {
            switch (p.points) {
                case 0:
                    return "Love";
                case 1:
                    return "Fifteen";
                case 2:
                    return "Thirty";
                case 3:
                    return "Forty";
                default:
                    return "Game";
            }
        }

        static String showScore(Player a, Player b) {
            return String.format("%s-%s", showPoints(a), showPoints(b));
        }

        static String showTie(Player a, Player b) {
            if (isAds(Math.max(a.points, b.points))) {
                return "Deuce";
            } else {
                return String.format("%s-All", showPoints(a));
            }
        }
    }

    private Player player1;
    private Player player2;

    public TennisGame(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1.name)) {
            player1 = player1.mapPoints(n -> n + 1);
        } else {
            player2 = player2.mapPoints(n -> n + 1);
        }
    }

    private int getScoreDifference() {
        return Math.abs(player1.points - player2.points);
    }

    private Player getLeadingPlayer() {
        if (player1.points >= player2.points) {
            return player1;
        } else {
            return player2;
        }
    }

    private boolean isTied() {
        return getScoreDifference() == 0;
    }

    /**
     * Returns true if the game is beyond a deuce in which case there needs to be a two-point difference to win the game.
     * This is called "advantage scoring" or "ads".
     */
    private boolean isAds() {
        return TennisGame.isAds(getLeadingPlayer().points);
    }

    private boolean isAdvantage() {
        return isAds() && getScoreDifference() == 1;
    }

    private boolean isWon() {
        return isAds() && getScoreDifference() >= 2;
    }

    public String getScore() {
        if (isWon()) {
            return Show.showWin(getLeadingPlayer());
        } if (isAdvantage()) {
            return Show.showAdvantage(getLeadingPlayer());
        } else if (isTied()) {
            return Show.showTie(player1, player2);
        } else {
            return Show.showScore(player1, player2);
        }
    }
}