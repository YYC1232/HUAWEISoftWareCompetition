import java.util.List;

public class carDispatch {
	public carDispatch() {
		  String carFileName = "Car.txt";
          String roadFileName = "Road.txt";
          String crossFileName = "Cross.txt";
          DataProcess dataProcess = new DataProcess(carFileName,crossFileName,roadFileName);
          dataProcess.MatrixBuild();//生成距离矩阵
          dataProcess.floyd();//floyd计算最短距离矩阵和path路径
          dataProcess.cal_Path();//生成最优路径
          dataProcess.print_Path();//输出最优路径
          dataProcess.init_car();//最优路径加入每辆车中
	}
    public void dispatchCar() {
    int dispatchNum = 0; 
	for(int time = DataProcess.minStartTime;dispatchNum<DataProcess.carNum;time++) {//根据车辆的调度完成数目来判断调度是否结束
		for(int cross = 0;cross<DataProcess.crossNum;cross++) {
			Cross temp_cross =DataProcess.crossidMap.get(cross);
			List<Car> temp_carList = temp_cross.upList;
			for(Car car:temp_carList) {
				if(car.startTime == time) {
					
				}
			}
		}
	}
    }
}
