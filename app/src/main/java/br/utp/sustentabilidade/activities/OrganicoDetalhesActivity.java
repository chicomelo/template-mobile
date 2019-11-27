package br.utp.sustentabilidade.activities;

import android.content.Intent;
import android.net.Uri;
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
import br.utp.sustentabilidade.databinding.ActivityOrganicoDetalhesBinding;
import br.utp.sustentabilidade.models.Organico;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dudis on 2019-11-26.
 */
public class OrganicoDetalhesActivity extends AppCompatActivity {

    private ActivityOrganicoDetalhesBinding mBinding;
    private Organico organico = new Organico();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_organico_detalhes);

        Bundle bundle = getIntent().getBundleExtra("organico");
        Log.d("Organico", "onCreate: " + bundle);

        if (bundle != null) {
            organico.setId(bundle.getString("id"));
            organico.setTitulo(bundle.getString("titulo"));
            organico.setDescricao(bundle.getString("descricao"));
            organico.setLocal(bundle.getString("local"));
            organico.setLocalUrl(bundle.getString("local_url"));
            organico.setFoto(bundle.getString("img"));
            Log.d("Organico", "onCreate: " + organico.getId());

            mBinding.setOrganico(organico);

            Glide.with(this)
                    .load(organico.getFoto())
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(mBinding.organicoImgFoto);
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

                mBinding.organicoTxtTitulo.setClickable(true);
                mBinding.organicoTxtTitulo.setCursorVisible(true);
                mBinding.organicoTxtTitulo.setFocusable(true);
                mBinding.organicoTxtTitulo.setFocusableInTouchMode(true);

                mBinding.organicoTxtDescricao.setClickable(true);
                mBinding.organicoTxtDescricao.setCursorVisible(true);
                mBinding.organicoTxtDescricao.setFocusable(true);
                mBinding.organicoTxtDescricao.setFocusableInTouchMode(true);

                mBinding.organicoTxtLocal.setClickable(true);
                mBinding.organicoTxtLocal.setCursorVisible(true);
                mBinding.organicoTxtLocal.setFocusable(true);
                mBinding.organicoTxtLocal.setFocusableInTouchMode(true);

                mBinding.organicoTxtLocalUrl.setClickable(true);
                mBinding.organicoTxtLocalUrl.setCursorVisible(true);
                mBinding.organicoTxtLocalUrl.setFocusable(true);
                mBinding.organicoTxtLocalUrl.setFocusableInTouchMode(true);

                mBinding.organicoTxtFotoUrl.setVisibility(View.VISIBLE);
                mBinding.organicoTxtFotoUrl.setClickable(true);
                mBinding.organicoTxtFotoUrl.setCursorVisible(true);
                mBinding.organicoTxtFotoUrl.setFocusable(true);
                mBinding.organicoTxtFotoUrl.setFocusableInTouchMode(true);

                mBinding.organicoImgFoto.setVisibility(View.GONE);

                return true;
            case R.id.action_exluir:
                Call<RespostaJSON<Organico>> call = NetworkManager.service().removerOrganico(organico.getId());
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
                            Toast.makeText(OrganicoDetalhesActivity.this, "Problema ao tentar excluir!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(final Call<RespostaJSON<Organico>> call, final Throwable t) {
                        Log.e("TAG", "onFailure: ", t);

                    }
                });
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void openURL(View v) {
        if (mBinding.organicoTxtLocalUrl.getText().toString().length() > 0) {
            String url;

            if (!mBinding.organicoTxtLocalUrl.getText().toString().startsWith("http://") && !mBinding.organicoTxtLocalUrl.getText().toString().startsWith("https://")) {
                url = "http://" + mBinding.organicoTxtLocalUrl.getText().toString();
            }
            else {
                url = mBinding.organicoTxtLocalUrl.getText().toString();
            }

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }

    public void saveAction(View v) {
        if (mBinding.organicoTxtTitulo.getText().toString().length() > 0 && mBinding.organicoTxtDescricao.getText().toString().length() > 0 && mBinding.organicoTxtLocal.getText().toString().length() > 0 && mBinding.organicoTxtLocalUrl.getText().toString().length() > 0) {
            organico.setTitulo(mBinding.organicoTxtTitulo.getText().toString());
            organico.setDescricao(mBinding.organicoTxtDescricao.getText().toString());
            organico.setLocal(mBinding.organicoTxtLocal.getText().toString());
            organico.setLocalUrl(mBinding.organicoTxtLocalUrl.getText().toString());
            organico.setFoto(mBinding.organicoTxtFotoUrl.getText().toString());
            organico.setDia("");
            organico.setHorario("");

            Call<RespostaJSON<Organico>> call = NetworkManager.service().atualizarOrganico(organico);
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
                        Toast.makeText(OrganicoDetalhesActivity.this, "Problema ao tentar salvar!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(final Call<RespostaJSON<Organico>> call, final Throwable t) {
                    Log.e("TAG", "onFailure: ", t);
                }
            });
        }
        else {
            Toast.makeText(OrganicoDetalhesActivity.this, "Preencha as duas informações!", Toast.LENGTH_SHORT).show();
        }
    }
}