package com.example.androidtestproject.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.ItemUserBinding
import com.example.androidtestproject.model.UserModel
import javax.inject.Inject

/**
 * Created by Neha Dessai on 30-03-2023.
 */

class UsersAdapter @Inject() constructor() :
    PagingDataAdapter<UserModel, UsersAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemUserBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemUserBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserModel) {
            binding.apply {
                tvUserName.text = context.resources.getString(
                    R.string.name_string,
                    item.first_name,
                    item.last_name
                )
                tvUserEmail.text = item.email

                ivAvtar.load(item.avatar) {
                    crossfade(true)
                    placeholder(R.drawable.placeholder)
                    scale(Scale.FILL)
                    transformations(RoundedCornersTransformation(8f))
                }

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((UserModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (UserModel) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}