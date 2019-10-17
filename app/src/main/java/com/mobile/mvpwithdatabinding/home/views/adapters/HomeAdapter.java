package com.mobile.mvpwithdatabinding.home.views.adapters;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.mvpwithdatabinding.BR;
import com.mobile.mvpwithdatabinding.R;
import com.mobile.mvpwithdatabinding.databinding.HomeRowLayoutBinding;
import com.mobile.mvpwithdatabinding.home.views.interfaces.HomeContract;
import com.mobile.mvpwithdatabinding.model.Contact;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Contact> dataModelList;
    private HomeContract.ViewModel viewModel;

    public HomeAdapter(HomeContract.ViewModel listener, List<Contact> dataModelList) {
        this.dataModelList = dataModelList;
        this.viewModel = listener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeRowLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.home_row_layout, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = dataModelList.get(position);
        holder.homeRowLayoutBinding.setModel(contact);
        holder.homeRowLayoutBinding.setHomeActions(viewModel);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        if (dataModelList != null) {
            return dataModelList.size();
        } else {
            return 0;
        }
    }
    public void addContact(Contact contact) {
        dataModelList.add(contact);
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        HomeRowLayoutBinding homeRowLayoutBinding;

        ViewHolder(HomeRowLayoutBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.homeRowLayoutBinding = itemRowBinding;
        }

        void bind(Object obj) {
            homeRowLayoutBinding.setVariable(BR.model, obj);
            homeRowLayoutBinding.executePendingBindings();
        }
    }
}
