import java.io.IOException;

public class assignmentP3 {
	public static void main(String[] args) {
		if(args.length != 0) {
			System.err.println("Usage Improper: Please provide the arguments as per the assignemnt requirement");
			System.exit(1);
		}
			QueryOperations queryOperations = new QueryOperations();
			try {
				queryOperations.runQuery();
			} catch (IOException e) {
				System.err.println("Error in reading from class StringOperations");
				System.exit(1);
			}
	}
}