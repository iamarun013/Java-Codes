/**
 * AuthSystem - Basic In-Memory Authentication
 *
 * Manages user registration, login, and session tracking
 * using two HashMaps. All operations run in O(1) time.
 *
 * register()         → adds new user, rejects duplicates
 * login()            → validates credentials, returns session token
 * isAuthenticated()  → checks if session token is active
 * logout()           → removes session token
 */
import java.util.HashMap;
import java.util.UUID;

public class AuthSystem
{
    private HashMap<String,Integer> userStore = new HashMap<>();
    private HashMap<String, String> sessionStore = new HashMap<>();
    
    public boolean register(String username, String password)
    {
        if(userStore.containsKey(username))
        {
            return false;
        }
        int hashedPassword = password.hashCode();
        userStore.put(username, hashedPassword);
        return true;
        
    }
    
    public String login(String username, String password)
    {
        if(!userStore.containsKey(username))
        {
            return null;
        }
        int hashedPassword = password.hashCode();
        if(userStore.get(username) != hashedPassword)
        {
            return null;
        }
        String sessionToken = UUID.randomUUID().toString();
        sessionStore.put(sessionToken, username);
        return sessionToken;
    }
    public boolean isAuthenticated(String sessionToken)
    {
        return sessionStore.containsKey(sessionToken);
    }
    public void logout(String sessionToken)
    {
        sessionStore.remove(sessionToken);
    }
    public static void main(String[] args)
    {
        AuthSystem auth = new AuthSystem();
        
        System.out.println(auth.register("Rahul", "pass123"));
        System.out.println(auth.register("Rahul", "pass123"));
        
        String token = auth.login("Rahul", "pass123");
        System.out.println("Token: " + token);
        System.out.println(auth.login("Rahul", "wrongpass"));
        
        System.out.println(auth.isAuthenticated(token));
        auth.logout(token);
        System.out.println(auth.isAuthenticated(token));
    }
}