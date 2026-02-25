'use client';

import { generatePagination } from '@/lib/common-util';
import { useRouter, usePathname } from 'next/navigation';

interface Props {
    totalPages: number;
}

export default function PaginationNumber({ totalPages }: Props) {
    const router = useRouter();
    const pathname = usePathname();

    // Lấy trang hiện tại từ pathname bằng Regex
    const match = pathname.match(/\/page\/(\d+)/);
    const currentPage = match ? Number(match[1]) : 1;

    const pages = generatePagination(currentPage, totalPages);

    const handleNav = (page: number) => {
        // 1. Xử lý Pathname mới
        let newPath = pathname;
        if (newPath.includes('/page/')) {
            newPath = newPath.replace(/\/page\/\d+/, `/page/${page}`);
        } else {
            newPath = `${newPath.replace(/\/$/, '')}/page/${page}/`;
        }

        // 2. Lấy Query String từ window.location
        // window.location.search sẽ trả về chuỗi bao gồm cả dấu "?" (ví dụ: "?show=A")
        const queryString = typeof window !== 'undefined' ? window.location.search : '';

        // 3. Kết hợp lại
        const finalUrl = `${newPath}${queryString}`;

        router.push(finalUrl);
    };

    return (
        <div className="flex items-center justify-center gap-2 my-8">
            {/* Nút Previous */}
            {currentPage > 1 && (
                <button
                    onClick={() => handleNav(currentPage - 1)}
                    className="px-3 py-2 font-medium rounded-xs bg-[#16151d] text-gray-300 hover:text-black transition-colors cursor-pointer"
                >
                    « Previous
                </button>
            )}

            {/* Danh sách các số trang */}
            {pages.map((page, index) => {
                const isSelected = currentPage === page;
                return (
                    <button
                        key={index}
                        onClick={() => handleNav(page)}
                        className={`min-w-[40px] h-[40px] text-sm font-medium rounded-xs transition-colors cursor-pointer ${isSelected
                            ? 'bg-black text-white'
                            : 'bg-[#16151d] text-gray-300 hover:text-black'
                            }`}
                    >
                        {page}
                    </button>
                );
            })}

            {/* Nút Next */}
            {currentPage < totalPages && (
                <button
                    onClick={() => handleNav(currentPage + 1)}
                    className="px-3 py-2 font-medium rounded-xs bg-[#16151d] text-gray-300 hover:text-black transition-colors cursor-pointer"
                >
                    Next »
                </button>
            )}
        </div>
    );
}