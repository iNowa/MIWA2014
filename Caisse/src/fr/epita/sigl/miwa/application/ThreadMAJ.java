package fr.epita.sigl.miwa.application;

import fr.epita.sigl.miwa.application.ihm.Home;

public class ThreadMAJ extends Thread {
	public void run() {
	    // faire quelque chose
		Home home = new Home();
		home.open();
	  }
	public ThreadMAJ() {
		// TODO Auto-generated constructor stub
	}

}
