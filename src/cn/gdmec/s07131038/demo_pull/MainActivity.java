package cn.gdmec.s07131038.demo_pull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			List<User> users = parse(getAssets().open("user.xml"));
			
			for(User user : users){
				Log.i("info", user.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> parse(InputStream in) {
		List<User> users = null;
		User user = null;
		String tagName = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

			XmlPullParser parser = factory.newPullParser();

			parser.setInput(in, "UTF-8");

			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					users = new ArrayList<User>();
					break;
				case XmlPullParser.START_TAG:
					tagName = parser.getName();

					if (tagName.equals("user")) {
						user = new User();
						user.setUid(Integer.parseInt(parser.getAttributeValue(
								null, "id")));
					}
					if(tagName.equals("name")){
						eventType=parser.next();
						user.setName(parser.getText());
					}
					
					if(tagName.equals("password")){
						eventType=parser.next();
						user.setPassword(parser.getText());
					}
					break;
				
				case XmlPullParser.END_TAG:
					
					if(parser.getName().equals("user")){
						users.add(user);
					}
					break;
				}
				eventType=parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}
}
