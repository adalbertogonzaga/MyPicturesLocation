package adalbertogonzaga.geolocation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Useless class... only used to make things easier when I was developing/designing it under
 * Eclipse's WindowBuilder plugin...
 * 
 * @author Adalberto Gonzaga
 *
 */
public class YandexMapPanelTester extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3612833686922858183L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public YandexMapPanelTester() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 736, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	public void setMapPanel(JPanel mapPanel) {
		this.contentPane = mapPanel;
		this.setContentPane(contentPane);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YandexMapPanelTester frame = new YandexMapPanelTester();
					frame.setVisible(true);
					JPanel mapPanel = new YandexMapPanel();
					((YandexMapPanel)mapPanel).changeMapTypeToSatellite();
					frame.setMapPanel(mapPanel);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
