/**   
 * @Title: ScreenPrint.java 
 * ��ȡ���а������µ�ͼƬ�����浽���أ������͵���������
 * ����ȫ���ȼ� alt+S ���沢����ͼƬ
 * 			alt+q �˳�����
 * ʹ���˵�����jar��jintellitype����ȫ���ȼ���ע��dll�ļ�Ҫ����package com.melloware.jintellitype��
 * @author olivelv  
 * @date Mar 23, 2016 4:46:45 PM  
 * 
 */
package com.olivelv.keytest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class ScreenPrint {
	private static final int FUN_KEY_MARK = 1;
	private static final int EXIT_KEY_MARK = 0;
	private Client client=null;
	private String base;
	public ScreenPrint(String ip,int port,String base) {
		System.out.println("initialing Client");
		client=new Client(ip,port);
		this.base=base;
		// ע���ȼ�alt+S ��ȡ���а�Ľ�ͼ�����浽����base·���£�������ͼƬ���͵���������
		JIntellitype.getInstance().registerHotKey(FUN_KEY_MARK,
				JIntellitype.MOD_ALT, (int) 'S');
		// ע���ȼ�alt+Q �˳�
		JIntellitype.getInstance().registerHotKey(EXIT_KEY_MARK,
				JIntellitype.MOD_ALT, (int) 'Q');
		// ����ȼ�������
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {

			@Override
			public void onHotKey(int markCode) {
				// TODO Auto-generated method stub
				switch (markCode) {
				case FUN_KEY_MARK:
					getImage();
					break;
				case EXIT_KEY_MARK:
					System.exit(0);
					break;
				}

			}

		});
	}

	public void getImage() {
		System.out.println("get Image");
		try {
			//Image image = getImageFromClipboard();
			long curr=System.currentTimeMillis();
			String path=base+Long.toString(curr)+".jpg";
			File f = new File(base);  
            if(!f.exists()){  
                f.mkdir();    
            }  
            // ��ȡ��ͼ��������
			//savePic(image,path);
            snapShot(path);
			// ����ͼƬ
			client.sendFile(path);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void snapShot(String path) {
		try {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			// ������Ļ��һ��BufferedImage����screenshot
			BufferedImage screenshot = (new Robot())
					.createScreenCapture(new Rectangle(0, 0,
							(int) dim.getWidth(), (int) dim.getHeight()));
			File f = new File(path);
			System.out.print("Save File " + path);
			// ��screenshot����д��ͼ���ļ�
			ImageIO.write(screenshot, "jpg", f);
			System.out.print(" Finished!\n");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	// �Ӽ��а��л�ȡͼƬ�����浽Image������
	public static Image getImageFromClipboard() throws Exception {
		Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable cc = sysc.getContents(null);
		if (cc == null)
			return null;
		else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
			return (Image) cc.getTransferData(DataFlavor.imageFlavor);
		return null;
	}

	public void savePic(Image iamge,String path) {
		System.out.println("save image");
		// �������Ŀ�͸�
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = dim.width;
		int h = dim.height;

		// ���ȴ���һ��BufferedImage��������ΪImageIOдͼƬ�õ���BufferedImage������
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

		// �ٴ���һ��Graphics����������������Ҫ���ֵ�ͼƬ�������洫�ݹ�����Image����
		Graphics g = bi.getGraphics();
		try {
			g.drawImage(iamge, 0, 0, null);

			// ��BufferedImage����д���ļ��С�
			ImageIO.write(bi, "jpg", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String []args){
		int port=8000;
		String base=System.getProperty("user.dir")+"/clientTemp/";
		String ip="localhost";
		Scanner cin=new Scanner(System.in);
		System.out.println("�������������ַ(Ĭ��Ϊlocalhost):");
		String tmp=cin.nextLine();
		if(tmp.length()!=0)ip=tmp;
		System.out.println("����������˿�(Ĭ��Ϊ8000):");
		tmp=cin.nextLine();
		
		if(tmp.length()!=0)port=Integer.parseInt(tmp); 
		System.out.println("�����뱣��ͼƬ��·����Ĭ��Ϊ"+base+"����");
		tmp=cin.nextLine();
		if(tmp.length()!=0)base=tmp;
		new ScreenPrint(ip,port,base);
	}

}
