package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.Feature.FeatureType;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;



//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Marcelo Queiroz
 * Date: December, 22nd, 2016 	
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	//private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	//public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	private List<PointFeature> earthquakes;
	
	public void setup() {
		size(950, 600, OPENGL);

			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleTerrainProvider());
			
			map.zoomToLevel(2);
			
			MapUtils.createDefaultEventDispatcher(this, map);	
			
			
			Location valLoc = new Location (-38.14f, -73.03f);
			//SimplePointMarker valEq = new SimplePointMarker(valLoc);
			PointFeature valEq = new PointFeature(valLoc);
			valEq.addProperty("title", "Valdivia, Chile");
			valEq.addProperty("magnitude", "9.5");
			valEq.addProperty("year", 1960);
			
			//Marker valMk = new SimplePointMarker(valLoc,valEq.getProperties());
			//map.addMarker(valMk);
			
			Location alaskaLoc = new Location (59.68f, -150.52f);
			Location sumatraLoc = new Location (-0.44f, 99.99f);
			Location japanLoc = new Location (38.82f, 140.30f);
			Location kamchatkaLoc = new Location (44.72f, 37.75f);
			
			PointFeature alaskaEq = new PointFeature(alaskaLoc);
			PointFeature sumatraEq = new PointFeature(sumatraLoc);
			PointFeature japanEq = new PointFeature(japanLoc);
			PointFeature kamchatkaEq = new PointFeature(kamchatkaLoc);
			
			alaskaEq.addProperty("magnitude", "9.2");
			sumatraEq.addProperty("magnitude", "9.1");
			japanEq.addProperty("magnitude", "9.0");
			kamchatkaEq.addProperty("magnitude", "9.0");
			
			alaskaEq.addProperty("title", "Alaska, USA");
			sumatraEq.addProperty("title", "Sumatra, Indonesia");
			japanEq.addProperty("title", "Tokyo, Japan");
			kamchatkaEq.addProperty("title", "Kamchatka, Russia");
			
			alaskaEq.addProperty("year", 1964);
			sumatraEq.addProperty("year", 2004);
			japanEq.addProperty("year", 2011);
			kamchatkaEq.addProperty("year", 1972);
			
			List<PointFeature> 	bigEqs = new ArrayList<PointFeature>();
			bigEqs.add(valEq);
			bigEqs.add(alaskaEq);
			bigEqs.add(sumatraEq);
			bigEqs.add(japanEq);
			bigEqs.add(kamchatkaEq);
			
		    // The List you will populate with new SimplePointMarkers
		    List<Marker> markers = new ArrayList<Marker>();
			
			for (PointFeature eq: bigEqs){
				markers.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));
			}
			
			int gray = color(200, 200, 200);
			int lightred = color(200,0,50, 80);
			
			
			for (Marker mk: markers){
				if( (int) mk.getProperty("year") > 2000){
					mk.setColor(lightred);
				}
				else{
					mk.setColor(gray);
				}
				
				
			}
		
			
	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    for (int i = 0; i < earthquakes.size(); i++) {
			PointFeature eqs = earthquakes.get(i);
			//markers.add( new  SimplePointMarker(eq2.getLocation(), eq2.getProperties()));
	    	SimplePointMarker marcadorTerremoto = createMarker(earthquakes.get(i));
	    	markers.add(marcadorTerremoto);
		}
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }
	    
	    map.addMarkers(markers);
			}
	 
	    
	    //TODO: Add code here as appropriate

		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		//SimplePointMarker marcadorTerremoto = null;
		int blue = color(0, 0, 255);
		int yellow = color(255, 255, 0);
		int red = color(255,0,0);
		
		float tmRaio = 0;
		int tmCor = 0;
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		if (mag < 4) { 
			tmRaio = 8;
			tmCor = blue;
		}
		else if (mag < 5){
			tmRaio = 15;
			tmCor = yellow;
		}
		else {
			tmRaio = 25;
			tmCor = red;
		}
		
		Location tmLocal = feature.getLocation();
		SimplePointMarker marcadorTerremoto = new SimplePointMarker(tmLocal);
		marcadorTerremoto.setColor(tmCor);
		marcadorTerremoto.setRadius(tmRaio);
		
		// finish implementing and use this method, if it helps.
		return marcadorTerremoto;
		
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		int numberOfEarthquakes = earthquakes.size();
		int leftMargin = 20;
		int keyWidth = 160;
		int keyCenter = leftMargin + keyWidth / 2;
		// Remember you can use Processing's graphics methods here
		fill(255, 255, 255);
		rect(leftMargin, 50, keyWidth, 250);
		fill(0, 0, 0);
		textAlign(CENTER, CENTER);
		textSize(20);
		text("LEGENDA", keyCenter, 70); 
		
		//red
		
		fill(255, 0, 0);
		ellipse(45, 140, 25,25); 
		textAlign(LEFT, CENTER);
		textSize(12);
		fill(0, 0, 0);
		text("Magnitude > 6", 70, 140); 
		
		//yellow

		fill(255, 255, 0);
		ellipse(45, 180, 15,15); 
		textAlign(LEFT, CENTER);
		
		fill(0, 0, 0);
		text("Magnitude > 4", 70, 180); 
		
		//blue
		
		fill(0, 0, 255);
		ellipse(45, 210, 8,8); 
		textAlign(LEFT, CENTER);
		
		fill(0, 0, 0);
		text("Magnitude > 2,5", 70, 210); 
		
		//how many earthquakes
		textAlign(LEFT);
		text("Houveram " + numberOfEarthquakes + "\n" +"terremotos" + "\n" + "na ultima semana",  35, 250); 
		
	
	}
}
