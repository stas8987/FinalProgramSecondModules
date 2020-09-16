import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnect {
    private final TCPConnectList eventList;
    private final Socket socket;
    private final Thread rxThread;
    private final BufferedReader input;
    private final BufferedWriter output;
    public TCPConnect(TCPConnectList eventList, String ipAddr, int port) throws IOException {
        this(eventList, new Socket(ipAddr, port));
    }
    public TCPConnect(TCPConnectList eventList, Socket socket) throws IOException {
        this.eventList = eventList;
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        output =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    eventList.onConnReady(TCPConnect.this);
                    while (!rxThread.isInterrupted()){
                        String line = input.readLine();
                        eventList.onReceString(TCPConnect.this, line);
                    }
                }catch (IOException e){
                    eventList.onException(TCPConnect.this, e);
                }finally {
                    eventList.onDisconnest(TCPConnect.this);
                }
            }
        });
        rxThread.start();
    }
    public synchronized void  sendString(String value){
        try {
            output.write(value + "\r\n");
            output.flush();
        }catch (IOException e) {
            eventList.onException(TCPConnect.this, e);
            disconnect();
        }

    }
    public synchronized void  disconnect(){
        rxThread.interrupt();
        try {
            socket.close();
        }catch (IOException e){
            eventList.onException(TCPConnect.this, e);
        }
    }
    @Override
    public String toString() {

        return "TCPConnect" + socket.getInetAddress() + ": " + socket.getPort();
    }
}
