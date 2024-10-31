import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Register extends AbsLogReg {

    //boolean isEntryValid = false; // I don't know if this is a good idea or not

    public Register(String username, String password){
        super(username, password); // instantiates username and password in abs class
    }


    @Override
    void plainText() {
        if(checkIfValidUsername()){
            String entry = "Username: " + username + "\nPassword: " + password + "\nSalt: 0";
            byte[] entryBytes = entry.getBytes(StandardCharsets.UTF_8);

            try (FileOutputStream fos = new FileOutputStream("plainText.txt")){
                fos.write(entryBytes);
            } catch (IOException e) {
                e.printStackTrace();
                canContinue = false;
            }
        }
        else{
            System.out.println("Unable to register as you have an invalid entry");
        }
    }


    public String passHasher(){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] pass = password.getBytes(StandardCharsets.UTF_8);
            byte[] digest =  messageDigest.digest(pass);

             return Base64.getEncoder().encodeToString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            canContinue = false;
            return "";
        }
    }

    @Override
    void hashedText(){
        if(checkIfValidUsername() && password.length() <= passLenAlwd){

            String base64Hash = passHasher();

            String entry = "Username: " + username + "\nPassword: " + base64Hash + "\nSalt: 0";
            byte[] entryBytes = entry.getBytes(StandardCharsets.UTF_8);

            try (FileOutputStream fos = new FileOutputStream("hashedText.txt")){
                fos.write(entryBytes);
            } catch (IOException e) {
                e.printStackTrace();
                canContinue = false;
            }

        } else {
            System.out.println("Unable to register as there's an invalid entry");
            canContinue = false;
        }
    }




    byte[] saltToSaltedPassStr(byte[] byteSalt){
        return (password + Base64.getEncoder().encodeToString(byteSalt)).getBytes(StandardCharsets.UTF_8);
    }

    public String saltPassHasher(byte[] byteSalt) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte[] digest = messageDigest.digest(saltToSaltedPassStr(byteSalt));

        return Base64.getEncoder().encodeToString(digest);

    }

    @Override
    void saltedHashText() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        if(checkIfValidUsername() && password.length() <= passLenAlwd) {
            try (FileOutputStream fos = new FileOutputStream("saltedAndHashedText.txt")){
                String passSaltHash = saltPassHasher(salt);

                String entry = "Username: " + username + "\nPassword: " + passSaltHash + "\nSalt: " + Base64.getEncoder().encodeToString(salt);
                fos.write(entry.getBytes(StandardCharsets.UTF_8));

            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                canContinue = false;
            }
        } else {
            System.out.println("Could not register as there are invalid entries");
            canContinue = false;
        }



    }


}






