package main;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class SearchFrame extends JFrame {

	private JPanel contentPane;
	private JTable goToFlightsTable;
	private JTable goBackFlightsTable;

//iterate goToFlightList, use each flight to add into the table 
	public static void fillGoToFlightsTable(ArrayList<Flight> goToFlightList) {

		for (int i = 0; i < goToFlightList.size(); i++) {
			Flight tmpFlight = goToFlightList.get(i);
			// TODO
		}

	}

// same as above method
	public static void fillGoBackFlightsTable(ArrayList<Flight> goBackFlightList) {

		for (int i = 0; i < goBackFlightList.size(); i++) {
			Flight tmpFlight = goBackFlightList.get(i);

			// TODO
		}

	}

	/**
	 * Create the frame.
	 */
	public SearchFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Available Flights");
		lblNewLabel.setForeground(new Color(0, 150, 255));
		lblNewLabel.setBounds(36, 22, 134, 16);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Departures");
		lblNewLabel_1.setForeground(new Color(0, 150, 255));
		lblNewLabel_1.setBounds(36, 50, 97, 16);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Returns");
		lblNewLabel_2.setForeground(new Color(0, 150, 255));
		lblNewLabel_2.setBounds(36, 285, 97, 16);
		contentPane.add(lblNewLabel_2);

		// table header
		String[] tableHeaderOfGoTo = { "Id", "Departing From", "Airport", "Arriving To", "Airport", "Departure Time",
				"Arrival Time", "Flight Number" };

		// create table
		goToFlightsTable = new JTable();
		goToFlightsTable.setBounds(46, 78, 944, 195);
		contentPane.add(goToFlightsTable);

		String[] tableHeaderOfGoBack = { "Id", "Departing From", "Airport", "Arriving To", "Airport", "Departure Time",
				"Arrival Time", "Flight Number" };

		goBackFlightsTable = new JTable();

		goBackFlightsTable.setBounds(46, 323, 944, 200);
		contentPane.add(goBackFlightsTable);

		JLabel lblNewLabel_3 = new JLabel("Select Number of Tickets");
		lblNewLabel_3.setForeground(new Color(0, 150, 255));
		lblNewLabel_3.setBounds(36, 547, 187, 16);
		contentPane.add(lblNewLabel_3);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "1" }));
		comboBox.setBounds(197, 547, 52, 27);
		contentPane.add(comboBox);

		JButton viewSeattingPlanBtn = new JButton("View Seatting Plan");
		viewSeattingPlanBtn.setForeground(new Color(0, 0, 0));
		viewSeattingPlanBtn.setBackground(new Color(4, 50, 255));
		viewSeattingPlanBtn.setBounds(30, 593, 219, 27);
		contentPane.add(viewSeattingPlanBtn);

		JButton purchaseBtn = new JButton("Purchase");
		purchaseBtn.setForeground(new Color(0, 0, 0));
		purchaseBtn.setBackground(new Color(4, 50, 255));
		purchaseBtn.setBounds(30, 658, 219, 27);
		contentPane.add(purchaseBtn);

		JLabel lblNewLabel_4 = new JLabel("Departing Seats:");
		lblNewLabel_4.setForeground(new Color(0, 150, 255));
		lblNewLabel_4.setBounds(521, 551, 227, 27);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_4_1 = new JLabel("Returning Seats:");
		lblNewLabel_4_1.setForeground(new Color(0, 150, 255));
		lblNewLabel_4_1.setBounds(521, 625, 227, 27);
		contentPane.add(lblNewLabel_4_1);

	}
}
