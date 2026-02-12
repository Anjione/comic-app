"use client";
import Link from 'next/link';
import React from 'react';
import { useWindowSize } from '@/hooks/useWindowSize';
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

// Hàm chunkArray linh hoạt
const chunkArray = (arr: GenreItem[], size: number) => {
    if (!arr) return [];
    const result = [];
    for (let i = 0; i < arr.length; i += Math.ceil(arr.length / size)) {
        result.push(arr.slice(i, i + Math.ceil(arr.length / size)));
    }
    return result.slice(0, size); // Đảm bảo luôn trả về đúng số cột mong muốn
};

export default function Genre() {
    const [mounted, setMounted] = React.useState(false);

    React.useEffect(() => {
        setMounted(true);
    }, []);

    const { data: genreResponse } = useQuery<GenreResponse>({
        queryKey: ['genre'],
        queryFn: async () => {
            const response = await axios.get('/api-remote/genre'); // Thay bằng endpoint thật của bạn
            return response.data;
        },
        staleTime: 1000 * 60 * 30, // Thể loại ít thay đổi, nên để cache lâu (30 phút)
    });

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
    // Ensure consistent rendering during hydration by using default (mobile) view until mounted
    const genres = genreResponse?.data || [];
    const columnCount = (!mounted || windowWidth < 880) ? 2 : 3;
    const columns = chunkArray(genres, columnCount);

    // Style cho đường kẻ dọc
    // const dividerClasses = "w-[1px] h-full bg-[#312f40]";

    return (
        // Sử dụng Tailwind cho styling container
        <div className="bg-[#222222]">

            {/* Tiêu đề */}
            <div className="release">
                <h2 className="font-semibold">
                    Genre
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
                                <li key={genre.id}>
                                    <Link
                                        href={`/genre/${genre.code.toLowerCase().replace(/\s+/g, '-')}`}
                                        className={tagClasses}>
                                        {genre.code}
                                    </Link>
                                </li>
                            ))}
                        </ul>

                        {/* Divider logic */}
                        {colIndex < columns.length - 1 && (
                            <div
                                className={`${columnCount === 2 ? 'hidden' : 'block'} mx-2 border-l border-[#312f40] h-full self-center`}
                                aria-hidden="true"
                            />
                        )}
                    </React.Fragment>
                ))}
            </div>
        </div>
    );
}