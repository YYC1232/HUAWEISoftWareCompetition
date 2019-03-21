import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
            //建立 id和对应的自增序列之间的映射
            public static Map<Integer ,Cross> crossidMap = new HashMap<Integer, Cross>();
            public static Map<Integer ,Road> roadidMap = new HashMap<Integer, Road>();
            public static Map<Integer,Integer> road_id_sequence = new HashMap<Integer,Integer>();
            public static Map<Integer,Integer>cross_id_sequence = new HashMap<Integer,Integer>();
            public static int matrix[][];
            public static int max = 999;
            public static int path [][];
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
						carNum+=1;
						Car car = new Car(intCarLine[0],intCarLine[1],intCarLine[2],intCarLine[3],intCarLine[4]);
					
						carMap.put(intCarLine[0], car);
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
   		    	     if(roadidMap.get(i).weatherTwoWay == 1)
   		    	    	matrix[end][start] = 1;
   		    	}
   		    	for(int i =0 ;i< crossNum;i++) {
   		    		for(int j =0;j< crossNum;j++) {
   		    			System.out.print(matrix[i][j]+"   ");
   		    		}
   		    		System.out.println();
   		    	}
   		    	
   		    }
   		    public void floyd() {
   		    	   path = new int [crossNum][crossNum];
   		    	    for(int k=0;k<crossNum;k++) {
   		    	    	for(int i =0;i<crossNum;i++) {
   		    	    		for(int j =0 ;j<crossNum;j++) {
   		    	    			if(matrix[i][j] > matrix[i][k]+matrix[k][j])
   		    	    			{
   		    	    				matrix[i][j] = matrix[i][k] + matrix[k][j];
   		    	    				path[i][j] = k;
   		    	    			}
   		    	    		}
   		    	    	}
   		    	    }
   		    }
   		    public void out_i_j(int i,int j) {
   		    	if(path[i][j] == 0) return;
   		    	out_i_j(i,path[i][j]);
   		    	System.out.print(path[i][j]+" ");
   		    	out_i_j(path[i][j],j);
   		    }
   		    public void  all_out_i_j() {
   		    	for(int i =0;i<crossNum;i++ ) {
   		    		System.out.print(i+" ");
   		    		for(int j =0 ;j<crossNum;j++) {
   		    			out_i_j(i,j);
   		    		}	
  
   		           System.out.println();
   		    	}
   		    	
   		    }
		
}
