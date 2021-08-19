package com.example.wanandroid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ItemSystemDetailBinding
import com.example.wanandroid.databinding.ItemSystemModuleBinding
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.list.TreeList
import com.example.wanandroid.viewmodel.SystemViewModel


class SystemAdapter( private val overviewList:ArrayList<Tree>, val viewModel:SystemViewModel) : RecyclerView.Adapter<SystemAdapter.ViewHolder>() {
    private val size = overviewList.size
    private lateinit var binding : ItemSystemModuleBinding
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moduleName = binding.moduleName

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_system_module, parent, false)
        binding = ItemSystemModuleBinding.bind(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.moduleName.text = overviewList[position].name
        binding.recyclerView.adapter = ModuleAdapter(overviewList[position].children,viewModel)
        binding.recyclerView.layoutManager= GridLayoutManager(binding.root.context, 4)
    }
    override fun getItemCount(): Int = size

}

class ModuleAdapter(val tree:ArrayList<Tree>,val viewModel:SystemViewModel) :RecyclerView.Adapter<ModuleAdapter.ViewHolder>(){
    private lateinit var binding :ItemSystemDetailBinding
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val detailName= binding.detailName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_system_detail, parent, false)
        binding = ItemSystemDetailBinding.bind(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.detailName.text = tree[position].name
    }

    override fun getItemCount(): Int  = tree.size

}



