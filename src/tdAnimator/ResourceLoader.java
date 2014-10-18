package tdAnimator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;

import javax.imageio.ImageIO;


public class ResourceLoader implements Serializable{


	private static final long serialVersionUID = 1L;

	
	/*public Image getIcon(String fileName){
		return Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/images/icons/" + fileName));
	}
	
	public Image getTitle(String fileName){
		return Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/images/title/" + fileName));
	}*/
	
	//USE THIS TO GET THE IMAGE FROM THE FILE LOCATION
	public BufferedImage getSprite(String filename) throws IOException{
		URL url = this.getClass().getResource("/resources/images/" + filename);
		BufferedImage img = ImageIO.read(url);
		return img;
	}
	
	public BufferedImage getSheet(String filename) throws IOException{
		URL url = this.getClass().getResource("/tdAnimator/resources/images/" + filename);
		BufferedImage img = ImageIO.read(url);
		return img;
	}
	/*
	public static BufferedImage getSheet(String filename) throws IOException{
		URL url = ResourceLoader.class.getResource("/resources/images/squares/" + filename);
		BufferedImage img = ImageIO.read(url);
		return img;
	}
	public int[] getMap(String city, boolean in, String filename) throws IOException{
		int[] mint = new int[1200];
		InputStream fin;
		if(in){
			fin = this.getClass().getResourceAsStream("/resources/files/maps/"+ city + "/inside/" + filename + ".txt");
		}else{
			fin = this.getClass().getResourceAsStream("/resources/files/maps/" + city + "/" + filename + ".txt");
		}
		ObjectInputStream finstrm = new ObjectInputStream(fin);
		try {
			mint = (int[]) finstrm.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		return mint;
	}*/
	public void saveAnim(Animation[] min, String filename) throws IOException{
		File d = new File(System.getProperty("user.home")+"/TDAnimations/"+"config.con");
		if(!d.exists()){
			File di = new File(System.getProperty("user.home")+"/TDAnimations/");
			di.mkdirs();
			System.out.println("Dir made");
			File o = new File(System.getProperty("user.home")+"/TDAnimations/config.con");
			o.createNewFile();
		}
		File hhm = new File(System.getProperty("user.home")+"/TDAnimations/" + filename + ".tdanim");
		hhm.createNewFile();
			FileOutputStream saveFile = new FileOutputStream(System.getProperty("user.home")+"/TDAnimations/" + filename + ".tdanim");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(min[0]);
			save.close();
			saveFile.close();
		
	}
}
