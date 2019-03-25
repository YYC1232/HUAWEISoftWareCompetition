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
	        //�����ļ�������
	        public String carFileName;
	        public String crossFileName;
     	    public String roadFileName;
	        //�򿪶�Ӧ�����ļ���������
	        public FileReader carFile;
	        public FileReader crossFile;
	        public FileReader roadFile;
	        //ÿ���������İ��ж�ȡ
	        public String carLine;
	        public String crossLine;
	        public String roadLine;
	        public StringBuffer buffer;
		    //�洢cross,car,road������
		    public static  int crossNum = 0;
		    public static int carNum = 0;
		    public static int roadNum = 0;
		    //������Ӧʵ����������֮��id֮���ӳ��
		    public static  Map<Integer,Cross> crossMap = new HashMap<Integer, Cross>();
		    public static Map<Integer,Road> roadMap = new HashMap<Integer, Road>();
            public static Map<Integer,Car> carMap = new HashMap<Integer, Car>();
            public static Map<Integer,Car> car_cross_Map = new HashMap<Integer, Car>();
            //���� id�Ͷ�Ӧ����������֮���ӳ��
            public static Map<Integer ,Integer> car_id_sequence = new HashMap<Integer,Integer>();
            public static Map<Integer ,Cross> crossidMap = new HashMap<Integer, Cross>();
            public static Map<Integer ,Road> roadidMap = new HashMap<Integer, Road>();
            public static Map<Integer,Integer> road_id_sequence = new HashMap<Integer,Integer>();
            public static Map<Integer,Integer>cross_id_sequence = new HashMap<Integer,Integer>();
            public static Map<Integer,Integer>cross_sequence_startcrossid = new HashMap<Integer,Integer>();
            //��¼����·���Ƿ���·
            public static int matrix[][];
      
            public static int max = 999;
          //[i][j]��¼i->j���·�������һ����
            public static int path [][];
          //[i][j]��¼i->j������·��
            public static List<Integer>[][] roadPath;
            public static int final_temp;
            public static int start_temp;
            public static List<Car> carList[];
            public static int minStartTime=max;
            public DataProcess(String carFileName,String crossFileName,String roadFileName) {
			//�����˾�̬��������ôÿ�ε���һ���µĹ��캯�������µ�һ�ζ�ȡ�����ļ�����Ҫ���������ʵ���б�		
   		    if(!crossMap .isEmpty()) {
				crossMap.clear();
			}
			if(!roadMap.isEmpty()) {
				roadMap.clear();
			}
			if(!carMap.isEmpty()) {
				carMap.clear();
			}
			//���ļ���
			try {
			carFile = new FileReader(carFileName);
			crossFile = new FileReader(crossFileName);
			roadFile = new FileReader(roadFileName);
	        BufferedReader carbuf = new BufferedReader(carFile);
	        BufferedReader crossbuf = new BufferedReader(crossFile);
	        BufferedReader roadbuf = new BufferedReader(roadFile);
	        
			try {
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
						car_cross_Map.put(intCarLine[1], car);
						carMap.put(intCarLine[0], car);
						carNum+=1;
					}
				} 
				
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
					cross.upList = new ArrayList<Car>();
					cross.rightList = new ArrayList<Car>();
					cross.downList = new ArrayList<Car>();
					cross.leftList = new ArrayList<Car>();
//					cross.list.add(car_cross_Map.get(intCrossLine[0]));
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
   		  
   		    public void floyd() {
   		    	path = new int [crossNum][crossNum];
   		    	roadPath = new ArrayList [crossNum][crossNum];
   		    	for(int i=0;i<crossNum;i++) {
   		    		for(int j=0;j<crossNum;j++) {
   		    			roadPath[i][j] = new ArrayList<>();
   		    		}
   		    	}
   		    	    for(int k=0;k<crossNum;k++) {
   		    	    	for(int m =0;m<crossNum;m++) {
   		    	    		for(int n =0 ;n<crossNum;n++) {
   		    	    			if(matrix[m][n] > matrix[m][k]+matrix[k][n])
   		    	    			{
   		    	    				matrix[m][n] = matrix[m][k] + matrix[k][n];
   		    	    				path[m][n] = k;
   		    	    			}
   		    	    		}
   		    	    	}
   		    	    }
   		    }
   		    public void print_pathMatrix() {
   			for(int i =0 ;i< crossNum;i++) {
		    		for(int j =0;j< crossNum;j++) {
		    			System.out.print(path[i][j]+"   ");
		    		}
		    		System.out.println();
		    	}
   		    }
   		    public void cal_ijPath(int i,int j) {
   		    	if(path[i][j] == 0)  return;
   		    	cal_ijPath(i,path[i][j]);
   		    	roadPath[start_temp][final_temp].add(path[i][j]);
//   		    	System.out.print(path[i][j]+" ");
   		    }

   		    public void  cal_Path() {
   		    	for(int i =0;i<crossNum;i++ ) {
   		    		start_temp = i;	
   		    		for(int j = 0 ;j<crossNum;j++) {
   		    			final_temp = j;
   		    			if(path[i][j]!=0) {
   		    			roadPath[start_temp][final_temp].add(i);
//   		    			System.out.print(i+" "); 
   		    			cal_ijPath(i,j);
   		    			roadPath[start_temp][final_temp].add(j);
//   		    			System.out.print(j);
//   		    		    System.out.println();
   		    			}
   		    		}
                   	}	
   		    }
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
   		    public void init_car() {//���������ڲ����ϼ��������·��
   		    	for(int i = 0;i<carNum;i++) {
   		    		Car car = carMap.get(car_id_sequence.get(i));
   		    		int start = cross_id_sequence.get(car.getStartCrossId());
   		    		int end = cross_id_sequence.get(car.getEndCrossId());
   		    		List<Integer> path = roadPath[start][end];
   		    		car.path = path;
   		    	}
   		    }
}
