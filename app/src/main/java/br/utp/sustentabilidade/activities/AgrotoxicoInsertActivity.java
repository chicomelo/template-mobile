package br.utp.sustentabilidade.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityAgrotoxicoInsertBinding;
import br.utp.sustentabilidade.models.Agrotoxico;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dudis on 2019-11-26.
 */
public class AgrotoxicoInsertActivity extends AppCompatActivity {
    private ActivityAgrotoxicoInsertBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agrotoxico_insert);
    }

    public void saveAction(View v) {
        if (mBinding.txtTitulo.getText().toString().length() > 0 && mBinding.txtDescricao.getText().toString().length() > 0) {
            Agrotoxico agrotoxico = new Agrotoxico();
            agrotoxico.setTitulo(mBinding.txtTitulo.getText().toString());
            agrotoxico.setDescricao(mBinding.txtDescricao.getText().toString());
            agrotoxico.setFoto(mBinding.txtFoto.getText().toString());

            Call<RespostaJSON<Agrotoxico>> call = NetworkManager.service().inserirAgrotoxico(agrotoxico);
            call.enqueue(new Callback<RespostaJSON<Agrotoxico>>() {

                @Override
                public void onResponse(final Call<RespostaJSON<Agrotoxico>> call, final Response<RespostaJSON<Agrotoxico>> response) {
                    RespostaJSON<Agrotoxico> resposta = response.body();
                    Log.d("TAG", "onResponse: " + resposta);
                    Log.d("TAG", "onResponse: " + resposta.getStatus());
                    if (resposta != null && resposta.getStatus() == 0) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(AgrotoxicoInsertActivity.this, "Problema ao tentar salvar!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(final Call<RespostaJSON<Agrotoxico>> call, final Throwable t) {
                    Log.e("TAG", "onFailure: ", t);
                }
            });
        }
        else {
            Toast.makeText(AgrotoxicoInsertActivity.this, "Preencha as duas informações!", Toast.LENGTH_SHORT).show();
        }
    }
}
