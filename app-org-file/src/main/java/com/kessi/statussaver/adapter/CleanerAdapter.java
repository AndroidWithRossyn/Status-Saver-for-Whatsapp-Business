package com.kessi.statussaver.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.kessi.statussaver.R;
import com.kessi.statussaver.fragments.Utils;
import com.kessi.statussaver.model.CleanerFileModel;
import java.io.File;
import java.util.List;

public class CleanerAdapter extends Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    int audios = 3;
    int document = 4;
    int gif = 5;
    int images = 1;
    int videos = 2;
    int voice = 6;
    Activity activity;
    String category;
//    private File file;
    List<CleanerFileModel> mData;
    public OnCheckboxListener onCheckboxListener;

    public interface OnCheckboxListener {
        void onCheckboxListener(View view, List<CleanerFileModel> list);
    }

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements OnClickListener {
        public CheckBox checkBox;
        public ImageView imagevi;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imagevi = (ImageView) itemView.findViewById(R.id.imageView);
            this.imagevi.setOnClickListener(this);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(((CleanerFileModel) CleanerAdapter.this.mData.get(getAdapterPosition())).getFilePath()), "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                CleanerAdapter.this.activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText( CleanerAdapter.this.activity, "No Apps in your device for view this type of file.", Toast.LENGTH_LONG).show();
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
            intent.setDataAndType(Uri.parse(((CleanerFileModel) CleanerAdapter.this.mData.get(getAdapterPosition())).getFilePath()), "audio/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                CleanerAdapter.this.activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText( CleanerAdapter.this.activity, "No Apps in your device for view this type of file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class ViewHolderDocs extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements OnClickListener {
        public CheckBox checkBox;
        private ImageView imageView;
        public TextView name;
        public TextView size;

        public ViewHolderDocs(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.size = (TextView) itemView.findViewById(R.id.size);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.imageView.setOnClickListener(this);
        }

        public void onClick(View v) {
            CleanerAdapter cleanerAdapter = CleanerAdapter.this;
            cleanerAdapter.viewIntent(cleanerAdapter.activity, ((CleanerFileModel) CleanerAdapter.this.mData.get(getAdapterPosition())).getFilePath());
        }
    }

    public class ViewHolderVideos extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements OnClickListener {
        public CheckBox checkBox;
        public ImageView imagevi;

        public ViewHolderVideos(View itemView) {
            super(itemView);
            this.imagevi = (ImageView) itemView.findViewById(R.id.imageView);
            this.imagevi.setOnClickListener(this);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(((CleanerFileModel) CleanerAdapter.this.mData.get(getAdapterPosition())).getFilePath()), "video/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                CleanerAdapter.this.activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText( CleanerAdapter.this.activity, "No Apps in your device for view this type of file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public CleanerAdapter(FragmentActivity activity2, List<CleanerFileModel> jData, String category2, OnCheckboxListener onCheckboxListener2) {
        this.mData = jData;
        this.activity = activity2;
        this.category = category2;
        this.onCheckboxListener = onCheckboxListener2;
    }

    @Override
    public int getItemViewType(int position) {
        switch (category) {
            default:
            case Utils.IMAGE:
                return images;
            case Utils.VIDEO:
                return videos;
            case Utils.AUDIO:
                return audios;
            case Utils.VOICE:
                return voice;
            case Utils.GIF:
                return gif;
            case Utils.DOCUMENT:
                return document;
        }
    }

    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaner_item, parent, false));
        }
        if (viewType == 2 || viewType == 5) {
            return new ViewHolderVideos(LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaner_video_item, parent, false));
        }
        if (viewType == 4) {
            return new ViewHolderDocs(LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaner_doc_item, parent, false));
        }
        return new ViewHolderAudios(LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaner_audio_item, parent, false));
    }


    public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        CleanerFileModel jpast = this.mData.get(position);
//        this.file = new File(jpast.getFilePath());
        if (getItemViewType(position) == 1) {
            ViewHolder viewHolder = (ViewHolder) holder;
            Glide.with(this.activity).load(jpast.getFilePath()).into(viewHolder.imagevi);
            viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((CleanerFileModel) CleanerAdapter.this.mData.get(position)).setSelected(isChecked);
                    if (CleanerAdapter.this.onCheckboxListener != null) {
                        CleanerAdapter.this.onCheckboxListener.onCheckboxListener(buttonView, CleanerAdapter.this.mData);
                    }
                }
            });
            if (jpast.isSelected()) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }
        } else if (getItemViewType(position) == 2 || getItemViewType(position) == 5) {
            ViewHolderVideos viewHolder2 = (ViewHolderVideos) holder;
            Glide.with(this.activity).load(jpast.getFilePath()).into(viewHolder2.imagevi);
            viewHolder2.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((CleanerFileModel) CleanerAdapter.this.mData.get(position)).setSelected(isChecked);
                    if (CleanerAdapter.this.onCheckboxListener != null) {
                        CleanerAdapter.this.onCheckboxListener.onCheckboxListener(buttonView, CleanerAdapter.this.mData);
                    }
                }
            });
            if (jpast.isSelected()) {
                viewHolder2.checkBox.setChecked(true);
            } else {
                viewHolder2.checkBox.setChecked(false);
            }
        } else if (getItemViewType(position) == 4) {
            ViewHolderDocs viewHolder3 = (ViewHolderDocs) holder;
            viewHolder3.name.setText(jpast.getFileName());
            viewHolder3.size.setText(Formatter.formatShortFileSize(activity, Long.parseLong(jpast.getSize())));
            viewHolder3.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((CleanerFileModel) CleanerAdapter.this.mData.get(position)).setSelected(isChecked);
                    if (CleanerAdapter.this.onCheckboxListener != null) {
                        CleanerAdapter.this.onCheckboxListener.onCheckboxListener(buttonView, CleanerAdapter.this.mData);
                    }
                }
            });
            if (jpast.isSelected()) {
                viewHolder3.checkBox.setChecked(true);
            } else {
                viewHolder3.checkBox.setChecked(false);
            }
        } else if (getItemViewType(position) == 3 || getItemViewType(position) == 6) {
            ViewHolderAudios viewHolder4 = (ViewHolderAudios) holder;
            if (getItemViewType(position) == 3) {
                viewHolder4.imageView.setImageResource(R.drawable.audio);
            } else {
                viewHolder4.imageView.setImageResource(R.drawable.voice);
            }
            viewHolder4.name.setText(jpast.getFileName());
            viewHolder4.size.setText(Formatter.formatShortFileSize(activity, Long.parseLong(jpast.getSize())));
            viewHolder4.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((CleanerFileModel) CleanerAdapter.this.mData.get(position)).setSelected(isChecked);
                    if (CleanerAdapter.this.onCheckboxListener != null) {
                        CleanerAdapter.this.onCheckboxListener.onCheckboxListener(buttonView, CleanerAdapter.this.mData);
                    }
                }
            });
            if (jpast.isSelected()) {
                viewHolder4.checkBox.setChecked(true);
            } else {
                viewHolder4.checkBox.setChecked(false);
            }
        }
    }

    public int getItemCount() {
        return this.mData.size();
    }

    public void viewIntent(Activity activity2, String path) {
        String ext = fileExt(path);
        if (ext != null) {


            MimeTypeMap myMime = MimeTypeMap.getSingleton();
            Intent newIntent = new Intent(Intent.ACTION_VIEW);
//            Uri uri = FileProvider.getUriForFile(activity, activity.getApplicationContext()
//                    .getPackageName() + ".provider",new File(path));
            Uri uri = Uri.parse(path);

            Log.e("viewIntent: ", path+"======"+ ext);
            newIntent.setDataAndType(uri, myMime.getMimeTypeFromExtension(ext.substring(1)));
            newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                activity2.startActivity(newIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(activity2, "No Apps in your device for view this type of file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

}
