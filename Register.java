public class Register {
    String username;
    String password;
    boolean isEntryValid = false;

    public Register(String username, String password){
        this.username = username;
        this.password = password;
    }
    void plainText(){

    }






    public void checkIfValidEntries(){
        char tempChar;
        boolean tempBool = false;
        if(username.length() > 10){
            System.out.println("Your username is too long.");
            // tempBool is already false;
        } else {
            tempBool = true;
        }

        for(int i = 0; i < username.length(); i++){
            tempChar = username.charAt(i);

            // Check  if the ! actually negates the boolean
            if(Character.isUpperCase(tempChar)){
                System.out.println("Make sure there are no capitals in your usernames");
                if(tempBool){
                    tempBool = false;
                }
            }
            if(Character.isDigit(tempChar)) {
                System.out.println("Make sure there aren't any numbers in your username");
                if(tempBool){
                    tempBool = false;
                }
            }
        }

        isEntryValid = tempBool;

    }
}
