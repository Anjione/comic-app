import Link from 'next/link';
import React from 'react';

// Giả định cấu trúc cho một bài blog item (nếu bạn muốn thêm nội dung)
// interface BlogItem {
//   id: string;
//   title: string;
//   url: string;
//   date: string;
// }

// interface BlogSectionProps {
//   items?: BlogItem[];
// }

// export default function BlogSection({ items = [] }: BlogSectionProps) {

export default function Blog() {

    // Class cho tiêu đề chính
    // Class cho tiêu đề chính
    // const titleClasses = "text-xl font-semibold text-white";

    // Class cho nút "View All"
    // Class cho nút "View All"
    // const viewAllClasses = "px-3 py-1 text-xs font-medium transition-colors rounded-sm bg-black text-white hover:bg-gray-700 uppercase";

    return (
        // Thay thế div.bixbox
        <div className="bg-[#222222]">

            {/* Thay thế div.releases.blog */}
            <div className="release flex items-center justify-between">
                <h2 className="font-semibold">
                    Blog
                </h2>
                <Link href="/blog" className="px-2 py-1 text-[8px] font-medium transition-colors rounded-sm bg-black text-white uppercase">
                    View All
                </Link>
            </div>

            {/* Thay thế div.bloglist */}
            <div className="blog-list-content min-h-[50px] p-4">
                {/* 💥 LƯU Ý: Đây là nơi bạn sẽ thêm logic lặp qua các bài blog.
          Ví dụ nếu bạn có mảng 'items':
          
          {items.length > 0 ? (
            <ul className="space-y-3">
              {items.map(item => (
                <li key={item.id} className="text-gray-300 hover:text-indigo-400 transition">
                  <Link href={item.url} className="block font-medium">
                    {item.title}
                  </Link>
                  <span className="text-xs text-gray-500">{item.date}</span>
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-500 text-sm">Chưa có bài viết blog nào.</p>
          )}
        */}
                <p className="text-gray-500 text-sm">Nội dung blog sẽ được tải tại đây...</p>
            </div>
        </div>
    );
}