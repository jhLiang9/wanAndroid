

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.WebViewActivity
import com.example.wanandroid.entity.Article

import com.example.wanandroid.viewmodel.UserViewModel


class MyCollectionAdapter(val viewModel :UserViewModel) :RecyclerView.Adapter<MyCollectionAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val time:TextView = view.findViewById(R.id.time)
        val superChapterName: TextView =view.findViewById(R.id.superChapterName)
        val cardView :CardView = view.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        val viewHolder=ViewHolder(view)
        viewHolder.cardView.visibility = View.GONE

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition //获取用户点击的position
            val article =viewModel.collectionList[position]
            val link=article.link
            val intent = Intent(parent.context, WebViewActivity::class.java)
            intent.putExtra("data", link);
            parent.context.startActivity(intent)
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = viewModel.collectionList[position]
        holder.title.text= article.title
        holder.author.text=article.author
        holder.superChapterName.text=article.superChapterName
        holder.time.text=article.niceDate
        //加载下一页
        if(position == itemCount-5){
//            getNextPage()

        }
    }
//    private fun getNextPage() = viewModel.getArticlesByPage(viewModel.nextPage++)

    override fun getItemCount(): Int = viewModel.collectionList.size

}