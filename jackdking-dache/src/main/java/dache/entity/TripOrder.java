package dache.entity;

public class TripOrder {
	
	private String userName;//用户名
	private String departure;//出发地点
	private String destination ;//目的地
	private int latitude;
	private int longtude;
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongtude() {
		return longtude;
	}
	public void setLongtude(int longtude) {
		this.longtude = longtude;
	}
	@Override
	public String toString() {
		return "TripOrder [userName=" + userName + ", departure=" + departure + ", destination=" + destination
				+ ", latitude=" + latitude + ", longtude=" + longtude + "]";
	}
}
