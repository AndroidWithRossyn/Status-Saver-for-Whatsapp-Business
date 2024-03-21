package com.kessi.statussaver.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kessi.statussaver.R;
import com.kessi.statussaver.fragments.Utils;
import com.kessi.statussaver.model.CleanerFileModel;

import java.io.File;
import java.util.List;

public class WallCleanerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity activity;
//    File file;
    List<CleanerFileModel> mData;
    public OnCheckboxListener onCheckboxListener;
    String category;
    int voice = 1;
    int wallpaper = 2;

    public WallCleanerAdapter(Activity activity2, List<CleanerFileModel> jData, String category2, OnCheckboxListener onCheckboxListener2) {
        this.mData = jData;
        this.activity = activity2;
        this.category = category2;
        this.onCheckboxListener = onCheckboxListener2;
    }


    @Override
    public int getItemViewType(int position) {
        switch (category) {
            default:
            case Utils.VOICE:
                return voice;
            case Utils.WALLPAPER:
                return wallpaper;
        }
    }

    public interface OnCheckboxListener {
        void onCheckboxListener(View view, List<CleanerFileModel> list);
    }

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements OnClickListener {
        CheckBox checkBox;
        ImageView imagevi;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imagevi = itemView.findViewById(R.id.imageView);
            this.imagevi.setOnClickListener(this);
            this.checkBox = itemView.findViewById(R.id.checkbox);
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(((CleanerFileModel) WallCleanerAdapter.this.mData.get(getAdapterPosition())).getFilePath()), "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                WallCleanerAdapter.this.activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(activity, "No Apps in your device for view this type of file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class ViewHolderAudios extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements OnClickListener {
        public CheckBox checkBox;
        public ImageView imageView;
        public TextView name;
        public TextView size;

        public ViewHolderAudios(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.size = (TextView) itemView.findViewById(R.id.size);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.imageView.setOnClickListener(this);
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(((CleanerFileModel) mData.get(getAdapterPosition())).getFilePath()), "audio/*");
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(activity, "No Apps in your device for view this type of file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == wallpaper) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaner_item, parent, false));
        }
        return new ViewHolderAudios(LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaner_audio_item, parent, false));
    }

    public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        CleanerFileModel jpast = this.mData.get(position);
//        this.file = new File(jpast.getFilePath());

        if (getItemViewType(position) == voice) {
            ViewHolderAudios viewHolder4 = (ViewHolderAudios) holder;

            viewHolder4.imageView.setImageResource(R.drawable.voice);

            viewHolder4.name.setText(jpast.getFileName());
            viewHolder4.size.setText(jpast.getSize());
            viewHolder4.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((CleanerFileModel) mData.get(position)).setSelected(isChecked);
                    if (onCheckboxListener != null) {
                        onCheckboxListener.onCheckboxListener(buttonView, mData);
                    }
                }
            });
            if (jpast.isSelected()) {
                viewHolder4.checkBox.setChecked(true);
            } else {
                viewHolder4.checkBox.setChecked(false);
            }
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;

            Glide.with(this.activity).load(jpast.getFilePath()).into(viewHolder.imagevi);
            viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((CleanerFileModel) WallCleanerAdapter.this.mData.get(position)).setSelected(isChecked);
                    if (WallCleanerAdapter.this.onCheckboxListener != null) {
                        WallCleanerAdapter.this.onCheckboxListener.onCheckboxListener(buttonView, WallCleanerAdapter.this.mData);
                    }
                }
            });
            if (jpast.isSelected()) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }
        }
    }

    public int getItemCount() {
        return this.mData.size();
    }
}
