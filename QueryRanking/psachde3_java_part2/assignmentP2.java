import java.io.IOException;

public class assignmentP2 {
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Usage Improper: Please provide the arguments as per the assignemnt requirement");
			System.exit(1);
		}
			StringOperations stringOperations = new StringOperations();
			try {
				stringOperations.countOccurence(args[0]);
				
			} catch (IOException e) {
				//e.printStackTrace();
				System.err.println("Error in reading from class StringOperations");
				System.exit(1);
			}
			
	}
}