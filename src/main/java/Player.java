import java.util.List;

public class Player {
    private List<String> card;
    private PokerCardValue cardValue;

    public Player(List<String> card) {
        this.card = card;
        this.cardValue = CalculateValue.getCardValue(card);
    }

    public List<String> getCard() {
        return card;
    }
    public void setCard(List<String> card) {
        this.card = card;
    }

    public PokerCardValue getCardValue() {
        return cardValue;
    }

    public void setCardValue(PokerCardValue cardValue) {
        this.cardValue = cardValue;
    }

    public int getCardLevel() {
        return cardValue.getLevel();
    }
}
