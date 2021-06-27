package it.polimi.ingsw.project.client.gui.market;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.listeners.market.ArrowListener;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.market.Marble;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrayGUI extends JPanel {
    private List<ResourceType> transmutationPerks;
    private ResourceType chosedTransmutationPerk;
    private Market market;
    private List<JButton> verticalArrows; //da sinistra a destra
    private List<JButton> horizontalArrows; //dall'alto verso il basso , è al contrario rispetto a quando faccio la move
    private final JLabel[][] trayJlabel = new JLabel[4][3];
    private JLabel outsideMarbleJlabel;
    private int width = 60;
    public TrayGUI(ResourceInHandGUI resourceInHandGUI, GUI gui, Market market)  {
        this.market = market;
        GridLayout gridLayout = new GridLayout(4, 5);
        this.setLayout(gridLayout);
        this.createTray();
        this.createObservers(resourceInHandGUI,gui);
        this.setVisible(true);
        this.transmutationPerks = new ArrayList<>();
    }

    private void createObservers(ResourceInHandGUI resourceInHandGUI, GUI gui) {
        for(int i = 0; i < verticalArrows.size(); i++){
            verticalArrows.get(i).addActionListener(new ArrowListener(this,0,i,resourceInHandGUI,gui));
        }
        for(int i = 0; i < horizontalArrows.size(); i++){ //dall'alto verso il basso , è al contrario rispetto a quando faccio la move
            horizontalArrows.get(i).addActionListener(new ArrowListener(this,1,horizontalArrows.size()-i-1,resourceInHandGUI, gui));
        }
    }

    private void createTray(){
        verticalArrows = new ArrayList<>();
        horizontalArrows = new ArrayList<>();
        Marble[][] tray = market.getTray();
        JLabel jLabel;
        JButton jButton;
        for(int j = 2; j > -1; j--) {

            for (int i = 0; i < 4; i++) {
                jLabel = new JLabel();
                //jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + tray[i][j].toString()+ ".png").getImage().getScaledInstance(this.width, this.width, Image.SCALE_SMOOTH)));
                jLabel.setIcon(Utils.readIcon("marbles/" + tray[i][j].toString()+ ".png",width,width));
                this.add(jLabel);
                this.trayJlabel[i][j] = jLabel;
            }
            jButton = new JButton();
           // jButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/freccia orizzontale.png").getImage().getScaledInstance(this.width, this.width, Image.SCALE_SMOOTH)));
            jButton.setIcon(Utils.readIcon("marbles/freccia orizzontale.png",width,width));
            this.add(jButton);
            horizontalArrows.add(jButton);
        }
        for(int i = 0; i < 4; i++){
            jButton = new JButton();
           // jButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/freccia verticale.png").getImage().getScaledInstance(this.width, this.width, Image.SCALE_SMOOTH)));
            jButton.setIcon(Utils.readIcon("marbles/freccia verticale.png",width,width));
            this.add(jButton);
            verticalArrows.add(jButton);
        }
        jLabel = new JLabel();
       // jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + market.getOutSideMarble().toString()+ ".png").getImage().getScaledInstance(this.width, this.width, Image.SCALE_SMOOTH)));
        jLabel.setIcon(Utils.readIcon("marbles/"  + market.getOutSideMarble().toString()+ ".png",width,width));

        this.add(jLabel);
        this.outsideMarbleJlabel = jLabel;
    }

    public List<Resource> insertMarble(int axis, int position) {
        ResourceType resourceType = this.chosedTransmutationPerk;
        this.chosedTransmutationPerk = null;

        return this.market.insertMarble(axis,position,resourceType);
    }

    public void refresh(){
        Marble[][] tray = market.getTray();

        for(int j = 2; j > -1; j--) {

            for (int i = 0; i < 4; i++) {
                //this.trayJlabel[i][j].setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + tray[i][j].toString()+ ".png").getImage().getScaledInstance(this.width, this.width, Image.SCALE_SMOOTH)));
                this.trayJlabel[i][j].setIcon(Utils.readIcon("marbles/" + tray[i][j].toString()+ ".png",width,width));

            }
        }
        //this.outsideMarbleJlabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + market.getOutSideMarble().toString()+ ".png").getImage().getScaledInstance(this.width, this.width, Image.SCALE_SMOOTH)));
        this.outsideMarbleJlabel.setIcon(Utils.readIcon("marbles/"  + market.getOutSideMarble().toString()+ ".png",width,width));

        for (JButton horizontalArrow : this.horizontalArrows) {
            //this.horizontalArrows.get(i).setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/freccia orizzontale.png").getImage().getScaledInstance(this.width, this.width, Image.SCALE_SMOOTH)));
            horizontalArrow.setIcon(Utils.readIcon("marbles/freccia orizzontale.png", width, width));

        }
        for (JButton verticalArrow : this.verticalArrows) {
            // this.verticalArrows.get(i).setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/freccia verticale.png").getImage().getScaledInstance(this.width, this.width, Image.SCALE_SMOOTH)));
            verticalArrow.setIcon(Utils.readIcon("marbles/freccia verticale.png", width, width));

        }

    }

    public void disableButtons() {
        for (JButton jButton : verticalArrows){
            jButton.setEnabled(false);
        }
        for (JButton jButton : horizontalArrows){
            jButton.setEnabled(false);
        }
    }

    public void setMarket(Market market, Player mePlayer) {
        this.market = market;
        this.transmutationPerks = mePlayer.getTransmutationPerk();
        this.refresh();
    }

    public Market getMarket() {
        return this.market;
    }


    public void enableButtons() {
        this.verticalArrows.forEach(x -> x.setEnabled(true));
        this.horizontalArrows.forEach(x -> x.setEnabled(true));
    }

    public void refreshSize(int width, int height) {
        this.width = Math.min(width, height);
        this.refresh();
    }

    public boolean isTransmutationChosable() {
        switch (this.transmutationPerks.size()){
            case 0:
                this.chosedTransmutationPerk = null;
                return false;
            case 1:
                this.chosedTransmutationPerk = this.transmutationPerks.get(0);
                return false;
            default:
                return true;

        }
    }

    public List<ResourceType> getTransmutationPerks() {
        return this.transmutationPerks;
    }

    public void setChosedTransmutationPerk(ResourceType resourceType) {
        this.chosedTransmutationPerk = resourceType;
    }
}
