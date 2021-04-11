package tk.pankajb.spacexcrew;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tk.pankajb.spacexcrew.Models.CrewMember;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isDataPresentInDatabase()) {
            loadFromDatabase();
        } else {
            requestFromAPI();
        }

    }

    public void clearDatabase(View view) {
        AppDatabase.getDatabase(this).crewDao().deleteAll();
    }

    public void refreshData(View view) {
        clearDatabase(null);
        requestFromAPI();
    }

    public void updateData(CrewMember[] crewMembers) {
        RecyclerAdapter adapter = new RecyclerAdapter(this, crewMembers);
        recyclerView.setAdapter(adapter);
    }

    private void initializeUI() {
        setupRecycler();
    }

    private void requestFromAPI() {
        Request request = new Request(this);
        request.execute();
    }

    private void loadFromDatabase() {
        FromDataBase fromDataBase = new FromDataBase();
        fromDataBase.execute(this);
    }

    private boolean isDataPresentInDatabase() {
        List<CrewMember> crewMemberList = AppDatabase.getDatabase(getApplicationContext()).crewDao().getAll();
        return !crewMemberList.isEmpty();
    }

    private void setupRecycler() {
        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void wentWrong(String err) {
        Toast.makeText(this, err, Toast.LENGTH_LONG).show();
    }
}