import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataProcess {
	        //数据文件的名称
	        public String carFileName;
	        public String crossFileName;
     	    public String roadFileName;
	        //打开对应数据文件的数据流
	        public FileReader carFile;
	        public FileReader crossFile;
	        public FileReader roadFile;
	        //每个数据流的按行读取
	        public String carLine;
	        public String crossLine;
	        public String roadLine;
	        public StringBuffer buffer;
		    //存储cross,car,road的数量
		    public static  int crossNum = 0;
		    public static int carNum = 0;
		    public static int roadNum = 0;
		    //建立对应实体对象和他们之间id之间的映射
		    public static  Map<Integer,Cross> crossMap = new HashMap<Integer, Cross>();
		    public static Map<Integer,Road> roadMap = new HashMap<Integer, Road>();
            public static Map<Integer,Car> carMap = new HashMap<Integer, Car>();
            public static Map<Integer,Car> car_cross_Map = new HashMap<Integer, Car>();
            //建立 id和对应的自增序列之间的映射
            public static Map<Integer ,Integer> car_id_sequence = new HashMap<Integer,Integer>();
            public static Map<Integer ,Cross> crossidMap = new HashMap<Integer, Cross>();
            public static Map<Integer ,Road> roadidMap = new HashMap<Integer, Road>();
            public static Map<Integer,Integer> road_id_sequence = new HashMap<Integer,Integer>();
            public static Map<Integer,Integer>cross_id_sequence = new HashMap<Integer,Integer>();
            public static Map<Integer,Integer>cross_sequence_startcrossid = new HashMap<Integer,Integer>();
            //记录两个路口是否有路
            public static int matrix[][];
            public static int max = 999;
          //[i][j]记录i->j最佳路径的最后一个点
            public static int path [][];
          //[i][j]记录i->j的完整路径
            public static List<Integer>[][] roadPath;
            public static int final_temp;
            public static int start_temp;
            public static int minStartTime=max;
            public DataProcess(String carFileName,String crossFileName,String roadFileName) {
			//设置了静态变量，那么每次调用一次新的构造函数就是新的一次读取数据文件，就要先清空数据实体列表		
   		    if(!crossMap .isEmpty()) {
				crossMap.clear();
			}
			if(!roadMap.isEmpty()) {
				roadMap.clear();
			}
			if(!carMap.isEmpty()) {
				carMap.clear();
			}
			//打开文件流
			try {
			carFile = new FileReader(carFileName);
			crossFile = new FileReader(crossFileName);
			roadFile = new FileReader(roadFileName);
	        BufferedReader carbuf = new BufferedReader(carFile);
	        BufferedReader crossbuf = new BufferedReader(crossFile);
	        BufferedReader roadbuf = new BufferedReader(roadFile);
	        
			try {
				//汽车文件的初始化
				while ((carLine = carbuf.readLine())!=null) {
					if (carLine.contains("#")) continue;
					else
					{
						String newCarLine []=new String[5];
						int intCarLine [] = new int[5];
						carLine = carLine.replace("(","");
						carLine = carLine.replace(")","");
						newCarLine = carLine.split(",");
						for(int i=0;i<newCarLine.length;i++) {
							newCarLine[i] = newCarLine[i].replaceAll(" ","");
							intCarLine [i] = Integer.parseInt(newCarLine[i]);
						}
						
						Car car = new Car(intCarLine[0],intCarLine[1],intCarLine[2],intCarLine[3],intCarLine[4]);
						car_id_sequence.put(carNum, intCarLine[0]);
						if(minStartTime>intCarLine[4])minStartTime = intCarLine[4];
						//把汽车的始发id和car本身做一个映射方便下面放进对应cross的list
						car_cross_Map.put(intCarLine[1], car);
						carMap.put(intCarLine[0], car);
						carNum+=1;
					}
				} 
				//路径文件的初始化
			while ((roadLine = roadbuf.readLine())!=null) {
				if (roadLine.contains("#")) continue;
				else
				{
					String newRoadLine [] = new String[7];
					int intRoadLine [] = new int[7];
					roadLine = roadLine.replace("(","");
					roadLine = roadLine.replace(")","");
					newRoadLine = roadLine.split(",");
					for(int i=0;i<newRoadLine.length;i++) {
						newRoadLine[i] = newRoadLine[i].replaceAll(" ","");
						intRoadLine [i] = Integer.parseInt(newRoadLine[i]);
					}
					
					Road road = new Road(intRoadLine[0],intRoadLine[1],intRoadLine[2],intRoadLine[3],intRoadLine[4],intRoadLine[5],intRoadLine[6]);
					roadidMap.put(roadNum,road );
					road_id_sequence.put(intRoadLine[0], roadNum);
				    roadNum+=1;
					roadMap.put(intRoadLine[0], road);
				}
			}
		       //路径文件的初始化
			while ((crossLine = crossbuf.readLine())!=null) {
				if (crossLine.contains("#")) continue;
				else
				{
					String newCrossLine [] = new String[5];
					int intCrossLine [] = new int [5];
					crossLine = crossLine.replace("(","");
					crossLine = crossLine.replace(")","");
					newCrossLine = crossLine.split(",");
					for(int i=0;i<newCrossLine.length;i++) {
						newCrossLine[i] = newCrossLine[i].replaceAll(" ","");
						intCrossLine [i] = Integer.parseInt(newCrossLine[i]);
						
					}
					
					Cross cross = new Cross(intCrossLine[0],intCrossLine[1],intCrossLine[2],intCrossLine[3],intCrossLine[4]);
					cross.list = new ArrayList<Car>();
					cross.upList = new ArrayList<Car>();
					cross.rightList = new ArrayList<Car>();
					cross.downList = new ArrayList<Car>();
					cross.leftList = new ArrayList<Car>();
					crossidMap.put(crossNum, cross);
					cross_id_sequence.put(intCrossLine[0],crossNum);
					crossNum+=1;
				    crossMap.put(intCrossLine[0], cross);
				    
				}
			}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
            //距离矩阵初始化,path矩阵初始化
   		    public void MatrixBuild() {
   		    	matrix = new int [crossNum][crossNum];
   		    	for(int i =0;i<crossNum;i++) {
   		    		for(int j =0;j<crossNum;j++) {
   		    			if(i!=j) {matrix[i][j] = max;matrix[j][i] = max;}
   		    		}
   		    	}
   		    	for(int i =0;i<roadNum;i++) {
   		    	     int start = cross_id_sequence.get(roadidMap.get(i).startCrossId);
   		    	     int end  = cross_id_sequence.get(roadidMap.get(i).endCrossId);
   		    	     matrix[start][end] = 1;
   		    	     if(roadidMap.get(i).weatherTwoWay == 1) {
   		    	    	matrix[end][start] = 1;      
   		    	     }
   		    	}
   		    }
   	
   		    //floyd算法计算最短距离同时生成path路径矩阵
   		    public void floyd() {
   		    	path = new int [crossNum][crossNum];
   		    	roadPath = new ArrayList [crossNum][crossNum];
   		    	for(int i=0;i<crossNum;i++) {
   		    		for(int j=0;j<crossNum;j++) {
   		    			roadPath[i][j] = new ArrayList<>();
   		    			path[i][j] = (i==j)?-1:i;
   		    		}
   		    	}
   		    	
   		    	    for(int k=0;k<crossNum;k++) {
   		    	    	for(int m =0;m<crossNum;m++) {
   		    	    		for(int n =0 ;n<crossNum;n++) {
   		    	    			if(matrix[m][n] > matrix[m][k]+matrix[k][n])
   		    	    			{
   		    	    				matrix[m][n] = matrix[m][k] + matrix[k][n];
   		    	    				path[m][n] = path[k][n];
   		    	    			}
   		    	    		}
   		    	    	}
   		    	    }
   		    }
   		    //输出floyd算法计算出来的矩阵
   		    public void print_pathMatrix() {
   			for(int i =0 ;i< crossNum;i++) {
		    		for(int j =0;j< crossNum;j++) {
		    			System.out.print(path[i][j]+"   ");
		    		}
		    		System.out.println();
		    	}
   		    }
   		    //输出i->j之间最后那个记录
   		    public void cal_ijPath(int i,int j) {
   		    	if(path[i][j] == -1)  return;
   		    	cal_ijPath(i,path[i][j]);
   		    	roadPath[start_temp][final_temp].add(path[i][j]);
   		    }
            //计算出成对路口之间的路径
   		    public void  cal_Path() {
   		    	for(int i =0;i<crossNum;i++ ) {
   		    		start_temp = i;	
   		    		for(int j = 0 ;j<crossNum;j++) {
   		    			final_temp = j;
   		    			if(path[i][j]!=-1) {
   		    			cal_ijPath(i,j);
   		    			roadPath[start_temp][final_temp].add(j);
   		    			}
   		    		}
                   	}	
   		    }
   		    //输出成对路口之间的最优路径
   		    public void print_Path() {
   		    	for(int i=0;i<crossNum;i++) {
   		    		for(int j=0;j<crossNum;j++) {
   		    			if(roadPath[i][j].isEmpty())continue;
   		    		for(int temp:roadPath[i][j]) {
   		    			System.out.print(temp+" ");
   		    		}
   		 		    System.out.println();
   		    		}
   		    	}
   		    }
   		    //在cross内部加上list<Car>
   		    public void init_cross(){
   		    	for(int i=0;i<carNum;i++) {
   		    		Car car = carMap.get(car_id_sequence.get(i));
   		    		crossMap.get(car.startCrossId).list.add(car);
   		    	}
   		    }
   		    //在汽车类内部加上计算出来的路径-路口标准
   		    public void init_car() {
   		    	for(int i = 0;i<carNum;i++) {
   		    		Car car = carMap.get(car_id_sequence.get(i));
   		    		int start = cross_id_sequence.get(car.getStartCrossId());
   		    		int end = cross_id_sequence.get(car.getEndCrossId());
   		    		List<Integer> path = roadPath[start][end];
   		    		car.path = path;
   		    	}
   		    }
   		  //在汽车类内部加上计算出来的路径-路标准
   		    public void init_road_path() {
   		    	for(int i=0;i<carNum;i++) {
   		    		Car car = carMap.get(car_id_sequence.get(i));
   		    		for(int j=0;j<car.path.size()-1;j++) {
   		    			Cross startCross = crossidMap.get(car.path.get(j));
                        Cross endCross = crossidMap.get(car.path.get(j+1));
                        if(startCross.upRoad!=null&&endCross.downRoad!=null&&startCross.upRoad == endCross.downRoad) {
                        	car.roadPath.add(startCross.upRoad.roadId);
                        }
                        if(startCross.rightRoad!=null&&endCross.leftRoad!=null&&startCross.rightRoad == endCross.leftRoad) {
                        	car.roadPath.add(startCross.rightRoad.roadId);
                        }
                        if(startCross.downRoad!=null&&endCross.upRoad!=null&&startCross.downRoad == endCross.upRoad) {
                        	car.roadPath.add(startCross.downRoad.roadId);
                        }
                        if(startCross.leftRoad!=null&&endCross.rightRoad!=null&&startCross.leftRoad == endCross.rightRoad) {
                        	car.roadPath.add(startCross.leftRoad.roadId);
                        }
   		    		}
   		    	}
   		    }
}
