
import java.util.List;

public class PokerHands {

    static Player compareCard(List<String> player1Card, List<String> player2Card) {

        Player player1 = new Player(player1Card);
        Player player2 = new Player(player2Card);

        return player1.getCardLevel() > player2.getCardLevel() ? player1 : player2;

    }
}
