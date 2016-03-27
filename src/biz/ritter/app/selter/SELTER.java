/**
 * 
 */
package biz.ritter.app.selter;

import java.awt.BorderLayout;
import java.net.MalformedURLException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import biz.ritter.app.visitor.Browser;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * Die SELTER Anwendung! 
 * 
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class SELTER implements Runnable{

	/** Hauptfenster */
	private JFrame mainWindow = null;
	/** Laufzeitflag */
	private boolean running = false;
	/** Anwendungsfenster als HTML Anwendung */
	private Browser htmlBrowser = null;
	
	/**
	 * Setzt einen eigenen Rahmen um die SELTER Funktionalität.
	 * @param yourMainWindow
	 */
	public void setMainWindow(final JFrame yourMainWindow) {
		if (null == yourMainWindow) throw new IllegalArgumentException(new NullPointerException("SELTER need a window to run in it!"));
		if (running) throw new RuntimeException(new IllegalStateException("SELTER is running."));
		this.mainWindow = yourMainWindow;
	}
	
	/**
	 * Initialisierung der Stand-Alone Variante von SELTER.
	 */
	private void init () {
		JFrame window = new JFrame("SELTER");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setSize(800, 600);
		this.setMainWindow(window);

		JFXPanel selterHTMLApp = new JFXPanel();
		Platform.runLater(new Runnable() {
			@Override public void run() {
				SELTER.this.htmlBrowser = new Browser();
				Scene scene = new Scene(SELTER.this.htmlBrowser,750,500, Color.web("#666970"));
				selterHTMLApp.setScene(scene);
			}
		});
		this.mainWindow.add(selterHTMLApp);
	}
	
	/**
	 * Starten von SELTER
	 */
	public void run () {
		this.running = true;
		
		this.mainWindow.setVisible(true);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// create full qualified URL...
				String url = "";
				try {
					url = new java.io.File ("./Resources/SELTER.html").toURI().toURL().toExternalForm();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				// ...and load
				SELTER.this.htmlBrowser.load(url);
			}
		});
		
	}
	
	
	/**
	 * Startet die Anwendung.
	 * @param ignored
	 */
	public static void main(final String[] ignored) {
		final SELTER selter = new SELTER();
		selter.init();
		SwingUtilities.invokeLater(selter);		
	}
	
}
