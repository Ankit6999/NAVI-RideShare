package service;

import java.util.ArrayList;

import java.util.List;

import model.user;
import model.vehicle;

public class userService {
	public static List<user> allUsers  = new ArrayList<user>();
	public static List<vehicle>allVehicle = new ArrayList<vehicle>();
	
       //creates new user
    public static user createUser(String name, int age ,String gender) {
    	user u = new user(name,gender,age);
    	allUsers.add(u);
    	System.out.println("New User " +name+" Created");
    	return u;
    }
    
    //add vehicle to the u user
	public static vehicle addVehicle(user u,String owner,String Model,String RegNo) {
		vehicle v = new vehicle(owner,Model,RegNo);
		u.addVehicle(v);
		allVehicle.add(v);
		System.out.println("New Vehicle "+Model+ " of owner "+owner+" Created");
		return v;
    }

	public static user getUserByName(String name) {			
		for(user u:allUsers) {
			if (u.getName().equals(name))
					return u;
		}
		return null;

	}
	public static vehicle getVehicleByRegNo(String RegNo) {
		for(vehicle v:allVehicle) {
			if(v.getCarRegNo().equals(RegNo)) {
				return v;
			}
		}
		return null;
	}
	
	public static user getUserByNameandRegNo(String name,String RegNo) {
		vehicle v =  getVehicleByRegNo(RegNo);
		if (v==null) {
			return null;
		}
		for(user u:allUsers) {
			if(u.getName().equals(name) && u.getVehicle().contains(v)) {
				return u;
			}
		}
		return null;
	}

}
