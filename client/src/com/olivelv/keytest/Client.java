/**   
* @Title: Client.java 
* socket�ͻ��ˣ����������ļ�������
* @author olivelv 
* @date Mar 23, 2016 8:10:43 PM  
* 
*/
package com.olivelv.keytest;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

/*	public static final String IP = "localhost";//��������ַ 
	public static final int PORT = 8000;//�������˿ں�  
*/	public String ip="localhost";
	public int port=8000;
	public Client(String ip,int port){
		System.out.println("creating client");
		this.ip=ip;
		this.port=port;
	}
	public void sendFile(String path){
		System.out.println("send file: "+path);
		File file=new File(path);
		DataOutputStream dos=null;
		try {
			Socket client = new Socket(ip, port);
			FileInputStream fo=new FileInputStream(file);
			OutputStream out=client.getOutputStream();
			dos = new DataOutputStream(out);
			byte buffer[]=new byte[fo.available()];
			System.out.println(buffer.length);
			fo.read(buffer);
			dos.write(buffer);
			dos.flush();
			out.close();
			dos.close();
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}  

