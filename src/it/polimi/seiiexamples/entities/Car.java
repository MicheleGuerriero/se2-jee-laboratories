package it.polimi.seiiexamples.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cars")
public class Car {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String carName;

	@Column(nullable = false)
	private int numberOfSpots;

	@OneToMany(mappedBy="car")
	private List<Reservation> carReservations;
	
	public List<Reservation> getCarReservations() {
		return carReservations;
	}

	public void setCarReservations(List<Reservation> carReservations) {
		this.carReservations = carReservations;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public void setNumberOfSpots(int numberOfSpots) {
		this.numberOfSpots = numberOfSpots;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getNumberOfSpots() {
		return numberOfSpots;
	}

	public void setNumberOfSpots(Integer numberOfSpots) {
		this.numberOfSpots = numberOfSpots;
	}

	public Car() {

	}

}
