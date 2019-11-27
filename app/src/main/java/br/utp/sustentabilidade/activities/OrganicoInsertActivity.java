package br.utp.sustentabilidade.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityOrganicoInsertBinding;
import br.utp.sustentabilidade.models.Organico;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dudis on 2019-11-26.
 */
public class OrganicoInsertActivity extends AppCompatActivity {
    private ActivityOrganicoInsertBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_organico_insert);
    }

    public void saveAction(View v) {
        if (mBinding.txtTitulo.getText().toString().length() > 0 && mBinding.txtDescricao.getText().toString().length() > 0 && mBinding.txtLocal.getText().toString().length() > 0 && mBinding.txtLocalUrl.getText().toString().length() > 0) {
            Organico organico = new Organico();
            organico.setTitulo(mBinding.txtTitulo.getText().toString());
            organico.setDescricao(mBinding.txtDescricao.getText().toString());
            organico.setLocal(mBinding.txtLocal.getText().toString());
            organico.setLocalUrl(mBinding.txtLocalUrl.getText().toString());
            organico.setDia("");
            organico.setHorario("");
            organico.setFoto(mBinding.txtFoto.getText().toString());

            Call<RespostaJSON<Organico>> call = NetworkManager.service().inserirOrganico(organico);
            call.enqueue(new Callback<RespostaJSON<Organico>>() {

                @Override
                public void onResponse(final Call<RespostaJSON<Organico>> call, final Response<RespostaJSON<Organico>> response) {
                    RespostaJSON<Organico> resposta = response.body();
                    Log.d("TAG", "onResponse: " + resposta);
                    Log.d("TAG", "onResponse: " + resposta.getStatus());
                    if (resposta != null && resposta.getStatus() == 0) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(OrganicoInsertActivity.this, "Problema ao tentar salvar!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(final Call<RespostaJSON<Organico>> call, final Throwable t) {
                    Log.e("TAG", "onFailure: ", t);
                }
            });
        }
        else {
            Toast.makeText(OrganicoInsertActivity.this, "Preencha as duas informações!", Toast.LENGTH_SHORT).show();
        }
    }
}