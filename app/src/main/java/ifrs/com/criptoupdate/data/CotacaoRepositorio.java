package ifrs.com.criptoupdate.data;

import java.util.ArrayList;
import java.util.List;

import ifrs.com.criptoupdate.model.CotacaoCadastro;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by diego on 07/11/2017.
 */

public class CotacaoRepositorio {

    public CotacaoCadastro selectByMoeda(int moeda){
        Realm realm = Realm.getDefaultInstance();
        CotacaoCadastro c = realm.where(CotacaoCadastro.class).equalTo("moeda", moeda).findFirst();
        return c;
    }

    public void updateValor(long id, double valor){
        CotacaoCadastro s = new CotacaoCadastro();
        s = selectById(id);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        s.setValorVenda(valor);
        realm.copyToRealmOrUpdate(s);
        realm.commitTransaction();
    }

    public void updateToken(long id, String token) {
        CotacaoCadastro s = selectById(id);
        if (s != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            s.setToken(token);
            realm.copyToRealmOrUpdate(s);
            realm.commitTransaction();
        }
    }


    public List<CotacaoCadastro> selectTodosAtivos() {
        Realm realm = Realm.getDefaultInstance();
        final List<CotacaoCadastro> questionarios = new ArrayList<>();
        final RealmResults<CotacaoCadastro> quests = realm.where(CotacaoCadastro.class).
                findAll();
        for (CotacaoCadastro a : quests) {
            questionarios.add(a);
        }
        return questionarios;
    }

    public long salvar(long id, int moeda, boolean ativo, int percent, String email) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        CotacaoCadastro s = new CotacaoCadastro();
        if(id!= 0){
            s = selectById(id);
        }
        else{
            try {
                s.setId(realm.where(CotacaoCadastro.class).max("id").intValue() + 1);
            } catch (Exception ex) {
                s.setId(1);
            }
        }
        s.setMoeda(moeda);
        s.setEmail(email);
        s.setPercentual(percent);
        s.setValorVenda(0);
        s.setAtivo(ativo);
        realm.copyToRealmOrUpdate(s);
        realm.commitTransaction();
        return s.getId();
    }

    public void deletar(long id) {
        Realm realm = Realm.getDefaultInstance();
        CotacaoCadastro c = selectById(id);
        if (c != null) {
            realm.beginTransaction();
            c.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    public CotacaoCadastro selectById(long id){
        Realm realm = Realm.getDefaultInstance();
        CotacaoCadastro c = realm.where(CotacaoCadastro.class).equalTo("id", id).findFirst();
        if (c != null)
            return c;
        else
            return null;
    }
}
