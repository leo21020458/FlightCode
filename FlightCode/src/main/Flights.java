package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Flights {

	public static ArrayList<Flight> flightList = new ArrayList<Flight>();

	public static ArrayList<String> cityList = new ArrayList<String>();

	public static ArrayList<String> sortedCityList = new ArrayList<String>();

	// user selected flight goTo . from the top table of search frame
	public static Flight selectedFlightGoTo;
	// user selecte flight goBack from the bottom table of search frame
	public static Flight selectedFlightGoBack;

	public static void readFlightFromCSVToFlights() throws FileNotFoundException {

		// find root directory URL when program is running ; "/" means runtime location
		/*
		 * Because the program will be compiled and run under the bin directory, its
		 * suffix will be changed from .java to .class
		 * Flights.java is under the main directory of src, Flights.class is under the
		 * main directory of bin, so "getResource("/")" means to obtain The runtime root
		 * of the directory where this file is located
		 */
		URL projectRootDirectoryURL = Flights.class.getResource("/");

		// "getPath()" method means get runtime absolute location relative to the computer
		String projectAbsDir = projectRootDirectoryURL.getPath();
		System.out.println("The absolute address of the directory where the project is located is：" + projectAbsDir);

		File csv = new File(projectAbsDir + "res/Flights.csv");
		// get access to the reader handle
		Scanner reader = new Scanner(csv);

		// csv stands for comma separate value;
		// Loop through the data , grab each row, split into columns , save the data
		int i = 0;
		while (reader.hasNextLine()) {

			String row = reader.nextLine();

			String[] col = row.split(",");
//			System.out.println(row);
			// print the unique value for the "col"

			// apply filters
			Flight myFlight = new Flight();
			myFlight.id = i;
			myFlight.dateOfFlight = col[0];
			myFlight.departureTime = col[1];
			myFlight.arrivalTime = col[2];
			myFlight.flightDuration = Float.parseFloat(col[3]);
			myFlight.distanceTravelled = Float.parseFloat(col[4]);
			myFlight.delay = Integer.parseInt(col[5]);
			myFlight.departureAirport = col[6];
			myFlight.departureCity = col[7];
			myFlight.arrivalAirport = col[8];
			myFlight.arrivalCity = col[9];
			myFlight.flightNumber = col[10];
			myFlight.airline = col[11];

			flightList.add(myFlight);
			i++;

		}
		// release memory
		reader.close();
		System.out.println("total read " + i + " lines of data");
	}

	// Method: Analyze the flight list and add the departure and landing cities to the city list,
    //with unique list elements, sorted in alphabetical order.
	public static void findUniqueCityFromFlightsListAndInsertIntoCityList() {
		for (int i = 0; i < flightList.size(); i++) {
			Flight tempFlight = flightList.get(i);
			if (!cityList.contains(tempFlight.departureCity)) {
				cityList.add(tempFlight.departureCity);
			}
			if (!cityList.contains(tempFlight.arrivalCity)) {
				cityList.add(tempFlight.arrivalCity);
			}
		}

		Collections.sort(cityList);
	}

	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String str = format.format(date);
		return str;
	}

	public static Date StrToDate(String str) throws ParseException {
		// dd means 2-digital day, if today is 9th, dd will solve it as 09;
		// MM means 2-digital month, if this month is Feb, MM will solve it as 02;
		// yyyy means 4-digital year.
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;

		date = format.parse(str);

		return date;
	}

	public static String formatDateIntoSlash(String dayStr) {
		String result = null;
		String[] splitedList = dayStr.split("-");
		// add day month year to result
		result = splitedList[2] + "/" + splitedList[1] + "/" + splitedList[0];
		return result;
	}

	public static Date getTodayDate() {
		Date today = new Date();
		// instantiate a Calendar Object
		Calendar cal = Calendar.getInstance();
		// set cal to today(year, month , day, hour, minute , second) , most cases in this object, hour, minute, second is not equals to 0
		cal.setTime(today);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// millisecond is smaller than second
		cal.set(Calendar.MILLISECOND, 0);
		Date today0 = cal.getTime();
		return today0;

	}

	public static String ifSelectedDatesAreLogical(Date goToDay, Date goBackDay) {
		String resultSentenceNull = "Please enter departure date and return date";
		String resultSentenceBeforeToday = "Departure date and return date must not have already passed";
		String resultSentenceAfter = "Return date must be after departure date";
		if (goToDay == null || goBackDay == null) {
			return resultSentenceNull;
		}
		Date today = getTodayDate();

//		System.out.println(DateToStr(today));
		if (goToDay.before(today) || goBackDay.before(today)) {
			return resultSentenceBeforeToday;
		}
		if (goBackDay.before(goToDay)) {
			return resultSentenceAfter;
		}

		return "ok";

	}

	public static ArrayList<Flight> searchFlights(String whichDay, String fromCity, String toCity) {
		ArrayList<Flight> resultList = new ArrayList<Flight>();

		for (int i = 0; i < flightList.size(); i++) {
			Flight tmpFlight = flightList.get(i);

			if (whichDay.equals(tmpFlight.dateOfFlight)

					&& (fromCity.equals(tmpFlight.departureCity))

					&& (toCity.equals(tmpFlight.arrivalCity))

			) {
				resultList.add(tmpFlight);
			}
			;
		}
		return resultList;
	}

	/**
	 * Create the frame.
	 */

	public static void main(String[] args) throws FileNotFoundException {
		Flights.readFlightFromCSVToFlights();
		Flights.findUniqueCityFromFlightsListAndInsertIntoCityList();
		FlightFrame flightFrame = new FlightFrame();

		flightFrame.setVisible(true);
		FlightFrame.fillCityNamesIn2ComboBox(cityList);

	}

}
