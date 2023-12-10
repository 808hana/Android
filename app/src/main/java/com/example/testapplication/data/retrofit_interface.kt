import com.example.testapplication.data.KittenImage
import retrofit2.http.GET

interface KittenApiService {
    @GET("randommlem")
    suspend fun getRandomKitten(): KittenImage
}
