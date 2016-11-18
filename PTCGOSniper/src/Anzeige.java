import java.awt.Component;
import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class Anzeige extends Thread {

	private JFrame frmPtcgosniper;
	public static JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Anzeige window = new Anzeige();
					window.frmPtcgosniper.setVisible(true);
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Anzeige() {
		initialize();
		frmPtcgosniper.setVisible(true);
	}

	public static void addTableRow(int gewinn, String zeit, String ocard, String wcard){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(new Object[]{gewinn, zeit, ocard, wcard});

		for (int column = 0; column < table.getColumnCount(); column++)
		{
		    TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    int preferredWidth = tableColumn.getMinWidth();
		    int maxWidth = tableColumn.getMaxWidth();

		    for (int row = 0; row < table.getRowCount(); row++)
		    {
		        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		        Component c = table.prepareRenderer(cellRenderer, row, column);
		        int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
		        preferredWidth = Math.max(preferredWidth, width);

		        //  We've exceeded the maximum width, no need to check other rows

		        if (preferredWidth >= maxWidth)
		        {
		            preferredWidth = maxWidth;
		            break;
		        }
		    }

		    tableColumn.setPreferredWidth( preferredWidth );
		}		
	}
	

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPtcgosniper = new JFrame();
		frmPtcgosniper.setAlwaysOnTop(true);
		frmPtcgosniper.setTitle("PTCGO-Sniper");
		frmPtcgosniper.setBounds(100, 100, 1915, 190);
		frmPtcgosniper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Gewinn");
		model.addColumn("Restzeit");
		model.addColumn("Karte(n)");
		model.addColumn("Kosten");
		table = new JTable(model);
		addTableRow(1000,"Restzeit","Karte(n)","Kosten");
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        //TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        //table.setRowSorter(sorter);  
        //ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(4);
        //sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
        //sorter.setSortKeys(sortKeys);
        table.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked(MouseEvent  e) 
            {
            	 if (e.getClickCount() == 1) {
            	      JTable target = (JTable)e.getSource();
            	      int row = target.getSelectedRow();
            	      //System.out.println(row);
            	      DefaultTableModel model = (DefaultTableModel) table.getModel();
              		  model.removeRow(row);
            	    }
            }
        });
		frmPtcgosniper.getContentPane().add(table, BorderLayout.NORTH);
		frmPtcgosniper.repaint();
		
	}
	
}
