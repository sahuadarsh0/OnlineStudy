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


class Play : AppCompatActivity() {
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

    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
    }

}