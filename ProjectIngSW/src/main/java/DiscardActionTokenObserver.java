public class DiscardActionTokenObserver implements Observer{
    private CardContainer cardContainer;
    private CardColor cardColor;
    @Override
    public void update() {
        cardContainer.discard(this.cardColor);
    }
    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }
}

