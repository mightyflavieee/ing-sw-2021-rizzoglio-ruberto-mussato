package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.market.Marble;
import it.polimi.ingsw.project.model.market.MarbleType;
import it.polimi.ingsw.project.model.market.Market;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TrayGui extends JPanel {
    private Image backgroundImage;
    private Market market;
    private GridLayout gridLayout;
    public TrayGui()  {
        this.market = new Market();
        gridLayout = new GridLayout(4,5);
        this.setLayout(gridLayout);
        createTray();
        //            try {
//                this.backgroundImage = ImageIO.read(new File("src/main/resources/plancia portabiglie.png")).getScaledInstance(100, 200, Image.SCALE_SMOOTH);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        this.setVisible(true);
        this.setPreferredSize(new Dimension(200,100));
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
        Marble[][] tray = market.getTray();
        JLabel jLabel;
        for(int j = 2; j > -1; j--) {

            for (int i = 0; i < 4; i++) {
                jLabel = new JLabel();
                jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + tray[i][j].toString()+ ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                this.add(jLabel);
            }
            jLabel = new JLabel();
            jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/freccia orizzontale.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            this.add(jLabel);
        }
        for(int i = 0; i < 4; i++){
            jLabel = new JLabel();
            jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/freccia verticale.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            this.add(jLabel);
        }
        jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/marbles/" + market.getOutSideMarble().toString()+ ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(jLabel);

    }

}
