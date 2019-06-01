package com.example.android.mediaplayerapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    ImageView repeat;
    ImageView repeatOff;
    MediaPlayer mediaPlayer;
    private boolean isRepeat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar seekBar = (SeekBar) findViewById(R.id.seek);

        final TextView time = (TextView)findViewById(R.id.time);
        final TextView duration_ = (TextView)findViewById(R.id.Duration);

        repeat = (ImageView) findViewById(R.id.repeat);
        repeatOff = (ImageView) findViewById(R.id.repeatOff);

        mediaPlayer = MediaPlayer.create(this, R.raw.petta2);

        final ImageView play = (ImageView) findViewById(R.id.playButton);
        final ImageView pause = (ImageView) findViewById(R.id.pauseButton);

        final int duration = mediaPlayer.getDuration() / 1000;

        seekBar.setMax(duration);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    play.setEnabled(false);
                    play.setVisibility(View.INVISIBLE);
                    pause.setEnabled(true);
                    pause.setVisibility(View.VISIBLE);
                }
            }
        });

        final Handler mHandler = new Handler();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);

                    int timeMin = (mediaPlayer.getCurrentPosition()/1000)/60;
                    int timeSec = (mediaPlayer.getCurrentPosition()/1000)%60;
                    time.setText(new DecimalFormat("00").format(timeMin)+":"+new DecimalFormat("00").format(timeSec));



                }
                mHandler.postDelayed(this, 1);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    pause.setEnabled(false);
                    pause.setVisibility(View.INVISIBLE);
                    play.setEnabled(true);
                    play.setVisibility(View.VISIBLE);
                }
            }
        });

        ImageView forward = (ImageView) findViewById(R.id.forward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
            }
        });

        ImageView backward = (ImageView) findViewById(R.id.backward);
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (isRepeat == true) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                } else {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.v("MainActivity", "Done");
                if (fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        int durMin = (mediaPlayer.getDuration()/1000)/60;
        int durSec = (mediaPlayer.getDuration()/1000)%60;
        duration_.setText(new DecimalFormat("00").format(durMin)+":"+new DecimalFormat("00").format(durSec));



        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repeat(0);
            }
        });

        repeatOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repeat(1);
            }
        });


    }

    private void Repeat(int i) {
        if (i == 0) {
            isRepeat = true;
            repeat.setEnabled(false);
            repeat.setVisibility(View.INVISIBLE);
            repeatOff.setEnabled(true);
            repeatOff.setVisibility(View.VISIBLE);
        } else if (i == 1) {
            isRepeat = false;
            repeat.setEnabled(true);
            repeat.setVisibility(View.VISIBLE);
            repeatOff.setEnabled(false);
            repeatOff.setVisibility(View.INVISIBLE);
        }
    }

}
