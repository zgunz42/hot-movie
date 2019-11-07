package com.kangmicin.hotmovie.ui.main

abstract class DataItemFragment<T> : PreloadFragment<T>() {
    override fun listItemAdapter(): ListItemAdapter {
        return ListItemAdapter(emptyList(), clickListener)
    }
}