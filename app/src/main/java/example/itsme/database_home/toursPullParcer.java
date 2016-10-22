package example.itsme.database_home;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itsme on 10/7/2016.
 */
public class toursPullParcer {

    private static final String LOGTAG = "EXPLOREPK";
    //First create 5 constent each of them matching the data element that is the part of the Xml file.
    // We will use these constent to identify what we are looking for.
    private static final String TOUR_ID = "tourId";
    private static final String TOUR_TITLE = "tourTitle";
    private static final String TOUR_DESCRIPTION = "description";
    private static final String TOUR_PRICE = "price";
    private static final String TOUR_IMAGE = "image";

    private tours currentTour = null;
    private String currentTag = null;
    List<tours> tours = new ArrayList<tours>();

    public List<tours> parseXML(Context context) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            InputStream stream = context.getResources().openRawResource(R.raw.tours);
            xpp.setInput(stream, null);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    handleStartTag(xpp.getName());

                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(xpp.getText());
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.d(LOGTAG, e.getMessage());

        } catch (IOException e) {
            Log.d(LOGTAG, e.getMessage());

        }
        return tours;
    }


    private void handleText(String text) {
        String xmlText = text;
        if (currentTour != null && currentTag != null) {

            if (currentTag.equals(TOUR_ID)) {
                Integer id = Integer.parseInt(xmlText);
                currentTour.setId(id);
            }

            if (currentTag.equals(TOUR_TITLE)) {
                currentTour.setTitle(xmlText);
            }

            if (currentTag.equals(TOUR_DESCRIPTION)) {
                currentTour.setDescription(xmlText);
            }

            if (currentTag.equals(TOUR_IMAGE)) {
                currentTour.setImage(xmlText);
            }
            if (currentTag.equals(TOUR_PRICE)) {
                double d = Double.parseDouble(xmlText);
                currentTour.setPrice(d);
            }
        }
    }


    private void handleStartTag(String name) {

        if (name.equals("tour")) {
            currentTour = new tours();
            tours.add(currentTour);
        } else {
            currentTag = name;
        }
    }
}

