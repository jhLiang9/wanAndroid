package com.example.wanandroid.entity

/**
 * 微信公众号文章
 */
data class WXAccount(
    val children: ArrayList<WXAccount>? = null,
    val courseId: Int = -1,
    val id: Int = -1,
    val name: String = "",
    val order: Int = -1,
    val parentChapterId: Int = -1,
    val userControlSetTop: Boolean = false,
    val visible: Int = 1
)

data class WXAccountList(val data: ArrayList<WXAccount>? = null) : BaseResponse()