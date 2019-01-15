package adalbertogonzaga.mypicslocation;

/**
 * Just a launcher class... the idea is to have a console version 
 * 
 * @author Adalberto Gonzaga
 *
 */
public class MyPicturesLocation {
	
	public static void main(String args[]) {
		
		if (args != null && args.length > 0) {
			
			if ( args[0].equals("-console") ) {
				System.out.println("Starting in console mode...");
			}
			
		}
		else {
			
			System.out.println("Starting in windowed mode...");
			
			MyPicturesLocationWindow myWindow = new MyPicturesLocationWindow();
			myWindow.setVisible(true);
		}
		
	}

}
