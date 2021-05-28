package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.DevCardPosition;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BoardGUI extends JInternalFrame {
    private JPanel panel;
    private final Map<DevCardPosition, JPanel> cardSlots;
    private final JPanel warehouse;
    private final JPanel faithMap;

    public BoardGUI(){
        this.setVisible(true);
        this.setPreferredSize(new Dimension(1066, 762));
        this.panel = new JPanel();
        this.cardSlots= new HashMap<>();
        this.warehouse = new JPanel();
        this.faithMap = new JPanel();
        try {
            createBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(this.panel);
    }

    private void setBoardFrameBackGroundImage() throws IOException {
        this.panel = new JPanelWithBackground("src/main/resources/prova_board.PNG");
    }

    private JPanel createDevCardSlots() throws IOException {
        JPanel devCardSlots = new JPanel();
        this.cardSlots.put(DevCardPosition.Left, new JPanelWithBackground("src/main/resources/prova_carte_resized.PNG"));
        this.cardSlots.put(DevCardPosition.Center, new JPanelWithBackground("src/main/resources/prova_carte_resized.PNG"));
        this.cardSlots.put(DevCardPosition.Right, new JPanelWithBackground("src/main/resources/prova_carte_resized.PNG"));
        this.cardSlots.get(DevCardPosition.Left).setPreferredSize(new Dimension(138, 209));
        this.cardSlots.get(DevCardPosition.Center).setPreferredSize(new Dimension(138, 209));
        this.cardSlots.get(DevCardPosition.Right).setPreferredSize(new Dimension(138, 209));
        devCardSlots.setOpaque(false);
        devCardSlots.setPreferredSize(new Dimension(750, 762-300));
        devCardSlots.setLayout(new FlowLayout());
        // creating the JPanel for the cards
        JPanel voidPanelStart = new JPanel();
        JPanel leftCardPanel = new JPanel();
        JPanel voidPanelLeftCenter = new JPanel();
        JPanel centerCardPanel = new JPanel();
        JPanel voidPanelCenterRight = new JPanel();
        JPanel rightCardPanel = new JPanel();
        JPanel voidPanelEnd = new JPanel();
        voidPanelStart.setPreferredSize(new Dimension(120, 762-300));
        leftCardPanel.setPreferredSize(new Dimension(138, 762-300));
        voidPanelLeftCenter.setPreferredSize(new Dimension(70, 762-300));
        centerCardPanel.setPreferredSize(new Dimension(138, 762-300));
        voidPanelCenterRight.setPreferredSize(new Dimension(70, 762-300));
        rightCardPanel.setPreferredSize(new Dimension(138, 762-300));
        voidPanelEnd.setPreferredSize(new Dimension(100, 762-300));
        voidPanelStart.setOpaque(false);
        leftCardPanel.setOpaque(false);
        voidPanelLeftCenter.setOpaque(false);
        centerCardPanel.setOpaque(false);
        voidPanelCenterRight.setOpaque(false);
        rightCardPanel.setOpaque(false);
        voidPanelEnd.setOpaque(false);
        leftCardPanel.setLayout(new FlowLayout());
        centerCardPanel.setLayout(new FlowLayout());
        rightCardPanel.setLayout(new FlowLayout());
        // adding space above the dev cards in order to center them in the JPanel
        JPanel spaceLeftSlot = new JPanel();
        JPanel spaceCenterSlot = new JPanel();
        JPanel spaceRightSlot = new JPanel();
        spaceLeftSlot.setOpaque(false);
        spaceCenterSlot.setOpaque(false);
        spaceRightSlot.setOpaque(false);
        spaceLeftSlot.setPreferredSize(new Dimension(138, 50));
        spaceCenterSlot.setPreferredSize(new Dimension(138, 50));
        spaceRightSlot.setPreferredSize(new Dimension(138, 50));
        leftCardPanel.add(spaceLeftSlot);
        centerCardPanel.add(spaceCenterSlot);
        rightCardPanel.add(spaceRightSlot);
        leftCardPanel.add(this.cardSlots.get(DevCardPosition.Left));
        centerCardPanel.add(this.cardSlots.get(DevCardPosition.Center));
        rightCardPanel.add(this.cardSlots.get(DevCardPosition.Right));
        // adding the dev cards JPanels to the main panel for the cards
        devCardSlots.add(voidPanelStart);
        devCardSlots.add(leftCardPanel);
        devCardSlots.add(voidPanelLeftCenter);
        devCardSlots.add(centerCardPanel);
        devCardSlots.add(voidPanelCenterRight);
        devCardSlots.add(rightCardPanel);
        devCardSlots.add(voidPanelEnd);
        return devCardSlots;
    }

    private void createWarehouse() {
        this.warehouse.setOpaque(false);
        this.warehouse.setPreferredSize(new Dimension((1066-800), (762-300)));
        this.warehouse.setLayout(new FlowLayout());
        // creates the void JPanel to help with fit the content
        JPanel voidPanelStart = new JPanel();
        voidPanelStart.setOpaque(false);
        voidPanelStart.setPreferredSize(new Dimension((1066-800), 55));
        // creates the warehouse JPanel comprised of the resource buttons
        JPanel warehouseSlot = new JPanel();
        JPanel firstShelf = new JPanel();
        JPanel secondShelf = new JPanel();
        JPanel thirdShelf = new JPanel();
        JPanel voidPanelFirstSecondShelf = new JPanel();
        JPanel voidPanelSecondThirdShelf = new JPanel();
        warehouseSlot.setOpaque(false);
        firstShelf.setOpaque(false);
        secondShelf.setOpaque(false);
        thirdShelf.setOpaque(false);
        voidPanelFirstSecondShelf.setOpaque(false);
        voidPanelSecondThirdShelf.setOpaque(false);
        warehouseSlot.setPreferredSize(new Dimension((1066-800), 210));
        firstShelf.setPreferredSize(new Dimension((1066-800), 45));
        secondShelf.setPreferredSize(new Dimension((1066-800), 45));
        thirdShelf.setPreferredSize(new Dimension((1066-800), 45));
        voidPanelFirstSecondShelf.setPreferredSize(new Dimension((1066-800), 10));
        voidPanelSecondThirdShelf.setPreferredSize(new Dimension((1066-800), 11));
        warehouseSlot.setLayout(new FlowLayout());
        firstShelf.setLayout(new FlowLayout());
        secondShelf.setLayout(new FlowLayout());
        thirdShelf.setLayout(new FlowLayout());
        JButton resourceButtonFirstShelf = new JButton();
        JButton resourceButtonSecondShelf1 = new JButton();
        JButton resourceButtonSecondShelf2 = new JButton();
        JButton resourceButtonThirdShelf1 = new JButton();
        JButton resourceButtonThirdShelf2 = new JButton();
        JButton resourceButtonThirdShelf3 = new JButton();
        resourceButtonFirstShelf.setPreferredSize(new Dimension(40, 40));
        resourceButtonSecondShelf1.setPreferredSize(new Dimension(40, 40));
        resourceButtonSecondShelf2.setPreferredSize(new Dimension(40, 40));
        resourceButtonThirdShelf1.setPreferredSize(new Dimension(40, 40));
        resourceButtonThirdShelf2.setPreferredSize(new Dimension(40, 40));
        resourceButtonThirdShelf3.setPreferredSize(new Dimension(40, 40));
        firstShelf.add(resourceButtonFirstShelf);
        secondShelf.add(resourceButtonSecondShelf1);
        secondShelf.add(resourceButtonSecondShelf2);
        thirdShelf.add(resourceButtonThirdShelf1);
        thirdShelf.add(resourceButtonThirdShelf2);
        thirdShelf.add(resourceButtonThirdShelf3);
        warehouseSlot.add(firstShelf);
        warehouseSlot.add(voidPanelFirstSecondShelf);
        warehouseSlot.add(secondShelf);
        warehouseSlot.add(voidPanelSecondThirdShelf);
        warehouseSlot.add(thirdShelf);
        // creates the chest JPanel
        JPanel chestSlot = new JPanel();
        chestSlot.setOpaque(false);
        chestSlot.setPreferredSize(new Dimension((1066-800), 200));
        // adds every component to the parent JPanel
        this.warehouse.add(voidPanelStart);
        this.warehouse.add(warehouseSlot);
        this.warehouse.add(chestSlot);

        voidPanelStart.setBorder(BorderFactory.createLineBorder(Color.black));
        warehouseSlot.setBorder(BorderFactory.createLineBorder(Color.black));
        firstShelf.setBorder(BorderFactory.createLineBorder(Color.black));
        secondShelf.setBorder(BorderFactory.createLineBorder(Color.black));
        thirdShelf.setBorder(BorderFactory.createLineBorder(Color.black));
        chestSlot.setBorder(BorderFactory.createLineBorder(Color.black));

        this.warehouse.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void createFaithMap() {
        this.faithMap.setOpaque(false);
        this.faithMap.setPreferredSize(new Dimension(1066, 240));
    }

    private JPanel createWarehouseAndDevCardSlotsRow() throws IOException {
        JPanel warehouseAndDevCardSlotsRow = new JPanel();
        warehouseAndDevCardSlotsRow.setOpaque(false);
        warehouseAndDevCardSlotsRow.setLayout(new FlowLayout());
        createWarehouse();
        warehouseAndDevCardSlotsRow.add(this.warehouse);
        warehouseAndDevCardSlotsRow.add(createDevCardSlots());
        return warehouseAndDevCardSlotsRow;
    }

    private void createBoard() throws IOException {
        setBoardFrameBackGroundImage();
        createFaithMap();
        JPanel warehouseAndDevCardSlotsRow = createWarehouseAndDevCardSlotsRow();
        this.panel.setLayout(new BorderLayout());
        this.panel.add(this.faithMap, BorderLayout.NORTH);
        this.panel.add(warehouseAndDevCardSlotsRow, BorderLayout.SOUTH);
    }
}
