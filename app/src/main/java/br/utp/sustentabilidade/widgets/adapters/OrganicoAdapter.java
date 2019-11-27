package br.utp.sustentabilidade.widgets.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import br.utp.sustentabilidade.R;
import br.utp.sustentabilidade.databinding.ItemOrganicoBinding;
import br.utp.sustentabilidade.models.Organico;

public class OrganicoAdapter extends RecyclerView.Adapter<OrganicoAdapter.OrganicoViewHolder> {

    private final List<Organico> mOrganicos;
    private final OrganicoListener mListener;

    public OrganicoAdapter(List<Organico> organicos, OrganicoListener listener) {
        mOrganicos = organicos;
        mListener = listener;
    }

    @NonNull
    @Override
    public OrganicoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemOrganicoBinding binding = ItemOrganicoBinding.inflate(layoutInflater, parent, false);
        return new OrganicoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrganicoViewHolder holder, final int position) {
        holder.bind(mOrganicos.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrganicos.size();
    }

    public interface OrganicoListener {
        void onOrganicoClick(Organico agrotoxico);
    }

    /**
     * Armazena os dados da view.
     */
    class OrganicoViewHolder extends RecyclerView.ViewHolder {

        private final ItemOrganicoBinding mBinding;

        public OrganicoViewHolder(final ItemOrganicoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Organico organico) {
            mBinding.setOrganico(organico);

            // TODO: Exibir foto

            Glide.with(mBinding.organicoImgFoto.getContext())
                    .load(organico.getFoto())
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(mBinding.organicoImgFoto);

            mBinding.organicoCard.setOnClickListener(v -> mListener.onOrganicoClick(organico));

            // TODO: Amarrar eventos
        }
    }
}
