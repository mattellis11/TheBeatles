/*
This fragment is loaded when the app is started.
It displays a image slideshow of each of the albums.
 */
package com.example.thebeatles

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment()
{

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
    val slideShow = SlideShow(this)
    slideShow.execute()
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View?
  {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_main, container, false)
  }

  companion object
  {
    private class SlideShow(private val mainFragment: MainFragment) : AsyncTask<Void, Int, Void>()
    {
      private val files = arrayOf("intro", "please_please_me", "with_the_beatles", "hard_days_night",
        "beatles_for_sale", "help", "rubber_soul", "revolver", "sgt_pepper", "white_album", "yellow_submarine",
        "abbey_road", "let_it_be"
      )
  
      override fun doInBackground(vararg p0: Void?): Void?
      {
        var count = 1
        while (true)
        {
          if (count >= files.size)
            count = 0
          var fileName = files[count]
          var resourceID = MainActivity.getInstance().resources.getIdentifier(fileName,
            "drawable", MainActivity.getInstance().packageName
          )
          Thread.sleep(3000)
          if (mainFragment.slideShowImageView == null)
            return null
          else
            publishProgress(resourceID)
          count++
        }
      }
  
      /*
      Parameter is the argument sent from publishProgress() within doInBackground()
       */
      override fun onProgressUpdate(vararg values: Int?)
      {
        super.onProgressUpdate(*values)
        val resourceID = values[0]
        if (resourceID != null && mainFragment.slideShowImageView != null)
        {
          mainFragment.slideShowImageView.setImageResource(resourceID)
        }
      }
  
      override fun onPostExecute(result: Void?)
      {
        // Receives null from doInBackground()
        // Need to call onPostExecute in order to end the task after switching to a new fragment
        // because slideShowImageView becomes null at that point
      }
  
    }

  }

}
