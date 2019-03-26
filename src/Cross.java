import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cross {
		public int crossId;
		public Road upRoad ;
		public Road rightRoad;
		public Road downRoad ;
		public Road leftRoad ;
		public ArrayList<Car> list;
		public ArrayList<Car> upList;
        public ArrayList<Car> rightList;
        public ArrayList<Car> downList;
        public ArrayList<Car> leftList;
		public Cross(int a,int b,int c,int d,int e) {
			crossId = a;
		    if(b == -1) setUpRoad(null);
		    else setUpRoad(DataProcess.roadMap.get(b));
		    if(c == -1) setRightRoad(null);
		    else setRightRoad(DataProcess.roadMap.get(c));  
		    if(d == -1) setDownRoad(null);
		    else setDownRoad(DataProcess.roadMap.get(d));  
		    if(e == -1) setLeftRoad(null);
		    else setLeftRoad(DataProcess.roadMap.get(e)); 
		    list = new ArrayList<Car>();
		}
	    
		@Override
		public String toString() {
			return "Cross [crossId=" + crossId + ", upRoad=" + upRoad + ", rightRoad=" + rightRoad + ", downRoad="
					+ downRoad + ", leftRoad=" + leftRoad + "]";
		}

		public int getCrossId() {
			return crossId;
		}
		public void setCrossId(int crossId) {
			this.crossId = crossId;
		}
		public Road getUpRoad() {
			return upRoad;
		}
		public void setUpRoad(Road upRoad) {
			this.upRoad = upRoad;
		}
		public Road getRightRoad() {
			return rightRoad;
		}
		public void setRightRoad(Road rightRoad) {
			this.rightRoad = rightRoad;
		}
		public Road getDownRoad() {
			return downRoad;
		}
		public void setDownRoad(Road downRoad) {
			this.downRoad = downRoad;
		}
		public Road getLeftRoad() {
			return leftRoad;
		}
		public void setLeftRoad(Road leftRoad) {
			this.leftRoad = leftRoad;
		};

}
