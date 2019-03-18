package com.gevyo.android.timetolearn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MovieViewHolder> {
    List<MovieData> movies;

    RVAdapter (List<MovieData> movies) {
        this.movies = movies;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView movieName;
        TextView movieYear;
        TextView movieDirector;
        ImageView movieImage;

        MovieViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            movieName = (TextView) itemView.findViewById(R.id.movie_name);
            movieYear = (TextView) itemView.findViewById(R.id.movie_year);
            movieImage = (ImageView) itemView.findViewById(R.id.movie_image);
            movieDirector = (TextView) itemView.findViewById(R.id.movie_director);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.movieName.setText(movies.get(i).name);
        movieViewHolder.movieYear.setText(movies.get(i).year);
        movieViewHolder.movieDirector.setText(movies.get(i).director);
        new DownloadImageTask(movieViewHolder.movieImage).execute(movies.get(i).image_url);
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) { //menjalankan komputasi di elakang, nanti dibalikkan ke post execute
            String urldisplay = urls[0]; //menampilkan jumlah url yang mau ditampilin
            Bitmap mIcon1I = null; //gambar yang ditampilkan dalam bentuk bitmap
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon1I = BitmapFactory.decodeStream(in); //yang akan memproses hasil compress dari gambar
            } catch (Exception e) { //untuk cek apakah gambarnya bisa keluar atau gak, if no = error
                Log.e("ERROR", e.getMessage());
                e.printStackTrace();
            }
            return mIcon1I;
        }
        protected void onPostExecute(Bitmap result) { //dia yang nanti menampilkan hasil komputasi dari doinbackground
            bmImage.setImageBitmap(result);
        }
    }
}
