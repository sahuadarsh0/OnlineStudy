package com.techipinfotech.onlinestudy1

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.techipinfotech.onlinestudy1.databinding.ActivityPlayBinding
import technited.minds.androidutils.SharedPrefs


class Play : AppCompatActivity()
//    : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener
{
    //    private lateinit var playerView: YouTubePlayerView
    private lateinit var videoUrl: String
    private lateinit var materialId: String

    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var binding: ActivityPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = DataBindingUtil.setContentView(this, R.layout.activity_play)
        youTubePlayerView = findViewById(R.id.youtube)
        this.lifecycle.addObserver(youTubePlayerView)

        userSharedPreferences = SharedPrefs(this, "USER")
        val args: PlayArgs by navArgs()
        videoUrl = args.url
        materialId = args.materialId

        youTubePlayerView.enterFullScreen();
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoUrl, 0F)
                youTubePlayer.play()

//                Viewed.materialViewed(
//                    this@Play,
//                    userSharedPreferences.get("student_mobile"),
//                    materialId
//                )

                youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        super.onStateChange(youTubePlayer, state)

                        if (state == PlayerConstants.PlayerState.ENDED) {
                            Viewed.materialViewed(
                                this@Play,
                                userSharedPreferences.get("student_mobile"),
                                materialId
                            )
                        }

                    }
                })
                youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        super.onStateChange(youTubePlayer, state)

                        if (state == PlayerConstants.PlayerState.ENDED) {
                            Viewed.materialViewed(
                                this@Play,
                                userSharedPreferences.get("student_mobile"),
                                materialId
                            )
                        }

                    }
                })
            }
        })


    }


//    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, b: Boolean) {
////        youTubePlayer.setShowFullscreenButton(false);
//        youTubePlayer.loadVideo(videoUrl)
//        //        youTubePlayer.play();
//        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
//        youTubePlayer.setShowFullscreenButton(false)
//        youTubePlayer.setFullscreen(true)
//        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener)
//    }

//    override fun onInitializationFailure(
//        provider: YouTubePlayer.Provider,
//        youTubeInitializationResult: YouTubeInitializationResult
//    ) {
//        Toast.makeText(this, "Unable to load Player", Toast.LENGTH_SHORT).show()
//    }

//    private inner class MyPlayerStateChangeListener : PlayerStateChangeListener {
//        override fun onLoading() {
//            // Called when the player is loading a video
//            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
//        }
//
//        override fun onLoaded(s: String) {
//            // Called when a video is done loading.
//            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
//        }
//
//        override fun onAdStarted() {
//            // Called when playback of an advertisement starts.
//        }
//
//        override fun onVideoStarted() {
//            // Called when playback of the video starts.
//        }
//
//        override fun onVideoEnded() {
//            // Called when the video reaches its end.
//            Viewed.materialViewed(this@Play, userSharedPreferences.get("student_mobile"), materialId)
//            finish()
//        }
//
//        override fun onError(errorReason: YouTubePlayer.ErrorReason) {
//            // Called when an error occurs.
//        }


    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
    }

}