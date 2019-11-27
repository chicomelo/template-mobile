package br.utp.sustentabilidade.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.activities.ReciclagemDetalhesActivity;
import br.utp.sustentabilidade.activities.ReciclagemInsertActivity;
import br.utp.sustentabilidade.databinding.FragmentReciclagemBinding;
import br.utp.sustentabilidade.models.Reciclagem;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.widgets.adapters.ReciclagemAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ReciclagemFragment extends Fragment implements ReciclagemAdapter.ReciclagemListener {

    private FragmentReciclagemBinding mBinding;
    private List<Reciclagem> mReciclagem;

    /**
     * Construtor de fragmentos.
     *
     * @return Retorna uma instância do fragmento de produtos orgânicos.
     */
    public static ReciclagemFragment newInstance() {
        return new ReciclagemFragment();
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reciclagem, container, false);

        // Inicializa a lista de produtos orgânicos
        mReciclagem = new ArrayList<>();

        // Inicializa o recycler view
        ReciclagemAdapter adapter = new ReciclagemAdapter(mReciclagem, this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mBinding.reciclagemRecyclerView.setAdapter(adapter);
        mBinding.reciclagemRecyclerView.setLayoutManager(layout);

        // Exibe a progressbar
        mBinding.organicoLoading.setVisibility(View.VISIBLE);

        // Chama o Webservice
        carregarWebService(0);

        FloatingActionButton fab = mBinding.fab;
        fab.setOnClickListener(view ->  callInsert());

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NetworkManager.cancelRequests();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void carregarWebService(final int pagina) {
        Call<RespostaJSON<List<Reciclagem>>> call = NetworkManager.service().listarReciclagems(0);
        call.enqueue(new Callback<RespostaJSON<List<Reciclagem>>>() {

            @Override
            public void onResponse(final Call<RespostaJSON<List<Reciclagem>>> call, final Response<RespostaJSON<List<Reciclagem>>> response) {
                RespostaJSON<List<Reciclagem>> resposta = response.body();
                Log.d("TAG", "onResponse: " + resposta);
                Log.d("TAG", "onResponse: " + resposta.getStatus());
                if (resposta != null && resposta.getStatus() == 0) {
                    atualizarListaReciclagem(resposta.getObject());
                } else {
                    exibirMensagemErro();
                }
            }

            @Override
            public void onFailure(final Call<RespostaJSON<List<Reciclagem>>> call, final Throwable t) {
                Log.e("TAG", "onFailure: ", t);
                exibirMensagemErro();
            }
        });
    }

    private void atualizarListaReciclagem(final List<Reciclagem> reciclagem) {
        // Atualiza os elementos da lista
        mReciclagem.addAll(reciclagem);
        mBinding.reciclagemRecyclerView.getAdapter().notifyDataSetChanged();

        // Esconde a progressbar
        mBinding.organicoLoading.setVisibility(View.GONE);
    }

    private void exibirMensagemErro() {
        // Esconde a progressbar
        mBinding.organicoLoading.setVisibility(View.GONE);

        // Exibe um snackbar com a mensagem de erro
        Snackbar.make(mBinding.getRoot(), R.string.organico_erro_webservice, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onReciclagemClick(final Reciclagem reciclagem) {
        Bundle bundle = new Bundle();
        bundle.putString("id", reciclagem.getId());
        bundle.putString("titulo", reciclagem.getTitulo());
        bundle.putString("descricao", reciclagem.getDescricao());
        bundle.putString("img", reciclagem.getFoto());

        Log.d("Fragmento", "onReciclagemClick: " + reciclagem.getId());

        Intent intent = new Intent(getContext(), ReciclagemDetalhesActivity.class);
        intent.putExtra("reciclagem", bundle);
        startActivityForResult(intent, 111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                mReciclagem.removeAll(mReciclagem);
                mBinding.reciclagemRecyclerView.getAdapter().notifyDataSetChanged();

                // Exibe a progressbar
                mBinding.organicoLoading.setVisibility(View.VISIBLE);

                // Chama o Webservice
                carregarWebService(0);
            }
        }
    }

    private void callInsert() {
        Intent intent = new Intent(getContext(), ReciclagemInsertActivity.class);
        startActivityForResult(intent, 111);
    }
}
