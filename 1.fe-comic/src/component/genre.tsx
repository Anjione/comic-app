import Link from 'next/link';
import React from 'react';

// Định nghĩa Interface cho mỗi mục Thể loại
interface GenreItem {
    name: string;
    url: string;
}

// Định nghĩa Props cho component
interface GenreProps {
    genres: GenreItem[];
    title?: string;
}

const chunkArray = (arr: GenreItem[]): GenreItem[][] => {
    const size = Math.ceil(arr.length / 3);
    return [
        arr.slice(0, size),
        arr.slice(size, size * 2),
        arr.slice(size * 2),
    ];
};

export default function Genre({ genres, title = 'Genre' }: GenreProps) {

    // Style chung cho các thẻ thể loại
    const tagClasses = `
    inline-block 
    text-xs 
    font-medium 
    text-gray-300 
    transition-colors 
    duration-200 
    hover:text-black
  `;

    const [col1, col2, col3] = chunkArray(genres);
    const columns = [col1, col2, col3];

    // Style cho đường kẻ dọc
    const dividerClasses = "w-[1px] h-full bg-[#312f40]";

    return (
        // Sử dụng Tailwind cho styling container
        <div className="bg-[#222222]">

            {/* Tiêu đề */}
            <div className="release">
                <h2 className="font-semibold">
                    {title}
                </h2>
            </div>

            {/* 💥 Cấu trúc GRID 3 CỘT với DIVIDER 💥 */}
            <div className="w-full grid grid-cols-[1fr_auto_1fr_auto_1fr] relative px-3">

                {columns.map((column, colIndex) => (
                    <React.Fragment key={colIndex}>
                        {/* Cột dữ liệu */}
                        <ul className="space-y-1 mt-2 mb-3  overflow-hidden text-ellipsis">
                            {column.map((genre) => (
                                <li key={genre.url}>
                                    <Link
                                        href={genre.url}
                                        title={`Xem tất cả truyện thuộc ${genre.name}`}
                                        className={tagClasses}
                                    >
                                        {genre.name}
                                    </Link>
                                </li>
                            ))}
                        </ul>

                        {/* Đường kẻ dọc giữa các cột */}
                        {/* Chỉ hiển thị divider nếu KHÔNG phải cột cuối cùng (index 0 và 1) */}
                        {colIndex < columns.length - 1 && (
                            <div className={`mx-2 ${dividerClasses}`} aria-hidden="true" />
                        )}
                    </React.Fragment>
                ))}
            </div>
        </div>
    );
}