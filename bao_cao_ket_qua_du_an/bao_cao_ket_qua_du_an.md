# Báo Cáo Kết Quả Dự Án

## Thông Tin Chung
- **Tên Dự Án**: Ứng dụng nghe nhạc offline - Fuze Music Player
- **Thời Gian Phát Triển**: 6 tuần
- **Nhóm Thực Hiện**:
  - Thành viên 1: Nguyễn Đức Hiếu - trưởng nhóm
  - Thành viên 2: Vũ Tuấn Hoàng
  - Thành viên 3: Nguyễn Thành Đạt
  - Thành viên 4: Vũ Ngọc Khoa

## Tổng Quan Dự Án
- **Mục Tiêu Dự Án**:
Tạo ra 1 ứng dụng nghe nhạc offline, cung cấp cho người dùng một trải nghiệm nghe nhạc tiện lợi và không phụ thuộc vào kết nối internet. Với ứng dụng này, người dùng có thể tải về và lưu trữ các bài hát trực tiếp trên thiết bị của mình, sau đó nghe chúng mọi lúc mọi nơi mà không cần kết nối internet

Các tính năng: 
  - Đọc tất cả các file định dạng âm thanh như .mp3, .flac, .wav,...
  - Phát nhạc theo thứ tự, ngẫu nhiên hoặc lặp lại
  - Tự động quét tất cả các tệp âm thanh từ thư mục trong máy
  - Lưu bài hát yêu thích và tùy chỉnh danh sách phát nhạc
  - Xem danh sách bài hát, nghệ sĩ, album
  - Tìm kiếm bài hát theo từ khoá

- **Đối Tượng Người Dùng**:
  - Người yêu âm nhạc: Đối với những người đam mê âm nhạc, ứng dụng nghe nhạc offline sẽ là một công cụ lý tưởng để tận hưởng âm nhạc yêu thích mọi lúc mọi nơi. Họ có thể tải về và lưu trữ các bài hát từ các nghệ sĩ, ban nhạc hoặc thể loại âm nhạc khác nhau để thỏa mãn sở thích của mình.
  - Người dùng phổ thông: Những người mới tìm đến ứng dụng và thử sử dụng ứng dụng, hoặc ở những nơi không có internet, ứng dụng nghe nhạc offline giúp người dùng trách gián đoạn khi thưởng thức bài hát với các file âm nhạc được người dùng tải về sẵn


## Quá Trình Phát Triển
### Giai Đoạn Khảo Sát và Thu Thập Yêu Cầu
- **Yêu Cầu Người Dùng**:
  - Ứng dụng phải có giao diện đơn giản, dễ sử dụng.
  - Người dùng có thể nghe nhạc mà không cần kết nối internet.
  - Hỗ trợ quản lý các bài hát theo album, nghệ sĩ, và danh sách phát.
  - Cho phép tạo và quản lý danh sách phát cá nhân.
  - Hiển thị các bài hát mới thêm gần đây và lịch sử phát nhạc.
  - Cung cấp chức năng tìm kiếm nhanh các bài hát, album, và nghệ sĩ.
  - Cho phép người dùng thêm, xóa, và sắp xếp thứ tự bài hát trong danh sách phát.
  - Có thể phát nhạc trong nền và hiển thị điều khiển trên màn hình khóa.

- **Yêu Cầu Hệ Thống**:
  - Ứng dụng phải hoạt động ổn định trên các thiết bị Android từ phiên bản 5.0 trở lên.
  - Dung lượng ứng dụng nhẹ, không tiêu tốn nhiều tài nguyên hệ thống.
  - Đảm bảo bảo mật và quyền riêng tư của người dùng.
  - Hỗ trợ nhiều định dạng file nhạc như MP3, AAC, FLAC.
  - Tích hợp hệ thống lưu trữ và truy xuất dữ liệu cục bộ.


### Giai Đoạn Thiết Kế
- **Thiết Kế Giao Diện**:
  (Mô tả quá trình và kết quả thiết kế giao diện.)
- **Thiết Kế Kiến Trúc**:
  (Mô tả kiến trúc tổng thể của ứng dụng.)

### Giai Đoạn Lập Trình
- **Công Nghệ Sử Dụng**:
1.Ngôn ngữ lập trình: Java hoặc Kotlin.
2.Môi trường phát triển: Android Studio.
3.Thư viện và công cụ:
  - MediaPlayer: Để phát nhạc.  
  - RecyclerView: Để hiển thị danh sách bài hát, album, nghệ sĩ.
  - Glide: Để tải và hiển thị ảnh bìa album.
  - Gson: Để xử lý JSON.
- **Chức Năng Chính**:
  - Chức Năng Nghe Nhạc:
    - Phát, tạm dừng, tiếp tục, chuyển bài hát.
    - Hỗ trợ chế độ phát lặp lại, phát ngẫu nhiên.
  - Quản Lý Bài Hát:
    - Hiển thị danh sách bài hát, album, nghệ sĩ.
  - Hiển Thị Thông Tin:
    - Hiển thị các bài hát mới thêm.
    - Hiển thị lịch sử phát nhạc.
  - Quản Lý Hàng Đợi Phát:
    - Hiển thị danh sách bài hát trong hàng đợi.
  - Chức Năng Nền:
    - Phát nhạc trong nền.


### Giai Đoạn Kiểm Thử
- **Phương Pháp Kiểm Thử**:
  (Mô tả các phương pháp kiểm thử đã áp dụng.)
- **Kết Quả Kiểm Thử**:
  (Mô tả kết quả kiểm thử và các lỗi phát hiện được.)

## Kết Quả và Đánh Giá
- **Đạt Được**:
  (Tổng kết các thành tựu của dự án.)
- **Hạn Chế**:
  (Thảo luận về các hạn chế và khó khăn trong dự án.)
- **Hướng Phát Triển Tương Lai**:
  (Đề xuất các hướng phát triển tương lai cho dự án.)

## Phụ Lục
- **Biểu Đồ**:
  (Các biểu đồ, sơ đồ liên quan.)
- **Mã Nguồn**:
  (Tóm tắt hoặc liên kết đến mã nguồn của dự án.)
