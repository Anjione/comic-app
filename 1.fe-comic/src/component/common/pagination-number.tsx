'use client';

import { generatePagination } from '@/lib/common-util';
import { useRouter, usePathname, useSearchParams } from 'next/navigation';

interface Props {
    totalPages: number;
}

export default function PaginationNumber({ totalPages }: Props) {
    const router = useRouter();
    const pathname = usePathname();
    const searchParams = useSearchParams();

    // Lấy trang hiện tại từ URL, mặc định là 1
    // Bóc tách trang hiện tại từ pathname
    const match = pathname.match(/\/page\/(\d+)/);
    const currentPage = match ? Number(match[1]) : 1;

    const pages = generatePagination(currentPage, totalPages);

    // const createPageURL = (pageNumber: number | string) => {
    //     // Lấy pathname hiện tại (ví dụ: /az-lists/page/1/)
    //     let currentPath = pathname;
    //     const params = new URLSearchParams(searchParams.toString());

    //     // 1. Xử lý Pathname: Thay thế hoặc thêm /page/x/
    //     if (currentPath.includes('/page/')) {
    //         // Nếu đã có /page/n/, dùng Regex để thay n bằng pageNumber mới
    //         currentPath = currentPath.replace(/\/page\/\d+/, `/page/${pageNumber}`);
    //     } else {
    //         // Nếu chưa có, thêm /page/x/ vào sau az-lists (hoặc cuối pathname)
    //         // Đảm bảo không bị thừa dấu gạch chéo
    //         currentPath = currentPath.replace(/\/$/, '') + `/page/${pageNumber}/`;
    //     }

    //     // 2. Xử lý Search Params: Giữ lại các filter như ?show=A
    //     const queryString = params.toString();

    //     return queryString ? `${currentPath}?${queryString}` : currentPath;
    // };

    const handleNav = (page: number) => {
        let newPath = pathname;
        if (newPath.includes('/page/')) {
            newPath = newPath.replace(/\/page\/\d+/, `/page/${page}`);
        } else {
            newPath = `${newPath.replace(/\/$/, '')}/page/${page}/`;
        }

        const queryString = searchParams.toString();
        const finalUrl = queryString ? `${newPath}?${queryString}` : newPath;

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
                // Kiểm tra xem có cần hiển thị dấu "..." không (Tùy chọn)
                // Nếu bạn muốn giống hệt ảnh 100% thì bỏ qua check ellipsis
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