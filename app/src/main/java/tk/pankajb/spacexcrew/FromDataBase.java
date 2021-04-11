package tk.pankajb.spacexcrew;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import tk.pankajb.spacexcrew.Models.CrewMember;

public class FromDataBase extends AsyncTask<Context, Void, CrewMember[]> {

    private WeakReference<MainActivity> weakReference;

    @Override
    protected CrewMember[] doInBackground(Context... contexts) {

        weakReference = new WeakReference<>((MainActivity) contexts[0]);
        CrewMember[] arr = getCrewMembersArrayFromDatabase();
        return arr;
    }

    @Override
    protected void onPostExecute(CrewMember[] crewMembers) {
        super.onPostExecute(crewMembers);

        weakReference.get().updateData(crewMembers);
    }

    private CrewMember[] getCrewMembersArrayFromDatabase() {
        List<CrewMember> crewMemberList = AppDatabase.getDatabase(weakReference.get().getApplicationContext())
                .crewDao().getAll();
        CrewMember[] arr = new CrewMember[crewMemberList.size()];
        crewMemberList.toArray(arr);
        return arr;
    }

}
