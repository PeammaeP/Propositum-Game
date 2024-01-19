package Lab_Ch10;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class w10_21_SemanticEvent extends JFrame
{
    private JPanel        contentpane;
    private MyPanel drawpane;
    private JButton	  add_button, rem_button;
    private JComboBox	  combo;
    private JRadioButton  [] radio;

    private Integer [] items = {1, 2, 3, 4};

    public w10_21_SemanticEvent()
    {
	setTitle("This is a Frame");
	setBounds(300, 200, 700, 400);
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );

	contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new FlowLayout() );
	AddComponents();
    }

    public void AddComponents()
    {
	// ----- (1) anonymous listener classes (implicitly implement listeners)
        String local = "local";
        final String finlocal = "final local";
        
	add_button = new JButton("Add");
	add_button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                java.util.Date date = new java.util.Date();
                date.setTime(e.getWhen());
                System.out.println(e.getActionCommand() + " >>> " + date.toString());

                System.out.printf("%s %s \n", local, finlocal);
                
                drawpane.doAdd();
            }
        });

        // local = "new value";
        
	rem_button = new JButton("Remove");
	rem_button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
		drawpane.doRemove();
            }
	});


	// ----- (2) get no. of birds from combo (use ItemEvent or ActionEvent)
	combo = new JComboBox(items);
	combo.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e )
            {
		int index = combo.getSelectedIndex();
		drawpane.setCount( items[index] );
            }
	});


	// ----- (3) change from combo to radio buttons (use ItemEvent or ChangeEvent)
	//           notice how we check user's selection in (3) and (4)
	
        radio = new JRadioButton[4];
        JPanel rpanel	   = new JPanel();
        ButtonGroup rgroup = new ButtonGroup();
        int outsideloop    = 0;
        for (int i=0; i < 4; i++) 
        {
            int insideloop = i;             // treated as diff variables in diff iterations
            
            radio[i] = new JRadioButton( items[i].toString() );
            if (i == 0) radio[i].setSelected(true);
            rgroup.add( radio[i] );
            rpanel.add( radio[i] );

            radio[i].addItemListener( new ItemListener() {
                @Override
		public void itemStateChanged( ItemEvent e )
		{
                    JRadioButton temp = (JRadioButton)e.getItem();
                    if (temp.isSelected())
                    {
                        System.out.printf("outside = %d, inside = %d \n", outsideloop, insideloop);
                        
			int count = Integer.parseInt( temp.getText() );
			drawpane.setCount( count );
                    }
                    //outsideloop++;        // updated inside inner class
                    //insideloop++;         // updated inside inner class
		}
            });	
            
            //outsideloop++;        // updated inside for-loop (enclosing method)
            //insideloop++;         // updated inside for-loop (enclosing method)
	}
        //outsideloop++;            // updated inside enclosing method
	


	drawpane = new MyPanel();
		
	contentpane.add(add_button);
	contentpane.add(rem_button);
	//contentpane.add(combo);				// for (3)
	contentpane.add(rpanel);			// for (4)
	contentpane.add(drawpane);

	validate();
    }


    public static void main(String[] args) 
    {
	new w10_2_SemanticEvent();
    }
};

////////////////////////////////////////////////////////////////////////////////////

// ----- (1) a pane for drawing birds

class MyPanel extends JPanel
{
    private java.util.Random  random;
    private int		      count = 1;

    private String   path  = "src/main/Java/Lab_Ch10/";
    private String[] birds = {"black.png", "blue.png", "green.png", 
		              "red.png", "white.png", "yellow.png"};

    public MyPanel()
    {
	setPreferredSize( new Dimension(550, 310) );
	random = new java.util.Random();	
    }

    public void setCount( int c )	{ count = c; }

    public void doAdd()
    {
	for (int i = 0; i < count; i++)
	{
            int r = random.nextInt(6);
            JLabel label = new JLabel( new ImageIcon(path + birds[r]) );
            add(label);
            validate();
	}
    }

    public void doRemove()
    {
        for (int i = 0; i < count; i++)
        {
            if (getComponentCount() > 0)
            {
                Component clabel = getComponent(0);
                remove( clabel );

                // ----- (4) try with and without validate
                validate();		
                repaint();	
            }
        }
    }
};
