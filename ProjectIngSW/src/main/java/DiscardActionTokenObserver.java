public class DiscardActionTokenObserver implements Observer{
    private Match match;
    private CardColor cardColor;
    @Override
    public void update() {
        match.discard(this.cardColor);
    }
    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }
}

