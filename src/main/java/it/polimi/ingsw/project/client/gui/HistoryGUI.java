package it.polimi.ingsw.project.client.gui;

import javax.swing.*;

public class HistoryGUI extends JInternalFrame {
  //  private String history;
    private final JTextArea jTextArea;

    public HistoryGUI(String history) {
        this.setTitle("My History");
      //  this.history = "";
        if(history.equals("")) {
            this.jTextArea = new JTextArea("No History");
        }else
        {
            this.jTextArea = new JTextArea(history);
        }
        this.jTextArea.setEditable(false);
        this.jTextArea.setVisible(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVisible(true);

        this.add(jScrollPane);

        this.setVisible(true);
    }

    public void setHistory(String history,String opponentNickName) {
        this.setTitle(opponentNickName + "'s History");
        if(history.equals("")) {
            this.jTextArea.setText("No History");
        }else
        {
            this.jTextArea.setText(history);
        }
        this.jTextArea.setEditable(false);
        this.jTextArea.setVisible(true);
    }
    public void setMyHistory(String history){
        this.setTitle("My history");
        if(history.equals("")) {
            this.jTextArea.setText("No History");
        }else
        {
            this.jTextArea.setText(history);
        }
        this.jTextArea.setEditable(false);
        this.jTextArea.setVisible(true);
    }
}
