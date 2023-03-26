package main;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//https://junit.org/junit5/docs/5.5.0/api/org/junit/jupiter/api/MethodOrderer.OrderAnnotation.html
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// let @Order works
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightsTest {

	// annotation
	@Test
	@Order(1)

	void test1() throws FileNotFoundException {

		Flights.readFlightFromCSVToFlights();
		// diagnose if the result is expected value, if it is green , pass ; red fail
		assertEquals(Flights.flightList.size(), 949000);
	}

	@Test
	@Order(2)
	void test2() {
		Flights.findUniqueCityFromFlightsListAndInsertIntoCityList();

		assertEquals(Flights.cityList.size(), 26);
	}

	@Test
	@Order(3)
	void test3StringToDate() throws ParseException {

		String aDayStr = "14/02/2023";
		Date resultDate = Flights.StrToDate(aDayStr);
		System.out.println(resultDate);
		System.out.println(resultDate.getTime());

		Calendar cal = Calendar.getInstance();// == new Calander();
		cal.set(2023, 1, 14, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0); 
		Date day20230214 = cal.getTime(); 
		System.out.println(day20230214);
		System.out.println(day20230214.getTime());

		assertEquals(resultDate, day20230214);
	}

	@Test
	@Order(4)
	void test4DateToString() throws ParseException {
		Calendar cal = Calendar.getInstance(); // == new Calander();
		cal.set(2023, 1, 14, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0); 

		Date day20230214 = cal.getTime();
		String resultStr = Flights.DateToStr(day20230214);
		System.out.println(resultStr);

		// 如果計算出來的結果不是"14/02/2023"就报错
		assertEquals(resultStr, "14/02/2023");
	}

	@Test
	@Order(5)
	void test5IfSelectedDatesAreLogical() throws ParseException {
		// test if any date parameter is empty
		Date goToDay = null;
		Date goBackDay = Flights.getTodayDate();
		String result = Flights.ifSelectedDatesAreLogical(goToDay, goBackDay);
		assertEquals(result, "Please enter departure date and return date");

		goToDay = Flights.getTodayDate();
		goBackDay = null;
		result = Flights.ifSelectedDatesAreLogical(goToDay, goBackDay);
		assertEquals(result, "Please enter departure date and return date");

		// test if selected past time
		goToDay = Flights.StrToDate("12/03/2023");
		goBackDay = Flights.getTodayDate();
		result = Flights.ifSelectedDatesAreLogical(goToDay, goBackDay);
		assertEquals(result, "Departure date and return date must not have already passed");

		// test if return date is before departure date
		goToDay = Flights.StrToDate("12/12/2023"); 
		goBackDay = Flights.StrToDate("11/11/2023"); 
		result = Flights.ifSelectedDatesAreLogical(goToDay, goBackDay);
		System.out.println(result);
		assertEquals(result, "Return date must be after departure date");
	}

	@Test
	@Order(6)
	void test6InitializeCityList() {
		Flights.findUniqueCityFromFlightsListAndInsertIntoCityList();
		ArrayList<String> result = Flights.cityList;
		assertEquals(result.size(), 26);
	}

	@Test
	@Order(7)
	void test7SearchFlights() throws FileNotFoundException {

		String whichDay = "31/03/2023";
		String departureCity = "Dubai";
		String arrivalCity = "Mexico";

		ArrayList<Flight> resultList = Flights.searchFlights(whichDay, departureCity, arrivalCity);
		// at here, it will print 4
		System.out.println("flight goTo list size "+resultList.size());
		for (int i = 0; i < resultList.size(); i++) {
			System.out.println(resultList.get(i).id);
		}
		// in excel, it count from 1, in code , count from 0
		assertEquals(resultList.size(), 4);
		assertEquals(resultList.get(0).id, 359248);
		assertEquals(resultList.get(1).id, 359249);
		assertEquals(resultList.get(2).id, 359250);
		assertEquals(resultList.get(3).id, 359251);

		whichDay = "01/04/2023";
		departureCity = "Mexico";
		arrivalCity = "Dubai";

		resultList = Flights.searchFlights(whichDay, departureCity, arrivalCity);
		System.out.println("flight goBack list size "+resultList.size());
		for (int i = 0; i < resultList.size(); i++) {
			System.out.println(resultList.get(i).id);
		}
		// in excel, it count from 1, in code , count from 0
		assertEquals(resultList.size(), 4);
		assertEquals(resultList.get(0).id, 362716);
		assertEquals(resultList.get(1).id, 362717);
		assertEquals(resultList.get(2).id, 362718);
		assertEquals(resultList.get(3).id, 362719);

	}

}
