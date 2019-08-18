import java.util.List;

public class Player {
    private List<String> card;

    public Player(List<String> card) {
        this.card = card;
    }

    public List<String> getCard() {
        return card;
    }

    public void setCard(List<String> card) {
        this.card = card;
    }

}
