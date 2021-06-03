package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.market.Market;

import javax.swing.*;
import java.awt.*;

public class MarketGUI extends JInternalFrame {
    //JPanel jPanel;

    private JLabel sfondo; //se lo metto in inglese mi da problemi
    private TrayGUI trayGui;
    private ResourceInHandGUI resourceInHandGUI;
    public MarketGUI(GUI gui, Market market, InformationsGUI informationsGUI) {
      //  this.jPanel = new JPanel();
        this.setTitle("Market");
        //this.constructorHelper();
      //  this.backgroundHelper();
        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
       // this.setLayout(new OverlayLayout());
      //  this.setSize(50,100);

        this.resourceInHandGUI = new ResourceInHandGUI(informationsGUI, gui.getTakeMarketResourceBuilder());
        this.trayGui = new TrayGUI(this.resourceInHandGUI, gui, market);
        this.add(trayGui);
        this.add(resourceInHandGUI);
        this.pack();
        //this.setPreferredSize(new Dimension(200,500));

    }
    private void constructorHelper(){
        this.setLayout(new GridLayout(2,3));
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel.setBackground(Color.RED);
        panel1.setBackground(Color.YELLOW);
        panel2.setBackground(Color.BLUE);
        this.add(panel);
        this.add(panel2);
        this.add(panel1);
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        panel5.setBackground(Color.RED);
        panel4.setBackground(Color.YELLOW);
        panel3.setBackground(Color.BLUE);
        this.add(panel3);
        this.add(panel4);
        this.add(panel5);
        this.setVisible(true);
        //this.add(jPanel);

    }
    private void backgroundHelper(){
        sfondo = new JLabel();
//        try {
//            jLabel = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/resources/plancia portabiglie.png"))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        sfondo.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/plancia portabiglie.png").getImage().getScaledInstance(380, 500, Image.SCALE_SMOOTH)));


        this.add(sfondo);

    }
//    public void doubleSize() {
//        jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/plancia portabiglie.png").getImage().getScaledInstance(jLabel.getWidth() * 2, jLabel.getHeight() * 2, Image.SCALE_SMOOTH)));
//    }
    public void setMarket(Market market){
        this.trayGui.setMarket(market);
    }


    public ResourceInHandGUI getResourceInHandGUI() {
        return this.resourceInHandGUI;
    }

    public Market getMarket() {
        return  this.trayGui.getMarket();
    }

    public void disableButtons() {
        this.trayGui.disableButtons();
    }

    public void enableButtons() {
        this.trayGui.enableButtons();
    }
}
