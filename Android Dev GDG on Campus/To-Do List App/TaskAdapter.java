import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskListener onTaskListener;

    public TaskAdapter(List<Task> taskList, OnTaskListener onTaskListener) {
        this.taskList = taskList;
        this.onTaskListener = onTaskListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view, onTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getName());
        holder.checkbox.setChecked(task.isCompleted());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox checkbox;
        TextView taskName;
        ImageView deleteTask;
        OnTaskListener onTaskListener;

        public TaskViewHolder(@NonNull View itemView, OnTaskListener onTaskListener) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            taskName = itemView.findViewById(R.id.task_name);
            deleteTask = itemView.findViewById(R.id.delete_task);
            this.onTaskListener = onTaskListener;

            checkbox.setOnClickListener(this);
            deleteTask.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.checkbox) {
                onTaskListener.onTaskCheck(getAdapterPosition(), checkbox.isChecked());
            } else if (v.getId() == R.id.delete_task) {
                onTaskListener.onTaskDelete(getAdapterPosition());
            }
        }
    }

    public interface OnTaskListener {
        void onTaskCheck(int position, boolean isChecked);
        void onTaskDelete(int position);
    }
}
