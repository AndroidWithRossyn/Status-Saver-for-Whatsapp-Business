package com.kessi.statussaver.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kessi.statussaver.R;


import java.io.File;
import java.util.List;

public class StaVideoAdapter extends BaseAdapter {

	Context context;
	List<String> videoValues;
	int width;
	LayoutInflater inflater;

	public StaVideoAdapter(Context context, List<String> videoValues) {
		super();
		this.context = context;
		this.videoValues = videoValues;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		width = displayMetrics.widthPixels; // width of the device
	}

	@Override
	public int getCount() {
		return videoValues.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridView = inflater.inflate(R.layout.ins_row_video, null);

        ImageView imageThumbnail = (ImageView) gridView
                .findViewById(R.id.gridImageVideo);

		Glide.with(context).load(videoValues.get(position)).into(imageThumbnail);


		ImageView share = gridView.findViewById(R.id.share);
		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				share( videoValues.get(position));
			}
		});

		ImageView delete = gridView.findViewById(R.id.delete);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new File(videoValues.get(position)).delete();
				delete(position);
				Toast.makeText(context, "Video Delete Successfully!!!", Toast.LENGTH_SHORT).show();
			}
		});

		gridView.setLayoutParams(new GridView.LayoutParams((width *460/1080),
				(width *460/1080)));
		return gridView;
	}

	public void delete(int position) {
		videoValues.remove(position);
		notifyDataSetChanged();
	}

	void share(String path) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		share.setType("video/*");
		Uri photoURI = FileProvider.getUriForFile(
				context.getApplicationContext(),
				context.getApplicationContext()
						.getPackageName() + ".provider", new File(path));
		share.putExtra(Intent.EXTRA_STREAM,
				photoURI);
		context.startActivity(Intent.createChooser(share, "Share via"));
	}
}
