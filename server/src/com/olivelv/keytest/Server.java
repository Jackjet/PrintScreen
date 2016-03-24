/**   
* @Title: Server.java
* socket服务端，用来接收文件，并保存为jpg格式。单向的。 
* @author olivelv  
* @date Mar 23, 2016 8:09:42 PM  
* 
*/
package com.olivelv.keytest;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	private int port = 8000;//监听的端口号   
	private String base="D:/serverTemp/";
	public Server(int port,String base){
		this.port=port;
		this.base=base;
	}
	public static void main(String[] args) { 
		
		Scanner cin=new Scanner(System.in);
		System.out.println("请输入监听端口(默认为8000):");
		String tmp=cin.nextLine();
		int port=8000;
		String base=System.getProperty("user.dir")+"/serverTemp/";
		if(tmp.length()!=0)port=Integer.parseInt(tmp); 
		System.out.println("请输入保存图片的路径（默认为"+base+"）：");
		tmp=cin.nextLine();
		if(tmp.length()!=0)base=tmp;
		Server server=new Server(port,base);
		server.init();  
	}  

	public void init() {  
		ServerSocket serverSocket = null;
		try {  
			serverSocket = new ServerSocket(port);  
			while (true) {  
				Socket client = serverSocket.accept();  
				receiveFile(client);
				//一个客户端连接就开户两个线程处理读写  
				//new Thread(new ReadHandlerThread(client)).start();
				
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally{
			try {
				if(serverSocket != null){
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}  
	public void receiveFile(Socket socket) throws IOException {  
        byte[] inputByte = null;  
        int length = 0;  
        DataInputStream dis = null;  
        FileOutputStream fos = null;  
        
        String filePath = base+Long.toString(System.currentTimeMillis())+".jpg";  
        try {  
            try {  
                dis = new DataInputStream(socket.getInputStream());  
                File f = new File(base);  
                if(!f.exists()){  
                    f.mkdir();    
                }  
                /*   
                 * 文件存储位置   
                 */  
                fos = new FileOutputStream(new File(filePath));      
                inputByte = new byte[1024];     
                System.out.println("开始接收数据...");    
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {  
                    fos.write(inputByte, 0, length);  
                    fos.flush();      
                }  
                System.out.println("完成接收："+filePath);  
            } finally {  
                if (fos != null)  
                    fos.close();  
                if (dis != null)  
                    dis.close();  
                if (socket != null)  
                    socket.close();   
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  