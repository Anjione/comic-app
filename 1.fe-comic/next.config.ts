import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: "images.unsplash.com",
      },
      {
        protocol: "https",
        hostname: "plus.unsplash.com",
      },
      {
        protocol: "https",
        hostname: "komik25.com",
      },
    ],
  },
  // Cấu hình Proxy để sửa lỗi CORS
  async rewrites() {
    return [
      {
        // Khi bạn gọi đến /api-remote, Next.js sẽ tự hiểu là gọi đến server 138...
        source: '/api-remote/:path*',
        destination: 'http://138.201.52.164:8081/api/:path*',
      },
      {
        // Khi người dùng vào /page/2, /page/3...
        source: '/page/:number',
        // Next.js sẽ ngầm hiểu là đang ở trang chủ với query param là number
        destination: '/?page=:number',
      },
      {
        // Chỉ cần bắt số trang. 
        // Các tham số như ?show=A sẽ tự động được Next.js nối vào destination.
        source: '/az-lists/page/:number',
        destination: '/az-lists?page=:number',
      },
      {
        // Chỉ cần bắt số trang. 
        // Các tham số như ?show=A sẽ tự động được Next.js nối vào destination.
        source: '/genre/:path*',
        destination: '/genre?genre=:path*',
      },
    ];
  },
};

export default nextConfig;
