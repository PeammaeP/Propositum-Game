package Lab_Ch12;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class w12_2_EventLambda extends JFrame
{
    private JPanel              contentpane;
    private JList		list;
    private DefaultListModel listmodel;
    private JButton		add_button, remove_button;

    public w12_2_EventLambda()
    {
	setTitle("This is a new Frame");
	setBounds(200, 200, 350, 250);
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

	contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new BorderLayout() );

	AddComponents();
    }

    public void AddComponents()
    {
	list = new JList( listmodel = new DefaultListModel() );
	list.setVisibleRowCount(10);
	list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
        // Anonymous inner class style
        /*
	list.addListSelectionListener( new ListSelectionListener() {
            public void valueChanged( ListSelectionEvent e )
            {
		if ( !e.getValueIsAdjusting() )
		{
                    Object[] items = list.getSelectedValues();
		}
            }
	});
        */
        
        // Lambda style -- with and without explicit type for parameters
        //list.addListSelectionListener((ListSelectionEvent e) -> {
        list.addListSelectionListener(e -> {        
            if ( !e.getValueIsAdjusting() )
            {
                Object[] items = list.getSelectedValues();
            }            
        });
       

	add_button  = new JButton("Add");
	add_button.addActionListener(e -> {
            String item = JOptionPane.showInputDialog("Enter input");
            listmodel.addElement(item);
	});

	remove_button  = new JButton("Remove");
	remove_button.addActionListener(e -> {
            Object x[] = list.getSelectedValues();
            for (int i=0; i < x.length; i++)
            {
                listmodel.removeElement(x[i]);
            }
	});

	JPanel mpanel = new JPanel();
	mpanel.add( new JScrollPane(list) );

	JPanel spanel = new JPanel();
	spanel.add(add_button);
	spanel.add(remove_button);

	contentpane.add( mpanel, BorderLayout.CENTER );
	contentpane.add( spanel, BorderLayout.SOUTH );
	validate();
    }

    public static void main(String[] args) 
    {
	new w12_2_EventLambda();
    }   
}
