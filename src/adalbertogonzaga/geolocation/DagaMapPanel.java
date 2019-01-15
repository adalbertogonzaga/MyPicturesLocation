package adalbertogonzaga.geolocation;

/**
 * The idea of this interface is to make things easier when I need to switch from Yandex Maps to another one, 
 * so I will only replace the "YandexMapPanel.java" following the main features described below... 
 * 
 * @author Adalberto Gonzaga
 *
 */
public interface DagaMapPanel {

	public abstract void reloadMap();
	
	public abstract void setLatitude(double lat);

	public abstract void setLongitude(double lon);
	
	public abstract void changeMapTypeToSatellite();
	
	public abstract void changeMapTypeToMap();	
	
	public abstract void increaseZoom();
	
	public abstract void decreaseZoom();
}
