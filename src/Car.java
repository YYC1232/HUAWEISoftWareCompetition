
public class Car {
	public int carId;
	public int startCrossId;
	public int endCrossId;
	public int maxSpeed;
	public int startTime;
	public Car(int Id, int startId, int endId, int Speed, int Time) {
		carId = Id;
		startCrossId = startId;
		endCrossId = endId;
		maxSpeed = Speed;
		startTime = Time;
	}
	@Override
	public String toString() {
		return "Car [carId=" + carId + ", startCrossId=" + startCrossId + ", endCrossId=" + endCrossId + ", maxSpeed="
				+ maxSpeed + ", startTime=" + startTime + "]";
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public int getStartCrossId() {
		return startCrossId;
	}
	public void setStartCrossId(int startCrossId) {
		this.startCrossId = startCrossId;
	}
	public int getEndCrossId() {
		return endCrossId;
	}
	public void setEndCrossId(int endCrossId) {
		this.endCrossId = endCrossId;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
};
