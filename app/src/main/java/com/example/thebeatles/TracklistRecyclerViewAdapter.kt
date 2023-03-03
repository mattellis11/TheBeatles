package com.example.thebeatles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class Track(var title: String, var songWriter: String)

class TracklistRecyclerViewAdapter : RecyclerView.Adapter<TracklistRecyclerViewAdapter.ViewHolder>
{
  var albumFile: String? = null // will be passed from the album recycleView
  var tracksArray: Array<Track>? = null // will get from processData(albumFile)

  constructor(album: Int)
  {
    val filenames = arrayOf("pleasepleaseme", "with_the_beatles", "harddaysnight", "beatlesforsale",
    "help", "rubber_soul", "revolver", "sgt_pepper", "magicalmysterytour", "yellowsubmarine",
    "white", "abbeyroad", "letitbe", "pastmastersvolume1", "pastmastersvolume2")
    albumFile = filenames[album]
    tracksArray = processData(albumFile!! + ".txt")
  }

  // Function processes tracklists for file passed from selected album
  private fun processData(file: String): Array<Track>
  {
    val inputStream = MainActivity.getInstance().assets.open(file)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))

    //Read all the lines in the file and store in an array:
    val lines = bufferedReader.readLines()

    //Convert the List to an array of Lines
    val arrayLines = lines.toTypedArray()

    //Create a 2D Array
    var allData = arrayOf<Array<String>>()

    //Parse into fields
    for (i in 0..arrayLines.size - 1)
    {
      val array = arrayLines[i].split("^")
      allData += array.toTypedArray()
    }

    var tracksArray = arrayOf<Track>()

    for (track in allData)
    {
      tracksArray += Track(track[0], track[1])
    }

    //Close the Reader
    bufferedReader.close()

    return tracksArray
  }

  override fun getItemCount(): Int
  {
    return tracksArray!!.size
  }

  //This creates a ViewHolder object based on card_layout for each cell
  //This is the glue, calls viewholder and puts data in and returns it
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
  {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.tracklist_card_layout, parent, false)
    return ViewHolder(v)
  }

  // This initializes the viewHolder
  override fun onBindViewHolder(holder: ViewHolder, position: Int)
  {
    holder.trackTitle.text = tracksArray!![position].title
    holder.songWriter.text = tracksArray!![position].songWriter

  }

  // creates the views for the buttons, gets called for each
  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  {
    var trackTitle: TextView
    var songWriter: TextView

    init
    {
      trackTitle = itemView.findViewById(R.id.trackTitle)
      songWriter = itemView.findViewById(R.id.albumTitle)
      val handler = Handler()
      itemView.setOnClickListener(handler)
    }

    inner class Handler() : View.OnClickListener
    {
      override fun onClick(view: View?)
      {
        val itemPosition = getLayoutPosition() // get the selected track
        val bundle = Bundle()
        bundle.putString("track", tracksArray!![itemPosition].title)
        val navController = Navigation.findNavController(TrackListFragment.getInstance().requireView())
        navController.navigate(R.id.action_tracklist_to_video, bundle)
      }
    }

  }
}

