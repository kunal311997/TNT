package com.kunal.tnt.createfeed.data

import com.squareup.moshi.Json

data class ImageUploadResponse(

	@Json(name="data")
	val data: ImageData? = null,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="status")
	val status: Int? = null
)

data class ImageData(

	@Json(name="in_most_viral")
	val inMostViral: Boolean? = null,

	@Json(name="ad_type")
	val adType: Int? = null,

	@Json(name="link")
	val link: String? = null,

	@Json(name="description")
	val description: Any? = null,

	@Json(name="section")
	val section: Any? = null,

	@Json(name="title")
	val title: Any? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="deletehash")
	val deletehash: String? = null,

	@Json(name="datetime")
	val datetime: Int? = null,

	@Json(name="has_sound")
	val hasSound: Boolean? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="in_gallery")
	val inGallery: Boolean? = null,

	@Json(name="vote")
	val vote: Any? = null,

	@Json(name="views")
	val views: Int? = null,

	@Json(name="height")
	val height: Int? = null,

	@Json(name="bandwidth")
	val bandwidth: Int? = null,

	@Json(name="nsfw")
	val nsfw: Any? = null,

	@Json(name="is_ad")
	val isAd: Boolean? = null,

	@Json(name="edited")
	val edited: String? = null,

	@Json(name="ad_url")
	val adUrl: String? = null,

	@Json(name="tags")
	val tags: List<Any?>? = null,

	@Json(name="account_id")
	val accountId: Int? = null,

	@Json(name="size")
	val size: Int? = null,

	@Json(name="width")
	val width: Int? = null,

	@Json(name="account_url")
	val accountUrl: Any? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="animated")
	val animated: Boolean? = null,

	@Json(name="favorite")
	val favorite: Boolean? = null
)
