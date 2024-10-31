import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanObj = new Scanner(System.in);
        int choice = 0;
        String userName = "";
        String password = "";
        System.out.println("-------------------------------------------------------");

        do {
            System.out.print("Press 1 for Login Page\nPress 2 for Registration Page: ");
            choice = scanObj.nextInt();;
        } while(choice != 1 && choice !=2);

        if(choice == 1){
            //Getting info for Object instantiation
            System.out.println("Login Page");
            System.out.println("--------------------------------------------");
            System.out.print("\nUsername: ");
            userName = scanObj.next();
            System.out.print("\nPassword: ");
            password = scanObj.next();
            Login login = new Login(userName, password);
            login.run();

        } else {
            //Getting info for Object instantiation
            System.out.println("Registration Page");
            System.out.println("---------------------------------------------");
            System.out.print("\nUsername: ");
            userName = scanObj.next();
            System.out.print("\nPassword: ");
            password = scanObj.next();

            // Instantiation.
            Register register = new Register(userName, password);
            register.run();
        }
    }
}
