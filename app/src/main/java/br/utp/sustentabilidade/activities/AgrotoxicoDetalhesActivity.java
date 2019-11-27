package br.utp.sustentabilidade.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ActivityAgrotoxicoDetalhesBinding;
import br.utp.sustentabilidade.models.Agrotoxico;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dudis on 2019-11-26.
 */
public class AgrotoxicoDetalhesActivity extends AppCompatActivity {

    private ActivityAgrotoxicoDetalhesBinding mBinding;
    private Agrotoxico agrotoxico = new Agrotoxico();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agrotoxico_detalhes);

        Bundle bundle = getIntent().getBundleExtra("agrotoxico");
        Log.d("Agrotoxico", "onCreate: " + bundle);

        if (bundle != null) {
            agrotoxico.setId(bundle.getString("id"));
            agrotoxico.setTitulo(bundle.getString("titulo"));
            agrotoxico.setDescricao(bundle.getString("descricao"));
            agrotoxico.setFoto(bundle.getString("img"));
            Log.d("Agrotoxico", "onCreate: " + agrotoxico.getId());

            mBinding.setAgrotoxico(agrotoxico);

            Glide.with(this)
                    .load(agrotoxico.getFoto())
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(mBinding.agrotoxicoImgFoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.excluir_itens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editar:
                mBinding.btnSave.setVisibility(View.VISIBLE);

                mBinding.agrotoxicoTxtTitulo.setClickable(true);
                mBinding.agrotoxicoTxtTitulo.setCursorVisible(true);
                mBinding.agrotoxicoTxtTitulo.setFocusable(true);
                mBinding.agrotoxicoTxtTitulo.setFocusableInTouchMode(true);

                mBinding.agrotoxicoTxtDescricao.setClickable(true);
                mBinding.agrotoxicoTxtDescricao.setCursorVisible(true);
                mBinding.agrotoxicoTxtDescricao.setFocusable(true);
                mBinding.agrotoxicoTxtDescricao.setFocusableInTouchMode(true);

                mBinding.agrotoxicoTxtFotoUrl.setVisibility(View.VISIBLE);
                mBinding.agrotoxicoTxtFotoUrl.setClickable(true);
                mBinding.agrotoxicoTxtFotoUrl.setCursorVisible(true);
                mBinding.agrotoxicoTxtFotoUrl.setFocusable(true);
                mBinding.agrotoxicoTxtFotoUrl.setFocusableInTouchMode(true);

                mBinding.agrotoxicoImgFoto.setVisibility(View.GONE);

                return true;
            case R.id.action_exluir:
                Call<RespostaJSON<Agrotoxico>> call = NetworkManager.service().removerAgrotoxico(agrotoxico.getId());
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
                            Toast.makeText(AgrotoxicoDetalhesActivity.this, "Problema ao tentar excluir!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(final Call<RespostaJSON<Agrotoxico>> call, final Throwable t) {
                        Log.e("TAG", "onFailure: ", t);

                    }
                });
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void saveAction(View v) {
        if (mBinding.agrotoxicoTxtTitulo.getText().toString().length() > 0 && mBinding.agrotoxicoTxtDescricao.getText().toString().length() > 0) {
            agrotoxico.setTitulo(mBinding.agrotoxicoTxtTitulo.getText().toString());
            agrotoxico.setDescricao(mBinding.agrotoxicoTxtDescricao.getText().toString());
            agrotoxico.setFoto(mBinding.agrotoxicoTxtFotoUrl.getText().toString());

            Call<RespostaJSON<Agrotoxico>> call = NetworkManager.service().atualizarAgrotoxico(agrotoxico);
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
                        Toast.makeText(AgrotoxicoDetalhesActivity.this, "Problema ao tentar salvar!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(final Call<RespostaJSON<Agrotoxico>> call, final Throwable t) {
                    Log.e("TAG", "onFailure: ", t);
                }
            });
        }
        else {
            Toast.makeText(AgrotoxicoDetalhesActivity.this, "Preencha as duas informações!", Toast.LENGTH_SHORT).show();
        }
    }
}