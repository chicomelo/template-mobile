package br.utp.sustentabilidade.widgets.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.utp.sustentabilidade.databinding.ItemAgrotoxicoBinding;
import br.utp.sustentabilidade.databinding.ItemReciclagemBinding;
import br.utp.sustentabilidade.models.Agrotoxico;
import br.utp.sustentabilidade.models.Reciclagem;

public class AgrotoxicoAdapter extends RecyclerView.Adapter<AgrotoxicoAdapter.AgrotoxicoViewHolder> {

    private final List<Agrotoxico> mAgrotoxico;

    public AgrotoxicoAdapter(List<Agrotoxico> agrotoxicos) {
        mAgrotoxico = agrotoxicos;
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

    /**
     * Armazena os dados da view.
     */
    class  AgrotoxicoViewHolder extends RecyclerView.ViewHolder {

        private final ItemAgrotoxicoBinding mBinding;

        public AgrotoxicoViewHolder(final ItemAgrotoxicoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Agrotoxico agrotoxico) {
            mBinding.setAgrotoxico(agrotoxico);

            // TODO: Exibir foto

            // TODO: Amarrar eventos
        }
    }
}
