package ifrs.com.criptoupdate.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by diego on 07/11/2017.
 */
@RealmClass
public class CotacaoCadastro extends RealmObject {

    public CotacaoCadastro() {
    }

    @PrimaryKey
    private long id;
    private boolean ativo;
    private int moeda;
    private double valorVenda;

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Moeda getMoedaEnum() {
        if (moeda == 1)
            return Moeda.BITCOIN;
        else if(moeda ==2){
            return Moeda.LITECOIN;
        }
        else
            return Moeda.BCASH;
    }

    public CotacaoCadastro(long id, boolean ativo, int moeda) {
        this.id = id;
        this.ativo = ativo;
        this.moeda = moeda;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getMoeda() {
        return moeda;
    }

    public void setMoeda(int moeda) {
        this.moeda = moeda;
    }
}
