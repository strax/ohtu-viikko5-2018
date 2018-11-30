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
        if (playerName == "player1")
            player1Score += 1;
        else
            player2Score += 1;
    }

    private String getDrawState(int score) {
        switch (score)
        {
            case 0:
                return "Love-All";
            case 1:
                return "Fifteen-All";
            case 2:
                return "Thirty-All";
            case 3:
                return "Forty-All";
            default:
                return "Deuce";
        }
    }

    private String getAdvantageousState(int a, int b) {
        if (a - b == 1) {
            return "Advantage player1";
        } else if (a - b == -1) {
            return "Advantage player2";
        } else if (a - b>= 2) {
            return "Win for player1";
        } else {
            return "Win for player2";
        }
    }

    private String getDefaultState(int a, int b) {
        var tempScore = 0;
        var score = "";
        for (int i = 1; i < 3; i++) {
            if (i == 1) {
                tempScore = player1Score;
            } else {
                score += "-";
                tempScore = player2Score;
            }

            switch(tempScore) {
                case 0:
                    score += "Love";
                    break;
                case 1:
                    score += "Fifteen";
                    break;
                case 2:
                    score += "Thirty";
                    break;
                case 3:
                    score += "Forty";
                    break;
            }
        }
        return score;
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