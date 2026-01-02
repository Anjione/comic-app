"use client";
import Link from 'next/link';
import React from 'react';
import { useWindowSize } from '@/hooks/useWindowSize';

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

// Hàm chunkArray linh hoạt
const chunkArray = (arr: GenreItem[], size: number) => {
    if (!arr) return [];
    const result = [];
    for (let i = 0; i < arr.length; i += Math.ceil(arr.length / size)) {
        result.push(arr.slice(i, i + Math.ceil(arr.length / size)));
    }
    return result.slice(0, size); // Đảm bảo luôn trả về đúng số cột mong muốn
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
    const windowWidth = useWindowSize();

    // Logic chia cột dựa trên breakpoint 880px bạn đã cài trong config
    // Nếu width < 880 thì chia 2, ngược lại chia 3
    const columnCount = windowWidth < 880 ? 2 : 3;
    const columns = chunkArray(genres, columnCount);

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
            <div className={`w-full grid relative px-3 
    ${columnCount === 2
                    ? 'grid-cols-2'
                    : 'grid-cols-[1fr_auto_1fr_auto_1fr]'
                }`}
            >
                {columns.map((column, colIndex) => (
                    <React.Fragment key={colIndex}>
                        {/* Cột dữ liệu */}
                        <ul className="space-y-1 mt-2 mb-3 overflow-hidden text-ellipsis">
                            {column.map((genre) => (
                                <li key={genre.url}>
                                    <Link href={genre.url} className={tagClasses}>
                                        {genre.name}
                                    </Link>
                                </li>
                            ))}
                        </ul>

                        {/* Divider logic */}
                        {colIndex < columns.length - 1 && (
                            <div
                                className={`${columnCount === 2 ? 'hidden' : 'block'} mx-2 border-l border-[#312f40] h-[100%] self-center`}
                                aria-hidden="true"
                            />
                        )}
                    </React.Fragment>
                ))}
            </div>
        </div>
    );
}