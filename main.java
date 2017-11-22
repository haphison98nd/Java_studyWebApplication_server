import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;


class TCPserver extends Thread{
	private static final int port = 50000;
	private Socket socket = null;
	
	public void run () {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String data = br.readLine();
            //int x = Integer.parseInt(br.readLine());

            System.out.println( socket.getLocalAddress() );
            System.out.println(data);
			pw.println(data);
			pw.flush();
            
            for(int step=0;step>1000;step++){;};
            
            //指定クライアントにデータを送る
            Socket s = new Socket(socket.getLocalAddress(), 6005);
            PrintWriter spw = new PrintWriter(s.getOutputStream());
            spw.println(data+"応答");
            spw.flush();
            s.shutdownOutput();
            s.close();

            socket.close();


		}
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	public static void main(String args[]){

        //out put version info
        System.out.println("version:");
        file_check_version();


        //start server
		try{
			ServerSocket ss = new ServerSocket(port);
			
			while (true) {
				TCPserver server = new TCPserver();
				server.socket = ss.accept();
				server.start();
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
    }
    
    public  static String file_check_version()
    {
        String str="";
        try{
            File file = new File("version.txt");
            if (checkBeforeReadfile(file)){
              BufferedReader br = new BufferedReader(new FileReader(file));
              while((str = br.readLine()) != null){
                System.out.println(str);
              }
              br.close();
              return str;
            }else{
              System.out.println("ファイルが見つからないか開けません");
              return str;

            }
          }catch(FileNotFoundException e){
            System.out.println(e);
            return str;
          }catch(IOException e){
            System.out.println(e);
            return str;
          }
    }
    //ファイルチェッカー
    public static boolean checkBeforeReadfile(File file)
    {
        if (file.exists()){if (file.isFile() && file.canRead()){return true;}}return false;
    }

}




