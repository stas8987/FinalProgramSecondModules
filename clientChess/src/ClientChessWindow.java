import javax.swing.*;

public class ClientChessWindow extends JFrame {
    private  static final String IP = "192.168.10.249";// ip к которому конектимся
    private  static final int Port = 8090; //порт к которому конектимся
    //размер окна
    private  static final int W = 600;
    private  static final int H = 400;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientChessWindow();
            }
        });
    }
    private ClientChessWindow (){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //закрытие окна крестиком ну или ноликом :)
        setSize(W, H); //устанавливаем размер окна
        setLocationRelativeTo(null); //окно по середине
        setAlwaysOnTop(true);//окно поверх всех окон
        setVisible(true); //окно становится видимым
    }
}
