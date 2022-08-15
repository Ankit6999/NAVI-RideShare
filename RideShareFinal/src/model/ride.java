package model;

public class ride {

	private user rideGiver;
	private vehicle giverVehicle;
	private int available_seats;
	private String origin;
	private String destination;
	private boolean isAvailible;

	public ride(user u,vehicle v,int seats,String origin,String destination,boolean isAvailible ) {
		this.rideGiver =u;
		this.giverVehicle = v;
		this.available_seats =seats;
		this.origin = origin;
		this.destination = destination;
		this.isAvailible =  isAvailible;
	}


	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public vehicle getGiverVehicle() {
		return giverVehicle;
	}

	public int getAvailable_seats() {
		return available_seats;
	}

	public user getRideUser() {
		return this.rideGiver;
	}

	public boolean getCurrAvailiblity(){
		return this.isAvailible;
	}

	public void setCurrAvailiblity(boolean isAvailible){
		this.isAvailible = isAvailible;
	}

	public void setAvailable_seats(int available_seats) {
		this.available_seats = available_seats;
	}



}