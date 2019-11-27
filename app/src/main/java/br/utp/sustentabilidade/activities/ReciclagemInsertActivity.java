package br.utp.sustentabilidade.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityReciclagemInsertBinding;
import br.utp.sustentabilidade.models.Reciclagem;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dudis on 2019-11-25.
 */
public class ReciclagemInsertActivity extends AppCompatActivity {
    private ActivityReciclagemInsertBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reciclagem_insert);
    }

    public void saveAction(View v) {
        if (mBinding.txtTitulo.getText().toString().length() > 0 && mBinding.txtDescricao.getText().toString().length() > 0) {
            Reciclagem reciclagem = new Reciclagem();
            reciclagem.setTitulo(mBinding.txtTitulo.getText().toString());
            reciclagem.setDescricao(mBinding.txtDescricao.getText().toString());
            reciclagem.setFoto(mBinding.txtFoto.getText().toString());

            Call<RespostaJSON<Reciclagem>> call = NetworkManager.service().inserirReciclagem(reciclagem);
            call.enqueue(new Callback<RespostaJSON<Reciclagem>>() {

                @Override
                public void onResponse(final Call<RespostaJSON<Reciclagem>> call, final Response<RespostaJSON<Reciclagem>> response) {
                    RespostaJSON<Reciclagem> resposta = response.body();
                    Log.d("TAG", "onResponse: " + resposta);
                    Log.d("TAG", "onResponse: " + resposta.getStatus());
                    if (resposta != null && resposta.getStatus() == 0) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(ReciclagemInsertActivity.this, "Problema ao tentar salvar!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(final Call<RespostaJSON<Reciclagem>> call, final Throwable t) {
                    Log.e("TAG", "onFailure: ", t);
                }
            });
        }
        else {
            Toast.makeText(ReciclagemInsertActivity.this, "Preencha as duas informações!", Toast.LENGTH_SHORT).show();
        }
    }
}
