package com.jfsb.blogsapp.features.dashboard.data.datasource.remote.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class EntryModel(
    @DocumentId var id: String? = null,
    var date:Date? = null,
    var author:String? = null,
    var content:String? = null,
    var title:String? = null
)
