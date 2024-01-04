package com.banrossyn.socialsaver.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.banrossyn.socialsaver.PreviewActivity;
import com.banrossyn.socialsaver.R;
import com.banrossyn.socialsaver.model.StatusModel;


import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class StaPhotoAdapter extends BaseAdapter{

	Fragment context;
	List<StatusModel> arrayList;
	int width;
	LayoutInflater inflater;
	public OnCheckboxListener onCheckboxListener;

	public StaPhotoAdapter(Fragment context, List<StatusModel> arrayList, OnCheckboxListener onCheckboxListener) {
		this.context = context;
		this.arrayList = arrayList;

		inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		width = displayMetrics.widthPixels; // width of the device

		this.onCheckboxListener = onCheckboxListener;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {

        View grid = inflater.inflate(R.layout.ins_row_video, null);

		ImageView play = grid.findViewById(R.id.play);

		if (isVideoFile(arrayList.get(arg0).getFilePath())) {
			play.setVisibility(View.VISIBLE);
		}else {
			play.setVisibility(View.GONE);
		}


		ImageView share = grid.findViewById(R.id.shareIV);
		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				share( arrayList.get(arg0).getFilePath());
			}
		});


//		grid.setLayoutParams(new GridView.LayoutParams((width*460/1080),
//				(width*460/1080)));

		ImageView imageView = (ImageView) grid.findViewById(R.id.gridImageVideo);
		
		
		Glide.with(context.getActivity()).load(arrayList.get(arg0).getFilePath()).into(imageView);


		CheckBox checkbox = grid.findViewById(R.id.checkbox);
		checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				((StatusModel) arrayList.get(arg0)).setSelected(isChecked);
				if (onCheckboxListener != null) {
					onCheckboxListener.onCheckboxListener(buttonView, arrayList);
				}
			}
		});
		if (arrayList.get(arg0).isSelected()) {
			checkbox.setChecked(true);
		} else {
			checkbox.setChecked(false);
		}

		grid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.e("click", "click");
//				Intent intent = new Intent(context.getActivity(), PreviewActivity.class);
				Intent intent = new Intent(context.requireActivity(), PreviewActivity.class);
				intent.putParcelableArrayListExtra("images", (ArrayList<? extends Parcelable>) arrayList);
				intent.putExtra("position", arg0);
				intent.putExtra("statusdownload", "download");
				context.startActivityForResult(intent, 10);

			}
		});

		return grid;
	}

	public void delete(int position) {
		arrayList.remove(position);
		notifyDataSetChanged();
	}

	public interface OnCheckboxListener {
		void onCheckboxListener(View view, List<StatusModel> list);
	}

	void share(String path) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		share.setType("video/*");
		Uri photoURI = FileProvider.getUriForFile(
				context.getActivity().getApplicationContext(),
				context.getActivity().getApplicationContext()
						.getPackageName() + ".provider", new File(path));
		share.putExtra(Intent.EXTRA_STREAM,
				photoURI);
		context.startActivity(Intent.createChooser(share, "Share via"));

	}

	public boolean isVideoFile(String path) {
		String mimeType = URLConnection.guessContentTypeFromName(path);
		return mimeType != null && mimeType.startsWith("video");
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("MyAdapter", "onActivityResult");
	}
}
