package ohtu;

public class TennisGame {

    static class Player {
        private String name;
        private int score = 0;

        public Player(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void incrementScore() {
            this.score++;
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
            player1.incrementScore();
        } else {
            player2.incrementScore();
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
            return "Advantage " + player1.getName();
        } else if (a - b == -1) {
            return "Advantage " + player2.getName();
        } else if (a - b >= 2) {
            return "Win for " + player1.getName();
        } else {
            return "Win for " + player2.getName();
        }
    }

    private String getDefaultState(int a, int b) {
        return String.format("%s-%s", getScoreCall(a), getScoreCall(b));
    }

    public String getScore() {
        if (player1.getScore() == player2.getScore()) {
            return getDrawState(player1.getScore());
        } else if (player1.getScore() >= 4 || player2.getScore() >= 4) {
            return getAdvantageousState(player1.getScore(), player2.getScore());
        } else {
            return getDefaultState(player1.getScore(), player2.getScore());
        }
    }
}