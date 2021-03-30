package tk.pankajb.spacexcrew;

import android.content.Context;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {
    private HttpURLConnection connection;

    ConnectionManager(Context context) throws IOException {
        URL connectionURL = new URL(context.getString(R.string.api_link));
        this.connection = (HttpURLConnection) connectionURL.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");
    }

    public HttpURLConnection getConnection(){
        return this.connection;
    }
}
