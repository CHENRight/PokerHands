import java.util.ArrayList;
import java.util.List;

public class PokerHands {

    static Player compareCard(List<String> player1Card, List<String> player2Card) {
        Player winner;

        Player player1 = new Player(player1Card);
        Player player2 = new Player(player2Card);

        if (isHasOnePair(player1Card) && !isHasOnePair(player2Card)) {
            winner = player1;
        } else {
            if (player1Card.get(0).charAt(0) > player2Card.get(0).charAt(0)) {
                winner = player1;
            } else {
                winner = player2;
            }
        }
        return winner;
    }

    static Boolean isHasOnePair(List<String> card) {
        List<Character> cardNumber = new ArrayList<>();
        for (String temp : card) {
            cardNumber.add(temp.charAt(0));
        }
        long count = cardNumber.stream().distinct().count();
        return count != cardNumber.size();
    }
}
