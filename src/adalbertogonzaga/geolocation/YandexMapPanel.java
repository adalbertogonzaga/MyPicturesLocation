package adalbertogonzaga.geolocation;

import java.awt.BorderLayout;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * A simple JPanel class that responds to some controls (reload, increase zoom, etc) and displays
 * the Yandex Maps on it's main content pane... 
 * 
 * https://tech.yandex.com/maps/
 * https://tech.yandex.com/maps/doc/staticapi/1.x/dg/concepts/about-docpage/
 * 
 * @author Adalberto Gonzaga
 *
 */
public class YandexMapPanel extends JPanel implements DagaMapPanel {

	private static final long serialVersionUID = -3735976936600516563L;
	private static final String BASE_YANDEX_URL = "https://static-maps.yandex.ru/1.x/?";
	private String mapLang = "en_US";
	private String mapMode = "map";
	private int zoom = 1;
	
	
	private double latitude = 8.9508023;
	private double longitude = -79.5296857;
	private JLabel lblImage;
	
	/**
	 * Create the panel.
	 */
	public YandexMapPanel() {
		setLayout(new BorderLayout(10, 10));

		lblImage = new JLabel("");
		add(lblImage, BorderLayout.CENTER);
		
		reloadMap();
	}

	public void setLatitude(double lat) {

		if (lat >= -90 && lat <= 90) {
			this.latitude = lat;
		}
	}

	public void setLongitude(double lon) {
		
		if (lon >= -180 && lon <= 180) {
			this.longitude = lon;
		}
	}
	
	public void changeMapTypeToSatellite() {
		
		this.mapMode = "sat,skl";
		this.reloadMap();
	}
	
	public void changeMapTypeToMap() {
		
		this.mapMode = "map";
		this.reloadMap();
	}
	
	public void increaseZoom() {
		if(this.zoom < 17) {
			this.zoom++;
			this.reloadMap();
		}
	}

	public void decreaseZoom() {
		if(this.zoom > 0) {
			this.zoom--;
			this.reloadMap();
		}		
	}
	
	public void reloadMap() {
		
		String mapURLString = 
				BASE_YANDEX_URL +
				"lang=" + mapLang + 
				"&ll="  + longitude+","+latitude +
				"&z="   + zoom +
				"&l="   + mapMode + 
				"&size=450,150" +
				"&pt="  + longitude+","+latitude + ",flag";
		System.out.println(mapURLString);
		
		URL mapURL = null;
		
		try {
			mapURL = new URL(mapURLString);
			
			InputStream is = mapURL.openStream();
			OutputStream os = new FileOutputStream("./images/map.png");
            
            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            
            os.flush();
            
            is.close();
            os.close();
			
            this.lblImage.setIcon( new ImageIcon (new ImageIcon("./images/map.png").getImage().getScaledInstance(736, 246, java.awt.Image.SCALE_SMOOTH)));

            System.out.println("Map was reloaded...");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
}
