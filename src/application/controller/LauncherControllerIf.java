package application.controller;


public interface LauncherControllerIf {
	int KassojenMaara();

	void setKello();
	void nopeuta();
	void hidasta();
	boolean getItsepalvelukassat();
	
	void handleStart();
}
