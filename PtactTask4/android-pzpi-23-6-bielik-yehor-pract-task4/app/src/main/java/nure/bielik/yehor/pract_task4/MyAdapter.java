package nure.bielik.yehor.pract_task4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<User> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, age;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            age = v.findViewById(R.id.age);
        }
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mData.get(position);
        holder.name.setText("Name: " + user.getName());
        holder.age.setText("Age: " + Integer.toString(user.getAge()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public MyAdapter(List<User> mData) {
        this.mData = mData;
    }

    public void updateData(List<User> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
}

