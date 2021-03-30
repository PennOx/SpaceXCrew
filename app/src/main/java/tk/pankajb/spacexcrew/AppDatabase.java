package tk.pankajb.spacexcrew;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tk.pankajb.spacexcrew.Models.CrewMember;

@Database(entities = {CrewMember.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CrewDao crewDao();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "spacedata").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
