package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.market.Market;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MarketGUI extends JInternalFrame {
    //JPanel jPanel;
    private Image background;
    private JLabel jLabel;
    public MarketGUI(String string) {
      //  this.jPanel = new JPanel();
        this.setTitle(string);
        //this.constructorHelper();
        this.backgroundHelper();
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

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
        jLabel = new JLabel();
//        try {
//            jLabel = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/resources/plancia portabiglie.png"))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/plancia portabiglie.png").getImage().getScaledInstance(380, 500, Image.SCALE_SMOOTH)));


        this.add(jLabel);

    }
    public void doubleSize() {
        jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/plancia portabiglie.png").getImage().getScaledInstance(jLabel.getWidth() * 2, jLabel.getHeight() * 2, Image.SCALE_SMOOTH)));
    }


}
