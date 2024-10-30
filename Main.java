public class Main {
    public static void main(String[] args) {
        Register obj = new Register("elroi", "password");
        Login login = new Login("elroi", "password");

        login.saltedHashText();
        login.hashedText();
    }
}
