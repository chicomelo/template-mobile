package br.utp.sustentabilidade.widgets.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ItemAgrotoxicoBinding;
import br.utp.sustentabilidade.models.Agrotoxico;

public class AgrotoxicoAdapter extends RecyclerView.Adapter<AgrotoxicoAdapter.AgrotoxicoViewHolder> {

    private final List<Agrotoxico> mAgrotoxico;
    private final AgrotoxicoListener mListener;

    public AgrotoxicoAdapter(List<Agrotoxico> agrotoxicos, AgrotoxicoListener listener) {
        mAgrotoxico = agrotoxicos;
        mListener = listener;
    }

    @NonNull
    @Override
    public AgrotoxicoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAgrotoxicoBinding binding = ItemAgrotoxicoBinding.inflate(layoutInflater, parent, false);
        return new AgrotoxicoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final AgrotoxicoViewHolder holder, final int position) {
        holder.bind(mAgrotoxico.get(position));
    }

    @Override
    public int getItemCount() {
        return mAgrotoxico.size();
    }

    public interface AgrotoxicoListener {
        void onAgrotoxicoClick(Agrotoxico agrotoxico);
    }

    /**
     * Armazena os dados da view.
     */
    class AgrotoxicoViewHolder extends RecyclerView.ViewHolder {

        private final ItemAgrotoxicoBinding mBinding;

        public AgrotoxicoViewHolder(final ItemAgrotoxicoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Agrotoxico agrotoxico) {
            mBinding.setAgrotoxico(agrotoxico);

            // TODO: Exibir foto
            Glide.with(mBinding.agrotoxicoImgFoto.getContext())
                    .load(agrotoxico.getFoto())
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(mBinding.agrotoxicoImgFoto);

            mBinding.agrotoxicoCard.setOnClickListener(v -> mListener.onAgrotoxicoClick(agrotoxico));

            // TODO: Amarrar eventos
        }
    }
}
