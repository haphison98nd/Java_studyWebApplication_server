import java.io.* ;
import java.net.* ;

class updater{
	public static void main(String args[]){

        JavaTCP jtcp=new JavaTCP_Updater();
        String version_info=jtcp.loadversion();

	}
}


class JavaTCP_Updater{
    
    public String tcp_text()
    {
        try{

            //new socket
			Socket s = new Socket(host, port);
            
            //stream
            OutputStream osStr = s.getOutputStream();
            InputStream is = s.getInputStream();
            //output server
            osStr.write( data.getBytes() , 0 , data.length() ); 

            for(int i=0;i<1000;i++){;};
            //receve
            byte[] inputBuff = new byte[512];
            int recvByteLength = is.read(inputBuff);
            String buff = new String(inputBuff , 0 , recvByteLength);

            s.close();
            //return receive;
            return buff;

		}
		catch(Exception e){
			return e.toString();
		}
    }
    

    //ファイルからTCP情報を読み取る
    public String loadversion(String filepass)
    {
        
        try{
            File file = new File(filepass);
            String str;
            if (checkBeforeReadfile(file)){
              BufferedReader br = new BufferedReader(new FileReader(file));
              while((str = br.readLine()) != null){
                System.out.println("now version is "+str);
              }
              br.close();

            }else{
              System.out.println("File not found");
            }
          }catch(FileNotFoundException e){
            System.out.println(e);
          }catch(IOException e){
            System.out.println(e);
          }


        return str;
    }

    //ファイルチェッカー
    private boolean checkBeforeReadfile(File file)
    {
        if (file.exists()){if (file.isFile() && file.canRead()){return true;}}return false;
    }
}