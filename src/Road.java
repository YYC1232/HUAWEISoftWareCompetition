
public class Road {
		public int roadId;
		public int roadLenth;
		public int maxLimitSpeed;
		public int roadWeight;
		public int startCrossId;
		public int endCrossId;
		public int weatherTwoWay;
		public Road(int Id,int Lenth,int LimitSpeed,int Weight,int startId,int endId,int Twoway) {
			roadId = Id;
			roadLenth = Lenth;
			maxLimitSpeed = LimitSpeed;
			roadWeight = Weight;
			startCrossId = startId;
			endCrossId = endId;
			weatherTwoWay = Twoway;
		}
		@Override
		public String toString() {
			return "Road [roadId=" + roadId + ", roadLenth=" + roadLenth + ", maxLimitSpeed=" + maxLimitSpeed
					+ ", roadWeight=" + roadWeight + ", startCrossId=" + startCrossId + ", endCrossId=" + endCrossId
					+ ", weatherTwoWay=" + weatherTwoWay + "]";
		}
		public int getRoadId() {
			return roadId;
		}
		public void setRoadId(int roadId) {
			this.roadId = roadId;
		}
		public int getRoadLenth() {
			return roadLenth;
		}
		public void setRoadLenth(int roadLenth) {
			this.roadLenth = roadLenth;
		}
		public int getMaxLimitSpeed() {
			return maxLimitSpeed;
		}
		public void setMaxLimitSpeed(int maxLimitSpeed) {
			this.maxLimitSpeed = maxLimitSpeed;
		}
		public int getRoadWeight() {
			return roadWeight;
		}
		public void setRoadWeight(int roadWeight) {
			this.roadWeight = roadWeight;
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
		public int getWeatherTwoWay() {
			return weatherTwoWay;
		}
		public void setWeatherTwoWay(int weatherTwoWay) {
			this.weatherTwoWay = weatherTwoWay;
		}
}
