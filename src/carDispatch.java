import java.util.List;

public class carDispatch {
	public carDispatch() {
		  String carFileName = "Car.txt";
          String roadFileName = "Road.txt";
          String crossFileName = "Cross.txt";
          DataProcess dataProcess = new DataProcess(carFileName,crossFileName,roadFileName);
          dataProcess.MatrixBuild();//���ɾ������
          dataProcess.floyd();//floyd������̾�������path·��
          dataProcess.cal_Path();//��������·��
          dataProcess.print_Path();//�������·��
          dataProcess.init_car();//����·������ÿ������
	}
    public void dispatchCar() {
    int dispatchNum = 0; 
	for(int time = DataProcess.minStartTime;dispatchNum<DataProcess.carNum;time++) {//���ݳ����ĵ��������Ŀ���жϵ����Ƿ����
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
