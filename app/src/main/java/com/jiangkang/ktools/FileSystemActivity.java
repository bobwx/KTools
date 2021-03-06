package com.jiangkang.ktools;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.jiangkang.tools.utils.FileUtils;
import com.jiangkang.tools.utils.ToastUtils;
import com.jiangkang.tools.widget.KDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jiangkang
 */
public class FileSystemActivity extends AppCompatActivity {

    private static final String TAG = FileSystemActivity.class.getSimpleName();
    @BindView(R.id.btn_get_general_file_path)
    Button btnGetGeneralFilePath;
    @BindView(R.id.btn_get_img_from_assets)
    Button btnGetImgFromAssets;
    @BindView(R.id.btn_get_audio_from_assets)
    Button btnGetAudioFromAssets;
    @BindView(R.id.btn_get_json_from_assets)
    Button btnGetJsonFromAssets;
    @BindView(R.id.btn_create_file)
    Button btnCreateFile;
    @BindView(R.id.btn_create_dir)
    Button btnCreateDir;
    @BindView(R.id.btn_traverse)
    Button btnTraverse;
    @BindView(R.id.btn_zip_files)
    Button btnZipFiles;
    @BindView(R.id.btn_unzip_files)
    Button btnUnzipFiles;
    @BindView(R.id.btn_read_json_from_file)
    Button btnReadJsonFromFile;
    @BindView(R.id.btn_store_json_to_file)
    Button btnStoreJsonToFile;
    @BindView(R.id.btn_parse_xml_file)
    Button btnParseXmlFile;
    @BindView(R.id.btn_get_file_size)
    Button btnGetFileSize;
    @BindView(R.id.btn_get_dir_size)
    Button btnGetDirSize;
    @BindView(R.id.btn_delete_file)
    Button btnDeleteFile;
    @BindView(R.id.btn_delete_dir)
    Button btnDeleteDir;
    @BindView(R.id.btn_encrypt_file)
    Button btnEncryptFile;
    @BindView(R.id.btn_decrypt_file)
    Button btnDecryptFile;
    @BindView(R.id.btn_hide_file)
    Button mBtnHideFile;

    ProgressDialog mProgressDialog;

    private AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_system);
        ButterKnife.bind(this);
        assetManager = getAssets();
        mProgressDialog = new ProgressDialog(this);
    }


    @OnClick(R.id.btn_get_general_file_path)
    public void onBtnGetGeneralFilePathClicked() {
        StringBuilder builder = new StringBuilder();
        builder.append("context.getExternalCacheDir():\n  --" + getExternalCacheDir().getAbsolutePath() + "\n");
        builder.append("context.getCacheDir():\n  --" + getCacheDir().getAbsolutePath() + "\n");
        builder.append("context.getExternalFilesDir(Environment.DIRECTORY_PICTURES):\n  --" + getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "\n");
        builder.append("context.getFilesDir():\n  --" + getFilesDir().getAbsolutePath() + "\n");
        KDialog.showMsgDialog(this, builder.toString());
    }

    @OnClick(R.id.btn_get_img_from_assets)
    public void onBtnGetImgFromAssetsClicked() {
        try {
            InputStream inputStream = assetManager.open("img/dog.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            KDialog.showImgInDialog(this, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_get_audio_from_assets)
    public void onBtnGetAudioFromAssetsClicked() {
        try {
            AssetFileDescriptor descriptor = FileUtils.getAssetFileDescription("music/baiyemeng.mp3");
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    showPlayerDialog(mp);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_get_json_from_assets)
    public void onBtnGetJsonFromAssetsClicked() {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(assetManager.open("json/demo.json"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String jsonStr = builder.toString();
            JSONObject json = new JSONObject(jsonStr);
            KDialog.showMsgDialog(this, json.toString(4));
        } catch (IOException e) {
        } catch (JSONException e) {
        }finally {
            if (inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.btn_create_file)
    public void onBtnCreateFileClicked() {
        boolean isSuccess = FileUtils.createFile("test.txt", Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ktools");
        ToastUtils.showShortToast(isSuccess ? "test.txt在ktools文件夹下创建成功" : "创建失败");
    }

    @OnClick(R.id.btn_create_dir)
    public void onBtnCreateDirClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_traverse)
    public void onBtnTraverseClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_zip_files)
    public void onBtnZipFilesClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_unzip_files)
    public void onBtnUnzipFilesClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_read_json_from_file)
    public void onBtnReadJsonFromFileClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_store_json_to_file)
    public void onBtnStoreJsonToFileClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_parse_xml_file)
    public void onBtnParseXmlFileClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_get_file_size)
    public void onBtnGetFileSizeClicked() {
        FileUtils.createFile("demo.txt",Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ktools");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ktools","demo.txt");
        FileUtils.writeStringToFile("这只是一个测试文件，测试文件",file,false);
        if(file.exists() && file.isFile()){
            try {
                FileInputStream fis = new FileInputStream(file);
                long size = fis.available();
                ToastUtils.showShortToast("size = " + size  + "B");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            ToastUtils.showShortToast("文件不存在");
        }
    }

    @OnClick(R.id.btn_get_dir_size)
    public void onBtnGetDirSizeClicked() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                showProgressDialog();
                long size = FileUtils.getFolderSize(Environment.getExternalStorageDirectory().getAbsolutePath());
                closeProgressDialog();
                KDialog.showMsgDialog(FileSystemActivity.this,"存储器根目录大小为" + String.valueOf(size / (1024 * 1024)) + "M");
            }
        });
    }

    @OnClick(R.id.btn_delete_file)
    public void onBtnDeleteFileClicked() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ktools",
                "test.txt");
        if (!file.exists()){
            ToastUtils.showShortToast("要删除的文件不存在");
        }else {
            boolean isDone = file.delete();
            ToastUtils.showShortToast(isDone? "删除成功":"删除失败");
        }

    }

    @OnClick(R.id.btn_delete_dir)
    public void onBtnDeleteDirClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_encrypt_file)
    public void onBtnEncryptFileClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }

    @OnClick(R.id.btn_decrypt_file)
    public void onBtnDecryptFileClicked() {
        ToastUtils.showShortToast("暂时还没有实现");
    }


    private void showPlayerDialog(final MediaPlayer player) {
        final int duration = player.getDuration();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_music_player, null);
        final SeekBar seekBar = view.findViewById(R.id.seek_bar_music);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //此处干扰正常播放，声音抖动
                int max = seekBar.getMax();
                int destPosition = progress * duration / max;
                player.seekTo(destPosition);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        view.findViewById(R.id.iv_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });
        view.findViewById(R.id.iv_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });
        view.findViewById(R.id.iv_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentPosition = player.getCurrentPosition();
                        int progress = currentPosition * 100 / duration;
                        seekBar.setProgress(progress);
                    }
                });
            }
        }, 0, 100);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("播放器")
                .setView(view)
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    @OnClick(R.id.btn_hide_file)
    public void onBtnHideFileClicked() {
        //隐藏文件或者文件夹只需要将文件名前面加一个‘.’号
        boolean isSuccess = FileUtils.hideFile(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ktools", "test.txt");
        ToastUtils.showShortToast(isSuccess ? "隐藏成功" : "隐藏失败");
    }


    private void showProgressDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setMessage("正在查询中，请稍等....");
                mProgressDialog.show();
            }
        });

    }


    private void closeProgressDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null && mProgressDialog.isShowing()){
                    mProgressDialog.hide();
                }
            }
        });
    }
}
