import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerChess implements TCPConnectList {
    public static void main(String[] args) {
        new ServerChess();
    }
    private  final ArrayList<TCPConnect> connects = new ArrayList<>();

    private  ServerChess(){
        System.out.println("ServerChess running...port 8090");
        try(ServerSocket serverSocket = new ServerSocket(8090)){
            while (true){
                try {
                    new TCPConnect(this, serverSocket.accept());
                }catch (IOException e){
                    System.out.println("TCPConect exception: " + e);
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnReady(TCPConnect tcpConnect) {
        connects.add(tcpConnect);
        sendToAllConnections("Client connected: " + tcpConnect);
    }

    @Override
    public synchronized void onReceString(TCPConnect tcpConnect, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnest(TCPConnect tcpConnect) {
        connects.remove(tcpConnect);
        sendToAllConnections("Client disconnect: " + tcpConnect);

    }

    @Override
    public synchronized void onException(TCPConnect tcpConnect, Exception e) {
        System.out.println("TCPConect exception: " + e);
    }
    private void sendToAllConnections(String value){
        System.out.println(value);
        final int conSise = connects.size();
        for (int i = 0; i < conSise; i++){
            connects.get(i).sendString(value);
        }
    }
}
