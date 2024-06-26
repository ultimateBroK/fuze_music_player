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
  - Tạo ra 1 ứng dụng nghe nhạc offline cơ bản, cung cấp cho người dùng một trải nghiệm nghe nhạc tiện lợi và không phụ thuộc vào kết nối internet. Với ứng dụng này, người dùng có thể tải về và lưu trữ các bài hát trực tiếp trên thiết bị của mình, sau đó nghe chúng mọi lúc mọi nơi mà không cần kết nối internet

  - Các tính năng: 
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
  - Hỗ trợ quản lý các bài hát theo album, nghệ sĩ.
  - Hiển thị các bài hát mới thêm gần đây và lịch sử phát nhạc.
  - Có thể phát nhạc trong nền.

- **Yêu Cầu Hệ Thống**:
  - Ứng dụng phải hoạt động ổn định trên các thiết bị Android từ phiên bản 11.0 trở lên. ([Tỉ lệ phân phối phiên bản Android 5/2023](https://tinhte.vn/thread/ti-le-phan-phoi-phien-ban-android-thang-5-android-13-co-gan-15-thi-phan-11-van-dan-dau.3677386/))
  - Dung lượng ứng dụng nhẹ, không tiêu tốn nhiều tài nguyên hệ thống.
  - Đảm bảo bảo mật và quyền riêng tư của người dùng.
  - Hỗ trợ nhiều định dạng file nhạc như MP3, AAC, FLAC.
  - Tích hợp hệ thống lưu trữ và truy xuất dữ liệu cục bộ.


### Giai Đoạn Thiết Kế
- **Thiết Kế Giao Diện**:
  - Giao diện For You:
    - ![Giao diện For You](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/267ee77e-82c5-4694-808e-1c883fc93dbe)
  - Giao diện History:
    - ![Giao diện History](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/88fec06e-57c0-4ef3-9812-4749d71e20da)
  - Giao diện Last Added:
    - ![Giao diện Last Added](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/2bbeeecd-f35a-4c92-a20f-124e345f0e6a)
  - Giao diện Songs:
    - ![Giao diện Songs](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/3b2a5a07-3fe6-49b5-aa2d-37b9f0311116)
  - Giao diện Album:
    - ![Giao diện Albums](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/93d770eb-8e5f-4462-9922-0f571c356f44)
  - Giao diện Album chi tiết:
    - ![Giao diện Album chi tiết](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/56ce8e8c-fab7-4106-a578-77cfe497eccb)
  - Giao diện Artists:
    - ![Giao diện Artists](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/5904279c-9932-4ff6-bebf-195654b6f1f8)
  - Giao diện Artist chi tiết:
    - ![Giao diện Album chi tiết](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/a8b89048-5296-4885-9231-d49bd7531d62)
  - Giao diện chơi nhạc:
    - ![Giao diện chơi nhạc](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/ce61a582-3d69-4dd1-b472-6e225080af56)
  - Giao diện hàng đợi:
    - ![Giao diện hàng đợi](https://github.com/ultimateBroK/fuze_music_player/assets/67256125/790cf0a6-abeb-4c64-83f2-ac10bdbe985a)

- **Thiết Kế Kiến Trúc**:
  (Mô tả kiến trúc tổng thể của ứng dụng.)

### Giai Đoạn Lập Trình
- **Công Nghệ Sử Dụng**:
  - Ngôn ngữ lập trình: Java.
  - Môi trường phát triển: Android Studio.
  - Thư viện và công cụ:
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
  - Kiểm Thử Chức Năng (Functional Testing):
    - Mục Tiêu: Đảm bảo tất cả các chức năng của ứng dụng hoạt động đúng như mong đợi.
    - Phương Pháp:
      - Kiểm Thử Tích Hợp (Integration Testing): Kiểm thử sự tương tác giữa các đơn vị mã nguồn để đảm bảo chúng phối hợp hoạt động đúng cách.
      - Kiểm Thử Hệ Thống (System Testing): Kiểm thử toàn bộ hệ thống để đảm bảo các thành phần hoạt động trơn tru khi kết hợp với nhau.
      - Kiểm Thử Hồi Quy (Regression Testing): Đảm bảo rằng các tính năng cũ vẫn hoạt động đúng sau khi có sự thay đổi hoặc thêm mới.

  - Kiểm Thử Hiệu Năng (Performance Testing):
    - Mục Tiêu: Đánh giá hiệu suất của ứng dụng trong các điều kiện khác nhau.
    - Phương Pháp: Kiểm Thử Thời Gian Phản Hồi (Response Time Testing): Đo thời gian phản hồi của ứng dụng trong các thao tác thông thường.

  - Kiểm Thử Giao Diện Người Dùng (UI Testing):
    - Mục Tiêu: Đảm bảo giao diện người dùng thân thiện, dễ sử dụng và không có lỗi.
    - Phương Pháp:
      - Kiểm Thử Giao Diện Bằng Tay (Manual UI Testing): Người kiểm thử sử dụng ứng dụng như người dùng thật để phát hiện các vấn đề giao diện.
      - Kiểm Thử Giao Diện Tự Động (Automated UI Testing): Sử dụng các công cụ tự động để kiểm thử các tương tác giao diện.

  - Kiểm Thử Tương Thích (Compatibility Testing):
    - Mục Tiêu: Đảm bảo ứng dụng hoạt động tốt trên các thiết bị và phiên bản hệ điều hành khác nhau.
    - Phương Pháp:
      - Kiểm Thử Trên Nhiều Thiết Bị (Device Testing): Kiểm thử trên nhiều loại thiết bị với các độ phân giải và kích thước màn hình khác nhau.
      - Kiểm Thử Trên Nhiều Phiên Bản Hệ Điều Hành (OS Version Testing): Kiểm thử trên các phiên bản hệ điều hành Android khác nhau.
  
- **Kết Quả Kiểm Thử**:
  (Mô tả kết quả kiểm thử và các lỗi phát hiện được.)
  
  - Chức Năng: Tất cả các chức năng chính (phát nhạc, quản lý bài hát, hiển thị thông tin, phát nhạc nền) hoạt động đúng như mong đợi. Một số lỗi nhỏ đã được phát hiện và sửa chữa trong quá trình kiểm thử đơn vị và tích hợp.
  
  - Giao Diện Người Dùng: Giao diện thân thiện, dễ sử dụng. Một số lỗi nhỏ về giao diện đã được phát hiện và sửa chữa (ví dụ: lỗi hiển thị trên một số thiết bị có màn hình nhỏ).
  
  - Tương Thích: Ứng dụng hoạt động tốt trên các thiết bị Android từ phiên bản 11.0 trở lên, bao gồm cả các thiết bị có độ phân giải và kích thước màn hình khác nhau.


## Kết Quả và Đánh Giá
- **Đạt Được**:
  - Ứng dụng đã xử lý được chức năng cơ bản của 1 ứng dụng nghe nhạc offline,  
- **Hạn Chế**:
  - Ứng dụng chưa phát triển chức năng tìm kiếm và setting
  - Vấn đề giao tiếp của 1 thành viên trong nhóm gây ảnh hưởng nghiêm trọng đến tiến độ dự án
- **Hướng Phát Triển Tương Lai**:
  - Cải thiện hiệu năng ứng dụng để tránh hiện tượng crash

## Phụ Lục
- **Biểu Đồ**:
  (Các biểu đồ, sơ đồ liên quan.)
- **Mã Nguồn**:
  - https://github.com/ultimateBroK/fuze_music_player
  - https://www.figma.com/design/xHAzOtnbujB5Qqmwm7XShn/Fuze-Music-Player?node-id=0-1&t=TK8T2hW6E03kZy6m-1
