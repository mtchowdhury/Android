package com.example.musiccontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Song> songList;

    SongAdapter(Context context, ArrayList<Song> songs) {
        this.songList = songs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            new View(context);
            assert inflater != null;
            view = inflater.inflate(R.layout.song, null);
            TextView songView = view.findViewById(R.id.song_title);
            TextView artistView = view.findViewById(R.id.song_artist);
            TextView bitRate = view.findViewById(R.id.bitrate);
            //get song using position
            Song currSong = songList.get(position);
            //get title and artist strings
            songView.setText(currSong.getTitle());
            artistView.setText(currSong.getArtist());
            bitRate.setText(String.format("%s KBPS", String.valueOf(currSong.getBitRate())));
            //set position as tag
            view.setTag(position);

        } else {
            view = convertView;
        }

        return view;
    }





}