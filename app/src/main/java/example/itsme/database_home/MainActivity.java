package example.itsme.database_home;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends ListActivity {

    toursDataSource dataSource;
    private static final String LOGTAG = "EXPLORECA";
    private List<tours> tours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new toursDataSource(this);
        dataSource.Open();

        List<tours> tours = dataSource.findAll();
        if (tours.size() == 0) {
            createData();
            tours = dataSource.findAll();
        }

        refreshDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_cheap:
                tours = dataSource.findFiltered("price <= 200", "price ASC");
                refreshDisplay();

                break;

            case R.id.menu_fancy:
                tours = dataSource.findFiltered("price >= 500", "price DESC");
                refreshDisplay();
                break;

            case R.id.menu_all:
                tours = dataSource.findAll();
                refreshDisplay();
                break;

            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        dataSource.Open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.Close();
        super.onPause();
    }

    public void refreshDisplay(){

        ArrayAdapter<tours> adapter = new
                ArrayAdapter<tours>(MainActivity.this,android.R.layout.simple_list_item_1,tours);
        setListAdapter(adapter);
    }

    private void createData() {

        toursPullParcer parcer = new toursPullParcer();
        List<tours> tours = parcer.parseXML(MainActivity.this);

        for (tours tour : tours) {

            dataSource.insertData(tour);
        }

    }

}
