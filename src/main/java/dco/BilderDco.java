package dco;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dao.BasicDao;
import Database.Entities.Artikel;
import Database.Entities.Bilder;
import Database.Entities.Reservierung;

public class BilderDco {
	
	public void createBilder(Artikel artikel, String pfad){
		Bilder bilder = new Bilder();	
		byte[] picture = loadPicture(pfad);
		
		if(picture != null){
			bilder.setBild(loadPicture(pfad));
			bilder.setArtikel(artikel);
			BasicDao<Bilder> artikelDao = new BasicDao<Bilder>(Bilder.class);
			artikelDao.create(bilder);
		}
	}
	
	public void deleteBild(long id){
		BasicDao<Bilder> bilderDao = new BasicDao<Bilder>(Bilder.class);
		bilderDao.delete(bilderDao.findById(id));
	}
	
	
	private byte[] loadPicture(String pfad){
		try {
			 
			byte[] imageInByte;
			BufferedImage originalImage = ImageIO.read(new File(pfad));
 
			// convert BufferedImage to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
 
//			// convert byte array back to BufferedImage
//			InputStream in = new ByteArrayInputStream(imageInByte);
//			BufferedImage bImageFromConvert = ImageIO.read(in);
// 
//			ImageIO.write(bImageFromConvert, "jpg", new File(
//					"c:/new-darksouls.jpg"));
 
		} catch (IOException e) {
			System.out.println("Bild kann nicht geladen werden in BilderDco " + e.getMessage());
			return null;
		}

	}

}
