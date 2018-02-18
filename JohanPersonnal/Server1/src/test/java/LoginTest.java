import org.junit.Test;

/**
 * Created by johan on 25.09.2017.
 */
public class LoginTest {
    //we use localhost as test client
    private final String CLIENT = "localhost";

    @Test
    public void loginOnUsername(){
        String username = "Johan";
        String password = "1234";
        RequestPlayer rp= new RequestPlayer(1,username,password);

//        boolean login = login(rp,null);

    }

}
