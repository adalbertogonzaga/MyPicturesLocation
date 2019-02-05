package adalbertogonzaga.mypicslocation;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import adalbertogonzaga.photoplaces.exif.ExifGPSUtil;

public class PicturesUtil {

	/**
	 * Reads a directory and retrieves all of it's images (some specific formats)
	 * 
	 * @param sourceDir A File class representing a directory
	 * @return An ArrayList containing a set of Strings holding the images file names 
	 */
	public static ArrayList<String> getImagesFromDirectory(File sourceDir) {
		
		System.out.println("getImagesFromDirectory");
		
		ArrayList<String> images = new ArrayList<String>();
		
		if (sourceDir.isDirectory()) {
			
			System.out.println(".isDirectory() >> True");
			
			/**
			 * Since the listFiles() method would return all of the files inside a directory and it's not 
			 * desirable at this point, it was needed to implement a FilenameFilter() to return only some
			 * specific formats (filtering by extension).
			 */
			File imageFiles[] = sourceDir.listFiles(
					new FilenameFilter() {
					    public boolean accept(File dir, String name) {
					    	
					    	File f = new File(dir.getAbsolutePath() + File.separator + name);
					        if( 
					        	f.isFile() &&	
				        		(
				        				name.toLowerCase().endsWith(".jpg") || 
				        				name.toLowerCase().endsWith(".jpeg") ||
				        				name.toLowerCase().endsWith(".tif") ||
				        				name.toLowerCase().endsWith(".tiff") ||
				        				name.toLowerCase().endsWith(".png")		        				
				        		)
					        		
			        		)
					        {
					        	// System.out.println(name+ " is image >> True");
					        	return true;
					        }
					        else {
					        	// System.out.println(name+ " is image >> False");
					        	return false;
					        }
					    }
				    }
					);
			
			if (imageFiles != null && imageFiles.length > 0) {
				for (File f : imageFiles) {
					images.add(f.getAbsolutePath());
				}
			}		
		}
		
		return images;
	}
	
	/**
	 * 
	 * For a given data source, being it a single image or an entire directory, it will look for each image file
	 * and try to insert the provided Latitude/Longitude data on it's EXIF data.
	 * The resulting file can be saved on the same folder (will have an indicator on it's name) or a new folder
	 * with the "GeoLocated" files will be created... 
	 * 
	 * @param sourceData A File object pointing to a single image file or a directory where the image files are stored
	 * @param createACopy If 'true' it will create a copy of the image file (the idea is to preserve the original). If 'false',
	 * 	it will create a new directory to store the GeoLocated files 
	 * @param latitude A Double value representing the Latitude. Desirable format: (-)50.123456
	 * @param longitude A Double value representing the Longitude. Desirable format: (-)50.123456
	 */
	public static void insertGeolocation(File sourceData, Boolean createACopy, double latitude, double longitude) throws ClassCastException{
		
		
		ArrayList<String> sourceFiles = new ArrayList<String>();
		if (sourceData.isDirectory()) {
			sourceFiles = PicturesUtil.getImagesFromDirectory(sourceData);
		} else {
			sourceFiles.add(sourceData.getAbsolutePath());
		}
				
		for (String filePath : sourceFiles) {
			
			File imageFile = new File(filePath);
			System.out.println("Preparing to insert geodata in file: " + filePath);
			
			String newFileName = null;
			
			if( createACopy ) {
				System.out.println("Create a copy: YES");
				String originalFilename = imageFile.getName();
				String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());

				System.out.println("Original File: " + originalFilename);
				System.out.println("File Extension: " + fileExtension);
				
				String nameWithNoExt = originalFilename.substring(0, originalFilename.lastIndexOf(fileExtension));
				newFileName = imageFile.getParentFile() + File.separator + nameWithNoExt + "_geo" + fileExtension;
								
			} else {
				System.out.println("Create a new folder: YES");
				
				File folder = new File(imageFile.getParentFile()+ File.separator + "geoLocatedImages");
				if (!folder.exists()) {
					folder.mkdirs();
				}
				newFileName = folder.getAbsolutePath() + File.separator + imageFile.getName();
			}
			System.out.println("New file: " + newFileName);
			ExifGPSUtil.insertGPSLocationIntoFile(imageFile, new File(newFileName), latitude, longitude);
		}		
	}
}
