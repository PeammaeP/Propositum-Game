package Project3_136.GameGUI;

import Project3_136.MainClass.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;

public class GameLeaderBoard extends GameLayeredPane {
    GameButton MainMenuButton;
    public static WriteScoreClass MyScoreFile;
    public static JTable MyTable;
    public static DefaultTableModel MyTableModel;
    JTableHeader MyTableHeader;
    JScrollPane MyScrollPane;
    JPanel MyButtonPanel;
    JLabel MyTopText;
    public static final String[] MyColumnsTableName = { "Rank", "Username" ,"Score", "Difficulty" };
    public GameLeaderBoard(MySoundEffectControl MySound) {
        super(MySound);
    }
    @Override
    public void addComponent() {
        try {
            MyScoreFile = new WriteScoreClass();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Setting the Background Image
        addBackgroundImage("background.png");

        //Setting the Top Text
        MyTopText = new JLabel(new MyImageIcon(MyComponentPreGamePath + "Leaderboard.png"));
        MyTopText.setOpaque(false);
        MyTopText.setBounds(20,30,width,height);
        MyTopText.setHorizontalAlignment(JLabel.LEFT);
        MyTopText.setVerticalAlignment(JLabel.TOP);
        add(MyTopText,JLayeredPane.DRAG_LAYER);

        //Creating The ButtonPanel Area
        MyButtonPanel = new JPanel();
        MyButtonPanel.setOpaque(false);
        MyButtonPanel.setBounds(20,height-80,width-40,70);
        MyButtonPanel.setLayout(new BorderLayout());

        //Main menu Button
        MainMenuButton = new GameButton("mainMenu",true);
        MainMenuButton.setSoundControl(MySound);
        MyButtonPanel.add(MainMenuButton,BorderLayout.EAST);

        //Creating Score Table
        MyScoreFile.FileReading();
        MyTable = new JTable(MyScoreFile.getGameData(),MyColumnsTableName);
        MyTable.setFont(new Font("Arial",Font.PLAIN,18));
        MyTable.setBackground(Color.lightGray);
        MyTable.setRowHeight(40);
        MyTable.setShowGrid(true);
        MyTable.setGridColor(Color.black);

        //Create JTable , Header , ScrollPane
        MyTableHeader = MyTable.getTableHeader();
        MyTableHeader.setBackground(Color.WHITE);
        MyTableHeader.setForeground(Color.lightGray);
        MyTableHeader.setFont(new Font("Arial",Font.BOLD,30));

        MyScrollPane = new JScrollPane(MyTable);
        MyScrollPane.setBounds(20, 100, width - 40, height - 200);
        MyScrollPane.setOpaque(false);

        add(MyScrollPane,JLayeredPane.DRAG_LAYER);
        add(MyButtonPanel,JLayeredPane.DRAG_LAYER);
    }
    public static void updateGameTable() {
        MyScoreFile.RowIncreasing();
        MyScoreFile.FileReading();
        MyTableModel = new DefaultTableModel(MyScoreFile.getGameData(),MyColumnsTableName);
        MyTable.setModel(MyTableModel);
    }
    public GameButton getMainMenuButton() {
        return MainMenuButton;
    }
}
