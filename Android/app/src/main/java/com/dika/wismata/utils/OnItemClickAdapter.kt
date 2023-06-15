package com.dika.wismata.utils

import androidx.core.app.ActivityOptionsCompat
import com.dika.wismata.network.model.DataItem

interface OnItemClickAdapter {
    fun onItemClick(dataItem: DataItem, optionsCompat: ActivityOptionsCompat)
}