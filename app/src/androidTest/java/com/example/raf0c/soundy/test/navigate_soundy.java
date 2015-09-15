package com.example.raf0c.soundy.test;

import com.example.raf0c.soundy.MainActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class navigate_soundy extends ActivityInstrumentationTestCase2<MainActivity> {
  	private Solo solo;
  	
  	public navigate_soundy() {
		super(MainActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'com.example.raf0c.soundy.MainActivity'
		solo.waitForActivity(com.example.raf0c.soundy.MainActivity.class, 2000);
        //Click on Connect SoundCloud!
		solo.clickOnView(solo.getView(com.example.raf0c.soundy.R.id.btn_connectSC));
        //Click on username
		solo.clickOnWebElement(By.id("username"));
        //Clear text in username
		solo.clearTextInWebElement(By.id("username"));
        //Enter text in username
		solo.enterTextInWebElement(By.id("username"), "ragampo@gmail.com");
        //Set default small timeout to 17210 milliseconds
		Timeout.setSmallTimeout(17210);
        //Scroll to ImageView
		android.widget.ListView listView0 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView0, 0);
        //Scroll to ImageView
		android.widget.ListView listView1 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView1, 0);
	}
}
