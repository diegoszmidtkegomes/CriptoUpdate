package ifrs.com.criptoupdate.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ifrs.com.criptoupdate.R;
import ifrs.com.criptoupdate.adapters.CotacaoAdapter;
import ifrs.com.criptoupdate.data.CotacaoRepositorio;
import ifrs.com.criptoupdate.helpers.CotacaoHelper;
import ifrs.com.criptoupdate.helpers.MainActivityHelper;
import ifrs.com.criptoupdate.model.CotacaoCadastro;
import ifrs.com.criptoupdate.model.Moeda;
import ifrs.com.criptoupdate.model.interfaces.iAsyncObj;
import ifrs.com.criptoupdate.model.response.Cotacao;
import ifrs.com.criptoupdate.util.ConexaoInternet;
import ifrs.com.criptoupdate.util.Notificacao;
import ifrs.com.criptoupdate.views.RecyclerView.ItemClickSupport;

public class MainActivity extends AppCompatActivity implements iAsyncObj, SwipeRefreshLayout.OnRefreshListener {

    FloatingActionButton _btnAdd;
    private RecyclerView recyclerViewAval;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CotacaoCadastro> avals = new ArrayList<>();
    private int _posicao;
    private ProgressDialog progress;
    private int contReq=0, contReqRet=0;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("CriptoUpdate");
        setSupportActionBar(toolbar);
        new CotacaoHelper().atualizaMoedas(this);
        new MainActivityHelper().verificaService(this);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);

        _btnAdd = (FloatingActionButton) findViewById(R.id.cotacoes_add);
        recyclerViewAval = (RecyclerView) findViewById(R.id.recycler_lista_cotacoes);

        recyclerViewAval.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.scrollToPosition(0);
        registerForContextMenu(recyclerViewAval);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewAval.addItemDecoration(itemDecoration);
        recyclerViewAval.setLayoutManager(mLayoutManager);

        ItemClickSupport.addTo(recyclerViewAval).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CotacaoCadastro cot = avals.get(position);

                try {
                    Intent intentResposta = new Intent(MainActivity.this, CotacaoCadastroActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(getString(R.string.param_id), cot.getId());
                    intentResposta.putExtras(bundle);
                    startActivity(intentResposta);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    processarCarregamento();
                }
            }
        });

        ItemClickSupport.addTo(recyclerViewAval).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                //Log.i("diego", "vou excluir");
                _posicao = position;
                return false;
            }
        });

        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResposta = new Intent(MainActivity.this, CotacaoCadastroActivity.class);
                //Intent intentResposta = new Intent(MainActivity.this, PreferenceConfigCot.class);
                startActivity(intentResposta);
            }
        });
        processarCarregamento();
        //carregaLista();
    }

    private void processarCarregamento() {

        ConexaoInternet cd = new ConexaoInternet(this);

        if (cd.possuiConexao()) {
            progress = ProgressDialog.show(this, "Cotação",
                    "Carregando informações..", true);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            validaAtivos();
        } else {
            Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }
    }


    private void validaAtivos() {
        List<CotacaoCadastro> lista = new CotacaoRepositorio().selectTodosAtivos();
        for (CotacaoCadastro cot: lista
             ) {
            if(cot.getMoedaEnum().equals(Moeda.BITCOIN)){
                new CotacaoHelper().buscaBtc(this);
                contReq++;
            }
            if(cot.getMoedaEnum().equals(Moeda.BCASH)){
                new CotacaoHelper().buscaBch(this);
                contReq++;
            }
            if(cot.getMoedaEnum().equals(Moeda.LITECOIN)){
                new CotacaoHelper().buscaLtc(this);
                contReq++;
            }
        }
        if(contReq ==0){
            if(progress!=null && progress.isShowing()){
                progress.dismiss();
            }
        }
    }

    private void carregaLista() {
        CotacaoRepositorio qr = new CotacaoRepositorio();
        avals = qr.selectTodosAtivos();
        CotacaoAdapter adapter = new CotacaoAdapter(avals);
        recyclerViewAval.setAdapter(adapter);
    }

    @Override
    public void processoEncerrado(Object obj) {
        if (obj instanceof Cotacao) {
            Cotacao cot = (Cotacao) obj;
            contReqRet++;
            new Notificacao().exibeCotacao(this, cot);
            CotacaoCadastro cad = new CotacaoRepositorio().selectByMoeda(cot.getMoedaEnum());
            if (cad != null) {
                new CotacaoRepositorio().updateValor(cad.getId(), Double.parseDouble(cot.getTicker().getSell()));
                if (contReq == contReqRet) {
                    if (progress != null && progress.isShowing()) {
                        carregaLista();
                        progress.dismiss();
                        contReq = 0;
                        contReqRet = 0;
                    }
                }
            } else {
                if (progress != null && progress.isShowing()) {
                    carregaLista();
                    progress.dismiss();
                    contReq = 0;
                    contReqRet = 0;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(contReq == 0)
            processarCarregamento();
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        processarCarregamento();
        swipeLayout.setRefreshing(false);
    }
}
