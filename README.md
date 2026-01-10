# comic-app
# Hướng dẫn Deploy Ứng dụng Next.js (Standard Mode)

Tài liệu này hướng dẫn chi tiết từng bước để đưa ứng dụng của bạn lên môi trường Production (VPS, Server riêng).

## Bước 1: Chuẩn bị file để Deploy

Sau khi bạn đã chạy lệnh `npm run build` thành công, bạn cần chọn lọc các file và thư mục sau để nén hoặc copy lên server.

**Danh sách các file/thư mục CẦN THIẾT:**

1.  📁 `.next` (Chứa code đã build)
2.  📁 `public` (Chứa ảnh, icon, file tĩnh)
3.  📄 `package.json` (Để cài đặt thư viện)
4.  📄 `package-lock.json` (Để đảm bảo đúng phiên bản)
5.  📄 `next.config.ts` (Cấu hình Next.js)

**Lưu ý:**
*   KHÔNG copy thư mục `node_modules` (Nó rất nặng, chúng ta sẽ cài lại trên server).
*   KHÔNG copy thư mục `src` (Không cần thiết cho production chạy, trừ khi bạn cần source map để debug sâu).

## Bước 2: Upload lên Server

1.  **Nén file:** (Khuyên dùng)
    *   Nén 5 mục trên thành một file `.zip` hoặc `.tar.gz` trên máy tính của bạn.
    *   Ví dụ: `comic-app-deploy.zip`.

2.  **Upload:**
    *   Sử dụng FTP clients (FileZilla, WinSCP) hoặc lệnh `scp` để đẩy file nén lên thư mục web trên server (ví dụ: `/var/www/comic-app`).

3.  **Giải nén trên Server:**
    *   Đăng nhập vào server SSH.
    *   Chạy lệnh giải nén (ví dụ `unzip comic-app-deploy.zip`).

## Bước 3: Cài đặt Dependencies trên Server

Tại thư mục chứa code vừa giải nén trên server, chạy lệnh sau để cài đặt các thư viện cần thiết cho việc chạy web:

```bash
# Chỉ cài đặt các gói cần thiết cho Production (nhẹ hơn)
npm install --production
```

## Bước 4: Chạy thử ứng dụng

Trước khi chạy chính thức, hãy thử chạy kiểm tra xem có lỗi gì không:

```bash
npm start
```

*   Nếu thấy thông báo `Ready in [...] on http://localhost:3000` nghĩa là đã thành công.
*   Nhấn `Ctrl + C` để tắt.

## Bước 5: Chạy ứng dụng liên tục (Production)

Để ứng dụng chạy ngầm và tự khởi động lại nếu server reset, chúng ta nên dùng **PM2**.

1.  **Cài đặt PM2** (nếu chưa có):
    ```bash
    npm install -g pm2
    ```

2.  **Khởi chạy ứng dụng với PM2:**
    ```bash
    pm2 start npm --name "comic-app" -- start
    ```
    *(Lệnh này chạy `npm start` dưới tên "comic-app")*

3.  **Lưu cấu hình để tự chạy khi khởi động lại:**
    ```bash
    pm2 save
    pm2 startup
    ```

## Tổng kết các lệnh trên Server

```bash
# 1. Di chuyển vào thư mục code
cd /path/to/your/app

# 2. Cài dependency
npm install --production

# 3. Chạy với PM2
pm2 start npm --name "comic-app" -- start

# (Tùy chọn) Xem log nếu có lỗi
pm2 logs comic-app
```
