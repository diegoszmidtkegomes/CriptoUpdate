package ifrs.com.criptoupdate.model.response;

import ifrs.com.criptoupdate.model.Moeda;

import static ifrs.com.criptoupdate.model.Moeda.BITCOIN;

/**
 * Created by diego on 07/11/2017.
 */

public class Cotacao {

    private Ticker ticker;
    private Moeda moeda;
    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public int getMoedaEnum(){
        switch (moeda){
            case BITCOIN:
                return 1;
            case LITECOIN:
                return 2;
            case BCASH:
                return 3;
            default:
                return 0;
        }
    }
}
