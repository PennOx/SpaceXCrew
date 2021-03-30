package tk.pankajb.spacexcrew;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.internal.$Gson$Preconditions;

import java.lang.ref.WeakReference;
import java.net.ContentHandler;
import java.util.List;

import tk.pankajb.spacexcrew.Models.CrewMember;

public class FromDataBase extends AsyncTask<Void, Void, CrewMember[]> {

    private WeakReference<MainActivity> weakReference;

    FromDataBase(MainActivity context){
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected CrewMember[] doInBackground(Void... voids) {

        List<CrewMember> crewMemberList = AppDatabase.getDatabase(weakReference.get().getApplicationContext()).crewDao().getAll();
        CrewMember[] arr = new CrewMember[crewMemberList.size()];

        for (int i = 0 ; i<crewMemberList.size(); i++){
            arr[i] = crewMemberList.get(i);
        }

        return arr;
    }

    @Override
    protected void onPostExecute(CrewMember[] crewMembers) {
        super.onPostExecute(crewMembers);
        weakReference.get().updateData(crewMembers);
    }
}
