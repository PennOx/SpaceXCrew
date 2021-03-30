package tk.pankajb.spacexcrew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import tk.pankajb.spacexcrew.Models.CrewMember;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private CrewMember[] crewMembers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        List<CrewMember> crewMemberList = AppDatabase.getDatabase(getApplicationContext()).crewDao().getAll();

        if(crewMemberList.isEmpty()){
            Request request = new Request(this);
            request.execute();
        }else{
            FromDataBase fromDataBase = new FromDataBase(this);
            fromDataBase.execute();
        }

    }

    public void updateData(CrewMember[] crewMembers) {
        this.crewMembers = crewMembers;
        RecyclerAdapter adapter = new RecyclerAdapter(this, crewMembers);
        recyclerView.setAdapter(adapter);
    }

    public void wentWrong() {
        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show();
    }
}