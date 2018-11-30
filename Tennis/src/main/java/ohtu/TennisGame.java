package ohtu;

public class TennisGame {
    
    private int player1Score = 0;
    private int player2Score = 0;
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
            player1Score++;
        } else {
            player2Score++;
        }
    }

    private String getScoreCall(int score) {
        switch (score) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
            default:
                return "Deuce";
        }
    }

    private String getDrawState(int score) {
        if (score >= 4) {
            return getScoreCall(score);
        } else {
            return String.format("%s-All", getScoreCall(score));
        }
    }

    private String getAdvantageousState(int a, int b) {
        if (a - b == 1) {
            return "Advantage " + player1Name;
        } else if (a - b == -1) {
            return "Advantage " + player2Name;
        } else if (a - b >= 2) {
            return "Win for " + player1Name;
        } else {
            return "Win for " + player2Name;
        }
    }

    private String getDefaultState(int a, int b) {
        return String.format("%s-%s", getScoreCall(a), getScoreCall(b));
    }

    public String getScore() {
        if (player1Score == player2Score) {
            return getDrawState(player1Score);
        } else if (player1Score >= 4 || player2Score >= 4) {
            return getAdvantageousState(player1Score, player2Score);
        } else {
            return getDefaultState(player1Score, player2Score);
        }
    }
}