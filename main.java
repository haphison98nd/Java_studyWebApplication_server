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


class Socket_test_server extends Thread{
    String inputdata;

    public void run()
    {
        try{

            System.out.println(this.inputdata);
            Thread.sleep(5000);

            // データグラムの送信元であるマシンの IP アドレス取得
                String ohost = "localhost";

			    Socket o_s = new Socket(ohost, 6005);
			    PrintWriter opw = new PrintWriter(o_s.getOutputStream());
                //opw.println(this.inputdata);
                opw.println("ok!");

			    // 出力ストリームをフラッシュ => 送信
			    opw.flush();
		    	// Finを送る
			    o_s.shutdownOutput();
			    // ソケットを閉じる
			    o_s.close();

                return;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {

        try{
             ServerSocket server=new ServerSocket(6000);

            while(true){
            Socket socket=server.accept();
            BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));


            String data;
            while(true){
				data = br.readLine();
				if(data == null){break;}

				System.out.println(data);

                //Thread Start
                Socket_test_server SS=new Socket_test_server();
                SS.inputdata=data;
                SS.start();
                }

                // 入力ストリームを閉じる
			    br.close();
                socket.close();
                server.close();

                /*
                //スレッドの起動
                SS.start();

                // データグラムの送信元であるマシンの IP アドレス取得
                String ohost = "localhost";
			    int oport = 60002;

			    Socket o_s = new Socket(ohost, oport);
			    PrintWriter opw = new PrintWriter(o_s.getOutputStream());
			    opw.println("ok!");

			    // 出力ストリームをフラッシュ => 送信
			    opw.flush();
		    	// Finを送る
			    o_s.shutdownOutput();
			    // ソケットを閉じる
			    o_s.close();
            */

            }//while end

        }
        catch(Exception e){
			System.out.println(e);
		}

    }//end main

       
}//end class