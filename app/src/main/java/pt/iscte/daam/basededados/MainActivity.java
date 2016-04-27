package pt.iscte.daam.basededados;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    protected EditText etNome, etEmail, etTelephone;
    protected ListView lvContactos;
    protected ContactoDatasource ds;

    // usado para manipular e saber que uma determinada posição da ListVIew corresponda a um determinado "id" na base de dados!
    protected Map<Integer, Long> hm;

    protected static int DETAILS = 0;
    protected static int RESULT_DELETE = 0;
    protected static int RESULT_BACK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = (EditText)findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelephone = (EditText) findViewById(R.id.etTelephone);
        lvContactos = (ListView) findViewById(R.id.lvContactos);

        ds = new ContactoDatasource(this);
        ds.open();

        hm = new HashMap<Integer, Long>();

        populateListView();

        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Context ctx = getApplicationContext();
                Intent i = new Intent(ctx, DetailsActivity.class);

                long currPos = hm.get(position);

                Log.i("MYAPP", "POSITION = " + position);
                Log.i("MYAPP", "RECORD ID = " + currPos);

                Contacto c = ds.get(currPos);

                i.putExtra("id", c.getId());
                i.putExtra("nome", c.getNome());
                i.putExtra("email", c.getEmail());
                i.putExtra("telephone", c.getTelephone());

                startActivityForResult(i, DETAILS);
            }
        });
    }

    // Adiciona o contacto e adiciona-o à ListView
    public void addContacto(View v) {
        if(etNome.getText().toString().isEmpty() ||
                etEmail.getText().toString().isEmpty() ||
                etTelephone.getText().toString().isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
        } else {
            ds.create(etNome.getText().toString(), etEmail.getText().toString(), etTelephone.getText().toString());

            etNome.setText("");
            etEmail.setText("");
            etTelephone.setText("");
            etNome.requestFocus();

            populateListView();

            Toast.makeText(this, "Contacto guardado!", Toast.LENGTH_SHORT).show();
        }

    }

    public void populateListView() {
        int c = ds.count();
        if(c > 0) {
            // percorre a lista de contactos e obtém o nome
            List<Contacto> lc = ds.getAll();
            String[] values = new String[c];

            int i = 0;
            for (Contacto cn : lc) {
                values[i] = cn.getNome();

                hm.put(i, cn.getId());

                i++;
            }

            // adiciona os valores à ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
            lvContactos.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_DELETE) {
            Log.i("MYAPP", "RECORD ID  = " + data.getLongExtra("recordID",0));
            ds.apagar(data.getLongExtra("recordID",0));
            populateListView();
            Toast.makeText(this, "Contacto Apagado!", Toast.LENGTH_SHORT).show();
        }
    }
}
