package com.example.thebeatles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class Album(var title: String, var releaseDate: String, var imgFileName: String)

class AlbumRecyclerViewAdapter : RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder>()
{
  var albumDataFile: String = "albums.txt"
  var albumArray: Array<Album> = processData(albumDataFile)

  private fun processData(file: String): Array<Album>
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

    var albumArray = arrayOf<Album>()

    for (album in allData)
    {
      albumArray += Album(album[0], album[2], album[3])
    }

    //Close the Reader
    bufferedReader.close()

    return albumArray
  }

  override fun getItemCount(): Int
  {
    return albumArray.size
  }

  //This creates a ViewHolder object based on card_layout for each cell
  //This is the glue, calls viewholder and puts data in and returns it
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
  {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
    return ViewHolder(v)
  }

  // This initializes the viewHolder
  override fun onBindViewHolder(holder: ViewHolder, position: Int)
  {
    holder.itemTitle.text = albumArray[position].title
    holder.itemReleaseDate.text = albumArray[position].releaseDate
    holder.itemImage.setImageResource(
      MainActivity.getInstance().resources.getIdentifier(
        albumArray[position].imgFileName,
        "drawable",
        MainActivity.getInstance().packageName
      )
    )
  }

  // creates the views for the buttons, gets called for each
  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  {
    var itemTitle: TextView
    var itemReleaseDate: TextView
    var itemImage: ImageView

    init
    {
      itemTitle = itemView.findViewById(R.id.albumTitle)
      itemReleaseDate = itemView.findViewById(R.id.albumReleaseDate)
      itemImage = itemView.findViewById(R.id.albumCover)
      var handler = Handler()
      itemView.setOnClickListener(handler)
    }

    inner class Handler() : View.OnClickListener
    {
      override fun onClick(view: View?)
      {
        val itemPostition = getLayoutPosition()
        var navController = Navigation.findNavController(AlbumFragment.getInstance().requireView())
        val bundle = Bundle()
        bundle.putInt("position", itemPostition)
        navController.navigate(R.id.action_album_to_tracklist, bundle)
      }
    }

  }
}
