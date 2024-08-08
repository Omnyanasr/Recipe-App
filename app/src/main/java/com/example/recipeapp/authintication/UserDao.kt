import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Insert
import com.example.recipeapp.authintication.User
import retrofit2.http.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUser(email: String, password: String): User?
}