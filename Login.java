import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class Login extends AbsLogReg{

    public  Login(String username, String password){
        super(username, password);
    }

    @Override
    void plainText(){

    }





    String passHasher(){
        // Instantiates the hashing algorithm
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("Message Digest Failed on login's hashedText()");
            canContinue = false;
            return "";
        }


        // Gets the hash of the user inputted password
        byte[] hashInBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashInBytes);
    }

    @Override
    void hashedText() {
        if (checkIfValidUsername() && password.length() <= passLenAlwd) {
            String extrUsername = "";
            String extrPass = "";
            String extrSalt = "";

            String line;


            File file = new File("hashedText.txt");
            // Opens hashedtext.txt file and extracts the username, password and salt.
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.startsWith("Username: ")) {
                        extrUsername = line.substring("Username:".length()).trim();
                    }
                    if (line.startsWith("Password: ")) {
                        extrPass = line.substring("Password:".length()).trim();
                    }
                    if (line.startsWith("Salt: ")) {
                        extrSalt = line.substring("Salt:".length()).trim();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error with scanning the file");
                canContinue = false;
                return;
            }


            String hashPass = passHasher();
            System.out.println("hashPass: " + hashPass);

            // Checks if the username nad password matches with the one in the database.
            if(username.equals(extrUsername) && hashPass.equals(extrPass)){
                System.out.println("You're logged in from the hashPass");
            } else {
                System.out.println("You have been denied from login hashedText()");
            }


        }
    }




    //Extracts info from the hashedText.txt file and compares it to what the user provided.
    @Override
    void saltedHashText() {
        String extractedUsername = "";
        String extractedPassword = "";
        String extractedSalt = "";
        String line = "";


        // Extracts the username, password and salt from the plain text file
        if(checkIfValidUsername() && password.length() <= passLenAlwd){
            File file = new File("saltedAndHashed.txt");
            try(Scanner scanner = new Scanner(file)){
                while(scanner.hasNextLine()){
                    // Checks line for certain keywords and extracts the provided info for them.
                    line = scanner.nextLine();
                    if(line.startsWith("Username: ")){
                        extractedUsername = line.substring("Username:".length()).trim();
                    }
                    if(line.startsWith("Password: ")) {
                        extractedPassword = line.substring("Password:".length()).trim();
                    }
                    if(line.startsWith("Salt: ")){
                        extractedSalt = line.substring("Salt: ".length()).trim();
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
                System.out.println("At the loginPlainText function");
                canContinue = false;
            }

            // get the password in the format of the db's format using the salt and password
            // that the user inputted.
            byte[] extrPassSaltBytes = (password + extractedSalt).getBytes(StandardCharsets.UTF_8);
            String formattedUserPass = null;
            try{
                // Instantiates the hashing Alg
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

                //takes the hash of password with the extractedSalt appended to then
                // turn it back into a base64 encoded string to be compared.
                byte[] digest = messageDigest.digest(extrPassSaltBytes);
                formattedUserPass = Base64.getEncoder().encodeToString(digest);
                System.out.println("formattedUserPass: " + formattedUserPass);
            } catch(NoSuchAlgorithmException e){
                e.printStackTrace();
                canContinue = false;
            }

            // Compares the user inputted username with the username from the db
            // compares the formatted user inputted password with the password in the db.
            if(extractedUsername.equals(username) && extractedPassword.equals(formattedUserPass)){
                System.out.println("You're logged in!");
            } else {
                System.out.println("You're not logged in twin.");
            }



            //Invalid input such as username not valid or password length too long.
        } else {
            System.out.println("Invalid Input");
            canContinue = false;
        }
    }
}
