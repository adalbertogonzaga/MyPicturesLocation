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
				while (true){
					System.out.print(".");
					try { Thread.sleep(357); } catch (Exception e) {}
					if (Math.floorMod(System.nanoTime(), 97) == 0) {
						System.out.println("Module loaded.");
					}
				}
			}
			
		}
		else {
			
			System.out.println("Starting in windowed mode...");

			SplashScreen profileDialog = new SplashScreen(3000);
			profileDialog.showSplashAndExit();
			
			MyPicturesLocationWindow myWindow = new MyPicturesLocationWindow();
			myWindow.setVisible(true);

		}
		
	}

}
