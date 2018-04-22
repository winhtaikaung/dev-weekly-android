package com.winhtaikaung.devweekly.base


import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.shimmer.ShimmerFrameLayout
import com.winhtaikaung.devweekly.R
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author rockerhieu on 7/6/15.
 */
class EndlessRecyclerViewAdapter @JvmOverloads constructor(private val context: Context, wrapped: Adapter<*>,
                                                           private val requestToLoadMoreListener: RequestToLoadMoreListener, @param:LayoutRes private val pendingViewResId: Int = R.layout.item_loading,
                                                           keepOnAppending: Boolean = true) : RecyclerViewAdapterWrapper(wrapped) {
    private val keepOnAppending: AtomicBoolean
    private val dataPending: AtomicBoolean

    init {
        this.keepOnAppending = AtomicBoolean(keepOnAppending)
        dataPending = AtomicBoolean(false)
    }

    private fun stopAppending() {
        setKeepOnAppending(false)
    }

    /**
     * Let the adapter know that data is load and ready to view.
     *
     * @param keepOnAppending whether the adapter should request to load more when scrolling to the
     * bottom.
     */
    fun onDataReady(keepOnAppending: Boolean) {
        dataPending.set(false)
        setKeepOnAppending(keepOnAppending)
    }

    private fun setKeepOnAppending(newValue: Boolean) {
        keepOnAppending.set(newValue)
        wrappedAdapter.notifyDataSetChanged()
    }

    /**
     *
     */
    fun restartAppending() {
        dataPending.set(false)
        setKeepOnAppending(true)
    }

    private fun getPendingView(viewGroup: ViewGroup): View {
        return LayoutInflater.from(context).inflate(pendingViewResId, viewGroup, false)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (keepOnAppending.get()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == wrappedAdapter.itemCount) {
            TYPE_PENDING
        } else super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == TYPE_PENDING) {
            PendingViewHolder(getPendingView(parent))
        } else super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_PENDING) {
            if (!dataPending.get()) {
                dataPending.set(true)
                requestToLoadMoreListener.onLoadMoreRequested()
            }
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    interface RequestToLoadMoreListener {
        /**
         * The adapter requests to load more data.
         */
        fun onLoadMoreRequested()
    }

    internal inner class PendingViewHolder(itemView: View) : ViewHolder(itemView) {

        init {
            val shimmerLayout = itemView.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)
            shimmerLayout.startShimmer()
        }
    }

    companion object {
        val TYPE_PENDING = 999
    }
}

