package com.example.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TrackListFragment : Fragment()
{
  private var layoutManager: RecyclerView.LayoutManager? = null
  private var adapter: RecyclerView.Adapter<TracklistRecyclerViewAdapter.ViewHolder>? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
    MainActivity.getInstance().getSupportActionBar()?.setTitle("Songs")
    layoutManager = LinearLayoutManager(MainActivity.getInstance())
    val recyclerView = MainActivity.getInstance().findViewById<RecyclerView>(R.id.tracklistRecyclerView)

    recyclerView.layoutManager = layoutManager
    adapter = TracklistRecyclerViewAdapter(arguments?.getInt("position")!!)
    recyclerView.adapter = adapter
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    instance = this
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View?
  {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_tracklist, container, false)
  }

  companion object
  {
    private var instance: TrackListFragment? = null
    fun getInstance(): TrackListFragment
    {
      return instance!!
    }
  }
}