package adalbertogonzaga.photoplaces.exif;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

/**
 * 
 * A simple class to insert/read EXIF data on image files.
 * In this case, only Geo Location data...
 * 
 * Library:
 *  Apache Commons Imaging
 *  https://commons.apache.org/proper/commons-imaging/ 
 * 	https://commons.apache.org/proper/commons-imaging/download_sanselan.cgi
 * 
 * Examples which were used to write this class:
 * 	https://commons.apache.org/proper/commons-imaging/xref-test/org/apache/commons/imaging/examples/
 *   
 * @author Adalberto Gonzaga
 *
 */
public class ExifGPSUtil {

	public ExifGPSUtil() {

	}

	/**
	 * Returns the GPS/Geo Location from a given image file.
	 * 
	 * @param fileLocation
	 *            A String containing the path for an image
	 * @return A String with the following format: -23.73793,-67.79077. If the
	 *         informed file/image has no GPS data, it will return null
	 */
	public static String getGPSLocationFromFile(String fileLocation) {

		File imgFile = new File(fileLocation);
		String gpsCoordinates = null;
				
		HashMap<String, Double> gpsCoordinatesMap = getGPSLocationFromFile(imgFile);
		
		if (gpsCoordinatesMap != null) {
			gpsCoordinates = gpsCoordinatesMap.get("Latitude") + "," + gpsCoordinatesMap.get("Longitude");
		}

		return gpsCoordinates;
	}

	/**
	 * Returns the GPS/Geo Location from a given image file.
	 * 
	 * @param imgFile
	 *            The image file which will be read to fetch the GPS data
	 * @return A HashMap<String,Double> with the following format: {Latitude=-23.73793,
	 *         Longitude=-67.79077} . If the informed file/image has no GPS
	 *         data, it will return null
	 */
	public static HashMap<String, Double> getGPSLocationFromFile(File imgFile) {

		HashMap<String, Double> gpsCoordinates = null;
		try {
			System.out.println("File: " + imgFile.getPath());			
						
			try {
				ImageInfo info = Sanselan.getImageInfo(imgFile);
				System.out.println("Image format: "+info.getFormat().name);
				//info.dump();
			} catch (ImageReadException e) {
				// TODO Auto-generated catch block
				System.out.println("Error:\n\t" + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error:\n\t" + e.getMessage());
			}
			
			// TODO: NEXT LESSON ::: CR2 FILES ARE THROWING EXCEPTIONS: IF IT THROWS, TRY TO INSERT METADATA!
			IImageMetadata metadata = Sanselan.getMetadata(imgFile);
			JpegImageMetadata jpgMetadata = (JpegImageMetadata) metadata;
	
			if (jpgMetadata != null && jpgMetadata.getExif() != null && jpgMetadata.getExif().getGPS() != null) {

				gpsCoordinates = new HashMap<String, Double>();
				gpsCoordinates.put("Latitude", jpgMetadata.getExif().getGPS().getLatitudeAsDegreesNorth());
				gpsCoordinates.put("Longitude", jpgMetadata.getExif().getGPS().getLongitudeAsDegreesEast());
			}

		} catch (ImageReadException e) {
			// TODO Auto-generated catch block
			System.out.println("Error:\n\t" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error:\n\t" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:\n\t" + e.getMessage());
		}
		return gpsCoordinates;
	}
	
	/**
	 * 
	 * Inserts a GPS Location into an image file.
	 * Most of the code was copied from Library's example files:
	 * 	https://commons.apache.org/proper/commons-imaging/xref-test/org/apache/commons/imaging/examples/WriteExifMetadataExample.html
	 * 
	 * @param originalFile
	 * @param destinationFile
	 * @param latitude
	 * @param longitude
	 */
	public static void insertGPSLocationIntoFile(File originalFile, File destinationFile, Double latitude, Double longitude) throws ClassCastException{

		OutputStream os = null;
		try {
			TiffOutputSet outputSet = null;
			// note that metadata might be null if no metadata is found.
			IImageMetadata metadata = Sanselan.getMetadata(originalFile);
			JpegImageMetadata jpegMetadata = null;
			try {
				jpegMetadata = (JpegImageMetadata) metadata;
			} catch (ClassCastException e) {
				throw e;
			} catch (Exception e) {
				// TODO handle this
				e.printStackTrace();
			}
			if (null != jpegMetadata) {
				// note that exif might be null if no Exif metadata is found.
				TiffImageMetadata exif = jpegMetadata.getExif();
				if (null != exif) {
					// TiffImageMetadata class is immutable (read-only).
					// TiffOutputSet class represents the Exif data to write.
					//
					// Usually, we want to update existing Exif metadata by
					// changing
					// the values of a few fields, or adding a field.
					// In these cases, it is easiest to use getOutputSet() to
					// start with a "copy" of the fields read from the image.
					outputSet = exif.getOutputSet();
				}
			}
			// if file does not contain any exif metadata, we create an empty
			// set of exif metadata. Otherwise, we keep all of the other
			// existing tags.
			if (null == outputSet)
				outputSet = new TiffOutputSet();
			
			// Example of how to add/update GPS info to output set.
			try {
				outputSet.setGPSInDegrees(longitude, latitude);
				
			} catch (ImageWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				os = new FileOutputStream(destinationFile);
				os = new BufferedOutputStream(os);
				new ExifRewriter().updateExifMetadataLossless(originalFile, os, outputSet);
				os.close();
				os = null;
			} catch (ImageReadException | ImageWriteException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ImageWriteException | ImageReadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
				}
		}
	}
	
	/**
	 * Just for debugging...
	 * @param args
	 */
	public static void main(String args[]) {

		HashMap<String, Double> info = ExifGPSUtil.getGPSLocationFromFile(new File("./IMG_0153_with_GPSMetadata.JPG"));
		if (info != null) System.out.println("\n./IMG_0153_with_GPSMetadata.JPG: " + info.toString());

		String gpsInfo = ExifGPSUtil.getGPSLocationFromFile("./IMG_0153.JPG");
		if (info != null) System.out.println("\n./IMG_0153.JPG: " + gpsInfo);
		
		info = ExifGPSUtil.getGPSLocationFromFile(new File("./myFile.CR2"));
		if (info != null) System.out.println("\n./IMG_2863_GPS.CR2: " + info.toString());

		gpsInfo = ExifGPSUtil.getGPSLocationFromFile("./IMG_2863.CR2");
		if (info != null) System.out.println("\n./IMG_2863.CR2: " + gpsInfo);

		ExifGPSUtil.insertGPSLocationIntoFile(new File("./IMG_3483.JPG"), new File("./IMG_3483_GEO.JPG"), new Double("-21.220118"), new Double("-47.7690902"));
	}
}
