package ru.ponomarchukpn.aston_intensiv_6.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val spacePixels: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        s: RecyclerView.State
    ) {
        parent.adapter?.let { adapter ->
            rect.bottom = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION, adapter.itemCount - 1 -> 0
                else -> spacePixels
            }
            rect.top = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION, FIRST_ELEMENT_POSITION -> 0
                else -> spacePixels
            }
            rect.left = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION -> 0
                else -> spacePixels
            }
            rect.right = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION -> 0
                else -> spacePixels
            }
        }
    }

    companion object {

        private const val FIRST_ELEMENT_POSITION = 0
    }
}
