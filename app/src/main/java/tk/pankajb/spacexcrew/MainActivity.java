package tk.pankajb.spacexcrew;

import android.os.Bundle;
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

    public void updateData(CrewMember[] crewMembers) {
        RecyclerAdapter adapter = new RecyclerAdapter(this, crewMembers);
        recyclerView.setAdapter(adapter);
    }

    private void initializeUI() {
        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void requestFromAPI() {
        Request request = new Request(this);
        request.execute();
    }

    private void loadFromDatabase() {
        FromDataBase fromDataBase = new FromDataBase(this);
        fromDataBase.execute();
    }

    private boolean isDataPresentInDatabase() {
        List<CrewMember> crewMemberList = AppDatabase.getDatabase(getApplicationContext()).crewDao().getAll();
        return !crewMemberList.isEmpty();
    }

    public void wentWrong() {
        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show();
    }
}