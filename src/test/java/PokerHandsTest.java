import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PokerHandsTest {

    @Test
    public void should_return_player1_win_when_player_has_big_card() {
        List<String> card1 = new ArrayList<String>() {{
            add("3C");
        }};
        List<String> card2 = new ArrayList<String>() {{
            add("4C");
        }};

        Player winner = PokerHands.compareCard(card1, card2);

        Assertions.assertEquals(card2, winner.getCard());
    }

    @Test
    public void should_player_has_pair_win() {
        List<String> card1 = new ArrayList<String>() {{
            add("3C");
            add("3H");
        }};
        List<String> card2 = new ArrayList<String>() {{
            add("3C");
            add("4H");
        }};
        Player winner = PokerHands.compareCard(card1, card2);
        Assertions.assertEquals(card1, winner.getCard());
    }

    @Test
    public void should_player_win_who_has_more_pair_than_another() {
        List<String> card1 = new ArrayList<String>() {
            {
                addAll(Arrays.asList("8C", "8H", "8S", "8D", "5C"));
            }
        };
        List<String> card2 = new ArrayList<String>() {
            {
                addAll(Arrays.asList("8C", "8H", "7S", "9D", "5C"));
            }
        };

        Player winner = PokerHands.compareCard(card1, card2);
        Assertions.assertEquals(card1, winner.getCard());
    }
}