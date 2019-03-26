import java.util.ArrayList;
import java.util.List;

public class Car {
	public int carId;
	public int startCrossId;
	public int endCrossId;
	public int maxSpeed;
	public int startTime;
	public int init_startTime;
	public int diaodu_time;
	public int getInit_startTime() {
		return init_startTime;
	}
	public void setInit_startTime(int init_startTime) {
		this.init_startTime = init_startTime;
	}
	public int getDiaodu_time() {
		return diaodu_time;
	}
	public void setDiaodu_time(int diaodu_time) {
		this.diaodu_time = diaodu_time;
	}
	public int getEnd_time() {
		return end_time;
	}
	public void setEnd_time(int end_time) {
		this.end_time = end_time;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getNewUp() {
		return newUp;
	}
	public void setNewUp(int newUp) {
		this.newUp = newUp;
	}
	public int getNewRight() {
		return newRight;
	}
	public void setNewRight(int newRight) {
		this.newRight = newRight;
	}
	public int getNewDown() {
		return newDown;
	}
	public void setNewDown(int newDown) {
		this.newDown = newDown;
	}
	public int getNewLeft() {
		return newLeft;
	}
	public void setNewLeft(int newLeft) {
		this.newLeft = newLeft;
	}
	public int getNextdirection() {
		return nextdirection;
	}
	public void setNextdirection(int nextdirection) {
		this.nextdirection = nextdirection;
	}
	public static List<Integer> getPath() {
		return path;
	}
	public static void setPath(List<Integer> path) {
		Car.path = path;
	}
	public static List<Integer> getRoadPath() {
		return roadPath;
	}
	public static void setRoadPath(List<Integer> roadPath) {
		Car.roadPath = roadPath;
	}
	public int end_time;
	public int position;//标记汽车行走的路口个数
	public int direction;//即将走的方向：分为直行，左转，右转，三个方向
	public int newUp,newRight,newDown,newLeft;//下一个路口行走状态，用来推导出direction
	public int nextdirection;//下一个路口的拐弯状况
	public static List<Integer> path;
	public static List<Integer> roadPath;
	public Car(int Id, int startId, int endId, int Speed, int Time ) {
		carId = Id;
		startCrossId = startId;
		endCrossId = endId;
		maxSpeed = Speed;
		startTime = Time;
		init_startTime = Time;
		position = 1;
		direction = 3;//初始化放进每个路口，都是直行
		nextdirection = 3;
		diaodu_time = 0;
		path = new ArrayList<Integer>(); 
		roadPath = new ArrayList<Integer>(); 
	}
	@Override
	public String toString() {
		return "Car [carId=" + carId + ", startCrossId=" + startCrossId + ", endCrossId=" + endCrossId + ", maxSpeed="
				+ maxSpeed + ", startTime=" + startTime + ", init_startTime=" + init_startTime + ", diaodu_time="
				+ diaodu_time + ", end_time=" + end_time + ", position=" + position + ", direction=" + direction
				+ ", newUp=" + newUp + ", newRight=" + newRight + ", newDown=" + newDown + ", newLeft=" + newLeft
				+ ", nextdirection=" + nextdirection + ", getInit_startTime()=" + getInit_startTime()
				+ ", getDiaodu_time()=" + getDiaodu_time() + ", getEnd_time()=" + getEnd_time() + ", getPosition()="
				+ getPosition() + ", getDirection()=" + getDirection() + ", getNewUp()=" + getNewUp()
				+ ", getNewRight()=" + getNewRight() + ", getNewDown()=" + getNewDown() + ", getNewLeft()="
				+ getNewLeft() + ", getNextdirection()=" + getNextdirection() + ", getCarId()=" + getCarId()
				+ ", getStartCrossId()=" + getStartCrossId() + ", getEndCrossId()=" + getEndCrossId()
				+ ", getMaxSpeed()=" + getMaxSpeed() + ", getStartTime()=" + getStartTime() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
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
