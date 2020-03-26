package cl.yerkodee.ionix_test.ui.main;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import java.util.List;
import java.util.Objects;

import cl.yerkodee.ionix_test.R;
import cl.yerkodee.ionix_test.databinding.ItemListCategoryBinding;
import cl.yerkodee.ionix_test.model.category.Category;
import cl.yerkodee.ionix_test.ui.common.DataBoundListAdapter;
import cl.yerkodee.ionix_test.ui.common.DataBoundViewHolder;

public class CategoryAdapter extends DataBoundListAdapter<Category, ItemListCategoryBinding> {

    private Context context;
    private final CategoryClickCallback callback;

    public CategoryAdapter(Context context, CategoryClickCallback callback){
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected ItemListCategoryBinding createBinding(ViewGroup parent) {
        ItemListCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_list_category, parent, false);
        return binding;
    }

    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<ItemListCategoryBinding> holder,
                                 int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        Category category = holder.binding.getCategory();
        int auxPosition = holder.getAdapterPosition();
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");

        holder.binding.itemListIcon.setTypeface(font);

        holder.binding.lytItemCategory.setOnClickListener(v -> {
            if (category != null) {
                callback.onClick(category, auxPosition);
            }
        });

    }

    @Override
    protected void bind(ItemListCategoryBinding binding, Category item) {
        binding.setCategory(item);
    }

    @Override
    protected boolean areItemsTheSame(Category oldItem, Category newItem) {
        return Objects.equals(oldItem.getTitle(), newItem.getTitle());
    }

    @Override
    protected boolean areContentsTheSame(Category oldItem, Category newItem) {
        return Objects.equals(oldItem.getIcon(), newItem.getIcon()) &&
                oldItem.getTitle().equals(newItem.getTitle());
    }

    public interface CategoryClickCallback {
        void onClick(Category category, int currentPosition);
    }

}
