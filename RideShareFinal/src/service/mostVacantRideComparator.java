package service;

import java.util.Comparator;

import model.ride;

public class mostVacantRideComparator implements Comparator<ride>{
	public int compare(ride a,ride b){
	    if(a.getAvailable_seats() == b.getAvailable_seats())
	        return 0;
	    else if (a.getAvailable_seats() < b.getAvailable_seats())
	        return 1;
	    else
	        return -1;
    }
	
}
