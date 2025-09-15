package com.example.mnymng.fragments.utilfragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log; // Ensure Log import is present
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnymng.R;
import com.example.mnymng.adapter.GridAdapter;
import com.example.mnymng.model.MenuItem;
import com.example.mnymng.viewmodel.EmojiViewModel;

import java.util.ArrayList;
import java.util.List;


public class PageOneFragment extends Fragment {
    private RecyclerView recyclerView;
    private GridAdapter<MenuItem, PageOneFragment.MenuItemViewHolder> adapter;
    private List<MenuItem> menuItems1;
    private EmojiViewModel emojiViewModel;

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView;
        TextView nameTextView;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            emojiTextView = itemView.findViewById(R.id.item_emoji);
            nameTextView = itemView.findViewById(R.id.item_name);
        }

        public void bind(MenuItem item) {
            if (item != null) {
                if (emojiTextView != null) {
                    emojiTextView.setText(item.getEmoji());
                }
                if (nameTextView != null) {
                    nameTextView.setText(item.getName());
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel, scoped to the parent Fragment (PopupViewFragment)
        // or to the Activity if the fragments are directly in an Activity.
        // Assuming PopupViewFragment is the parent that holds MyViewPagerAdapter
        emojiViewModel = new ViewModelProvider(requireParentFragment()).get(EmojiViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_cataicon, container, false);
        recyclerView = view.findViewById(R.id.rv_icon_list);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));

        menuItems1 = new ArrayList<>();
        menuItems1.add(new MenuItem("🏠", "Expense"));
        menuItems1.add(new MenuItem("🖼️", "Gallery"));
        menuItems1.add(new MenuItem("🍽️", "Food"));
        menuItems1.add(new MenuItem("🛍️", "Shopping"));
        menuItems1.add(new MenuItem("🚗", "Transport"));
        menuItems1.add(new MenuItem("💊", "Health"));
        menuItems1.add(new MenuItem("🎉", "Fun"));
        menuItems1.add(new MenuItem("💡", "Bills"));
        menuItems1.add(new MenuItem("📚", "Education"));
        menuItems1.add(new MenuItem("🎁", "Gifts"));
        menuItems1.add(new MenuItem("✈️", "Travel"));
        menuItems1.add(new MenuItem("💻", "Work"));
        menuItems1.add(new MenuItem("📱", "Gadgets"));
        menuItems1.add(new MenuItem("👕", "Clothing"));
        menuItems1.add(new MenuItem("🐶", "Pets"));
        menuItems1.add(new MenuItem("⚽", "Sports"));
        menuItems1.add(new MenuItem("🎬", "Entertainment"));
        menuItems1.add(new MenuItem("🔧", "Maintenance"));
        menuItems1.add(new MenuItem("💰", "Savings"));
        menuItems1.add(new MenuItem("🧾", "Taxes"));
        menuItems1.add(new MenuItem("👶", "Baby"));
        menuItems1.add(new MenuItem("🥂", "Drinks"));
        menuItems1.add(new MenuItem("🛒", "Groceries"));
        menuItems1.add(new MenuItem("🏥", "Hospital"));
        menuItems1.add(new MenuItem("🧺", "Laundry"));
        menuItems1.add(new MenuItem("💅", "Salon"));
        menuItems1.add(new MenuItem("🥪", "Snacks"));
        menuItems1.add(new MenuItem("💸", "Salary"));
        menuItems1.add(new MenuItem("🏦", "Bank"));
        menuItems1.add(new MenuItem("📈", "Investment"));
        menuItems1.add(new MenuItem("🚌", "Bus"));
        menuItems1.add(new MenuItem("⛽", "Fuel"));
        menuItems1.add(new MenuItem("🅿️", "Parking"));
        menuItems1.add(new MenuItem("🛠️", "Repairs"));
        menuItems1.add(new MenuItem("📞", "Phone"));
        menuItems1.add(new MenuItem("🌐", "Internet"));
        menuItems1.add(new MenuItem("📺", "Streaming"));
        menuItems1.add(new MenuItem("🎮", "Games"));
        menuItems1.add(new MenuItem("🏋️", "Gym"));
        menuItems1.add(new MenuItem("🎟️", "Tickets"));
        menuItems1.add(new MenuItem("🤝", "Charity"));
        menuItems1.add(new MenuItem("🤷", "Miscellaneous"));
        menuItems1.add(new MenuItem("🛡️", "Insurance"));
        menuItems1.add(new MenuItem("📰", "Subscriptions"));
        menuItems1.add(new MenuItem("🛋️", "Furniture"));
        menuItems1.add(new MenuItem("🎵", "Music"));
        menuItems1.add(new MenuItem("💾", "Software"));
        menuItems1.add(new MenuItem("👮", "Fines"));
        menuItems1.add(new MenuItem("📄", "Loans"));
        menuItems1.add(new MenuItem("💳", "Gift Cards"));
        menuItems1.add(new MenuItem("🚲", "Bicycle"));
        menuItems1.add(new MenuItem("🏍️", "Motorcycle"));
        menuItems1.add(new MenuItem("📎", "Office Supplies"));
        menuItems1.add(new MenuItem("🌱", "Gardening"));
        menuItems1.add(new MenuItem("☕", "Coffee"));
        menuItems1.add(new MenuItem("💎", "Jewelry"));
        menuItems1.add(new MenuItem("🚘", "Rental Car"));
        menuItems1.add(new MenuItem("🎨", "Hobby"));
        menuItems1.add(new MenuItem("🐾", "Vet"));
        menuItems1.add(new MenuItem("👨‍👩‍👧‍👦", "Childcare"));
        menuItems1.add(new MenuItem("🛣️", "Tolls"));
        menuItems1.add(new MenuItem("👟", "Shoes"));
        menuItems1.add(new MenuItem("🆔", "Memberships"));
        menuItems1.add(new MenuItem("💒", "Wedding"));
        menuItems1.add(new MenuItem("🎭", "Theater"));
        menuItems1.add(new MenuItem("🏛️", "Museum"));
        menuItems1.add(new MenuItem("🗞️", "Newspapers"));
        menuItems1.add(new MenuItem("📦", "Shipping"));
        menuItems1.add(new MenuItem("🧖‍♀️", "Self-care"));
        menuItems1.add(new MenuItem("🦷", "Dentist"));
        menuItems1.add(new MenuItem("👓", "Optician"));
        menuItems1.add(new MenuItem("🧠", "Therapy"));
        menuItems1.add(new MenuItem("🥡", "Takeout"));
        menuItems1.add(new MenuItem("🧹", "Cleaning"));
        menuItems1.add(new MenuItem("🧴", "Personal Care"));
        menuItems1.add(new MenuItem("💁", "Tips"));

        GridAdapter.ViewHolderFactory<PageOneFragment.MenuItemViewHolder> factory = itemView -> new PageOneFragment.MenuItemViewHolder(itemView);
        GridAdapter.ViewHolderBinder<MenuItem, PageOneFragment.MenuItemViewHolder> binder = (holder, item) -> holder.bind(item);

        adapter = new GridAdapter<>(
                getContext(),
                menuItems1,
                R.layout.item_grid, 
                factory,
                binder
        );

        adapter.setOnItemClickListener((menuItem, position) -> {
            Log.d("IconListFragment", "Clicked: " + menuItem.getName() + " at position " + position);
            emojiViewModel.selectEmoji(menuItem.getEmoji()); // Use ViewModel
        });

        recyclerView.setAdapter(adapter);
        recyclerView.requestFocus();

        recyclerView.post(() -> {
            Log.d("PageOneFragment", "RecyclerView height: " + recyclerView.getMeasuredHeight());
            if (recyclerView.getChildCount() > 0) {
                View firstItem = recyclerView.getChildAt(0);
                Log.d("PageOneFragment", "First item height: " + firstItem.getMeasuredHeight());
            }
            if (adapter != null) {
                 Log.d("PageOneFragment", "Adapter item count: " + adapter.getItemCount());
            }
        });

        return view;
    }
}
