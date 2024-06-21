//package com.android.fuze_music_player;
//
//// Các import cần thiết
//import static com.android.fuze_music_player.MainActivity.repeatBoolean;
//import static com.android.fuze_music_player.MainActivity.shuffleBoolean;
//import static com.android.fuze_music_player.MainActivity.songModels;
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.RadialGradient;
//import android.graphics.drawable.GradientDrawable;
//import android.media.MediaMetadataRetriever;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.palette.graphics.Palette;
//import com.bumptech.glide.Glide;
//import com.android.fuze_music_player.model.SongModel;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import java.util.ArrayList;
//import java.util.Random;
//
//public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
//
//    // Khai báo các biến giao diện
//    TextView song_name, song_artist, duration_played, duration_total;
//    ImageView cover_art;
//    ImageButton skipNextBtn, skipPreviousBtn, repeatBtn, shuffleBtn, backBtn;
//    FloatingActionButton playPauseBtn;
//    SeekBar seekBar;
//    int position = -1;
//    static ArrayList<SongModel> listSongs = new ArrayList<>();
//    static Uri uri;
//    static MediaPlayer mediaPlayer;
//    private Handler handler = new Handler();
//    private Thread playThread, prevThread, nextThread;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_player);
//        initViews(); // Khởi tạo các view
//        getIntentMethod(); // Lấy dữ liệu từ Intent
//
//        // Thiết lập tên bài hát và nghệ sĩ
//        song_name.setText(listSongs.get(position).getTitle());
//        song_artist.setText(listSongs.get(position).getArtist());
//
//        // Đặt listener cho MediaPlayer khi hoàn thành bài hát
//        mediaPlayer.setOnCompletionListener(this);
//
//        // Cập nhật SeekBar
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // Nếu MediaPlayer đang hoạt động và người dùng thay đổi SeekBar
//                if (mediaPlayer != null && fromUser) {
//                    mediaPlayer.seekTo(progress * 1000); // Chuyển MediaPlayer đến vị trí tương ứng với SeekBar
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // Không làm gì khi bắt đầu kéo SeekBar
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // Không làm gì khi dừng kéo SeekBar
//            }
//        });
//
//        // Cập nhật thời gian phát nhạc hiện tại
//        PlayerActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mediaPlayer != null) {
//                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000; // Lấy vị trí hiện tại của MediaPlayer
//                    seekBar.setProgress(mCurrentPosition); // Cập nhật SeekBar
//                    duration_played.setText(formattedTime(mCurrentPosition)); // Cập nhật thời gian đã phát
//                }
//                handler.postDelayed(this, 1000); // Lặp lại mỗi giây
//            }
//        });
//
//        // Thiết lập hành động cho nút Shuffle
//        shuffleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (shuffleBoolean) {
//                    shuffleBoolean = false;
//                    shuffleBtn.setImageResource(R.drawable.shuffle_24dp); // Đổi icon nút Shuffle
//                } else {
//                    shuffleBoolean = true;
//                    shuffleBtn.setImageResource(R.drawable.shuffle_24dp_active); // Đổi icon nút Shuffle
//                }
//            }
//        });
//
//        // Thiết lập hành động cho nút Repeat
//        repeatBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (repeatBoolean) {
//                    repeatBoolean = false;
//                    repeatBtn.setImageResource(R.drawable.repeat_24dp); // Đổi icon nút Repeat
//                } else {
//                    repeatBoolean = true;
//                    repeatBtn.setImageResource(R.drawable.repeat_24dp_active); // Đổi icon nút Repeat
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        playThreadBtn(); // Thiết lập sự kiện cho nút Play/Pause
//        nextThreadBtn(); // Thiết lập sự kiện cho nút Next
//        prevThreadBtn(); // Thiết lập sự kiện cho nút Previous
//        super.onResume();
//    }
//
//    // Thiết lập sự kiện cho nút Previous
//    private void prevThreadBtn() {
//        prevThread = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                skipPreviousBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        prevBtnClicked(); // Gọi hàm xử lý khi nút Previous được nhấn
//                    }
//                });
//            }
//        };
//        prevThread.start();
//    }
//
//    // Xử lý khi nút Previous được nhấn
//    private void prevBtnClicked() {
//        // Kiểm tra xem mediaPlayer có đang phát hay không
//        if (mediaPlayer.isPlaying()) {
//            // Dừng phát nhạc và giải phóng tài nguyên của mediaPlayer
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            // Nếu chế độ xáo trộn được bật và không phải lặp lại bài hát
//            if (shuffleBoolean && !repeatBoolean) {
//                // Lấy vị trí ngẫu nhiên trong danh sách bài hát
//                position = getRandom(listSongs.size() - 1);
//            } else if (!shuffleBoolean && !repeatBoolean) {
//                // Chuyển sang bài hát trước đó, nếu đang ở bài đầu tiên thì chuyển về bài cuối cùng
//                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
//            }
//            // Lấy đường dẫn URI của bài hát mới
//            uri = uri.parse(listSongs.get(position).getPath());
//            // Tạo mediaPlayer mới với bài hát mới
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            // Cập nhật metadata cho bài hát
//            metaData(uri);
//            // Cập nhật tên bài hát và tên nghệ sĩ lên giao diện người dùng
//            song_name.setText(listSongs.get(position).getTitle());
//            song_artist.setText(listSongs.get(position).getArtist());
//            // Đặt giá trị tối đa cho thanh seekBar bằng thời gian của bài hát (giây)
//            seekBar.setMax(mediaPlayer.getDuration() / 1000);
//            // Chạy một luồng trên giao diện người dùng để cập nhật tiến trình của thanh seekBar mỗi giây
//            PlayerActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mediaPlayer != null) {
//                        // Lấy vị trí hiện tại của bài hát và cập nhật thanh seekBar
//                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
//                        seekBar.setProgress(mCurrentPosition);
//                    }
//                    // Tiếp tục cập nhật mỗi giây
//                    handler.postDelayed(this, 1000);
//                }
//            });
//            // Đặt sự kiện khi bài hát kết thúc
//            mediaPlayer.setOnCompletionListener(this);
//            // Cập nhật nút play/pause thành nút pause
//            playPauseBtn.setBackgroundResource(R.drawable.pause_40dp);
//            // Bắt đầu phát nhạc
//            mediaPlayer.start();
//        } else {
//            // Dừng phát nhạc và giải phóng tài nguyên của mediaPlayer
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            // Nếu chế độ xáo trộn được bật và không phải lặp lại bài hát
//            if (shuffleBoolean && !repeatBoolean) {
//                // Lấy vị trí ngẫu nhiên trong danh sách bài hát
//                position = getRandom(listSongs.size() - 1);
//            } else if (!shuffleBoolean && !repeatBoolean) {
//                // Chuyển sang bài hát trước đó, nếu đang ở bài đầu tiên thì chuyển về bài cuối cùng
//                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
//            }
//            // Lấy đường dẫn URI của bài hát mới
//            uri = uri.parse(listSongs.get(position).getPath());
//            // Tạo mediaPlayer mới với bài hát mới
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            // Cập nhật metadata cho bài hát
//            metaData(uri);
//            // Cập nhật tên bài hát và tên nghệ sĩ lên giao diện người dùng
//            song_name.setText(listSongs.get(position).getTitle());
//            song_artist.setText(listSongs.get(position).getArtist());
//            // Đặt giá trị tối đa cho thanh seekBar bằng thời gian của bài hát (giây)
//            seekBar.setMax(mediaPlayer.getDuration() / 1000);
//            // Chạy một luồng trên giao diện người dùng để cập nhật tiến trình của thanh seekBar mỗi giây
//            PlayerActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mediaPlayer != null) {
//                        // Lấy vị trí hiện tại của bài hát và cập nhật thanh seekBar
//                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
//                        seekBar.setProgress(mCurrentPosition);
//                    }
//                    // Tiếp tục cập nhật mỗi giây
//                    handler.postDelayed(this, 1000);
//                }
//            });
//            // Đặt sự kiện khi bài hát kết thúc
//            mediaPlayer.setOnCompletionListener(this);
//            // Cập nhật nút play/pause thành nút play
//            playPauseBtn.setBackgroundResource(R.drawable.play_arrow_40dp);
//        }
//    }
//
//    // Thiết lập sự kiện cho nút Next
//    private void nextThreadBtn() {
//        // Tạo một Thread mới để xử lý sự kiện khi nút Next được nhấn
//        nextThread = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                // Đặt sự kiện click cho nút skipNextBtn
//                skipNextBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Gọi hàm xử lý khi nút Next được nhấn
//                        nextBtnClicked();
//                    }
//                });
//            }
//        };
//        // Bắt đầu Thread
//        nextThread.start();
//    }
//
//    // Xử lý khi nút Next được nhấn
//    private void nextBtnClicked() {
//        // Kiểm tra xem mediaPlayer có đang phát hay không
//        if (mediaPlayer.isPlaying()) {
//            // Dừng phát nhạc và giải phóng tài nguyên của mediaPlayer
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            // Nếu chế độ xáo trộn được bật và không phải lặp lại bài hát
//            if (shuffleBoolean && !repeatBoolean) {
//                // Lấy vị trí ngẫu nhiên trong danh sách bài hát
//                position = getRandom(listSongs.size() - 1);
//            } else if (!shuffleBoolean && !repeatBoolean) {
//                // Chuyển sang bài hát tiếp theo
//                position = ((position + 1) % listSongs.size());
//            }
//            // Lấy đường dẫn URI của bài hát mới
//            uri = uri.parse(listSongs.get(position).getPath());
//            // Tạo mediaPlayer mới với bài hát mới
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            // Cập nhật metadata cho bài hát
//            metaData(uri);
//            // Cập nhật tên bài hát và tên nghệ sĩ lên giao diện người dùng
//            song_name.setText(listSongs.get(position).getTitle());
//            song_artist.setText(listSongs.get(position).getArtist());
//            // Đặt giá trị tối đa cho thanh seekBar bằng thời gian của bài hát (giây)
//            seekBar.setMax(mediaPlayer.getDuration() / 1000);
//            // Chạy một luồng trên giao diện người dùng để cập nhật tiến trình của thanh seekBar mỗi giây
//            PlayerActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mediaPlayer != null) {
//                        // Lấy vị trí hiện tại của bài hát và cập nhật thanh seekBar
//                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
//                        seekBar.setProgress(mCurrentPosition);
//                    }
//                    // Tiếp tục cập nhật mỗi giây
//                    handler.postDelayed(this, 1000);
//                }
//            });
//            // Đặt sự kiện khi bài hát kết thúc
//            mediaPlayer.setOnCompletionListener(this);
//            // Cập nhật nút play/pause thành nút pause
//            playPauseBtn.setBackgroundResource(R.drawable.pause_40dp);
//            // Bắt đầu phát nhạc
//            mediaPlayer.start();
//        } else {
//            // Dừng phát nhạc và giải phóng tài nguyên của mediaPlayer
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            // Nếu chế độ xáo trộn được bật và không phải lặp lại bài hát
//            if (shuffleBoolean && !repeatBoolean) {
//                // Lấy vị trí ngẫu nhiên trong danh sách bài hát
//                position = getRandom(listSongs.size() - 1);
//            } else if (!shuffleBoolean && !repeatBoolean) {
//                // Chuyển sang bài hát tiếp theo
//                position = ((position + 1) % listSongs.size());
//            }
//            // Lấy đường dẫn URI của bài hát mới
//            uri = uri.parse(listSongs.get(position).getPath());
//            // Tạo mediaPlayer mới với bài hát mới
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            // Cập nhật metadata cho bài hát
//            metaData(uri);
//            // Cập nhật tên bài hát và tên nghệ sĩ lên giao diện người dùng
//            song_name.setText(listSongs.get(position).getTitle());
//            song_artist.setText(listSongs.get(position).getArtist());
//            // Đặt giá trị tối đa cho thanh seekBar bằng thời gian của bài hát (giây)
//            seekBar.setMax(mediaPlayer.getDuration() / 1000);
//            // Chạy một luồng trên giao diện người dùng để cập nhật tiến trình của thanh seekBar mỗi giây
//            PlayerActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mediaPlayer != null) {
//                        // Lấy vị trí hiện tại của bài hát và cập nhật thanh seekBar
//                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
//                        seekBar.setProgress(mCurrentPosition);
//                    }
//                    // Tiếp tục cập nhật mỗi giây
//                    handler.postDelayed(this, 1000);
//                }
//            });
//            // Đặt sự kiện khi bài hát kết thúc
//            mediaPlayer.setOnCompletionListener(this);
//            // Cập nhật nút play/pause thành nút play
//            playPauseBtn.setBackgroundResource(R.drawable.play_arrow_40dp);
//        }
//    }
//
//    // Lấy một vị trí ngẫu nhiên
//    private int getRandom(int i) {
//        Random random = new Random();
//        return random.nextInt(i + 1);
//    }
//
//    // Thiết lập sự kiện cho nút Play/Pause
//    private void playThreadBtn() {
//        // Tạo một Thread mới để xử lý sự kiện khi nút Play/Pause được nhấn
//        playThread = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                // Đặt sự kiện click cho nút playPauseBtn
//                playPauseBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Gọi hàm xử lý khi nút Play/Pause được nhấn
//                        playPauseBtnClicked();
//                    }
//                });
//            }
//        };
//        // Bắt đầu Thread
//        playThread.start();
//    }
//
//    // Xử lý khi nút Play/Pause được nhấn
//    private void playPauseBtnClicked() {
//        if (mediaPlayer.isPlaying()) {
//            // Đổi hình ảnh nút play/pause thành hình play
//            playPauseBtn.setImageResource(R.drawable.play_arrow_40dp);
//            // Tạm dừng phát nhạc
//            mediaPlayer.pause();
//        } else {
//            // Đổi hình ảnh nút play/pause thành hình pause
//            playPauseBtn.setImageResource(R.drawable.pause_40dp);
//            // Bắt đầu phát nhạc
//            mediaPlayer.start();
//        }
//
//        // Cập nhật giá trị tối đa cho thanh seekBar bằng thời gian của bài hát (giây)
//        seekBar.setMax(mediaPlayer.getDuration() / 1000);
//
//        // Chạy một luồng trên giao diện người dùng để cập nhật tiến trình của thanh seekBar mỗi giây
//        PlayerActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mediaPlayer != null) {
//                    // Lấy vị trí hiện tại của bài hát và cập nhật thanh seekBar
//                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
//                    seekBar.setProgress(mCurrentPosition);
//                }
//                // Tiếp tục cập nhật mỗi giây
//                handler.postDelayed(this, 1000);
//            }
//        });
//    }
//
//    // Định dạng thời gian từ giây sang phút:giây
//    private String formattedTime(int mCurrentPosition) {
//        // Biến chứa chuỗi thời gian đã định dạng
//        String totalout;
//        String totalNew;
//        // Tính toán số giây và số phút từ vị trí hiện tại
//        String seconds = String.valueOf(mCurrentPosition % 60);
//        String minutes = String.valueOf(mCurrentPosition / 60);
//        // Ghép chuỗi phút và giây thành định dạng "phút:giây"
//        totalout = minutes + ":" + seconds;
//        // Thêm số 0 vào trước số giây nếu số giây chỉ có một chữ số
//        totalNew = minutes + ":" + "0" + seconds;
//        // Kiểm tra độ dài của chuỗi giây để quyết định định dạng trả về
//        if (seconds.length() == 1) {
//            // Nếu số giây chỉ có một chữ số, trả về chuỗi định dạng có thêm số 0 ở trước
//            return totalNew;
//        } else {
//            // Nếu số giây có hai chữ số, trả về chuỗi định dạng ban đầu
//            return totalout;
//        }
//    }
//
//    // Lấy dữ liệu từ Intent và khởi tạo MediaPlayer
//    private void getIntentMethod() {
//        position = getIntent().getIntExtra("position", -1);
//        listSongs = songModels;
//        if (listSongs != null) {
//            playPauseBtn.setImageResource(R.drawable.pause_40dp);
//            uri = Uri.parse(listSongs.get(position).getPath());
//        }
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            mediaPlayer.start();
//        } else {
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            mediaPlayer.start();
//        }
//        seekBar.setMax(mediaPlayer.getDuration() / 1000);
//        metaData(uri);
//    }
//
//    // Khởi tạo các view
//    private void initViews() {
//        song_name = findViewById(R.id.song_name);
//        song_artist = findViewById(R.id.song_artist);
//        duration_played = findViewById(R.id.duration_played);
//        duration_total = findViewById(R.id.duration_total);
//        cover_art = findViewById(R.id.cover_art);
//        skipNextBtn = findViewById(R.id.skip_next);
//        skipPreviousBtn = findViewById(R.id.skip_previous);
//        repeatBtn = findViewById(R.id.repeat);
//        shuffleBtn = findViewById(R.id.shuffle);
//        backBtn = findViewById(R.id.back_btn);
//        playPauseBtn = findViewById(R.id.play_pause);
//        seekBar = findViewById(R.id.play_status);
//    }
//
//    // Lấy metadata từ bài hát và cập nhật giao diện
//    private void metaData(Uri uri) {
//        // Tạo đối tượng MediaMetadataRetriever để lấy thông tin metadata từ tệp âm thanh
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        // Đặt nguồn dữ liệu cho retriever là đường dẫn URI của tệp âm thanh
//        retriever.setDataSource(PlayerActivity.uri.toString());
//
//        // Lấy tổng thời gian của bài hát và chuyển đổi từ mili giây sang giây
//        int durationTotal = Integer.parseInt(listSongs.get(position).getDuration()) / 1000;
//        // Cập nhật thời gian tổng của bài hát lên giao diện người dùng
//        duration_total.setText(formattedTime(durationTotal));
//
//        // Lấy ảnh nhúng trong tệp âm thanh (nếu có)
//        byte[] art = retriever.getEmbeddedPicture();
//        Bitmap bitmap = null;
//        if (art != null) {
//            // Chuyển đổi mảng byte của ảnh nhúng thành đối tượng Bitmap
//            bitmap = BitmapFactory.decodeByteArray(art, 0, art.length);
//            // Gọi hàm thực hiện hiệu ứng ảnh cho cover art
//            ImageAnimation(this, cover_art, bitmap);
//
//            // Tạo đối tượng Palette để lấy màu sắc chủ đạo từ ảnh nhúng
//            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//                @Override
//                public void onGenerated(@Nullable Palette palette) {
//                    // Lấy màu sắc chủ đạo từ Palette
//                    Palette.Swatch swatch = palette.getDominantSwatch();
//                    if (swatch != null) {
//                        // Tìm các View cần thay đổi giao diện
//                        ImageView imgGradient = findViewById(R.id.imgGradient);
//                        FloatingActionButton play_pause = findViewById(R.id.play_pause);
//                        imgGradient.setBackgroundResource(R.drawable.gradient_background);
//
//                        // Tạo GradientDrawable với màu chính từ swatch
//                        GradientDrawable gradientDrawable = new GradientDrawable(
//                                GradientDrawable.Orientation.TOP_BOTTOM,
//                                new int[]{swatch.getRgb(), 0x00000000}
//                        );
//                        // Đặt GradientDrawable làm nền cho imgGradient và play_pause
//                        imgGradient.setBackground(gradientDrawable);
//                        play_pause.setBackground(gradientDrawable);
//
//                        // Đặt màu nền của play_pause để phù hợp với màu chính từ swatch
//                        play_pause.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
//                    }
//                }
//            });
//        } else {
//            // Nếu không có ảnh nhúng, sử dụng ảnh mặc định
//            Glide.with(this)
//                    .asBitmap()
//                    .load(R.drawable.music_note)
//                    .into(cover_art);
//            // Tìm các View cần thay đổi giao diện
//            ImageView imgGradient = findViewById(R.id.imgGradient);
//            FloatingActionButton play_pause = findViewById(R.id.play_pause);
//            imgGradient.setBackgroundResource(R.drawable.gradient_background);
//            play_pause.setBackgroundResource(R.drawable.gradient_background);
//
//            // Đặt màu nền mặc định cho play_pause khi không tìm thấy ảnh nhúng
//            play_pause.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//        }
//    }
//
//    // Thực hiện hiệu ứng ảnh khi chuyển đổi
//    public void ImageAnimation(Context context, @NonNull ImageView imageView, Bitmap bitmap) {
//        Animation animOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
//        Animation animIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
//        animOut.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Glide.with(context).load(bitmap).into(imageView);
//                animIn.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//                    }
//                });
//                imageView.startAnimation(animIn);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        imageView.startAnimation(animOut);
//    }
//
//    // Xử lý sự kiện khi một bài hát kết thúc
//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        // Gọi hàm xử lý khi nút Next được nhấn
//        nextBtnClicked();
//
//        // Kiểm tra mediaPlayer có khác null hay không
//        if (mediaPlayer != null) {
//            // Tạo mới mediaPlayer với bài hát tiếp theo
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            // Bắt đầu phát nhạc
//            mediaPlayer.start();
//            // Đặt sự kiện khi bài hát kết thúc
//            mediaPlayer.setOnCompletionListener(this);
//        }
//    }
//}