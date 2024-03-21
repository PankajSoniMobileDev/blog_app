import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.myblog.R
import androidx.core.text.HtmlCompat
import com.myblog.model.BlogDetails
import com.myblog.model.BlogDetailsResponse
import com.myblog.network.ApiService
import com.myblog.network.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogDetailsFragment : Fragment() {

    private var slug: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            slug = it.getString("slug")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blog_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slug?.let {
            fetchBlogDetails(it)
        }

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.let { compatActivity ->
            compatActivity.setSupportActionBar(toolbar)
            compatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
            compatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            compatActivity.supportActionBar?.setDisplayShowHomeEnabled(false)

        }

        toolbar.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun fetchBlogDetails(slug: String) {
        // Get an instance of Retrofit and create a service from it
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)

        // Since this call is not suspend function, we can directly enqueue
        val call = apiService.getBlogDetailsBySlug(slug)
        call.enqueue(object : Callback<BlogDetailsResponse> {
            override fun onResponse(call: Call<BlogDetailsResponse>, response: Response<BlogDetailsResponse>) {
                if (response.isSuccessful) {
                    val blogDetails = response.body()?.data
                    blogDetails?.let {
                        updateUI(it)
                    }
                } else {
                    // error
                    Toast.makeText(context, "Failed to fetch details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BlogDetailsResponse>, t: Throwable) {
                // failure
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(blogDetails: BlogDetails) {
        val titleTextView: TextView = view?.findViewById(R.id.blog_detail_title) ?: return
        val contentTextView: TextView = view?.findViewById(R.id.blog_detail_content) ?: return
        val imageView: ImageView = view?.findViewById(R.id.blog_detail_image) ?: return

        titleTextView.text = blogDetails.title
        contentTextView.text = HtmlCompat.fromHtml(blogDetails.content, HtmlCompat.FROM_HTML_MODE_LEGACY)

        Glide.with(this).load(blogDetails.feature_image_url).into(imageView)
    }


    override fun onResume() {
        super.onResume()
        (activity?.findViewById<TextView>(R.id.tvLearnTitle))?.visibility = View.GONE
        (activity?.findViewById<View>(R.id.view))?.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        (activity?.findViewById<TextView>(R.id.tvLearnTitle))?.visibility = View.VISIBLE
        (activity?.findViewById<View>(R.id.view))?.visibility = View.VISIBLE
    }
}
