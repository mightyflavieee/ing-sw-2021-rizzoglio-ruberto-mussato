package it.polimi.ingsw.project.client.gui.board;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.listeners.selectresources.ExtraDepositGUISelectResourceListener;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExtraDepositsGUI extends JInternalFrame {
    private List<JButton> firstExtraDepositButtons;
    private List<JButton> secondExtraDepositButtons;
    private InformationsGUI informationsGUI;
    private Map<ResourceType, Integer> resourcesInExtraDeposits;
    private Warehouse warehouseModel;
    private Map<Integer, List<ActionListener>> actionListenersPerExtraDeposit;
    private boolean clickable;

    public ExtraDepositsGUI(InformationsGUI informationsGUI, Warehouse warehouseModel) {
        this.setTitle("Extra Deposits");
        this.setVisible(true);
        this.setLayout(new GridLayout(2, 1));
//        JInternalFrame firstExtraDepo = new JInternalFrame("First extra deposit");
//        JInternalFrame secondExtraDepo = new JInternalFrame("Second extra deposit");
        JPanel firstExtraDepo = new JPanel();
        JPanel secondExtraDepo = new JPanel();
        firstExtraDepo.setVisible(true);
        secondExtraDepo.setVisible(true);
        firstExtraDepo.setLayout(new GridLayout(1, 2));
        secondExtraDepo.setLayout(new GridLayout(1, 2));
        createButtons();
        firstExtraDepo.add(this.firstExtraDepositButtons.get(0));
        firstExtraDepo.add(this.firstExtraDepositButtons.get(1));
        secondExtraDepo.add(this.secondExtraDepositButtons.get(0));
        secondExtraDepo.add(this.secondExtraDepositButtons.get(1));
        this.add(firstExtraDepo);
        this.add(secondExtraDepo);
        this.pack();
        this.informationsGUI = informationsGUI;
        this.warehouseModel = warehouseModel;
        this.clickable = false;
        refresh();
    }

    private void refresh() {
        if (this.warehouseModel.getExtraDeposit() != null) {
            int count = 1;
            for (ResourceType type : this.warehouseModel.getExtraDeposit().keySet()) {
                if (this.warehouseModel.getExtraDeposit().get(type) == 1) {
                    if (count == 1) {
                        this.firstExtraDepositButtons.get(0).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/resourcetype/" + type + ".png")
                                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                        this.firstExtraDepositButtons.get(1).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                        // adds listener
                        if (Arrays.stream(this.firstExtraDepositButtons.get(0).getActionListeners()).count() == 0) {
                            this.firstExtraDepositButtons.get(0).addActionListener(
                                    new ExtraDepositGUISelectResourceListener(this,
                                            this.informationsGUI, type, 1, 0));
                        }
                    } else {
                        this.secondExtraDepositButtons.get(0).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/resourcetype/" + type + ".png")
                                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                        this.secondExtraDepositButtons.get(1).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                        // adds listener
                        if (Arrays.stream(this.firstExtraDepositButtons.get(0).getActionListeners()).count() == 0) {
                            this.firstExtraDepositButtons.get(0).addActionListener(
                                    new ExtraDepositGUISelectResourceListener(this,
                                            this.informationsGUI, type, 1, 0));
                        }
                    }
                }
                if (this.warehouseModel.getExtraDeposit().get(type) == 2) {
                    if (count == 1) {
                        this.firstExtraDepositButtons.get(0).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/resourcetype/" + type + ".png")
                                        .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                        this.firstExtraDepositButtons.get(1).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/resourcetype/" + type + ".png")
                                        .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                        this.firstExtraDepositButtons.get(0).addActionListener(new ExtraDepositGUISelectResourceListener(this,
                                this.informationsGUI, type, 1, 0));
                        this.firstExtraDepositButtons.get(1).addActionListener(new ExtraDepositGUISelectResourceListener(this,
                                this.informationsGUI, type, 1, 1));
                    } else {
                        this.secondExtraDepositButtons.get(0).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/resourcetype/" + type + ".png")
                                        .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                        this.secondExtraDepositButtons.get(1).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/resourcetype/" + type + ".png")
                                        .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                        this.secondExtraDepositButtons.get(0).addActionListener(new ExtraDepositGUISelectResourceListener(this,
                                this.informationsGUI, type, 2, 0));
                        this.secondExtraDepositButtons.get(1).addActionListener(new ExtraDepositGUISelectResourceListener(this,
                                this.informationsGUI, type, 2, 1));
                    }
                }
                count++;
            }
        }
    }

    private void createButtons() {
        JButton button1FirstExtraDepo = new JButton();
        JButton button2FirstExtraDepo = new JButton();
        JButton button1SecondExtraDepo = new JButton();
        JButton button2SecondExtraDepo = new JButton();
        button1FirstExtraDepo.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button2FirstExtraDepo.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button1SecondExtraDepo.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button2SecondExtraDepo.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button1FirstExtraDepo.setEnabled(false);
        button2FirstExtraDepo.setEnabled(false);
        button1SecondExtraDepo.setEnabled(false);
        button2SecondExtraDepo.setEnabled(false);
        this.firstExtraDepositButtons = new ArrayList<>();
        this.secondExtraDepositButtons = new ArrayList<>();
        this.firstExtraDepositButtons.add(button1FirstExtraDepo);
        this.firstExtraDepositButtons.add(button2FirstExtraDepo);
        this.secondExtraDepositButtons.add(button1SecondExtraDepo);
        this.secondExtraDepositButtons.add(button2SecondExtraDepo);
    }

    public List<JButton> getFirstExtraDepositButtons() {
        return firstExtraDepositButtons;
    }

    public List<JButton> getSecondExtraDepositButtons() {
        return secondExtraDepositButtons;
    }

    public void insertInExtraDeposit(List<Resource> resourcesToInsert) {
        this.warehouseModel.insertInExtraDeposit(resourcesToInsert);
        refresh();
    }

    public void eliminateFromExtraDeposit(List<Resource> resourcesToEliminate) {
        //todo this.warehouseModel.eliminateResourcesExtraDeposit(resourcesToEliminate);
        refresh();
    }

    public void enableExtraDeposit(ResourceType type) {
        if (this.firstExtraDepositButtons.get(0).isEnabled()) {
            this.secondExtraDepositButtons.get(0).setEnabled(true);
            this.secondExtraDepositButtons.get(1).setEnabled(true);
            this.secondExtraDepositButtons.get(0).addActionListener(new ExtraDepositGUISelectResourceListener(this,
                    this.informationsGUI, type, 2, 1));
            this.secondExtraDepositButtons.get(1).addActionListener(new ExtraDepositGUISelectResourceListener(this,
                    this.informationsGUI, type, 1, 1));
        } else {
            this.firstExtraDepositButtons.get(0).setEnabled(true);
            this.firstExtraDepositButtons.get(1).setEnabled(true);
            this.firstExtraDepositButtons.get(0).addActionListener(new ExtraDepositGUISelectResourceListener(this,
                    this.informationsGUI, type, 1, 1));
            this.secondExtraDepositButtons.get(1).addActionListener(new ExtraDepositGUISelectResourceListener(this,
                    this.informationsGUI, type, 1, 2));
        }
    }

    public void disableAllButtons() { this.clickable = false; }

    public void enableAllButtons() {
        this.clickable = true;
    }

    public boolean isClickable() {
        return this.clickable;
    }
}

