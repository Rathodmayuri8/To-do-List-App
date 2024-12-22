import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskListener {

    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabAddTask = findViewById(R.id.fab_add_task);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Task");

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            EditText editTextTaskName = customLayout.findViewById(R.id.edit_text_task_name);
            String taskName = editTextTaskName.getText().toString().trim();

            if (!taskName.isEmpty()) {
                Task newTask = new Task(taskName);
                taskList.add(newTask);
                taskAdapter.notifyItemInserted(taskList.size() - 1);
            } else {
                Toast.makeText(MainActivity.this, "Task name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onTaskCheck(int position, boolean isChecked) {
        taskList.get(position).setCompleted(isChecked);
    }

    @Override
    public void onTaskDelete(int position) {
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}
