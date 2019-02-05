package adalbertogonzaga.mypicslocation;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import adalbertogonzaga.geolocation.DagaMapPanel;
import adalbertogonzaga.geolocation.GeoLocationUtilities;
import adalbertogonzaga.geolocation.YandexMapPanel;


/**
 * 
 * This is the main GUI class.
 * 
 * It was generated using the Eclipse's WindowBuilder (v1.9.1) plugin
 * http://marketplace.eclipse.org/content/windowbuilder?mpc=true&mpc_state=
 * 
 * @author Adalberto Gonzaga
 *
 */
public class MyPicturesLocationWindow extends JFrame {

	private static final long serialVersionUID = 2144181795789854985L;
	private JPanel contentPane;
	private final JTextField textFieldLocationSource = new JTextField();
	private final ButtonGroup btngrpPictureSource = new ButtonGroup();
	private static String ACCEPTED_FILE_FORMATS[] = {"jpg", "jpeg", "png", "tif", "tiff"};
	private static String CHOOSE_LOCATION_TEXT = "< Chooose picture(s) source...>";
	private JTextField textFieldLatitude;
	private JTextField textFieldLongitude;
	private JTextField textFieldSourceURL;
	private final ButtonGroup btngrpGeoDataSource = new ButtonGroup();
	private final ButtonGroup btngrpCreateCopyOrNewFolder = new ButtonGroup();
	private JRadioButton rdbtnEnterLatLon;
	private JRadioButton rdbtnInformGoogleMaps;
	private JRadioButton rdbtnCreateACopy;
	private JRadioButton rdbtnSinglePicture;
	private JPanel panelMap;

	/**
	 * Create the frame.
	 */
	public MyPicturesLocationWindow() {
		setResizable(false);
		setTitle("My Pictures Location - GPS Location/Coordinates embedder for your pics!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblChooseSource = new JLabel("1. Choose the source of your picture(s):");
		lblChooseSource.setBounds(12, 13, 276, 17);
		lblChooseSource.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblChooseSource);
		
		rdbtnSinglePicture = new JRadioButton("Single Picture");
		rdbtnSinglePicture.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rdbtnPictureSourceChanged();
			}
		});
		rdbtnSinglePicture.setSelected(true);
		rdbtnSinglePicture.setBounds(530, 10, 107, 25);
		btngrpPictureSource.add(rdbtnSinglePicture);

		contentPane.add(rdbtnSinglePicture);
		
		JRadioButton rdbtnEntireFolder = new JRadioButton("Entire Folder");
		rdbtnEntireFolder.setBounds(647, 10, 101, 25);
		btngrpPictureSource.add(rdbtnEntireFolder);
		contentPane.add(rdbtnEntireFolder);
		textFieldLocationSource.setText(MyPicturesLocationWindow.CHOOSE_LOCATION_TEXT);
		textFieldLocationSource.setEditable(false);
		textFieldLocationSource.setBounds(80, 43, 555, 22);
		
		contentPane.add(textFieldLocationSource);
		textFieldLocationSource.setColumns(255);
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(12, 46, 56, 16);
		contentPane.add(lblLocation);
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int picturesSelectionMode;
				
				if (rdbtnSinglePicture.isSelected()) {
					picturesSelectionMode = JFileChooser.FILES_ONLY;  
				} else {
					picturesSelectionMode = JFileChooser.DIRECTORIES_ONLY;
				}
				
				btnBrowsePicturesClicked(picturesSelectionMode);
				
			}
		});
		btnBrowse.setBounds(647, 42, 97, 25);
		contentPane.add(btnBrowse);
		
		JLabel lblDataInput = new JLabel("2. Input the geolocation data:");
		lblDataInput.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDataInput.setBounds(12, 90, 276, 17);
		contentPane.add(lblDataInput);
		
		rdbtnEnterLatLon = new JRadioButton("Enter Latitude and Longitude");
		rdbtnEnterLatLon.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				latitudeAndLongitureSourceChanged(evt);
			}
		});
		rdbtnEnterLatLon.setSelected(true);
		btngrpGeoDataSource.add(rdbtnEnterLatLon);
		rdbtnEnterLatLon.setBounds(12, 115, 276, 25);
		contentPane.add(rdbtnEnterLatLon);
		
		rdbtnInformGoogleMaps = new JRadioButton("Inform Google Maps URL:");
		btngrpGeoDataSource.add(rdbtnInformGoogleMaps);
		rdbtnInformGoogleMaps.setBounds(306, 115, 196, 25);
		contentPane.add(rdbtnInformGoogleMaps);
		
		JLabel lblLatitude = new JLabel("Latitude:");
		lblLatitude.setBounds(12, 149, 80, 16);
		contentPane.add(lblLatitude);
		
		textFieldLatitude = new JTextField();
		textFieldLatitude.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("Enter was pressed on latitude field...");
					reloadMapImage();
				}
			}
		});
		textFieldLatitude.setToolTipText("Format: 8.9162143 or -5.6297802");
		textFieldLatitude.setBounds(104, 149, 116, 22);
		contentPane.add(textFieldLatitude);
		textFieldLatitude.setColumns(10);
		
		JLabel lblLongitude = new JLabel("Longitude:");
		lblLongitude.setBounds(12, 178, 80, 16);
		contentPane.add(lblLongitude);
		
		textFieldLongitude = new JTextField();
		textFieldLongitude.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("Enter was pressed on longitude field...");
					reloadMapImage();
				}
			}
		});
		textFieldLongitude.setToolTipText("Format: -79.5341035 or 111.6074552");
		textFieldLongitude.setColumns(10);
		textFieldLongitude.setBounds(104, 175, 116, 22);
		contentPane.add(textFieldLongitude);
		
		textFieldSourceURL = new JTextField();
		textFieldSourceURL.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("Enter was pressed on GMaps URL field...");
					getLocationFromURL();
				}
			}			
		});
		textFieldSourceURL.setEnabled(false);
		textFieldSourceURL.setBounds(306, 149, 441, 22);
		contentPane.add(textFieldSourceURL);
		textFieldSourceURL.setColumns(10);
		
		JLabel lblCheckLocation = new JLabel("3. Check the location on map:");
		lblCheckLocation.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCheckLocation.setBounds(12, 225, 276, 17);
		contentPane.add(lblCheckLocation);
		
		panelMap = new YandexMapPanel();
		panelMap.setBackground(SystemColor.activeCaption);
		panelMap.setBounds(12, 255, 736, 246);
		contentPane.add(panelMap);
		
		JLabel lblInsertData = new JLabel("4. Insert Geolocation data in your picture(s):");
		lblInsertData.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblInsertData.setBounds(12, 524, 401, 17);
		contentPane.add(lblInsertData);
		
		rdbtnCreateACopy = new JRadioButton("Create a copy of your picture(s)");
		btngrpCreateCopyOrNewFolder.add(rdbtnCreateACopy);
		rdbtnCreateACopy.setSelected(true);
		rdbtnCreateACopy.setBounds(12, 551, 276, 25);
		contentPane.add(rdbtnCreateACopy);
		
		JRadioButton rdbtnInsertGeolocationData = new JRadioButton("Create a new folder for the new file(s)");
		btngrpCreateCopyOrNewFolder.add(rdbtnInsertGeolocationData);
		rdbtnInsertGeolocationData.setBounds(12, 581, 276, 25);
		contentPane.add(rdbtnInsertGeolocationData);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveButtonClicked();
			}
		});
		btnSave.setBounds(651, 524, 97, 88);
		contentPane.add(btnSave);
		
		JButton btnMapTypeMap = new JButton("Map");
		btnMapTypeMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DagaMapPanel)getPanelMap()).changeMapTypeToMap();				
			}
		});
		btnMapTypeMap.setToolTipText("Change Map layout to \"Map/Roads\"");
		btnMapTypeMap.setBounds(461, 222, 70, 25);
		contentPane.add(btnMapTypeMap);
		
		JButton btnMapTypeSatellite = new JButton("Sat");
		btnMapTypeSatellite.setToolTipText("Change Map layout to \"Satellite\"");
		btnMapTypeSatellite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DagaMapPanel)getPanelMap()).changeMapTypeToSatellite();
			}
		});
		btnMapTypeSatellite.setBounds(534, 222, 70, 25);
		contentPane.add(btnMapTypeSatellite);
		
		JButton btnIncreaseZoom = new JButton("+");
		btnIncreaseZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DagaMapPanel)getPanelMap()).increaseZoom();
			}
		});
		btnIncreaseZoom.setToolTipText("Increase Map Zoom");
		btnIncreaseZoom.setBounds(606, 222, 70, 25);
		contentPane.add(btnIncreaseZoom);
		
		JButton btnDecreaseZoom = new JButton("-");
		btnDecreaseZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DagaMapPanel)getPanelMap()).decreaseZoom();
			}
		});
		btnDecreaseZoom.setToolTipText("Decrease Map Zoom");
		btnDecreaseZoom.setBounds(678, 222, 70, 25);
		contentPane.add(btnDecreaseZoom);
	}
	
	private void getLocationFromURL() {
		
		String url = this.textFieldSourceURL.getText();
		
		try {
			
			String latAndLon[] = GeoLocationUtilities.getLocationFromGoogleMapsURL(url).split(",");
			System.out.println("lat,lon"+latAndLon[0]);
			
			if (latAndLon.length == 2) {
				String latitude = latAndLon[0];
				String longitude = latAndLon[1];
				this.textFieldLatitude.setText(latitude);
				this.textFieldLongitude.setText(longitude);
				reloadMapImage();
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "The informed URL is not valid.", "Check the provided URL", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	protected void reloadMapImage() {
		
		if ( isGeolocationDataValid()) {
			
			DagaMapPanel panel = (DagaMapPanel)getPanelMap();
			panel.setLatitude(Double.parseDouble( textFieldLatitude.getText().replace(",",".")) );
			panel.setLongitude(Double.parseDouble( textFieldLongitude.getText().replace(",",".")) );
			panel.reloadMap();
			
		}		
	}

	protected void saveButtonClicked() {
		
		System.out.println("saveButtonClicked");
		if ( isPictureSourceValid() && isGeolocationDataValid() ) {
			Boolean createACopy = rdbtnCreateACopy.isSelected();
						
			File location = new File( getTextFieldLocationSource().getText() );
			
			String confirmMessage = "Confirm if the following information is according to your desire:\n\n";
			
			if (location.isDirectory()) {
				confirmMessage += "All images in following directory will be changed:\n"
						+ "\t"+location.getAbsolutePath()+"\n";
			} else {
				confirmMessage += "The following file will have the GeoLocation data embedded on it:\n"
						+ "\t"+location.getAbsolutePath()+"\n";				
			}
			
			if(createACopy) {
				confirmMessage += "\nCreate a copy of your picture(s): YES";								
			} else {
				confirmMessage += "\nCreate a new folder to place the \"GeoLocated\" file: Yes";				
			}
			
			confirmMessage += "\n\nIf the above information is correct, click 'YES', otherwise click 'NO'";
			
			int confirmation = JOptionPane.showConfirmDialog(this, confirmMessage, "Confirm the information", JOptionPane.YES_NO_OPTION);
			
			if(confirmation == JOptionPane.YES_OPTION) {
				System.out.println("YES");
				PicturesUtil.insertGeolocation(location, createACopy, getLatitude(), getLongitude() );
				JOptionPane.showMessageDialog(this, "Done!", "Finished", JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("NO");
			}			
		}
	}
		
	private Double getLatitude() {
		
		Double lat = null;
		try {
			lat = Double.parseDouble( getTextFieldLatitude().getText().replace(",", ".") );
		} catch (Exception e) {
			throw new NumberFormatException("Error on input data for Latitude:\n\"" + e.getLocalizedMessage());
		}
		
		return lat;
	}
	
	private Double getLongitude() {
		
		Double lon = null;
		try {
			lon = Double.parseDouble( getTextFieldLongitude().getText().replace(",", ".") );
		} catch (Exception e) {
			throw new NumberFormatException("Error on input data for Longitude:\n\"" + e.getLocalizedMessage());
		}
		
		return lon;
	}

	private boolean isGeolocationDataValid() {
		
		Boolean isGeolocationDataValid = false;
		String errorMessage = null;
		
		try {
			Double latitude = getLatitude();
			System.out.println("Latitude: " + latitude);
			Double longitude = getLongitude();
			System.out.println("Longitude: " + longitude);
			
			isGeolocationDataValid = GeoLocationUtilities.validateLatitudeAndLongitude(latitude, longitude);
			
			if (!isGeolocationDataValid) {
				errorMessage = "Check the Latitude and Longitude (max and min values)";
			}
			
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}

		if (!isGeolocationDataValid) {
			JOptionPane.showMessageDialog(this, errorMessage, "Check your Geolocation data source", JOptionPane.ERROR_MESSAGE);
		}
		
		return isGeolocationDataValid;
	}

	private Boolean isPictureSourceValid() {
		
		Boolean isLocationValid = false;

		if ( getTextFieldLocationSource() != null ) {
			
			File location = new File( getTextFieldLocationSource().getText() );
			
			if ( location.exists() ) {
				isLocationValid = true;
				System.out.println("Picture source: valid");
			}
			else {
				JOptionPane.showMessageDialog(this, "Please choose a valid source for your picture(s)", "Check picture(s) source", JOptionPane.ERROR_MESSAGE);
			}
		}				
				
		return isLocationValid;
	}

	protected void rdbtnPictureSourceChanged() {
		
		if ( getRdbtnSinglePicture() != null && getTextFieldLocationSource() != null ) {
			
			String location = getTextFieldLocationSource().getText();
			File pictureSource = new File(location);
			
			if( getRdbtnSinglePicture().isSelected() ) {
				
				if( pictureSource.exists() && pictureSource.isDirectory() ) {
					getTextFieldLocationSource().setText(MyPicturesLocationWindow.CHOOSE_LOCATION_TEXT);
				}
			} 
			else {
				
				if( pictureSource.exists() && !pictureSource.isDirectory() ) {
					getTextFieldLocationSource().setText(MyPicturesLocationWindow.CHOOSE_LOCATION_TEXT);
				}
			}
		}		
	}

	
	protected void latitudeAndLongitureSourceChanged(ChangeEvent event) {

		if(getRdbtnEnterLatLon() != null && getTextFieldLatitude() != null && getTextFieldLongitude() != null && getTextFieldSourceURL() != null ) {
			if ( getRdbtnEnterLatLon().isSelected() ) {
				getTextFieldLatitude().setEnabled(true);
				getTextFieldLongitude().setEnabled(true);
				getTextFieldSourceURL().setEnabled(false);
			} 
			else {
				getTextFieldSourceURL().setEnabled(true);
				getTextFieldLatitude().setEnabled(false);
				getTextFieldLongitude().setEnabled(false);
			}
		}		
	}

	public void btnBrowsePicturesClicked(int fileChooserMode) {
		
		JFileChooser fchPicSource = new JFileChooser();
		fchPicSource.setMultiSelectionEnabled(false);
		fchPicSource.setFileSelectionMode(fileChooserMode);
		fchPicSource.setFileFilter(new FileNameExtensionFilter("Image Files",MyPicturesLocationWindow.ACCEPTED_FILE_FORMATS));
		
		int option = fchPicSource.showOpenDialog(MyPicturesLocationWindow.this);
		
		if(option == JFileChooser.APPROVE_OPTION) {
			this.textFieldLocationSource.setText(fchPicSource.getSelectedFile().getAbsolutePath());
		} else {
			this.textFieldLocationSource.setText(MyPicturesLocationWindow.CHOOSE_LOCATION_TEXT);
		}		
	}
	
	protected JRadioButton getRdbtnEnterLatLon() {
		return rdbtnEnterLatLon;
	}
	protected JRadioButton getRdbtnInformGoogleMaps() {
		return rdbtnInformGoogleMaps;
	}
	protected JTextField getTextFieldLatitude() {
		return textFieldLatitude;
	}
	protected JTextField getTextFieldLongitude() {
		return textFieldLongitude;
	}
	protected JTextField getTextFieldSourceURL() {
		return textFieldSourceURL;
	}
	protected JRadioButton getRdbtnCreateACopy() {
		return rdbtnCreateACopy;
	}
	protected JRadioButton getRdbtnSinglePicture() {
		return rdbtnSinglePicture;
	}
	protected JTextField getTextFieldLocationSource() {
		return textFieldLocationSource;
	}
	protected JPanel getPanelMap() {
		return panelMap;
	}
}
