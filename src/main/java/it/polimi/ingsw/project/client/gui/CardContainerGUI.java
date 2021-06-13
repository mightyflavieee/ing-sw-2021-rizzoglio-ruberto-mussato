package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.selectcard.SelectCardForPurchaseListener;
import it.polimi.ingsw.project.model.CardContainer;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CardContainerGUI extends JInternalFrame {
    private CardContainer cardContainer;
    private List<String> cardsToShow;
    private List<JButton> showedCards;
    private InformationsGUI informationsGUI;
    private int width = 70, height = 140;

    public CardContainerGUI(InformationsGUI informationsGUI, CardContainer cardContainer) {
        this.informationsGUI = informationsGUI;
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

            jButton.setIcon(new ImageIcon(
                    new javax.swing.ImageIcon("src/main/resources/developmentcards/"+ this.cardsToShow.get(i) + ".png")
                    .getImage().getScaledInstance(this.width,this.height, Image.SCALE_SMOOTH)));
            jButton.setVisible(true);
        }
    }

    public void refresh() {
        this.cardsToShow = cardContainer.getAvailableDevCards().stream().map(DevelopmentCard::getId).collect(Collectors.toList());
        for (int i = 0; i < cardsToShow.size(); i++){
            this.showedCards.get(i).setIcon(new ImageIcon(
                    new javax.swing.ImageIcon("src/main/resources/developmentcards/"+ this.cardsToShow.get(i) + ".png")
                    .getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH)));
                if (Arrays.stream(this.showedCards.get(i).getActionListeners()).count() == 0) {
                    this.showedCards.get(i).addActionListener(new SelectCardForPurchaseListener(this.informationsGUI,
                        this.cardContainer.fetchCard(this.cardsToShow.get(i))));
                } else {
                    this.showedCards.get(i).removeActionListener(this.showedCards.get(i).getActionListeners()[0]);
                    this.showedCards.get(i).addActionListener(new SelectCardForPurchaseListener(this.informationsGUI,
                            this.cardContainer.fetchCard(this.cardsToShow.get(i))));
                }
        }
    }

    public void setCardContainer(CardContainer cardContainer) {
        this.cardContainer = cardContainer;
        this.refresh();
    }

    public void disableAllButtons() {
        for (JButton button : this.showedCards) {
            button.setEnabled(false);
        }
    }

    public void enableAllButtons() {
        for (JButton button : this.showedCards) {
            button.setEnabled(true);
        }
    }

    public void refreshSize(int width, int height) {
        this.width = width/4;
        this.height = height/3;
        this.refresh();
    }
}
