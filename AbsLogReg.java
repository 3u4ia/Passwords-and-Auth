public abstract class AbsLogReg {
    String username;
    String password;
    int passLenAlwd = 10;
    boolean canContinue = true;

    abstract void plainText();
    abstract void hashedText();
    abstract void saltedHashText();


    AbsLogReg(String username, String password){
        this.username = username;
        this.password = password;
    }

    boolean checkIfValidUsername(){
        char tempChar;
        if(username.length() > 10){
            System.out.println("Your username is too long.");
            return false;
            // tempBool is already false;
        }

        for(int i = 0; i < username.length(); i++){
            tempChar = username.charAt(i);

            // Check  if the ! actually negates the boolean
            if(Character.isUpperCase(tempChar)){
                System.out.println("Make sure there are no capitals in your usernames");
                return false;
            }
            if(Character.isDigit(tempChar)) {
                System.out.println("Make sure there aren't any numbers in your username");
                return false;
            }
        }
        return true;
    }



}
