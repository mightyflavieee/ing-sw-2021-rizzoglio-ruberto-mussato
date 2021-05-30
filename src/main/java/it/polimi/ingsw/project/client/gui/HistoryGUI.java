package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.CardContainer;
import it.polimi.ingsw.project.model.LeaderCardContainer;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryGUI extends JInternalFrame {
  //  private String history;
    private JTextArea jTextArea;

    public HistoryGUI() {
        this.setTitle("History");
      //  this.history = "";
        this.jTextArea = new JTextArea("No History");
        this.jTextArea.setEditable(false);
        this.jTextArea.setVisible(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVisible(true);

        this.add(jScrollPane);

       // jScrollPane.add(jTextArea);
      //  this.setHistory(new CardContainer().fetchCard("id1").toString() + new CardContainer().fetchCard("id1").toString());
        this.setVisible(true);
    }

    public void setHistory(String history) {
      //  this.history = history;
        this.jTextArea.setText(history);
    }
}
