public interface TCPConnectList {
    void onConnReady(TCPConnect tcpConnect);
    void onReceString(TCPConnect tcpConnect, String value);
    void onDisconnest(TCPConnect tcpConnect);
    void onException(TCPConnect tcpConnect, Exception e);
}