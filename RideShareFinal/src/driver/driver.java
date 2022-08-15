package driver;

import service.rideService;
import service.userService;
import model.ride;
import model.user;
import model.vehicle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import static service.rideService.offerRide;

public class driver {

	public static void main(String[] args) throws Exception {

		//This variable will store the path of the input file name
		String filePath ;

		Scanner sc= new Scanner(System.in);
		System.out.print("Enter a Input file path:");
		filePath= sc.nextLine();
		System.out.println();

		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		while ((st = br.readLine()) != null) //parsing line by line of the input file till the EOF
			driver.parser(st);
			

	}

	//Utility function to parse the line of the file
	private static void parser(String st) throws Exception {

		if(st.length()==0){
			//Ignore Empty Lines in input file
			return;
		}
		else if(st.startsWith("/")){
			//Ignore Comments in input file
			return;
		}

		System.out.println(st);

		String[] param =  driver.paramParser(st);

		if(st.startsWith("add_user")){
			user u =userService.createUser(param[0], Integer.parseInt(param[2]), param[1]);
		}
		else if(st.startsWith("add_vehicle")){
			user u = userService.getUserByName(param[0]);
			vehicle v1 =userService.addVehicle(u, param[0],param[1], param[2]);
		}
		else if(st.startsWith("offer_ride")){
			ride r1 = rideService.offerRide(param[0],param[1], param[2], Integer.parseInt(param[3]), param[4], param[5]);
		}
		else if(st.startsWith("select_ride")){
			ride rs1 = rideService.selectRide(param[0], param[1], param[2], Integer.parseInt(param[3]),param[4]);
		}
		else if(st.startsWith("end_ride")){
			rideService.endRide(param[0],param[1]);
		}
		else if(st.startsWith("print_ride_stats()")){
			rideService.totalRidesByUser();
		}

		System.out.println();

		return;
	}

	//Utility function to parse the parameter
	private static String[] paramParser(String st){
		String[] arr = st.split("[(]");

		//this check will handle print_ride_stat corner case
		if(arr[arr.length-1].length()==1)
			return null;

		arr[arr.length-1] = arr[arr.length-1].split("[)]")[0];

		String[] param =  arr[1].split(",");

		for(int i =0;i<param.length;i++){
			param[i] = param[i].trim();
			param[i] = param[i].replaceAll("^\"|\"$", "");
		}
		return param;
	}

}
