package com.example.musiccontroller;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> songList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSongList();

        ListView songView = findViewById(R.id.song_list);

        SongAdapter songAdt = new SongAdapter(getApplicationContext(), songList);
        songView.setAdapter(songAdt);




        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getSongList() {
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor musicCursor = getApplicationContext().getContentResolver().query(musicUri, null, null, null, selection);

        //Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int sizeColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int lengthColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int path = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                Song song = new Song();
                song.setId(thisId);
                song.setTitle(thisTitle);
                song.setArtist(thisArtist);
                int size = musicCursor.getInt(sizeColumn)*8;
                int length = musicCursor.getInt(lengthColumn)/1000;
                int bitRate =length!=0? (size/length)/1000:0;
                song.setBitRate(bitRate);

                String pathMusic = musicCursor.getString(path);
                MediaExtractor mex = new MediaExtractor();
                try {
                    mex.setDataSource(musicCursor.getString(path));// the adresss location of the sound on sdcard.
                    MediaFormat mf = mex.getTrackFormat(0);
                    int xyz = mf.getInteger(MediaFormat.KEY_BIT_RATE);
                    int sampleRate = mf.getInteger(MediaFormat.KEY_SAMPLE_RATE);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }





                songList.add(song);
            }
            while (musicCursor.moveToNext());
        }
        Objects.requireNonNull(musicCursor).close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
