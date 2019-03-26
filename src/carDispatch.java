import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class carDispatch {
	public static int diaodu_time;
	public carDispatch() {
		  String carFileName = "Car.txt";
          String roadFileName = "Road.txt";
          String crossFileName = "Cross.txt";
          DataProcess dataProcess = new DataProcess(carFileName,crossFileName,roadFileName);
          dataProcess.MatrixBuild();//���ɾ������
          dataProcess.floyd();//floyd������̾�������path·��
          dataProcess.cal_Path();//��������·��
          dataProcess.print_Path();//�������·��
          dataProcess.init_car();//����·�ڼ���ÿ������
          dataProcess.init_road_path();//����·������ÿ������
          dataProcess.init_cross();//ÿ������ʼ���Ž�ÿ��cross��list��
	}
	//��cross��list�������з�->����������·��list����������󷢳�
	void divide_FourList(Cross cross) {//��cross��List�Ž�4����·��list��
		ArrayList<Car> carList = cross.list;
		for(int i=0;i<carList.size();i++) {
			if(carList.get(i).position < carList.get(i).path.size()){
			Cross nextCross = DataProcess.crossidMap.get(carList.get(i).path.get(carList.get(i).position));
			if(cross.upRoad!=null&&nextCross.downRoad!=null&&cross.upRoad == nextCross.downRoad) {
			    carList.get(i).position +=1;//����һ��position
			    Cross nextNCross = DataProcess.crossidMap.get(carList.get(i).path.get(carList.get(i).position));
			    //�ڲ�ѭ������car����һ��·�ڵ�direction��ֵ
			    if(nextCross.upRoad!=null&&nextNCross.downRoad!=null&&nextCross.upRoad == nextNCross.downRoad)carList.get(i).nextdirection = 3;//ֱ��
			    if(nextCross.rightRoad!=null&&nextNCross.leftRoad!=null&&nextCross.rightRoad == nextNCross.leftRoad)carList.get(i).nextdirection = 1;//��ת��
			    if(nextCross.downRoad!=null&&nextNCross.upRoad!=null&&nextCross.downRoad == nextNCross.upRoad)carList.get(i).nextdirection = 3;//ֱ��
			    if(nextCross.leftRoad!=null&&nextNCross.rightRoad!=null&&nextCross.leftRoad == nextNCross.rightRoad)carList.get(i).nextdirection = 2;//��ת  
 				cross.upList.add(carList.get(i));
			}
			if(cross.rightRoad!=null&&nextCross.leftRoad!=null&&cross.rightRoad == nextCross.leftRoad) {
			    carList.get(i).position +=1;//����һ��position
			    Cross nextNCross = DataProcess.crossidMap.get(carList.get(i).path.get(carList.get(i).position));
			    //�ڲ�ѭ������car����һ��·�ڵ�direction��ֵ
			    if(nextCross.upRoad!=null&&nextNCross.downRoad!=null&&nextCross.upRoad == nextNCross.downRoad)carList.get(i).nextdirection = 2;//��ת
			    if(nextCross.rightRoad!=null&&nextNCross.leftRoad!=null&&nextCross.rightRoad == nextNCross.leftRoad)carList.get(i).nextdirection = 3;//ֱ��
			    if(nextCross.downRoad!=null&&nextNCross.upRoad!=null&&nextCross.downRoad == nextNCross.upRoad)carList.get(i).nextdirection = 1;//��ת
			    if(nextCross.leftRoad!=null&&nextNCross.rightRoad!=null&&nextCross.leftRoad == nextNCross.rightRoad)carList.get(i).nextdirection = 3;//ֱ��
				cross.rightList.add(carList.get(i));
			}
			if(cross.downRoad !=null&& nextCross.upRoad!=null&&cross.downRoad == nextCross.upRoad) {
			    carList.get(i).position +=1;//����һ��position
			    Cross nextNCross = DataProcess.crossidMap.get(carList.get(i).path.get(carList.get(i).position));
			    //�ڲ�ѭ������car����һ��·�ڵ�direction��ֵ
			    if(nextCross.upRoad!=null&&nextNCross.downRoad!=null&&nextCross.upRoad == nextNCross.downRoad)carList.get(i).nextdirection = 3;//ֱ��
			    if(nextCross.rightRoad!=null&&nextNCross.leftRoad!=null&&nextCross.rightRoad == nextNCross.leftRoad)carList.get(i).nextdirection = 2;//��ת
			    if(nextCross.downRoad!=null&&nextNCross.upRoad!=null&&nextCross.downRoad == nextNCross.upRoad)carList.get(i).nextdirection = 3;//ֱ��
			    if(nextCross.leftRoad!=null&&nextNCross.rightRoad!=null&&nextCross.leftRoad == nextNCross.rightRoad)carList.get(i).nextdirection = 1;//��ת  
				cross.downList.add(carList.get(i));
			}
			if(cross.leftRoad !=null&& nextCross.rightRoad!=null&&cross.leftRoad == nextCross.rightRoad) {
			    carList.get(i).position +=1;//����һ��position
			    Cross nextNCross = DataProcess.crossidMap.get(carList.get(i).path.get(carList.get(i).position));
			    //�ڲ�ѭ������car����һ��·�ڵ�direction��ֵ
			    if(nextCross.upRoad!=null&&nextNCross.downRoad!=null&&nextCross.upRoad == nextNCross.downRoad)carList.get(i).nextdirection = 1;//��ת
			    if(nextCross.rightRoad!=null&&nextNCross.leftRoad!=null&&nextCross.rightRoad == nextNCross.leftRoad)carList.get(i).nextdirection = 3;//ֱ��
			    if(nextCross.downRoad!=null&&nextNCross.upRoad!=null&&nextCross.downRoad == nextNCross.upRoad)carList.get(i).nextdirection = 2;//��ת
			    if(nextCross.leftRoad!=null&&nextNCross.rightRoad!=null&&nextCross.leftRoad == nextNCross.rightRoad)carList.get(i).nextdirection = 3;//ֱ��  
				cross.leftList.add(carList.get(i));
			}
		}
		}
	}
	//�Զ���Comparator���ж���İ���ָ������ֵ��������
	Comparator<Car> compator = new Comparator<Car>() {
		public int compare(Car car1,Car car2) {
			if(car1.startTime != car2.startTime) {
				return car2.startTime-car1.startTime;
			}else {
				if(car1.carId!=car2.carId) {
					return car2.carId-car1.carId;
				}
			}
			if(car1.startTime == car2.startTime&&car1.carId!=car2.carId) {
				return car2.direction-car1.direction;
			}
			return 0;
		}
	};
	//��ÿ��cross���ĸ�List��������,������������ݣ�startTime,id,turnDirection
	public void sort_FourList(Cross cross) {
		if(!cross.upList.isEmpty())Collections.sort(cross.upList,compator);
		if(!cross.rightList.isEmpty())Collections.sort(cross.rightList,compator);
		if(!cross.downList.isEmpty())Collections.sort(cross.downList,compator);
		if(!cross.leftList.isEmpty())Collections.sort(cross.leftList,compator);
	}
	//ÿ��·���ĸ�������з���
    public void dispatchCar() {
    int dispatchNum = 0; 
	for(int time = DataProcess.minStartTime;dispatchNum<DataProcess.carNum;time++) {//���ݳ����ĵ��������Ŀ���жϵ����Ƿ����
		for(int cross = 0;cross<DataProcess.crossNum;cross++) {
			Cross temp_cross =DataProcess.crossidMap.get(cross);
			divide_FourList(temp_cross);
			sort_FourList(temp_cross);
			if(!temp_cross.upList.isEmpty()) {
				for(int k = 0;k<temp_cross.upList.size();k++) {
					 if(temp_cross.upList.get(k).startTime == time) {
						 temp_cross.upRoad.maxLimitSpeed = temp_cross.upRoad.maxLimitSpeed>temp_cross.upList.get(k).maxSpeed?temp_cross.upList.get(k).maxSpeed:temp_cross.upRoad.maxLimitSpeed;  
						 temp_cross.upList.get(k).diaodu_time += Math.ceil(temp_cross.upRoad.roadLenth/temp_cross.upRoad.maxLimitSpeed);
						 temp_cross.upList.get(k).startTime +=Math.ceil(temp_cross.upRoad.roadLenth/temp_cross.upRoad.maxLimitSpeed);
						 Cross nextCross = DataProcess.crossidMap.get(temp_cross.upList.get(k).path.get(temp_cross.upList.get(k).position));
						 nextCross.downList.add( temp_cross.upList.get(k));
						 if(temp_cross.upList.get(k).position == temp_cross.upList.get(k).path.size()-1) {
							 System.out.print("("+temp_cross.upList.get(k).carId+","+temp_cross.upList.get(k).init_startTime+",");
							 for(int h=0;h<temp_cross.upList.get(k).roadPath.size()-1;h++) {
								 System.out.print(temp_cross.upList.get(k).roadPath.get(h)+",");
							 }
							 System.out.println(temp_cross.upList.get(k).roadPath.get(temp_cross.upList.get(k).roadPath.size()-1)+")");
							 dispatchNum+=1;
						 }
						 temp_cross.upList.remove(k);
					 }
					}
			}
			if(!temp_cross.rightList.isEmpty()) {
				for(int k = 0;k<temp_cross.rightList.size();k++) {
					 if(temp_cross.rightList.get(k).startTime == time) {
						 temp_cross.rightRoad.maxLimitSpeed = temp_cross.rightRoad.maxLimitSpeed>temp_cross.rightList.get(k).maxSpeed?temp_cross.rightList.get(k).maxSpeed:temp_cross.rightRoad.maxLimitSpeed;  
						 temp_cross.rightList.get(k).diaodu_time += Math.ceil(temp_cross.upRoad.roadLenth/temp_cross.upRoad.maxLimitSpeed);
						 temp_cross.rightList.get(k).startTime +=Math.ceil(temp_cross.rightRoad.roadLenth/temp_cross.rightRoad.maxLimitSpeed);
						 Cross nextCross = DataProcess.crossidMap.get(temp_cross.rightList.get(k).path.get(temp_cross.rightList.get(k).position));
						 nextCross.leftList.add( temp_cross.rightList.get(k));
						 if(temp_cross.rightList.get(k).position == temp_cross.rightList.get(k).path.size()-1) {
							 System.out.print("("+temp_cross.rightList.get(k).carId+","+temp_cross.rightList.get(k).init_startTime+",");
							 for(int h=0;h<temp_cross.rightList.get(k).roadPath.size()-1;h++) {
								 System.out.print(temp_cross.rightList.get(k).roadPath.get(h)+",");
							 }
							 System.out.println(temp_cross.rightList.get(k).roadPath.get(temp_cross.rightList.get(k).roadPath.size()-1)+")");
							 dispatchNum+=1;
						 }
						 temp_cross.rightList.remove(k);
					 }
					}
			}
			if(!temp_cross.downList.isEmpty()) {
				for(int k = 0;k<temp_cross.downList.size();k++) {
					 if(temp_cross.downList.get(k).startTime == time) {
						 temp_cross.downRoad.maxLimitSpeed = temp_cross.downRoad.maxLimitSpeed>temp_cross.downList.get(k).maxSpeed?temp_cross.downList.get(k).maxSpeed:temp_cross.downRoad.maxLimitSpeed;  
						 temp_cross.downList.get(k).diaodu_time += Math.ceil(temp_cross.downRoad.roadLenth/temp_cross.downRoad.maxLimitSpeed);
						 temp_cross.downList.get(k).startTime +=Math.ceil(temp_cross.downRoad.roadLenth/temp_cross.downRoad.maxLimitSpeed);
						 Cross nextCross = DataProcess.crossidMap.get(temp_cross.downList.get(k).path.get(temp_cross.downList.get(k).position));
						 nextCross.upList.add( temp_cross.downList.get(k));
						 if(temp_cross.downList.get(k).position == temp_cross.downList.get(k).path.size()-1) {
							 System.out.print("("+temp_cross.downList.get(k).carId+","+temp_cross.downList.get(k).init_startTime+",");
							 for(int h=0;h<temp_cross.downList.get(k).roadPath.size()-1;h++) {
								 System.out.print(temp_cross.downList.get(k).roadPath.get(h)+",");
							 }
							 System.out.println(temp_cross.downList.get(k).roadPath.get(temp_cross.downList.get(k).roadPath.size()-1)+")");
							 dispatchNum+=1;
						 }
						 temp_cross.downList.remove(k);
					 }
					}
			}
			if(!temp_cross.leftList.isEmpty()) {
				for(int k = 0;k<temp_cross.leftList.size();k++) {
					 if(temp_cross.leftList.get(k).startTime == time) {
						 temp_cross.leftRoad.maxLimitSpeed = temp_cross.leftRoad.maxLimitSpeed>temp_cross.leftList.get(k).maxSpeed?temp_cross.leftList.get(k).maxSpeed:temp_cross.leftRoad.maxLimitSpeed;  
						 temp_cross.leftList.get(k).diaodu_time += Math.ceil(temp_cross.leftRoad.roadLenth/temp_cross.leftRoad.maxLimitSpeed);
						 temp_cross.leftList.get(k).startTime +=Math.ceil(temp_cross.leftRoad.roadLenth/temp_cross.leftRoad.maxLimitSpeed);
						 Cross nextCross = DataProcess.crossidMap.get(temp_cross.leftList.get(k).path.get(temp_cross.leftList.get(k).position));
						 nextCross.rightList.add( temp_cross.leftList.get(k));
						 if(temp_cross.leftList.get(k).position == temp_cross.leftList.get(k).path.size()-1) {
							 System.out.print("("+temp_cross.leftList.get(k).carId+","+temp_cross.leftList.get(k).init_startTime+",");
							 for(int h=0;h<temp_cross.leftList.get(k).roadPath.size()-1;h++) {
								 System.out.print(temp_cross.leftList.get(k).roadPath.get(h)+",");
							 }
							 System.out.println(temp_cross.leftList.get(k).roadPath.get(temp_cross.leftList.get(k).roadPath.size()-1)+")");
							 dispatchNum+=1;
						 }
						 temp_cross.leftList.remove(k);
					 }
					}
			}
		}
	}
    }
}
