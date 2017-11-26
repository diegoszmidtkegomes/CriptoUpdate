package ifrs.com.criptoupdate.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import ifrs.com.criptoupdate.R;
import ifrs.com.criptoupdate.data.CotacaoRepositorio;
import ifrs.com.criptoupdate.helpers.CotacaoHelper;
import ifrs.com.criptoupdate.model.CotacaoCadastro;
import ifrs.com.criptoupdate.model.interfaces.iAsyncObj;
import ifrs.com.criptoupdate.model.response.Email;

public class CotacaoCadastroActivity extends AppCompatActivity implements iAsyncObj {

    private Button _btnSalvar;
    private ToggleButton btnAtivo;
    private Spinner spinner;
    private CotacaoCadastroActivity view;
    private EditText percent;
    private EditText email;
    private Button _btnDel;
    private long idCot = 0;
    private CotacaoCadastro cotacaoCadastro = null;
    private Toolbar toolbar;
    private Integer moedaAntiga;
    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotacao_cadastro);
        buscaParametrosIntent();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cotação");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = this;


        btnAtivo = (ToggleButton) findViewById(R.id.act_cot_cad_ativo); // initiate a toggle button
        percent = (EditText) findViewById(R.id.act_cot_cad_percent);
        email = (EditText) findViewById(R.id.act_cot_cad_email);
        spinner = (Spinner) findViewById(R.id.act_cot_cad_moeda);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moedas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(CotacaoCadastroActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                if (++check > 1) {
                    if (!verificaMoedaCadastrada()) {
                        //Toast.makeText(CotacaoCadastroActivity.this, "Moeda já cadastrada", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);

        moedaAntiga = spinner.getSelectedItemPosition() + 1;

        if (cotacaoCadastro != null) {
            if (cotacaoCadastro.getEmail() != null) {
                email.setText(cotacaoCadastro.getEmail());
            }
            percent.setText(String.valueOf(cotacaoCadastro.getPercentual()));
        }

        if(cotacaoCadastro!=null){
            btnAtivo.setChecked(cotacaoCadastro.isAtivo());
            spinner.setSelection(cotacaoCadastro.getMoeda()-1);
        }
        else{
            btnAtivo.setChecked(true);
        }
        /*_btnSalvar = (Button) findViewById(R.idCot.cadastro_cotacao_btnsalvar);
        _btnDel = (Button) findViewById(R.idCot.cadastro_cotacao_btndel);

        _btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!verificaMoedaCadastrada()){
                    idCot = new CotacaoRepositorio().salvar(idCot, spinner.getSelectedItemPosition() + 1,
                            btnAtivo.isChecked(), Integer.parseInt(percent.getText().toString()),
                            email.getText().toString());
                    new CotacaoHelper().salvarUsuario(CotacaoCadastroActivity.this, email.getText().toString());
                    view.finish();
                }
                else{
                    Toast.makeText(view, "Moeda já cadastrada", Toast.LENGTH_SHORT).show();
                }

            }
        });

        _btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CotacaoRepositorio().deletar(idCot);
                view.finish();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cotacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cotacao_action_finalizar) {
            boolean teste = verificaMoedaCadastrada();
            if (!verificaMoedaCadastrada()) {
                idCot = (int) new CotacaoRepositorio().salvar(idCot, spinner.getSelectedItemPosition() + 1,
                        btnAtivo.isChecked(), Integer.parseInt(percent.getText().toString()),
                        email.getText().toString());
                new CotacaoHelper().salvarUsuario(CotacaoCadastroActivity.this, email.getText().toString());
                view.finish();
            } else {
                Toast.makeText(view, "Moeda já cadastrada", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.cotacao_action_deletar) {
            new CotacaoRepositorio().deletar(idCot);
            view.finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private boolean verificaMoedaCadastrada() {
        boolean retorno;
        CotacaoCadastro c = new CotacaoRepositorio().selectByMoeda(spinner.getSelectedItemPosition() + 1);
        if (c == null) {
            retorno = false;
        } else {
            retorno = c.getId() != cotacaoCadastro.getId();
        }
        return retorno;
    }

    private void buscaParametrosIntent() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null){
            idCot = (long) bundle.getSerializable(getString(R.string.param_id));
            cotacaoCadastro = new CotacaoRepositorio().selectById(idCot);
        }
    }

    @Override
    public void processoEncerrado(Object obj) {
        if (obj instanceof Email) {
            Email e = (Email) obj;
            new CotacaoRepositorio().updateToken(idCot, e.getUser().getToken());
        }
    }
}
