package tk.pankajb.spacexcrew;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tk.pankajb.spacexcrew.Models.CrewMember;

@Dao
public interface CrewDao {

    @Query("SELECT * FROM crewmember")
    List<CrewMember> getAll();

    @Insert
    void insertAll(CrewMember[] crew);

    @Query("DELETE FROM crewmember")
    void deleteAll();
}
