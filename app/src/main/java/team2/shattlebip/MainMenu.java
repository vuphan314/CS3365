package team2.shattlebip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by Paul on 3/7/2016.
 */
public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mm_main);
        button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
    }

    private void button2Click() {
        startActivity(new Intent("team2.shattlebip.MainActivity"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                button2Click();
                break;

        }
    }
}
