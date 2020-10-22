package com.example.musicplayer2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {

    private int resourceId;

    public MusicAdapter(@NonNull Context context, int resource, @NonNull List<Music> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Music m = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView music_id = (TextView) view.findViewById(R.id.music_id);
        TextView music_title = (TextView) view.findViewById(R.id.music_title);

        TextView music_artist = (TextView) view.findViewById(R.id.music_artist);
        TextView music_url = (TextView) view.findViewById(R.id.music_url);
        TextView music_duration = (TextView) view.findViewById(R.id.music_duration);

        ImageView imageView = (ImageView) view.findViewById(R.id.music_image);

        music_id.setText("" + m.getId());
        music_title.setText(m.getTitle());
        music_artist.setText(m.getArtist());
        music_url.setText(m.getUrl());

        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String time = sdf.format(m.getDuration());
        music_duration.setText(time);

        System.out.println(m.getImage_url());
        if (m.getImage_url()!=null){

            imageView.setImageBitmap(m.getImage_url());
        }else{
            System.out.println("mId:"+m.getId());
            switch (m.getId()){
                case 30:
                    imageView.setImageResource(R.drawable.ic_launcher_background);
                    break;
                case 29:
                    imageView.setImageResource(R.drawable.ic_launcher_background);
                    break;
                case 31:
                    imageView.setImageResource(R.drawable.ic_launcher_background);
                    break;
            }

        }
        return view;
    }
}
