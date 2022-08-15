package service;

import model.ride;

import java.util.Collections;
import java.util.List;


public class selectionStrategy {
	public static ride getPreferedVehicleRide(String vehicle , List<ride> specificRides){

		if(vehicle==null){
			return null;
		}

		for(ride r:specificRides) {
			// should have preferred vehicle
			if(r.getGiverVehicle().getCarModel().equals(vehicle)) {
				return r;
			}
		}
		return null;
	}

	public static ride getMostVacantRide(List<ride> specificRides){
		Collections.sort(specificRides, new mostVacantRideComparator());
		return specificRides.get(0);
	}

}
