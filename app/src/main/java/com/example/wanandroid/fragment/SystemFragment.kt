package com.example.wanandroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.adapter.SystemAdapter
import com.example.wanandroid.database.SystemDatabase
import com.example.wanandroid.database.dao.SystemDatabaseDao
import com.example.wanandroid.databinding.FragmentSystemBinding
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.list.TreeList
import com.example.wanandroid.viewmodel.SystemViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors
import kotlin.concurrent.thread


class SystemFragment : Fragment() {

    private lateinit var binding: FragmentSystemBinding
    private lateinit var viewModel: SystemViewModel
    private lateinit var database: SystemDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(SystemViewModel::class.java)
        database = SystemDatabase.getInstance(requireContext()).systemDatabaseDao
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_system, container, false)
        binding.systemModule.adapter = SystemAdapter(viewModel.list, viewModel)
        binding.systemModule.layoutManager = LinearLayoutManager(context)
        initData()
        return binding.root
    }

    private fun initData() {

        viewModel.getData()
        viewModel.overview.observe(viewLifecycleOwner, {
            lateinit var disposable: Disposable
            Observable.create(ObservableOnSubscribe<Tree> { emitter ->
                for(item in it.data){
                    emitter.onNext(item)
                }
                emitter.onComplete()
            })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(object :Observer<Tree>{
                    override fun onSubscribe(d: Disposable?) {
                        if (d != null) {
                            disposable = d
                        }
                    }

                    override fun onNext(t: Tree?) {
                        if (t != null) {
                            if(database.getTree(t.id)==null){
                                Log.i("system","insert")
                                database.insert(t)
                            }

                        }
                        Log.i("system","OnNext")
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {

                        viewModel.list.addAll(database.getAllSystemTree())
                        Log.i("system",viewModel.list[0].toString())
                    }

                })
            //TODO 处理好先后关系

            while(!disposable.isDisposed){}
            Thread.sleep(2000L)
            binding.systemModule.adapter?.notifyDataSetChanged()
            Log.i("system","notify")
            binding.loadingPanel.visibility = View.GONE
        })
    }


}