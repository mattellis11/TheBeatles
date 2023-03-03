package com.example.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import org.json.JSONObject
import java.net.URL

class VideoFragment : Fragment()
{
  private var song: String = ""

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)

    //Set the song
    song = arguments?.get("track").toString()
    MainActivity.getInstance().getSupportActionBar()?.setTitle(song)
    val origSong = song
    song = song.replace(" ", "+")


    //Set the artist
    var artist = "The Beatles"
    val origArtist = artist
    artist = artist.replace(" ","+")

    //Encode search for YouTube
    val keywords = "$artist+$song"
    val max = 50

    //Set the youtube search
    /*
    API key removed
    */

    // The URL will provide the JSON data and must be run within a background thread
    // Retrieval data must be done on a helper thread!! Cannot be done in onCreate
    val helper = Helper(string, origSong, origArtist)
    val thread = Thread(helper)
    thread.start()
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
    return inflater.inflate(R.layout.fragment_video, container, false)
  }

  companion object
  {
  }
}

class Helper : Runnable
{
  private var url: String =  ""
  private var song: String = ""
  private var artist: String = ""

  constructor(url:String, song: String, artist: String)
  {
    this.url = url
    this.song = song
    this.artist = artist

  }

  override fun run()
  {

    //A URL and readText is then performed within the run method of the helper thread:
    val data = URL(url).readText()

    //This reads the data as text – it next needs to be converted to JSON:
    val json = JSONObject(data)

    //Extract the array of items as shown below:
    val items = json.getJSONArray("items") // this is the "items: [ ] part, array of dictionaries

    //And create arrays for titles and videoid as shown below:
    val titles = ArrayList<String>()
    val videos = ArrayList<String>()

    //Next loop thru all 50 items extracting out the title/video id
    for (i in 0 until items.length())
    {
      var videoObject = items.getJSONObject(i)
      var idDict = videoObject.getJSONObject("id")
      var videoId = idDict.getString("videoId")
      var snippetDict = videoObject.getJSONObject("snippet")
      var title =  snippetDict.getString("title")
      title = title.replace("&#39;", "'")
      titles.add(title)
      videos.add(videoId)
    }

    // use index of on tiles to find the best matching video*****for app4
    var iter = 0
    for (title in titles)
    {
      if (title.contains("beatles", true) && (title.contains(song, true)) )
      {
        break
      }
      if (title.contains(song, true) && (title.contains("remastered", true)))
      {
        break
      }
      iter++
    }

    //In this example, we'll  be selecting the 15th video – but you will need to write a loop to select the best //matching video.
    var selectedVideo : String = ""
    if (iter != 50)
    {
      selectedVideo = videos[iter]
    }
    else
    {
      selectedVideo = videos[0]
    }

    //Invoke the UI helper thread as shown below:
    var helper1 = UIThreadHelper(selectedVideo)
    MainActivity.getInstance().runOnUiThread(helper1)
  }
}

class UIThreadHelper: Runnable
{
  private var video: String = ""

  constructor(video: String)
  {
    this.video = video
  }

  override fun run()
  {
    if (!video.equals(""))
    {
      //Update the webView
      // these settings are good for playing youtube videos
      var web = MainActivity.getInstance().findViewById<WebView>(R.id.videoWebView)
      val settings = web.getSettings()
      settings.setJavaScriptEnabled(true)
      settings.setDomStorageEnabled(true)
      settings.setMinimumFontSize(10)
      settings.setLoadWithOverviewMode(true)
      settings.setUseWideViewPort(true)
      settings.setBuiltInZoomControls(true)
      settings.setDisplayZoomControls(false)
      web.setVerticalScrollBarEnabled(false)
      settings.setDomStorageEnabled(true)
      web.setWebViewClient(WebViewClient())
      var str = "https://www.youtube.com/watch?v=" + video
      web.loadUrl(str)
    }
  }
}