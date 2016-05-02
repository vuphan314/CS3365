package team2.shattlebip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * Created by Paul on 3/7/2016.
 */
public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button button1;
    Button button11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mm_main);
        button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button11 = (Button) findViewById(R.id.button5);
        button11.setOnClickListener(this);
    }

    private void button2Click() {
        startActivity(new Intent("team2.shattlebip.MainActivity"));
    }
    private void button5Click() {
        startActivity(new Intent("team2.shattlebip.Credits"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                button2Click();
                break;

            case R.id.button5:
                button5Click();
                break;


        }
    }
}
