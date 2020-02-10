package com.cleanup.todoc.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.ViewModelFactory;
import com.cleanup.todoc.model.ProjectModelUi;
import com.cleanup.todoc.model.TaskCellModelUi;
import com.cleanup.todoc.model.TasksModelUi;
import com.cleanup.todoc.ui.adapter.TasksAdapter;
import com.cleanup.todoc.ui.viewmodel.SortMethod;
import com.cleanup.todoc.ui.viewmodel.TaskViewModel;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    TaskViewModel mTaskViewModel;

    private final ProjectModelUi[] mAllProjects = ProjectModelUi.getAllProjects();

    private final TasksAdapter mTaskAdapter = new TasksAdapter(this);

    @Nullable
    public AlertDialog mAlertDialog = null;

    @Nullable
    private EditText mDialogEditText = null;

    @Nullable
    private Spinner mDialogSpinner = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Suppress warning is safe because variable is initialized in onCreate
        RecyclerView recyclerView = findViewById(R.id.list_tasks);
        final TextView textViewNoTask = findViewById(R.id.lbl_no_task);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mTaskAdapter);

        findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

        mTaskViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(TaskViewModel.class);

        mTaskViewModel.getTaskModelUiMediatorLiveData().observe(this, new Observer<TasksModelUi>() {
            @Override
            public void onChanged(TasksModelUi tasksModelUi) {
                mTaskAdapter.updateTasks(tasksModelUi.getTaskCellModels());

                if (tasksModelUi.isEmptyStateDisplayed()){
                    textViewNoTask.setVisibility(View.VISIBLE);
                } else {
                    textViewNoTask.setVisibility(View.GONE);
                }

                if (mDialogEditText != null) {
                    if (tasksModelUi.getEmptyTaskNameErrorStringRes() != 0) {
                        mDialogEditText.setError(getString(tasksModelUi.getEmptyTaskNameErrorStringRes()));
                    } else {
                        mDialogEditText.setError(null);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            mTaskViewModel.setSortMethod(SortMethod.ALPHABETICAL);
        } else if (id == R.id.filter_alphabetical_inverted) {
            mTaskViewModel.setSortMethod(SortMethod.ALPHABETICAL_INVERTED);
        } else if (id == R.id.filter_oldest_first) {
            mTaskViewModel.setSortMethod(SortMethod.OLD_FIRST);
        } else if (id == R.id.filter_recent_first) {
            mTaskViewModel.setSortMethod(SortMethod.RECENT_FIRST);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(TaskCellModelUi task) {
        mTaskViewModel.deleteTask(task);
    }

    private void onPositiveButtonClick(AlertDialog alertDialog) {
        ProjectModelUi taskProject = null;

        if (mDialogSpinner != null && mDialogSpinner.getSelectedItem() instanceof ProjectModelUi) {
            taskProject = (ProjectModelUi) mDialogSpinner.getSelectedItem();
        }

        String taskName = "";

        if (mDialogEditText != null) {
            taskName = mDialogEditText.getText().toString();
        }

        alertDialog.dismiss();

        mTaskViewModel.addNewTask(taskName,taskProject);
    }

    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        mDialogEditText = dialog.findViewById(R.id.txt_task_name);
        mDialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mDialogEditText = null;
                mDialogSpinner = null;
                mAlertDialog = null;
            }
        });

        mAlertDialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClick(mAlertDialog);
                    }
                });
            }
        });

        return mAlertDialog;
    }

    private void populateDialogSpinner() {
        final ArrayAdapter<ProjectModelUi> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mAllProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (mDialogSpinner != null) {
            mDialogSpinner.setAdapter(adapter);
        }
    }
}
