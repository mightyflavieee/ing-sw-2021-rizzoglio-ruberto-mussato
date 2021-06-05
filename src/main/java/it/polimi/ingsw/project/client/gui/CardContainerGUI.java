package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.CardContainer;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardContainerGUI extends JInternalFrame {
    CardContainer cardContainer;
    List<String> cardsToShow;
    List<JButton> showedCards;

    public CardContainerGUI(CardContainer cardContainer) {
        this.cardContainer = cardContainer;
        this.setTitle("CardContainer");
        this.setLayout(new GridLayout(3,4));
        this.constructorHelper();
        this.setVisible(true);
    }

    private void constructorHelper() {
        this.cardsToShow = cardContainer.getAvailableDevCards().stream().map(DevelopmentCard::getId).collect(Collectors.toList());
        this.showedCards = new ArrayList<>();
        JButton jButton;
        for(int i = 0; i < cardsToShow.size(); i++){
            jButton = new JButton();
            this.add(jButton);
            this.showedCards.add(jButton);

            jButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/developmentcards/"+ this.cardsToShow.get(i) + ".png").getImage().getScaledInstance(80,150, Image.SCALE_SMOOTH)));
            jButton.setVisible(true);
        }
    }

    public void refresh() {
        this.cardsToShow = cardContainer.getAvailableDevCards().stream().map(DevelopmentCard::getId).collect(Collectors.toList());
        for (int i = 0; i < cardsToShow.size(); i++){
            this.showedCards.get(i).setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/developmentcards/"+ this.cardsToShow.get(i) + ".png").getImage().getScaledInstance(80, 150, Image.SCALE_SMOOTH)));

        }
    }

    public void setCardContainer(CardContainer cardContainer) {
        this.cardContainer = cardContainer;
        this.refresh();
    }
}
