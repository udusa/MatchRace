package com.matchrace.matchrace.classes;

import android.os.Environment;

/**
 * The application's constants.
 *
 */
public class C {

	// Location constants.
	public static final long MIN_TIME = 4000;
	public static final float MIN_DISTANCE = 10f;

	// Map constants.
	public static final float RADIUS_BUOY = 40f;
	public static final float ZOOM_LEVEL = 17.0f;
	public static final int MAX_BUOYS = 3;

	// Users constants.
	public static final String SAILOR_PREFIX = "Sailor";
	public static final String BUOY_PREFIX = "BuoyNum";

	// Login constants.
	public static final String USER_NAME = "user_name";
	public static final String USER_PASS = "user_pass";
	public static final String EVENT_NUM = "event_num";
	public static final String PREFS_USER = "user_prefs";
	public static final String PREFS_FULL_USER_NAME = "full_user_name";

	// DB constants.
	//public static final String URL_INSERT_CLIENT = "http://kcg-lab.info/map-tracking-2d/services/insertClient.php?table=clients";
	public static final String URL_CLIENTS_TABLE = "http://kcg-lab.info/map-tracking-2d/services/json-clients.php?table=clients";
	public static final String URL_HISTORY_TABLE = "http://kcg-lab.info/map-tracking-2d/services/json-clients.php?table=history";

    //My DB LOCAL HOST
    //10.0.2.2 / 192.168.43.215
    /*
    public static final String URL_REGISTER_CLIENT = "http://192.168.43.215/androidRegister.php?Table=clients";
    public static final String URL_SIGNIN_CLIENT = "http://192.168.43.215/androidSignIn.php?TableUsers=clients";//&TableEvents=events";
    public static final String URL_UPDATE_CLIENT = "http://192.168.43.215/androidUpdate.php?Table=clients";
    public static final String URL_SET_BUOYS = "http://192.168.43.215/androidSetBouys.php?Table=events";

    public static final String URL_GET_BUOYS = "http://192.168.43.215/androidGetBuoys.php?Table=events&Event=";
    public static final String URL_GET_CLIENT = "http://192.168.43.215/androidGetClients.php?Table=clients&Event=";
//    public static final String URL_GET_CLIENT = "http://10.0.2.2/androidGetClients.php?Table=clients&Event=";
    */
    //My DB on SERVER  	bmr.comuv.com
    public static final String URL_REGISTER_CLIENT = "http://bmr.comuv.com/androidRegister.php?Table=clients";
    public static final String URL_SIGNIN_CLIENT = "http://bmr.comuv.com/androidSignIn.php?TableUsers=clients";//&TableEvents=events";
    public static final String URL_UPDATE_CLIENT = "http://bmr.comuv.com/androidUpdate.php?Table=clients";
    public static final String URL_SET_BUOYS = "http://bmr.comuv.com/androidSetBouys.php?Table=events";

    public static final String URL_GET_BUOYS = "http://bmr.comuv.com/androidGetBuoys.php?Table=events&Event=";
    public static final String URL_GET_CLIENT = "http://bmr.comuv.com/androidGetClients.php?Table=clients&Event=";
    public static final String URL_GET_KML = "http://bmr.comuv.com/androidGetKml.php?Table=history";
    public static final String URL_GET_WEATHER = "http://api.openweathermap.org/data/2.5/weather?";//api.openweathermap.org/data/2.5/weather?lat=35&lon=139
    // Data constants.
	public static final String APP_DIR = Environment.getExternalStorageDirectory().getPath() + "/BlindMatchRace/";
}
