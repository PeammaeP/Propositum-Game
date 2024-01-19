//6513136 Mahannop Thabua
package Ex8_136;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

class MainApplication extends JFrame {
    // components
    private JPanel contentpane;
    private JLabel drawpane;
    private JComboBox combo;
    private JToggleButton[] tb;
    private JButton moveButton, stopButton, itemButton;
    private JTextField scoreText;
    private MyImageIcon backgroundImg;
    private MySoundEffect themeSound;
    private WormLabel wormLabel;
    private Thread itemThread;
    private ArrayList<ItemLabel> activatedItemsThread = new ArrayList<>();
    private MainApplication currentFrame;
    private final int frame_width = MyConstants.FRAMEWIDTH;
    private final int frame_height = MyConstants.FRAMEHEIGHT;
    private int score;
    //private MyWindowListener wormGameListener;
    private ButtonGroup MyButtonGroup;

    public static void main(String[] args) {
        new MainApplication();
    }

    //--------------------------------------------------------------------------
    public MainApplication() {
        setTitle("Worm Game");
        setSize(frame_width, frame_height);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        currentFrame = this;

        // (1) Add WindowListener (anonymous class)
        //     - Stop everything. Show total score
        this.addWindowListener(new WindowAdapter() {
            @Override
                public void windowClosing(WindowEvent e) {
                    themeSound.stop();
                    wormLabel.setMove(false);

                for (ItemLabel item : activatedItemsThread) {
                    item.setItemMove(false);
                }
                TheDialogOfScore.show("Total score = " + score);
            }
        });

        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        AddComponents();
    }

    //--------------------------------------------------------------------------
    public void AddComponents() {
        backgroundImg = new MyImageIcon(MyConstants.FILE_BG).resize(frame_width, frame_height);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

        themeSound = new MySoundEffect(MyConstants.FILE_THEME);
        themeSound.playLoop();
        themeSound.setVolume(0.4f);
        wormLabel = new WormLabel(currentFrame);
        drawpane.add(wormLabel);

        // (2) Add ActionListener (anonymous class) to moveButton
        // - If Worm isn't moving, create wormThread to make it move
        moveButton = new JButton("Move");
        moveButton.addActionListener(e -> {
              if(!wormLabel.isMove()) {
                  setWormThread();
                  wormLabel.setMove(true);
              }
        });

        // (3) Add ActionListener (anonymous class) to stopButton
        // - Stop wormThread, i.e. make it return from method run
        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
              wormLabel.setMove(false);
        });

        // (4) Add ItemListener (anonymouse class) to combo
        // - Set Worm's speed, i.e. sleeping time for wormThread
        String[] speed = {"fast", "medium", "slow"};
        combo = new JComboBox(speed);
        combo.setSelectedIndex(1);
        //select the ComboBox with 1 option
        combo.addItemListener(e -> {
            if(combo.getSelectedIndex() == 0) {
                wormLabel.setSpeed(250);
            }
            else if(combo.getSelectedIndex() == 1) {
                wormLabel.setSpeed(500);
            }
            else if(combo.getSelectedIndex() == 2) {
                wormLabel.setSpeed(1000);
            }
        });

        // (5) Add ItemListener ( anonymouse class ) to tb[i]
        // - Make Worm turn left/right, only 1 radio button can be selected at a time
        tb = new JToggleButton[2];
        MyButtonGroup = new ButtonGroup();
        tb[0] = new JRadioButton("Left");
        tb[0].setName("Left");
        tb[1] = new JRadioButton("Right");
        tb[1].setName("Right");
        tb[0].setSelected(true);
        //Add Left Button with Anonymous Class
        tb[0].addItemListener(e -> wormLabel.turnLeft());
        //Add Right Button with Anonymous Class
        tb[1].addItemListener(e -> wormLabel.turnRight());

        for(int i=0; i<2; i++) {
            MyButtonGroup.add(tb[i]);
        }

        // (6) Add ActionListener (anonymous class) to itemButton
        // - Create a new itemThread
        itemButton = new JButton("More item");
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setItemThread();
            }
        });

        scoreText = new JTextField("0", 5);
        scoreText.setEditable(true);

        JPanel control = new JPanel();
        control.setBounds(0, 0, 1000, 50);
        control.add(new JLabel("Worm Control : "));
        control.add(moveButton);
        control.add(stopButton);
        control.add(combo);
        control.add(tb[0]);
        control.add(tb[1]);
        control.add(new JLabel("                 "));
        control.add(new JLabel("Item Control : "));
        control.add(itemButton);
        control.add(new JLabel("                 "));
        control.add(new JLabel("Score : "));
        control.add(scoreText);
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
    }

    //--------------------------------------------------------------------------
    public void setWormThread() {
        // end run
        Thread wormThread = new Thread(() -> {
            while (wormLabel.isMove()) {
                wormLabel.updateLocation();
            }
        }); // end thread creation
        wormThread.start();
    }

    //--------------------------------------------------------------------------
    public void setItemThread() {
        ItemLabel MyItems = new ItemLabel(currentFrame);
        drawpane.add(MyItems);
        activatedItemsThread.add(MyItems);

        // end run
        itemThread = new Thread(() -> {
            MyItems.setItemMove(true);
            // (7) Create a new ItemLabel, add it to drawpane
            //     - Keep updating its location
            //     - Check whether it collides with Worm. If it does,
            //       play hit sound and update score
            //     - Once reaching the top/bottom or colliding with Worm,
            //       remove the item from drawpane and end this thread

            while(MyItems.isItemMove() && MyItems.getLocation().y >= 0 || MyItems.getLocation().y <= currentFrame.getHeight()) {
                MyItems.updateLocation();

            if(MyItems.getBounds().intersects(wormLabel.getBounds())) {
                MyItems.playHitSound();

                if(MyItems.getItemType() == 0) {
                    updateScore(1);
                }
                else if(MyItems.getItemType() == 1) {
                    updateScore(-1);
                }
                break;
            }
          }
         repaint();
         drawpane.remove(MyItems);
        }); // end thread creation
        itemThread.start();
    }
    //--------------------------------------------------------------------------
    synchronized public void updateScore(int score) {
        // (8) Score update must be synchronized since it can be done by >1 itemThreads
        this.score += score;
        this.scoreText.setText(String.valueOf(this.score));
    }

    //using to complete the WindowListener class in (1)
    /*class MyWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            score = contentpane.getComponentCount();
            themeSound.stop();
            wormLabel.setMove(false);
            TheDialogOfScore.show("Total score = " + score);
        }
    }*/
    //using to show the dialog of message in (1)
    static class TheDialogOfScore {
        public static void show(String message) {
            JOptionPane.showMessageDialog(new JFrame(), message,
                    "Worm Game", JOptionPane.INFORMATION_MESSAGE );
        }
    }

} // end class MainApplication


    ////////////////////////////////////////////////////////////////////////////////
    class WormLabel extends JLabel {
        private MainApplication parentFrame;
        private MyImageIcon leftImg, rightImg;

        private int width = MyConstants.WORMWIDTH;
        private int height = MyConstants.WORMHEIGHT;
        private int curX = 700, curY = 200;
        private int speed = 500;
        private boolean left = true, move = false;

        public WormLabel(MainApplication pf) {
            parentFrame = pf;
            leftImg = new MyImageIcon(MyConstants.FILE_WORM_LEFT).resize(width, height);
            rightImg = new MyImageIcon(MyConstants.FILE_WORM_RIGHT).resize(width, height);
            setIcon(leftImg);
            setBounds(curX, curY, width, height);
        }

        public void setSpeed(int s) {
            speed = s;
        }

        public void turnLeft() {
            setIcon(leftImg);
            left = true;
        }

        public void turnRight() {
            setIcon(rightImg);
            left = false;
        }

        public void setMove(boolean m) {
            move = m;
        }

        public boolean isMove() {
            return move;
        }

        public void updateLocation() {
            if (left) {
                curX = curX - 50;
                if (curX < -100) {
                    curX = parentFrame.getWidth();
                }
            } else {
                curX = curX + 50;
                if (curX > parentFrame.getWidth() - 100) {
                    curX = 0;
                }
            }
            setLocation(curX, curY);
            repaint();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // end class WormLabel

    ////////////////////////////////////////////////////////////////////////////////
    class ItemLabel extends JLabel {
        private MainApplication parentFrame;
        private int type;                // 0 = good item (falling down), 1 = bad item (floating up)
        private MyImageIcon itemImg;
        private MySoundEffect hitSound;
        String[] imageFiles = {MyConstants.FILE_LEAF, MyConstants.FILE_SCORPION};
        String[] soundFiles = {MyConstants.FILE_COIN, MyConstants.FILE_KNIFE};

        private int width = MyConstants.ITEMWIDTH;
        private int height = MyConstants.ITEMHEIGHT;
        private int curX, curY;
        private int speed = 400;
        private static final int THREAD_MOVE = 50;
        private boolean move = false;

        public ItemLabel(MainApplication pf) {
            parentFrame = pf;
            curX = (int) (Math.random() * 5555) % (parentFrame.getWidth() - 100);
            if (curX % 2 == 0) {
                type = 0;
                curY = 0;
            } else {
                type = 1;
                curY = parentFrame.getHeight();
            }

            itemImg = new MyImageIcon(imageFiles[type]).resize(width, height);
            hitSound = new MySoundEffect(soundFiles[type]);
            setIcon(itemImg);
            setBounds(curX, curY, width, height);
        }

        public void setItemMove(boolean s) {
            move = s;
        }

        public boolean isItemMove() {
            return move;
        }

        public void updateLocation() {
            if(isItemMove()) {

            if(type == 0) {
            curY = curY + THREAD_MOVE;
            setLocation(curX,curY);
            }

            else if(type == 1) {
                curY = curY - THREAD_MOVE;
                setLocation(curX,curY);
            }

            repaint();

            try { Thread.sleep(speed); }
            catch(InterruptedException e) {
                e.printStackTrace();
               }
            }
        }
        public void playHitSound() {
            hitSound.playOnce();
        }
        public int getItemType() {
            return type;
        }

} // end class ItemLabel
