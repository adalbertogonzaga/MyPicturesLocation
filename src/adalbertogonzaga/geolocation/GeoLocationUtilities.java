package adalbertogonzaga.geolocation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Simple class to parse/validate some Latitude/Longitude values
 * 
 * @author Adalberto Gonzaga
 *
 */
public class GeoLocationUtilities {
	
	public static Boolean validateLatitudeAndLongitude(Double latitude, Double longitude) {
		
		return (validateLatitude(latitude) && validateLongitude(longitude) );
	}
	
	public static Boolean validateLatitude(Double latitude) {
		
		Boolean latitudeValid = false;
		
		if (latitude >= -90 && latitude <= 90) {
			latitudeValid = true;			
		}
		
		return latitudeValid;
	}
	
	public static Boolean validateLongitude(Double longitude) {
		
		Boolean longitudeValid = false;
		
		if (longitude >= -180 && longitude <= 180) {
			longitudeValid = true;			
		}
		
		return longitudeValid;
	}
	
	
	/**
	 * For a given URL from Google Maps it will try to extract the Latitude/Longitude from it.
	 * Usually the Lat/Lon data on GMaps URL are after a "@" character or after a "/dir////" sequence.
	 * 
	 * It uses the "parseMapsURL" private method to 'scan' the value using a simple regex
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String getLocationFromGoogleMapsURL(String url) throws IOException {
		
		System.out.println("getLocationFromGoogleMapsURL >> " + url);
		
		String latAndLonResponse = "";
		
		Matcher m = parseMapsURL(url);
		
		if(m.find()) {
			// System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			latAndLonResponse = m.group(1) + "," + m.group(2);
		}
		
		return latAndLonResponse;		
	}
	
	private static Matcher parseMapsURL(String url) {
		
		System.out.println("parseMapsURL");
		
		String regexPattern = "[@|dir/]+([-]*[\\d]+.[\\d]+),([-]*[\\d]+.[\\d]+)";
		Pattern r = Pattern.compile(regexPattern);
		Matcher m;
		
		// if the provided URL is in a shortened way the below code is supposed to detect the redirect 
		// and get the resulting/final URL... 
		// maybe it's not working :(  I'll leave it here for the future,.....zzzZzZ 
		// it's possible that the following link has the solution >> https://stackoverflow.com/a/26046079
		if (!url.contains("@") && !url.contains("/dir/") ) {
			URL myURL;
			try {
				System.out.println(">> Opening connection... provided URL seems to be shortened...");
				myURL = new URL(url);
				URLConnection con = myURL.openConnection();
				con.connect();
				url = con.getURL().toString();
				System.out.println(">> New URL : " + url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		m = r.matcher(url);
			
		return m;
	}
	
}
