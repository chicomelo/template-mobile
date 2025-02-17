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
import br.utp.sustentabilidade.databinding.ActivityReciclagemDetalhesBinding;
import br.utp.sustentabilidade.models.Reciclagem;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReciclagemDetalhesActivity extends AppCompatActivity {

    private ActivityReciclagemDetalhesBinding mBinding;
    private Reciclagem reciclagem = new Reciclagem();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reciclagem_detalhes);

        Bundle bundle = getIntent().getBundleExtra("reciclagem");
        Log.d("Reciclagem", "onCreate: " + bundle);

        if (bundle != null) {
            reciclagem.setId(bundle.getString("id"));
            reciclagem.setTitulo(bundle.getString("titulo"));
            reciclagem.setDescricao(bundle.getString("descricao"));
            reciclagem.setFoto(bundle.getString("img"));
            Log.d("Reciclagem", "onCreate: " + reciclagem.getId());

            mBinding.setReciclagem(reciclagem);

            Glide.with(this)
                    .load(reciclagem.getFoto())
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(mBinding.reciclagemImgFoto);
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

                mBinding.reciclagemTxtTitulo.setClickable(true);
                mBinding.reciclagemTxtTitulo.setCursorVisible(true);
                mBinding.reciclagemTxtTitulo.setFocusable(true);
                mBinding.reciclagemTxtTitulo.setFocusableInTouchMode(true);

                mBinding.reciclagemTxtDescricao.setClickable(true);
                mBinding.reciclagemTxtDescricao.setCursorVisible(true);
                mBinding.reciclagemTxtDescricao.setFocusable(true);
                mBinding.reciclagemTxtDescricao.setFocusableInTouchMode(true);

                mBinding.reciclagemTxtFotoUrl.setVisibility(View.VISIBLE);
                mBinding.reciclagemTxtFotoUrl.setClickable(true);
                mBinding.reciclagemTxtFotoUrl.setCursorVisible(true);
                mBinding.reciclagemTxtFotoUrl.setFocusable(true);
                mBinding.reciclagemTxtFotoUrl.setFocusableInTouchMode(true);

                mBinding.reciclagemImgFoto.setVisibility(View.GONE);

                return true;
            case R.id.action_exluir:
                Call<RespostaJSON<Reciclagem>> call = NetworkManager.service().removerReciclagem(reciclagem.getId());
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
                            Toast.makeText(ReciclagemDetalhesActivity.this, "Problema ao tentar excluir!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(final Call<RespostaJSON<Reciclagem>> call, final Throwable t) {
                        Log.e("TAG", "onFailure: ", t);

                    }
                });
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void saveAction(View v) {
        if (mBinding.reciclagemTxtTitulo.getText().toString().length() > 0 && mBinding.reciclagemTxtDescricao.getText().toString().length() > 0) {
            reciclagem.setTitulo(mBinding.reciclagemTxtTitulo.getText().toString());
            reciclagem.setDescricao(mBinding.reciclagemTxtDescricao.getText().toString());
            reciclagem.setFoto(mBinding.reciclagemTxtFotoUrl.getText().toString());

            Call<RespostaJSON<Reciclagem>> call = NetworkManager.service().atualizarReciclagem(reciclagem);
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
                        Toast.makeText(ReciclagemDetalhesActivity.this, "Problema ao tentar salvar!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(final Call<RespostaJSON<Reciclagem>> call, final Throwable t) {
                    Log.e("TAG", "onFailure: ", t);
                }
            });
        }
        else {
            Toast.makeText(ReciclagemDetalhesActivity.this, "Preencha as duas informações!", Toast.LENGTH_SHORT).show();
        }
    }
}