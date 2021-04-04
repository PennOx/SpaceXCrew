package tk.pankajb.spacexcrew;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.Scanner;

import tk.pankajb.spacexcrew.Models.CrewMember;

public class Request extends AsyncTask<Void, Void, CrewMember[]> {

    private final WeakReference<MainActivity> weakReference;
    private CrewMember[] crewMembers;

    Request(MainActivity context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected CrewMember[] doInBackground(Void... voids) {

        if (!isActivityLive()) {
            return null;
        }

        try {
            ConnectionManager connectionManager = new ConnectionManager(weakReference.get());
            HttpURLConnection connection = connectionManager.getConnection();

            if (!isConnectionResponseOK(connection)) {
                throw new IOException(connection.getResponseMessage());
            }
            String rawResponse = getRawResponseFromConnection(connection);
            setCrewMembersArrayFromResponse(rawResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return getCrewMembers();
    }

    @Override
    protected void onPostExecute(CrewMember[] crewMembers) {
        super.onPostExecute(crewMembers);

        if (crewMembers == null) {
            weakReference.get().wentWrong();
            return;
        }
        AppDatabase.getDatabase(weakReference.get().getApplicationContext()).crewDao().insertAll(crewMembers);
        weakReference.get().updateData(crewMembers);
    }

    private void setCrewMembersArrayFromResponse(String rawResponse) {
        Gson gson = new Gson();
        crewMembers = gson.fromJson(rawResponse, CrewMember[].class);
    }

    private String getRawResponseFromConnection(HttpURLConnection connection) throws IOException {

        Scanner scan = new Scanner(connection.getInputStream());
        StringBuilder rawResponse = new StringBuilder();
        while (scan.hasNextLine()) {
            rawResponse.append(scan.nextLine());
        }

        return rawResponse.toString();
    }

    private boolean isConnectionResponseOK(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() < 300;
    }

    private boolean isActivityLive() {
        return weakReference.get() != null || weakReference.get().isFinishing();
    }

    public CrewMember[] getCrewMembers() {
        return crewMembers;
    }
}
