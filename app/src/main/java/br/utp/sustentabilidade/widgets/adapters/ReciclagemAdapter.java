package br.utp.sustentabilidade.widgets.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ItemReciclagemBinding;
import br.utp.sustentabilidade.models.Reciclagem;

public class ReciclagemAdapter extends RecyclerView.Adapter<ReciclagemAdapter.ReciclagemViewHolder> {

    private final List<Reciclagem> mReciclagem;
    private final ReciclagemListener mListener;

    public ReciclagemAdapter(List<Reciclagem> reciclagems, ReciclagemListener listener) {
        mReciclagem = reciclagems;
        mListener = listener;
    }

    @NonNull
    @Override
    public ReciclagemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReciclagemBinding binding = ItemReciclagemBinding.inflate(layoutInflater, parent, false);
        return new ReciclagemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReciclagemViewHolder holder, final int position) {
        holder.bind(mReciclagem.get(position));
    }

    @Override
    public int getItemCount() {
        return mReciclagem.size();
    }

    public interface ReciclagemListener {
        void onReciclagemClick(Reciclagem reciclagem);
    }

    /**
     * Armazena os dados da view.
     */
    class ReciclagemViewHolder extends RecyclerView.ViewHolder {

        private final ItemReciclagemBinding mBinding;

        public ReciclagemViewHolder(final ItemReciclagemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Reciclagem reciclagem) {
            mBinding.setReciclagem(reciclagem);

            // TODO: Exibir foto
            Glide.with(mBinding.reciclagemImgFoto.getContext())
                    .load(reciclagem.getFoto())
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(mBinding.reciclagemImgFoto);

            mBinding.reciclagemCard.setOnClickListener(v -> mListener.onReciclagemClick(reciclagem));


            // TODO: Amarrar eventos
        }
    }
}
