package ifrs.com.criptoupdate.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

import ifrs.com.criptoupdate.R;
import ifrs.com.criptoupdate.data.CotacaoRepositorio;
import ifrs.com.criptoupdate.model.CotacaoCadastro;

public class CotacaoCadastroActivity extends AppCompatActivity {

    private Button _btnSalvar;
    private ToggleButton btnAtivo;
    private Spinner spinner;
    private CotacaoCadastroActivity view;
    private Button _btnDel;
    private long id=0;
    private CotacaoCadastro cotacaoCadastro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotacao_cadastro);
        buscaParametrosIntent();
        view = this;

        btnAtivo = (ToggleButton) findViewById(R.id.act_cot_cad_ativo); // initiate a toggle button

        spinner = (Spinner) findViewById(R.id.act_cot_cad_moeda);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moedas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if(cotacaoCadastro!=null){
            btnAtivo.setChecked(cotacaoCadastro.isAtivo());
            spinner.setSelection(cotacaoCadastro.getMoeda()-1);
        }
        else{
            btnAtivo.setChecked(true);
        }


        _btnSalvar = (Button) findViewById(R.id.cadastro_cotacao_btnsalvar);
        _btnDel = (Button) findViewById(R.id.cadastro_cotacao_btndel);

        _btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean ativo = btnAtivo.isChecked();
                String text = spinner.getSelectedItem().toString();
                new CotacaoRepositorio().salvar(id, spinner.getSelectedItemPosition() + 1,ativo);
                view.finish();
            }
        });

        _btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CotacaoRepositorio().deletar(id);
                view.finish();
            }
        });
    }

    private void buscaParametrosIntent() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null){
            id = (long) bundle.getSerializable(getString(R.string.param_id));
            cotacaoCadastro = new CotacaoRepositorio().selectById(id);
        }
    }
}
