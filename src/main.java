
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
           String carFileName = "Car.txt";
           String roadFileName = "Road.txt";
           String crossFileName = "Cross.txt";
           DataProcess dataProcess = new DataProcess(carFileName,crossFileName,roadFileName);
           dataProcess.MatrixBuild();
           dataProcess.floyd();
           dataProcess.all_out_i_j();
	}
}
