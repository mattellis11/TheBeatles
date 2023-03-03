package com.example.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlbumFragment : Fragment()
{
  private var layoutManager: RecyclerView.LayoutManager? = null
  private var adapter: RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder>? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
    MainActivity.getInstance().getSupportActionBar()?.setTitle("Albums")
    layoutManager = LinearLayoutManager(MainActivity.getInstance())
    val recyclerView = MainActivity.getInstance().findViewById<RecyclerView>(R.id.albumRecyclerView)

    recyclerView.layoutManager = layoutManager
    adapter = AlbumRecyclerViewAdapter()
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
    return inflater.inflate(R.layout.fragment_album, container, false)
  }

  companion object
  {
    private var instance : AlbumFragment? = null
    fun getInstance() : AlbumFragment
    {
      return instance!!
    }
  }

}