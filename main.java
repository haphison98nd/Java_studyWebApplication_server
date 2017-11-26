import java.io.*;
import java.net.*;

class TCPserver extends Thread{
	private static final int port = 6005;
    static Socket socket = null;
    static ServerSocket ss=null;
    static int main_roop_flag;
    static String version_info;
    
	public void run () {
		try {
            OutputStream osStr = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            BufferedReader irStr = new BufferedReader(new InputStreamReader(is));
            byte[] inputBuff = new byte[512];

            int recvByteLength = is.read(inputBuff);
            String buff = new String(inputBuff , 0 , recvByteLength);
            System.out.println(buff);

            //end process
            if(buff.equals("end_flag"))
            {
                InetAddress IntentAddr = socket.getInetAddress();
                String temp_ip=IntentAddr.toString();
                if( temp_ip.equals("/127.0.0.1"))
                {
                    end_processing();
                    main_roop_flag=0;
                    buff="end_ping";
                }
            }



            String send="receive@"+buff;

            if(buff.equals("check_ip"))
            {
                InetAddress IntentAddr = socket.getInetAddress();
                String temp_ip=IntentAddr.toString();
                if( temp_ip.equals("/127.0.0.1"))
                {
                    send=version_info;
                }
            }
            else{
                send="receive@"+buff;
            }


            //指定クライアントにデータを送信
            osStr.write(send.getBytes());//送信
            socket.close();
            

        }
        
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	public static void main(String args[]){

        //out put version info
        version_info=file_check_version();
        System.out.println("version:"+version_info);

        //open data file
        //
        //


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
				server.start();
			}
		}
		catch(Exception e){
            System.out.println(e);
        }
        

        try{
            ss.close();
		}
		catch(Exception e){
            System.out.println(e);
        }

        //server end processing
        System.out.println("end processing compeleted");



    }
    
    public  static String file_check_version()
    {
        String str="";
        try{
            File file = new File("version.txt");
            if (checkBeforeReadfile(file)){
              BufferedReader br = new BufferedReader(new FileReader(file));
              str=br.readLine();
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
        if (file.exists()){if (file.isFile() && file.canRead()){return true;}}
        return false;
    }

    //
    public static void end_processing()
    {
        ;
    }
    

}