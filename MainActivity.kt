package com.example.musicplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    //now we'll need to change the seekbar postion while the song is playing
    //to do this we need to create a runnable object and a handler

    lateinit var runnable: Runnable
    private var handler = Handler()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Let's create a new media player object
        

        val mediaPlayer = MediaPlayer.create(this,R.raw.song)
        // Now let's create our play button event\
        val playbtn = findViewById<ImageButton>(R.id.play_btn)
        val seek = findViewById<SeekBar>(R.id.seekbar)

        // Now let's add our seekbar functionalities
        seek.progress = 0

        // and now we will add the maximum value of our seekbar the duration of the music

    seek.max = mediaPlayer.duration


       playbtn.setOnClickListener() {
           if (!mediaPlayer.isPlaying) {
               mediaPlayer.start()

               playbtn.setImageResource(R.drawable.baseline_pause_24)
           }else{ // the media player is playing and we can pause it
               mediaPlayer.pause()
               playbtn.setImageResource(R.drawable.baseline_play_arrow_24)
            

           }
       }
        // now we will add the seek bar event

        // when we change our seekbar progress the song will change the position

        seek.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                //now when we change the position of seekbar the music will go to that position
                if (changed){
                    mediaPlayer.seekTo(pos)

                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                mediaPlayer.pause()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.let { seekBar ->
                    mediaPlayer.seekTo(seekBar.progress)
                    mediaPlayer.start()
                }
            }

        })

      

        runnable = Runnable {
            seek.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable,1000)

        }

        handler.postDelayed(runnable,1000)

//now we want that when the music finish to play the seekbar will backt  to 0 and button image changes

        mediaPlayer.setOnCompletionListener {
            playbtn.setImageResource(R.drawable.baseline_play_arrow_24)
            seek.progress=0
        }
    }


}
