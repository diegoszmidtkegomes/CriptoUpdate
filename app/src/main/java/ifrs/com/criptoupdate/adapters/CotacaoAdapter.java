package ifrs.com.criptoupdate.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import ifrs.com.criptoupdate.R;
import ifrs.com.criptoupdate.model.CotacaoCadastro;
import ifrs.com.criptoupdate.model.Moeda;

/**
 * Adapter para a lista de checklists pendentes
 *
 * @author diego.gomes
 * @version 1.0.0
 */
public class CotacaoAdapter extends RecyclerView.Adapter<CotacaoAdapter.ViewHolder> {

    /**
     * Lista de _avaliacoes recebida por parâmetro
     */
    private List<CotacaoCadastro> _avaliacoes;
    /**
     * Fonte do FontAwesome
     */
    private Typeface _font;
    private View view;

    /**
     * Construtor da classe
     *
     * @param _avaliacoes the avaliacoes
     */
    public CotacaoAdapter(List<CotacaoCadastro> _avaliacoes) {
        this._avaliacoes = _avaliacoes;
    }

    /**
     * Método implementando para a criação do ViewHolder da RecyclerView. Chamado uma vez.
     *
     * @param parent   Usado para realizar o inflate da View
     * @param viewType tipo da View
     * @return ViewHolder específico para a RecyclerView
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        _font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.list_cotacao, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Método implementando para a criação do ViewHolder da RecyclerView. Chamado uma vez para cada item.
     *
     * @param holder   Holder que será usado na RecyclerView.
     * @param position Posição do item que está sendo gerado
     */

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvTitulo.setText(_avaliacoes.get(position).getMoedaEnum().toString());
        holder.tvDescricao.setText(String.valueOf(_avaliacoes.get(position).getValorVenda()));
        if(_avaliacoes.get(position).getMoedaEnum().equals(Moeda.BITCOIN)){
            holder.tvImagem.setImageDrawable(view.getResources().getDrawable(R.drawable.bitcoin_icon));
        }
        else if(_avaliacoes.get(position).getMoedaEnum().equals(Moeda.LITECOIN)){
            holder.tvImagem.setImageDrawable(view.getResources().getDrawable(R.drawable.litecoin_icon));
        }
        else if(_avaliacoes.get(position).getMoedaEnum().equals(Moeda.BCASH)){
            holder.tvImagem.setImageDrawable(view.getResources().getDrawable(R.drawable.btc_cash_icon));
        }
    }

    /**
     * Método implementando para a criação do ViewHolder da RecyclerView. Retorno o tamanho da lista de avaliações
     *
     * @return Inteiro com o tamanho da lista
     */

    @Override
    public int getItemCount() {
        return _avaliacoes.size();
    }

    /**
     * Classe para exibição dos itens na RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * TextView da imagem do item da lista
         */
        public ImageView tvImagem;
        /**
         * TextView do título do item da lista
         */
        public TextView tvTitulo;
        /**
         * TextView da descrição do item da lista
         */
        public TextView tvDescricao;

        /**
         * Construtor da classe
         *
         * @param v the v
         */
        public ViewHolder(View v) {
            super(v);
            tvTitulo = (TextView) v.findViewById(R.id.list_checklist_pend_titulo);
            tvDescricao = (TextView) v.findViewById(R.id.list_checklist_pend_desc);
            tvImagem = (ImageView) v.findViewById(R.id.list_checklist_pend_imagem);
        }
    }
}
