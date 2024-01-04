package com.banrossyn.socialsaver.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.banrossyn.socialsaver.R;
import com.banrossyn.socialsaver.adapter.CleanerAdapter;
import com.banrossyn.socialsaver.model.CleanerFileModel;
import com.banrossyn.socialsaver.util.SharedPrefs;
import com.banrossyn.socialsaver.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CleanListFragment extends Fragment implements CleanerAdapter.OnCheckboxListener {
    String category, isSent;
    LinearLayout delete;
    TextView txt;

    File file;
    ArrayList<CleanerFileModel> filesToDelete = new ArrayList<>();
    CleanerAdapter mAdapter;
    LayoutManager mLayoutManager;
    String path;
    RecyclerView recyclerView;
    ArrayList<CleanerFileModel> statusImageList = new ArrayList<>();
    CheckBox selectAll;

    ConstraintLayout loaderLay, emptyLay;
    LinearLayout sAccessBtn;
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 1010;

    public static CleanListFragment newInstance(String category, String path, String isSent) {
        CleanListFragment cleanListFragment = new CleanListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        bundle.putString("category", category);
        bundle.putString("isSent", isSent);
        cleanListFragment.setArguments(bundle);
        return cleanListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clean_list, container, false);
        if (getArguments() != null) {
            this.path = getArguments().getString("path");
            this.category = getArguments().getString("category");
            this.isSent = getArguments().getString("isSent");
        }
        loaderLay = view.findViewById(R.id.loaderLay);
        emptyLay = view.findViewById(R.id.emptyLay);
        this.recyclerView = view.findViewById(R.id.recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.mLayoutManager = new GridLayoutManager(getActivity(), 3);
        this.recyclerView.setLayoutManager(this.mLayoutManager);

        this.txt = view.findViewById(R.id.txt);
        this.delete = view.findViewById(R.id.delete);
        this.delete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (!filesToDelete.isEmpty()) {
                    new AlertDialog.Builder(getContext())
                            .setMessage("Are you sure , You want to delete selected files?")
                            .setCancelable(true)
                            .setNegativeButton("Yes", (dialogInterface, i) -> {
                                new deleteAll().execute();
                            })
                            .setPositiveButton("No", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                }
            }
        });

        selectAll = view.findViewById(R.id.selectAll);
        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



                if (!compoundButton.isPressed()) {
                    return;
                }

                filesToDelete.clear();

                for (int i = 0; i < statusImageList.size(); i++) {
                    if (!statusImageList.get(i).selected) {
                        b = true;
                        break;
                    }
                }

                if (b) {
                    for (int i = 0; i < statusImageList.size(); i++) {
                        statusImageList.get(i).selected = true;
                        filesToDelete.add(statusImageList.get(i));
                    }
                    selectAll.setChecked(true);
                } else {
                    for (int i = 0; i < statusImageList.size(); i++) {
                        statusImageList.get(i).selected = false;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        sAccessBtn = view.findViewById(R.id.sAccessBtn);
        sAccessBtn.setOnClickListener(v -> {


            StorageManager sm = (StorageManager) getActivity().getSystemService(Context.STORAGE_SERVICE);

            String statusDir = path;

            Intent intent = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");

                String scheme = uri.toString();

                scheme = scheme.replace("/root/", "/document/");

                scheme += "%3A" + statusDir;

                uri = Uri.parse(scheme);

                intent.putExtra("android.provider.extra.INITIAL_URI", uri);
            } else {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse("content://com.android.externalstorage.documents/document/primary%3A" + statusDir));
            }


            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

            startActivityForResult(intent, REQUEST_ACTION_OPEN_DOCUMENT_TREE);

        });

        if (Utils.iswApp && this.category.equals(Utils.IMAGE)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWAImgSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWAImgTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (!Utils.iswApp && this.category.equals(Utils.IMAGE)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWBImgSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWBImgTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (Utils.iswApp && this.category.equals(Utils.VIDEO)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWAVideoSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWAVideoTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (!Utils.iswApp && this.category.equals(Utils.VIDEO)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWBVideoSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWBVideoTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (Utils.iswApp && this.category.equals(Utils.DOCUMENT)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWADocSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWADocTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (!Utils.iswApp && this.category.equals(Utils.DOCUMENT)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWBDocSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWBDocTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (Utils.iswApp && this.category.equals(Utils.AUDIO)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWAAudioSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWAAudioTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (!Utils.iswApp && this.category.equals(Utils.AUDIO)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWBAudioSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWBAudioTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (Utils.iswApp && this.category.equals(Utils.GIF)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWAGifSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWAGifTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        } else if (!Utils.iswApp && this.category.equals(Utils.GIF)) {
            if (this.isSent.equals("yes")) {
                if (!SharedPrefs.getWBGifSendTree(getActivity()).equals("")) {
                    populateGrid();
                }
            } else {
                if (!SharedPrefs.getWBGifTree(getActivity()).equals("")) {
                    populateGrid();
                }
            }

        }

        return view;
    }

    class deleteAll extends AsyncTask<Void, Void, Void>{
        AlertDialog alertDialog;
        int success = -1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = com.banrossyn.socialsaver.util.Utils.loadingPopup(getActivity());
            alertDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<CleanerFileModel> deletedFiles = new ArrayList<>();

            for (CleanerFileModel details : filesToDelete) {
                DocumentFile fromTreeUri = DocumentFile.fromSingleUri(getActivity(), Uri.parse(details.getFilePath()));
                if (fromTreeUri.exists()) {
                    if (fromTreeUri.delete()) {
                        deletedFiles.add(details);
                        if (success == 0) {
                            break;
                        }
                        success = 1;
                    } else {
                        success = 0;
                    }
                } else {
                    success = 0;
                }
            }

            filesToDelete.clear();
            for (CleanerFileModel deletedFile : deletedFiles) {
                statusImageList.remove(deletedFile);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            mAdapter.notifyDataSetChanged();
            if (success == 0) {
                Toast.makeText(CleanListFragment.this.getContext(), "Couldn't delete some files", Toast.LENGTH_SHORT).show();
            } else if (success == 1) {
                Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_SHORT).show();
            }
            txt.setText(R.string.delete_items_blank);
            txt.setTextColor(getResources().getColor(R.color.h_btn_text_color));
            selectAll.setChecked(false);
            alertDialog.dismiss();
        }
    }

    loadDataAsync async;

    public void populateGrid() {
        async = new loadDataAsync();
        async.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (async != null) {
            async.cancel(true);
        }
    }

    class loadDataAsync extends AsyncTask<Void, Void, Void> {
        DocumentFile[] allFiles;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loaderLay.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            sAccessBtn.setVisibility(View.GONE);
            emptyLay.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            allFiles = null;
            statusImageList = new ArrayList<>();
            allFiles = getFromSdcard();
            for (int i = 0; i < allFiles.length; i++) {
                if (!allFiles[i].getUri().toString().contains(".nomedia") && !allFiles[i].isDirectory()) {
                    statusImageList.add(new CleanerFileModel(allFiles[i].getUri().toString(),
                            allFiles[i].getName(), String.valueOf(allFiles[i].length())));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new Handler().postDelayed(() -> {
                if (getActivity() != null) {
//                    Collections.reverse(statusImageList);
                    mAdapter = new CleanerAdapter(getActivity(), statusImageList, category, CleanListFragment.this);
                    recyclerView.setAdapter(mAdapter);
                    loaderLay.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                if (statusImageList == null || statusImageList.size() == 0) {
                    emptyLay.setVisibility(View.VISIBLE);
                } else {
                    emptyLay.setVisibility(View.GONE);
                }

            }, 300);
        }
    }

    private DocumentFile[] getFromSdcard() {
        String treeUri = "";

        if (Utils.iswApp && this.category.equals(Utils.IMAGE)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWAImgSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWAImgTree(getActivity());
            }
        } else if (!Utils.iswApp && this.category.equals(Utils.IMAGE)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWBImgSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWBImgTree(getActivity());
            }
        } else if (Utils.iswApp && this.category.equals(Utils.VIDEO)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWAVideoSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWAVideoTree(getActivity());
            }
        } else if (!Utils.iswApp && this.category.equals(Utils.VIDEO)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWBVideoSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWBVideoTree(getActivity());
            }
        } else if (Utils.iswApp && this.category.equals(Utils.DOCUMENT)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWADocSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWADocTree(getActivity());
            }
        } else if (!Utils.iswApp && this.category.equals(Utils.DOCUMENT)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWBDocSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWBDocTree(getActivity());
            }
        } else if (Utils.iswApp && this.category.equals(Utils.AUDIO)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWAAudioSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWAAudioTree(getActivity());
            }
        } else if (!Utils.iswApp && this.category.equals(Utils.AUDIO)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWBAudioSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWBAudioTree(getActivity());
            }
        } else if (Utils.iswApp && this.category.equals(Utils.GIF)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWAGifSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWAGifTree(getActivity());
            }
        } else if (!Utils.iswApp && this.category.equals(Utils.GIF)) {
            if (this.isSent.equals("yes")) {
                treeUri = SharedPrefs.getWBGifSendTree(getActivity());
            } else {
                treeUri = SharedPrefs.getWBGifTree(getActivity());
            }
        }

        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(requireContext().getApplicationContext(), Uri.parse(treeUri));
        if (fromTreeUri != null && fromTreeUri.exists() && fromTreeUri.isDirectory()
                && fromTreeUri.canRead() && fromTreeUri.canWrite()) {

            return fromTreeUri.listFiles();
        } else {
            return null;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ACTION_OPEN_DOCUMENT_TREE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
//            Log.e("onActivityResult: ", "" + data.getData());
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    requireContext().getContentResolver()
                            .takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (Utils.iswApp && this.category.equals(Utils.IMAGE)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWAImgSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWAImgTree(getActivity(), uri.toString());
                }
            } else if (!Utils.iswApp && this.category.equals(Utils.IMAGE)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWBImgSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWBImgTree(getActivity(), uri.toString());
                }
            } else if (Utils.iswApp && this.category.equals(Utils.VIDEO)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWAVideoSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWAVideoTree(getActivity(), uri.toString());
                }
            } else if (!Utils.iswApp && this.category.equals(Utils.VIDEO)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWBVideoSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWBVideoTree(getActivity(), uri.toString());
                }
            } else if (Utils.iswApp && this.category.equals(Utils.DOCUMENT)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWADocSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWADocTree(getActivity(), uri.toString());
                }
            } else if (!Utils.iswApp && this.category.equals(Utils.DOCUMENT)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWBDocSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWBDocTree(getActivity(), uri.toString());
                }
            } else if (Utils.iswApp && this.category.equals(Utils.AUDIO)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWAAudioSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWAAudioTree(getActivity(), uri.toString());
                }
            } else if (!Utils.iswApp && this.category.equals(Utils.AUDIO)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWBAudioSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWBAudioTree(getActivity(), uri.toString());
                }
            } else if (Utils.iswApp && this.category.equals(Utils.GIF)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWAGifSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWAGifTree(getActivity(), uri.toString());
                }
            } else if (!Utils.iswApp && this.category.equals(Utils.GIF)) {
                if (this.isSent.equals("yes")) {
                    SharedPrefs.setWBGifSendTree(getActivity(), uri.toString());
                } else {
                    SharedPrefs.setWBGifTree(getActivity(), uri.toString());
                }
            }

            populateGrid();
        }

    }

    @Override
    public void onCheckboxListener(View view, List<CleanerFileModel> updatedFiles) {
        filesToDelete.clear();
        for (CleanerFileModel details : updatedFiles) {
            if (details.isSelected()) {
                filesToDelete.add(details);
            }
        }
        if (filesToDelete.size() == statusImageList.size()) {
            selectAll.setChecked(true);
        }
        if (!filesToDelete.isEmpty()) {
            long totalFileSize = 0;

            for (CleanerFileModel details : filesToDelete) {
//                totalFileSize += new File(details.getFilePath()).length();
                totalFileSize += Integer.parseInt(details.getSize());
//                totalFileSize++;
            }
            String size = Formatter.formatShortFileSize(getActivity(), totalFileSize);
            txt.setText("Delete (" + size + ")");
//            txt.setText("Delete Selected Items (" + totalFileSize + ")");
            this.txt.setTextColor(Color.parseColor("#5AA061"));
            return;
        }
        this.txt.setText(R.string.delete_items_blank);
        txt.setTextColor(getResources().getColor(R.color.h_btn_text_color));
        selectAll.setChecked(false);
    }
}
