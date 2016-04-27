package pt.iscte.daam.basededados;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    protected TextView tvNome, tvEmail, tvTelephone;
    protected long id;

    protected static int RESULT_DELETE = 0;
    protected static int RESULT_BACK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvTelephone = (TextView) findViewById(R.id.tvTelephone);

        Intent i = getIntent();

        id = i.getLongExtra("id", 0);
        tvNome.setText(i.getStringExtra("nome"));
        tvEmail.setText(i.getStringExtra("email"));
        tvTelephone.setText(i.getStringExtra("telephone"));
    }

    public void deleteBtn(View v) {
        Intent i = new Intent();
        i.putExtra("recordID", id);
        setResult(RESULT_DELETE, i);
        finish();
    }

    public void backBtn(View v) {
        setResult(RESULT_BACK);
        finish();
    }
}
