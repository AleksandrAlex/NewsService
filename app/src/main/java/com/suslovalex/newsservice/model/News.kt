import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName




@Entity(tableName = "news")
data class News (
	@PrimaryKey(autoGenerate = true)
	val id: Int? = 0,
	val status : String,
	val totalResults : Int,
	@Embedded val articles : List<Articles>
)