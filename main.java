import java.io.*;
import java.net.*;

class TCPserver extends Thread{
	private static final int port = 6005;
    static Socket socket = null;
    static ServerSocket ss=null;
    static int main_roop_flag;
    
	
	public void run () {
		try {
            OutputStream osStr = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            BufferedReader irStr = new BufferedReader(new InputStreamReader(is));
            byte[] inputBuff = new byte[512];

            int recvByteLength = is.read(inputBuff);
            String buff = new String(inputBuff , 0 , recvByteLength);
            System.out.println(buff);
            
            //指定クライアントにデータを送信
            String send="receive@"+buff;
			osStr.write(send.getBytes());//送信

            if(buff.equals("end_flag"))
            {
                System.out.println("get end flag");
                InetAddress IntentAddr = socket.getInetAddress();
                System.out.println("IP:"+socket.getInetAddress());
                String temp_ip=IntentAddr.toString();//socket.getLocalAddress();
                if( temp_ip.equals("/127.0.0.1"))
                {
                    System.out.println("Server end processing");

                    ss.close();
                    socket.close();
                    main_roop_flag=0;
                }
                
            }
            


        }
        
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	public static void main(String args[]){

        //out put version info
        System.out.println("version:");
        file_check_version();

        //init status
        main_roop_flag=1;


        //start server
		try{
            ss = new ServerSocket(port);
            
			
            while (main_roop_flag==1)
            {
				TCPserver server = new TCPserver();
                socket = ss.accept();
                System.out.println(socket.getInetAddress() + "accept");
                //ss.close();
				server.start();
			}
		}
		catch(Exception e){
            System.out.println(e);
        }
        
        //server end processing
        //ex.save_data();
        System.out.println("end processing compeleted");

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
              System.out.println("File not found");
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




