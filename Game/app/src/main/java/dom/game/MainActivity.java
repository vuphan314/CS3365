package dom.game;

import android.content.res.Resources;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    int red = 0xFFFF4444, blue = 0xFF33B5E5,
            green = 0xff00ff00, white = 0xFFFFFFFF,
            gray = 0xFF888888;

    public void cell_clicked(View cell) {
        if (cell == findViewById(R.id.yc13) ||
                cell == findViewById(R.id.yc21) ||
                cell == findViewById(R.id.yc31) ||
                cell == findViewById(R.id.tc11) ||
                cell == findViewById(R.id.tc32) ||
                cell == findViewById(R.id.tc33))
            cell.setBackgroundColor(red);
        else
            cell.setBackgroundColor(blue);
    }

    public void reset_game(View view) {
        findViewById(R.id.yc13).setBackgroundColor(green);
        findViewById(R.id.yc21).setBackgroundColor(green);
        findViewById(R.id.yc31).setBackgroundColor(green);

        findViewById(R.id.yc11).setBackgroundColor(white);
        findViewById(R.id.yc12).setBackgroundColor(white);
        findViewById(R.id.yc22).setBackgroundColor(white);
        findViewById(R.id.yc23).setBackgroundColor(white);
        findViewById(R.id.yc32).setBackgroundColor(white);
        findViewById(R.id.yc33).setBackgroundColor(white);

        findViewById(R.id.tc11).setBackgroundColor(gray);
        findViewById(R.id.tc12).setBackgroundColor(gray);
        findViewById(R.id.tc13).setBackgroundColor(gray);
        findViewById(R.id.tc21).setBackgroundColor(gray);
        findViewById(R.id.tc22).setBackgroundColor(gray);
        findViewById(R.id.tc23).setBackgroundColor(gray);
        findViewById(R.id.tc31).setBackgroundColor(gray);
        findViewById(R.id.tc32).setBackgroundColor(gray);
        findViewById(R.id.tc33).setBackgroundColor(gray);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://dom.game/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://dom.game/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
