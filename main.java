import java.io.*;
import java.net.*;

class TCPserver extends Thread{
	private static final int port = 6005;
    private Socket socket = null;
    static int main_roop_flag;
    
	
	public void run () {
		try {
            InputStream sok_in = socket.getInputStream();
			InputStreamReader sok_is = new InputStreamReader(sok_in);
			BufferedReader sok_br = new BufferedReader(sok_is);
            OutputStream os = socket.getOutputStream();
            String receive =  sok_br.readLine();
            
            System.out.println(receive);
            receive =  sok_br.readLine();
            //指定クライアントにデータを送信
            String send=receive+"応答";
			os.write(send.getBytes());//送信

            
            if(receive.equals("end_flag"))
            {
                System.out.println("get end flag");
                InetAddress IntentAddr = socket.getInetAddress();
                System.out.println(socket.getInetAddress());
                String temp_ip="";//socket.getLocalAddress();
                if( temp_ip.equals("/127.0.0.1"))
                {
                    System.out.println("Server end processing");
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
			ServerSocket ss = new ServerSocket(port);
			
            while (main_roop_flag==1)
            {
				TCPserver server = new TCPserver();
                server.socket = ss.accept();
                ss.close();
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




