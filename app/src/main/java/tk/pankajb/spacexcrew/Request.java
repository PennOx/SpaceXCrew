package tk.pankajb.spacexcrew;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.Scanner;

import tk.pankajb.spacexcrew.Models.CrewMember;

public class Request extends AsyncTask<Void, Void, CrewMember[]> {

    private WeakReference<MainActivity> weakReference;
    private CrewMember[] crewMembers;

    Request(MainActivity context){
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected CrewMember[] doInBackground(Void... voids) {

        if(!isActivityLive()){
            return null;
        }

        try{
            ConnectionManager connectionManager = new ConnectionManager(weakReference.get());
            HttpURLConnection connection = connectionManager.getConnection();

            if(connection.getResponseCode() >= 300){
                throw new IOException(connection.getResponseMessage());
            }

            Scanner scan = new Scanner(connection.getInputStream());
            String rawResponse = "";
            while (scan.hasNextLine()){
                rawResponse += scan.nextLine();
            }

            Gson gson = new Gson();

            return gson.fromJson(rawResponse, CrewMember[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return getCrewMembers();
    }

    @Override
    protected void onPostExecute(CrewMember[] crewMembers) {
        super.onPostExecute(crewMembers);

        if(crewMembers == null){
            weakReference.get().wentWrong();
            return;
        }
        for (CrewMember crewMember:crewMembers){
            AppDatabase.getDatabase(weakReference.get().getApplicationContext()).crewDao().insertAll(crewMember);
        }
        weakReference.get().updateData(crewMembers);
    }

    private boolean isActivityLive() {
        return weakReference.get() != null || weakReference.get().isFinishing();
    }

    public void setCrewMembers(CrewMember[] crewMembers) {
        this.crewMembers = crewMembers;
    }

    public CrewMember[] getCrewMembers() {
        return crewMembers;
    }
}
