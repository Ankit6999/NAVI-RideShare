package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.ride;
import model.user;
import model.vehicle;

public class rideService {
	//contains all rides
	public static List<ride> allOffers = new ArrayList<ride>();
	//contains data for all rides taken by which user
	public static HashMap<user,List<ride>> allRidesTaken = new HashMap<user,List<ride>>();
	//contains data of all ongoing rides
	public static List<ride> currRide = new ArrayList<>();

	public static ride offerRide(String name,String vehicle,String  RegNo,int seats,String origin,String destination) throws Exception
	{			
		user u = userService.getUserByNameandRegNo(name,RegNo);
	
			if (u == null) {
				System.out.println("User "+name+" has no such vehicle "+RegNo+" so cannot offer such ride");
				return null;
			}

		vehicle v = userService.getVehicleByRegNo(RegNo);
		ride r = new ride(u,v,seats,origin,destination,true);


		//to check whether user cant offer more than one ride  for same vehicle
		for(ride ride:allOffers) {
			if (u.getIds().equals(ride.getRideUser().getIds()) && RegNo.equals(ride.getGiverVehicle().getCarRegNo())) {
				System.out.println("Ride has been already offered by this User " + name + " with this vehicle " + RegNo);
				return null;
			}
		}


		u.setRideOffered(u.getRideOffered()+1);// add ride offered to the user
		allOffers.add(r);
		System.out.println("Ride has offered by this User " + name + " with this vehicle " + RegNo + " from " + origin + " to " + destination + ".");
		return r;
	}
	
	private static ride selectRideService(user u,String origin,String destination,int reqSeats,String typeStr) {
		List<ride> specificRides= getAllAvailableRidesWithSourceAndDestinationForUser(u,origin,destination,reqSeats);

		String[] arrOfType = typeStr.split("=");
		String type = arrOfType[0];
		String vehicle = null;

		if(arrOfType.length == 2)
			vehicle = arrOfType[1];

		if(specificRides.size()==0)
			return null;

		switch(type) {
		
		case "Most Vacant":{
			return selectionStrategy.getMostVacantRide(specificRides);
		}
			
		case "Preferred Vehicle":{
			return selectionStrategy.getPreferedVehicleRide(vehicle,specificRides);
		}
			
		default:
			return null;

			
		}
	}
	
	public static ride selectRide(String name,String origin,String destination,int reqSeats,String type) {
		user u = userService.getUserByName(name);
		
		if(u == null){
			System.out.println("No such user is there "+ name);
			return null;
			}
		
		ride r = selectRideService(u,origin,destination,reqSeats,type);
		
		if (r == null) {
			System.out.println("No such ride is there with origin " + origin+ " Destination " +destination+" and  "+type);
			return null;
		}
		
		//change the state of ride offerer
		r.setAvailable_seats(r.getAvailable_seats()-1);	
		
		//change the state of ride taker
		u.setRidetaken(u.getRidetaken()+1);

		//change the state of ride availibilty
		r.setCurrAvailiblity(false);
		
		//map the user with which ride it takes
		if(allRidesTaken.containsKey(u)) {
			List<ride> rideByUser = allRidesTaken.get(u);
			rideByUser.add(r);
			
		}else {
			List<ride> rideByUser =new ArrayList<ride>();
			rideByUser.add(r);
			allRidesTaken.put(u,rideByUser);
		}

		// adding to current ongoing rides list
		currRide.add(r);

		System.out.println("Ride is given by "+r.getRideUser().getName()+" With Vehicle "+ r.getGiverVehicle().getCarModel()+"-"+ r.getGiverVehicle().getCarRegNo()+" to "+u.getName());
		
		return r;
	}


	private static List<ride> getAllAvailableRidesWithSourceAndDestinationForUser(user u,String Source,String Destination,int reqSeats){
		List<ride>specifiedRide = new ArrayList<ride>();
		for(ride r:allOffers) {
			//should not have same user
			//should be having same source and destination
			//should have available seats
			//should be availible to take ride
			if(!(u.getIds().equals(r.getRideUser().getIds())) && r.getOrigin().equals(Source) && r.getDestination().equals(Destination) && r.getAvailable_seats()>=reqSeats &&
					r.getCurrAvailiblity() == true) {
				specifiedRide.add(r);
			}
		}
		return specifiedRide;
	}


	public static void endRide(String name, String RegNo){

		user u = userService.getUserByNameandRegNo(name,RegNo);
		if(u==null)
		{
			System.out.println("User "+name+" has no such vehicle "+RegNo+" so cannot end such ride");
			return;
		}

		for(ride r:currRide) {

			if(r.getRideUser().equals(u)) {

				// remove ride from current ongoing rides
				currRide.remove(r);

				// change the state of ride availiblity
				r.setCurrAvailiblity(true);

				System.out.println("Ride ended which is given by "+r.getRideUser().getName()+" With Vehicle "+ r.getGiverVehicle().getCarModel()+"-"+ r.getGiverVehicle().getCarRegNo());
				return;
			}
		}

		System.out.println("No such ride exist with this ride giver name " +  name + " and vehicle No "+RegNo);
		return;

	}
	
	public static void totalRidesByUser() {
		
		for(user u:userService.allUsers) {
			System.out.println(u.getName()+" "+u.getRidetaken()+":"+"Taken"+" "+u.getRideOffered()+":"+"Offered");
		}
		
	}	
	
}


