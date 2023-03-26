package main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class FlightFrame extends JFrame {

	private JPanel contentPane;
	private DatePicker departDatePicker;
	private DatePicker returnDatePicker;
	private JComboBox fromComboBox;
	private JComboBox toComboBox;

	private static DefaultComboBoxModel<String> fromCityBoxModel = new DefaultComboBoxModel<>();
	private static DefaultComboBoxModel<String> toCityBoxModel = new DefaultComboBoxModel<>();

	// set two drop down box filled with city names
	public static void fillCityNamesIn2ComboBox(ArrayList<String> cityList) {

		for (int i = 0; i < cityList.size(); i++) {
			String tmpString = cityList.get(i);
			fromCityBoxModel.addElement(tmpString);
			toCityBoxModel.addElement(tmpString);
		}
	}

	/**
	 * Create the frame.
	 */
	public FlightFrame() {
		// when click close button the program will exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setBonds() means set the frame size, there are 4 parameters inside
		// setBounds() methods which are:
		// 1. the distance from the upper left side of frame to the screen edge on
		// x-axis
		// 2.the distance from the upper left side of frame to the screen edge on y-axis
		// 3.the width of the frame
		// 4.the height of the frame
		setBounds(100, 100, 1024, 512);

		// create a contentPane
		contentPane = new JPanel();

		// set the edge of the contentPane as 5 pixel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// set contentPane inside the frame
		setContentPane(contentPane);

		// absolute layout means what you see is what you get
		contentPane.setLayout(null);

		JButton shopBtn = new JButton("Shop");
		shopBtn.setBounds(348, 35, 117, 29);
		contentPane.add(shopBtn);

		JButton ResBtn = new JButton("Restaurnat");
		ResBtn.setBounds(500, 35, 117, 29);
		contentPane.add(ResBtn);

		JButton FinanceBtn = new JButton("Finance");
		FinanceBtn.setBounds(666, 35, 117, 29);
		contentPane.add(FinanceBtn);

		DatePickerSettings settings = new DatePickerSettings(Locale.ENGLISH);

		// instantiate the DatePicker component, reason of doing this is to set the
		// language of the component into English
		departDatePicker = new DatePicker(settings);
		departDatePicker.setDateToToday();

		// format of the detePicker into the style like this :"dd/MM/yyyy" ;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		departDatePicker.getSettings().setFormatForDatesCommonEra(dateFormat);

		departDatePicker.setBounds(105, 260, 200, 30);
		contentPane.add(departDatePicker);

		DatePickerSettings returnSetting = new DatePickerSettings(Locale.ENGLISH);
		returnDatePicker = new DatePicker(returnSetting);
		returnDatePicker.setDateToToday();

		DateTimeFormatter returnDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		returnDatePicker.getSettings().setFormatForDatesCommonEra(returnDateFormat);

		returnDatePicker.setBounds(500, 260, 200, 30);
		contentPane.add(returnDatePicker);

		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("The departure date chosen is："
						+ Flights.formatDateIntoSlash(departDatePicker.getDateStringOrEmptyString()));
				System.out.println("The return date chosen is："
						+ Flights.formatDateIntoSlash(returnDatePicker.getDateStringOrEmptyString()));

				// get String date from datePicker and convert "-" into "/";
				String goToDayStr = Flights.formatDateIntoSlash(departDatePicker.getDateStringOrEmptyString());
				String goBackDayStr = Flights.formatDateIntoSlash(returnDatePicker.getDateStringOrEmptyString());

				// because String data type of date cannot compare each other, so it has to be
				// transformed into Date data type

				Date goToDay = null;
				Date goBackDay = null;

				try {
					goToDay = Flights.StrToDate(goToDayStr);
					goBackDay = Flights.StrToDate(goBackDayStr);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				String result = Flights.ifSelectedDatesAreLogical(goToDay, goBackDay);
				if (!result.equals("ok")) {
					// this method showMessageDialog() has 4 parameters:
					// 1. null (i don't understand)
					// 2. the content of the message
					// 3. the title of the dialog
					// 4. the type of the dialog (there is a warning image inside)
					
					JOptionPane.showMessageDialog(null, result, "Message", JOptionPane.WARNING_MESSAGE);
				} else {
					// search goTo flights
					String whichDay = Flights.DateToStr(goToDay);
					String departureCity = (String) fromComboBox.getSelectedItem();
					String arrivalCity = (String) toComboBox.getSelectedItem();
					ArrayList<Flight> goToFlights = Flights.searchFlights(whichDay, departureCity, arrivalCity);
					System.out.println("there are "+goToFlights.size()+" departure flights :");
					for (int i = 0; i < goToFlights.size(); i++) {
						System.out.println("the flight number is "+goToFlights.get(i).flightNumber+" the flight id is"+goToFlights.get(i).id);

					}
					// search goBack flights
					whichDay = Flights.DateToStr(goBackDay);
					departureCity = (String) toComboBox.getSelectedItem();
					arrivalCity = (String) fromComboBox.getSelectedItem();
					ArrayList<Flight> goBackFlights = Flights.searchFlights(whichDay, departureCity, arrivalCity);
					System.out.println("there are "+goBackFlights.size()+" return flights :");

					for (int i = 0; i < goBackFlights.size(); i++) {
						System.out.println("the flight number is "+goBackFlights.get(i).flightNumber+" the flight id is"+goBackFlights.get(i).id);
					}
					
					// instantiate searchFrame
					SearchFrame searchFrame = new SearchFrame();
					// when click the close button at search frame , it won't exit the whole program, just close searchFrame
					searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					// allow user see seearchFrame
					searchFrame.setVisible(true);

					// TODO
					// fill goToFlightsTable, goBackFlightsTable

				}

			}
		});
		searchBtn.setBounds(302, 396, 117, 29);
		contentPane.add(searchBtn);

		JLabel fromLabel = new JLabel("From");
		fromLabel.setBounds(42, 169, 61, 16);
		contentPane.add(fromLabel);

		JLabel toLabel = new JLabel("To");
		toLabel.setBounds(446, 169, 61, 16);
		contentPane.add(toLabel);

		JLabel departLabel = new JLabel("Depart");
		departLabel.setBounds(26, 270, 61, 16);
		contentPane.add(departLabel);

		JLabel returnLabel = new JLabel("Return");
		returnLabel.setBounds(429, 270, 61, 16);
		contentPane.add(returnLabel);

		// departure city drop down box

		fromComboBox = new JComboBox(fromCityBoxModel);
		fromComboBox.setBounds(95, 165, 144, 29);
		contentPane.add(fromComboBox);

		// arrival city drop down box
		toComboBox = new JComboBox(toCityBoxModel);
		toComboBox.setBounds(500, 165, 133, 29);
		contentPane.add(toComboBox);

		JLabel lblNewLabel = new JLabel("New label");
//		getResource means find the directory of the reference file in the hard disk
		lblNewLabel.setIcon(new ImageIcon(FlightFrame.class.getResource("/res/logo.jpg")));
		lblNewLabel.setBounds(6, 6, 342, 151);
		contentPane.add(lblNewLabel);

	}
}
