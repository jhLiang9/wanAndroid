package com.example.wanandroid.entity

import java.util.*
import kotlin.collections.ArrayList

data class CoinData(val data :Coin,val errorCode:Int,val errorMsg:String) {
}

data class Coin(val coinCount:Int,val rank :Int,val userId:Int,val username:String)
data class CoinDetailData(val data :CoinDetailList,val errorCode:Int,val errorMsg:String) {

}
data class CoinDetailList(val curPage:Int,val datas:ArrayList<CoinDetail>,val offset:Int,val over:Boolean,val pageCount:Int,val size:Int,val total:Int)
data class CoinDetail(val coinCount: Int,val date: Long,val desc:String,val id:Int,val reason:String,val type:Int ,val userId:Int,val userName:String)
//{
//    "data": {
//        "coinCount": 451, //总积分
//        "rank": 7, //当前排名
//        "userId": 2,
//        "username": "x**oyang"
//    },
//    "errorCode": 0,
//    "errorMsg": ""
//}


///{"coinCount":26,"date":1630051936000,"desc":"2021-08-27 16:12:16 签到 , 积分：11 + 15","id":489391,"reason":"签到","type":1,"userId":30064,"userName":"12345"}