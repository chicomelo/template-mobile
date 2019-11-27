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
import br.utp.sustentabilidade.activities.AgrotoxicoDetalhesActivity;
import br.utp.sustentabilidade.activities.AgrotoxicoInsertActivity;
import br.utp.sustentabilidade.databinding.FragmentAgrotoxicoBinding;
import br.utp.sustentabilidade.models.Agrotoxico;
import br.utp.sustentabilidade.models.RespostaJSON;
import br.utp.sustentabilidade.network.NetworkManager;
import br.utp.sustentabilidade.widgets.adapters.AgrotoxicoAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AgrotoxicoFragment extends Fragment implements AgrotoxicoAdapter.AgrotoxicoListener {

    private FragmentAgrotoxicoBinding mBinding;
    private List<Agrotoxico> mAgrotoxico;

    /**
     * Construtor de fragmentos.
     *
     * @return Retorna uma instância do fragmento de produtos orgânicos.
     */
    public static AgrotoxicoFragment newInstance() {
        return new AgrotoxicoFragment();
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_agrotoxico, container, false);

        // Inicializa a lista de produtos orgânicos
        mAgrotoxico = new ArrayList<>();

        // Inicializa o recycler view
        AgrotoxicoAdapter adapter = new AgrotoxicoAdapter(mAgrotoxico, this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mBinding.agrotoxicoRecyclerView.setAdapter(adapter);
        mBinding.agrotoxicoRecyclerView.setLayoutManager(layout);

        // Exibe a progressbar
        mBinding.agrotoxicoLoading.setVisibility(View.VISIBLE);

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
        Call<RespostaJSON<List<Agrotoxico>>> call = NetworkManager.service().listarAgrotoxicos(0);
        call.enqueue(new Callback<RespostaJSON<List<Agrotoxico>>>() {

            @Override
            public void onResponse(final Call<RespostaJSON<List<Agrotoxico>>> call, final Response<RespostaJSON<List<Agrotoxico>>> response) {
                RespostaJSON<List<Agrotoxico>> resposta = response.body();
                Log.d("TAG", "onResponse: " + resposta);
                Log.d("TAG", "onResponse: " + resposta.getStatus());
                if (resposta != null && resposta.getStatus() == 0) {
                    atualizarListaAgrotoxico(resposta.getObject());
                } else {
                    exibirMensagemErro();
                }
            }

            @Override
            public void onFailure(final Call<RespostaJSON<List<Agrotoxico>>> call, final Throwable t) {
                Log.e("TAG", "onFailure: ",t );
                exibirMensagemErro();
            }
        });
    }

    private void atualizarListaAgrotoxico(final List<Agrotoxico> agrotoxico) {
        // Atualiza os elementos da lista
        mAgrotoxico.addAll(agrotoxico);
        mBinding.agrotoxicoRecyclerView.getAdapter().notifyDataSetChanged();

        // Esconde a progressbar
        mBinding.agrotoxicoLoading.setVisibility(View.GONE);
    }

    private void exibirMensagemErro() {
        // Esconde a progressbar
        mBinding.agrotoxicoLoading.setVisibility(View.GONE);

        // Exibe um snackbar com a mensagem de erro
        Snackbar.make(mBinding.getRoot(), R.string.organico_erro_webservice, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onAgrotoxicoClick(final Agrotoxico agrotoxico) {
        Bundle bundle = new Bundle();
        bundle.putString("id", agrotoxico.getId());
        bundle.putString("titulo", agrotoxico.getTitulo());
        bundle.putString("descricao", agrotoxico.getDescricao());
        bundle.putString("img", agrotoxico.getFoto());

        Log.d("Fragmento", "onAgrotoxicoClick: " + agrotoxico.getId());

        Intent intent = new Intent(getContext(), AgrotoxicoDetalhesActivity.class);
        intent.putExtra("agrotoxico", bundle);
        startActivityForResult(intent, 111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                mAgrotoxico.removeAll(mAgrotoxico);
                mBinding.agrotoxicoRecyclerView.getAdapter().notifyDataSetChanged();

                // Exibe a progressbar
                mBinding.agrotoxicoLoading.setVisibility(View.VISIBLE);

                // Chama o Webservice
                carregarWebService(0);
            }
        }
    }

    private void callInsert() {
        Intent intent = new Intent(getContext(), AgrotoxicoInsertActivity.class);
        startActivityForResult(intent, 111);
    }
}
