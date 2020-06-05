import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {
	public static void main(String[] args) throws FileNotFoundException, UndefinedVariableException, SyntaxError {
		String fileName, ans;
		Scanner sc = new Scanner(System.in);

		do {

			System.out.print("Enter the file name (*.txt): ");
			fileName = sc.nextLine();
			try {
				new ESPInterpreter(fileName);
			} catch (FileNotFoundException e) {
				System.out.println("File not found!!!");
			}
			System.out.print("Do you want to try again. Press Y or y, any key to exit: ");
			ans = sc.nextLine();

		} while (ans.equals("y") || ans.equals("Y"));
		sc.close();
	}
}
