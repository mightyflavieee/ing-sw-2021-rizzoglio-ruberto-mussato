package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.ArrowListener;
import it.polimi.ingsw.project.model.market.Marble;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.resource.Resource;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrayGui extends JPanel {
    private Image backgroundImage;
    private Market market;
    private GridLayout gridLayout;
    private List<JButton> verticalArrows; //da sinistra a destra
    private List<JButton> horizontalArrows; //dall'alto verso il basso , è al contrario rispetto a quando faccio la move
    private JLabel[][] trayJlabel = new JLabel[4][3];
    private JLabel outsideMarbleJlabel;
    public TrayGui(ResourceInHandGUI resourceInHandGUI, GUI gui)  {
        this.market = new Market();
        gridLayout = new GridLayout(4,5);
        this.setLayout(gridLayout);
        this.createTray();
        this.createObservers(resourceInHandGUI,gui);
        //            try {
//                this.backgroundImage = ImageIO.read(new File("src/main/resources/plancia portabiglie.png")).getScaledInstance(100, 200, Image.SCALE_SMOOTH);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        this.setVisible(true);
       // this.setPreferredSize(new Dimension(200,100));
    }

    private void createObservers(ResourceInHandGUI resourceInHandGUI, GUI gui) {
        for(int i = 0; i < verticalArrows.size(); i++){
            verticalArrows.get(i).addActionListener(new ArrowListener(this,0,i,resourceInHandGUI,gui));
        }
        for(int i = 0; i < horizontalArrows.size(); i++){ //dall'alto verso il basso , è al contrario rispetto a quando faccio la move
            horizontalArrows.get(i).addActionListener(new ArrowListener(this,1,horizontalArrows.size()-i-1,resourceInHandGUI, gui));
        }
    }

    //    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(this.backgroundImage, 0, 0, this);
//    }
    private void createTray(){
//        for(MarbleType marbleType : this.market.toMarbleType()){
//            JLabel jLabel = new JLabel();
//            jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + marbleType.toString()+ ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
//            this.add(jLabel); }
        verticalArrows = new ArrayList<>();
        horizontalArrows = new ArrayList<>();
        Marble[][] tray = market.getTray();
        JLabel jLabel;
        JButton jButton;
        for(int j = 2; j > -1; j--) {

            for (int i = 0; i < 4; i++) {
                jLabel = new JLabel();
                jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + tray[i][j].toString()+ ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                this.add(jLabel);
                this.trayJlabel[i][j] = jLabel;
            }
            jButton = new JButton();
            jButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/freccia orizzontale.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            this.add(jButton);
            horizontalArrows.add(jButton);
        }
        for(int i = 0; i < 4; i++){
            jButton = new JButton();
            jButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/freccia verticale.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            this.add(jButton);
            verticalArrows.add(jButton);
        }
        jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + market.getOutSideMarble().toString()+ ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(jLabel);
        this.outsideMarbleJlabel = jLabel;
    }

    public List<Resource> insertMarble(int axis, int position) {

        return this.market.insertMarble(axis,position,null); //TODO
    }

    public void refresh(){
        Marble[][] tray = market.getTray();

        for(int j = 2; j > -1; j--) {

            for (int i = 0; i < 4; i++) {
                this.trayJlabel[i][j].setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + tray[i][j].toString()+ ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

            }
        }
        this.outsideMarbleJlabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + market.getOutSideMarble().toString()+ ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

    }

    public void stopTray() {
        for (JButton jButton : verticalArrows){
            jButton.setEnabled(false);
        }
        for (JButton jButton : horizontalArrows){
            jButton.setEnabled(false);
        }
    }

    public void setMarket(Market market) {
        this.market = market;
        this.refresh();
    }
}
