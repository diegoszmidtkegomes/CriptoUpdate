package ifrs.com.criptoupdate.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by diego on 07/11/2017.
 */
@RealmClass
public class CotacaoCadastro extends RealmObject {

    @PrimaryKey
    private long id;
    private boolean ativo;
    private int moeda;
    private double valorVenda;
    private int percentual;
    private String email;
    private String token;

    public CotacaoCadastro() {
    }

    public CotacaoCadastro(long id, boolean ativo, int moeda) {
        this.id = id;
        this.ativo = ativo;
        this.moeda = moeda;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPercentual() {
        return percentual;
    }

    public void setPercentual(int percentual) {
        this.percentual = percentual;
    }

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
