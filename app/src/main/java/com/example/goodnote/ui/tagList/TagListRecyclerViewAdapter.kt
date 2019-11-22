package com.example.goodnote.ui.tagList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goodnote.R
import com.example.goodnote.database.models.Tag
import kotlinx.android.synthetic.main.tag_item.view.*

class TagListRecyclerViewAdapter(private val clickedTag: TagListFragment.onTagClicked) : RecyclerView.Adapter<TagListRecyclerViewAdapter.ViewHolder>() {

    private val TAG = TagListRecyclerViewAdapter::class.java.simpleName
    private val tags: MutableList<Tag> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.tag_item,
            parent,false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int  = tags.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.tag_item_name
        val tagItem = view.tag_item_layout

        fun bind(position: Int) {
            name.text = tags[position].name

            tagItem.setOnClickListener {
                clickedTag.onTagClick(tags[position].tagId)
            }

            // has no indication it worked
            tagItem.setOnLongClickListener {
                clickedTag.onTagLongPress(tags[position].tagId)
                true
            }
        }
    }

    internal fun setTags(newTags: List<Tag>) {
        this.tags.clear()
        this.tags.addAll(newTags)
        notifyDataSetChanged()
    }
}