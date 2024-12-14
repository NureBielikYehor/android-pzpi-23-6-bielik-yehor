package nure.bielik.yehor.pract_task3_part3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final String[] mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            linearLayout = v.findViewById(R.id.linearLayout);
        }
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mData[position]);
        holder.linearLayout.setOnClickListener(view -> {
                Toast.makeText(
                holder.linearLayout.getContext(),
                        "item " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public MyAdapter(String[] mData) {
        this.mData = mData;
    }
}
