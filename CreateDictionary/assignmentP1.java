import java.io.IOException;

public class assignmentP1 {
	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println("Usage Improper: Please provide the arguments as per the assignemnt requirement");
			System.exit(1);
		}
		FileProcessor fileProcessor = new FileProcessor(args[0]);
			fileProcessor.openFile();
			StringOperations stringOperations = new StringOperations(fileProcessor);
			try {
				stringOperations.countOccurence(args[1]);
				
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error in reading from class StringOperations");
				System.exit(1);
			}
	}
}