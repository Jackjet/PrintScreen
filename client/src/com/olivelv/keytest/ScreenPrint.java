/**   
 * @Title: ScreenPrint.java 
 * 获取剪切板中最新的图片，保存到本地，并发送到服务器端
 * 设置全局热键 alt+S 保存并发送图片
 * 			alt+q 退出程序
 * 使用了第三方jar包jintellitype监听全局热键；注意dll文件要放在package com.melloware.jintellitype下
 * @author olivelv  
 * @date Mar 23, 2016 4:46:45 PM  
 * 
 */
package com.olivelv.keytest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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
		// 注册热键alt+S 获取剪切板的截图，保存到本地base路径下，并将该图片发送到服务器端
		JIntellitype.getInstance().registerHotKey(FUN_KEY_MARK,
				JIntellitype.MOD_ALT, (int) 'S');
		// 注册热键alt+Q 退出
		JIntellitype.getInstance().registerHotKey(EXIT_KEY_MARK,
				JIntellitype.MOD_ALT, (int) 'Q');
		// 添加热键监听器
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
			Image image = getImageFromClipboard();
			long curr=System.currentTimeMillis();
			String path=base+Long.toString(curr)+".jpg";
			File f = new File(base);  
            if(!f.exists()){  
                f.mkdir();    
            }  
            // 获取截图，并保存
			savePic(image,path);
			// 发送图片
			client.sendFile(path);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	// 从剪切板中获取图片，保存到Image对象中
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
		// 获得桌面的宽和高
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = dim.width;
		int h = dim.height;

		// 首先创建一个BufferedImage变量，因为ImageIO写图片用到了BufferedImage变量。
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

		// 再创建一个Graphics变量，用来画出来要保持的图片，及上面传递过来的Image变量
		Graphics g = bi.getGraphics();
		try {
			g.drawImage(iamge, 0, 0, null);

			// 将BufferedImage变量写入文件中。
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
		System.out.println("请输入服务器地址(默认为localhost):");
		String tmp=cin.nextLine();
		if(tmp.length()!=0)ip=tmp;
		System.out.println("请输入监听端口(默认为8000):");
		tmp=cin.nextLine();
		
		if(tmp.length()!=0)port=Integer.parseInt(tmp); 
		System.out.println("请输入保存图片的路径（默认为"+base+"）：");
		tmp=cin.nextLine();
		if(tmp.length()!=0)base=tmp;
		new ScreenPrint(ip,port,base);
	}

}
