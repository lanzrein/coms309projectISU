import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * Created by johan on 25.09.2017.
 */
public class Server {
    public static byte[] bytes = {10,26,51,(byte)120};//very bad.
    public final static byte[] serverBytes = {10,25,69,55};

    public static void main(String[] args){
        try {
//            Scanner scanner = new Scanner(System.in);
//            if(scanner.next()=="redhat"){
//                bytes = serverBytes;
//            }
            System.out.println("Launching on mode : "+(bytes == serverBytes ? "Server red hat": "Server local test"));
            InetSocketAddress inet = new InetSocketAddress(InetAddress.getByAddress(bytes),80 );
            HttpServer server = HttpServer.create(inet, 0);
            System.out.println(server.getAddress().toString());

            server.createContext("/game",new PlayerHandler());
            server.setExecutor(null);
            server.start();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
