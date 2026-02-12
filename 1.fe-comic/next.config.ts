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
    ];
  },
};

export default nextConfig;
