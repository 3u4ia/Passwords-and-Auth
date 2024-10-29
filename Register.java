import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AbsLogReg {

    //boolean isEntryValid = false; // I don't know if this is a good idea or not

    public Register(String username, String password){
        super(username, password); // instantiates username and password in abs class
    }

    @Override
    void plainText() {
        if(checkIfValidUsername()){
            String entry = "Username: " + username + "\nPassword: " + password;
            byte[] entryBytes = entry.getBytes();

            try (FileOutputStream fos = new FileOutputStream("plainText.txt")){
                fos.write(entryBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Unable to register as you have an invalid entry");
        }
    }


    void hashText(){

    }
    @Override
    void hashedText(){
        if(checkIfValidUsername()){

            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] pass = password.getBytes(StandardCharsets.UTF_8);
                byte[] digest = messageDigest.digest(pass);
                // Then do the file opening and writing.

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    void saltedHashText(){

    }


}
