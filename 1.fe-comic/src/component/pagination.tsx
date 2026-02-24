"use client";

import Link from "next/link";
import { useSearchParams } from "next/navigation";

export default function Pagination({
    total,
    pageSize,
    page,
    basePath
}: {
    total: number,
    pageSize: number,
    page: number,
    basePath: string
}) {
    const searchParams = useSearchParams();
    const totalPages = Math.max(1, Math.ceil(total / pageSize));
    const current = Math.min(Math.max(1, page), totalPages);

    const isFirstPage = current <= 1;
    const isLastPage = current >= totalPages;

    // Hàm tạo link dựa trên basePath
    const createPageLink = (pageNumber: number) => {
        const params = new URLSearchParams(searchParams.toString());

        // Sử dụng Switch Case dựa trên basePath như bạn yêu cầu
        switch (basePath) {
            case "/manga":
                // Đối với trang manga, ta giữ lại các filter cũ và ghi đè page
                params.set("page", pageNumber.toString());
                return `${basePath}/?${params.toString()}`;

            case "/page":
                // Đối với trang chủ (latest update) dùng cấu trúc /page/[number]
                return `/page/${pageNumber}`;

            default:
                // Mặc định hoặc các trường hợp khác
                return `${basePath}/${pageNumber}`;
        }
    };

    const buttonClasses = "flex items-center px-8 py-1 text-sm font-medium bg-black rounded-xs transition-colors duration-500 whitespace-nowrap text-gray-400 hover:text-white";

    return (
        <div className="flex justify-center items-center w-full gap-2 py-6">
            {!isFirstPage && (
                <Link
                    href={createPageLink(current - 1)}
                    className={buttonClasses}
                >
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4 mr-1">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
                    </svg>
                    Previous
                </Link>
            )}

            {/* Hiển thị số trang hiện tại để người dùng dễ theo dõi */}
            {/* <span className="text-xs text-gray-500 px-4">
                Page {current} of {totalPages}
            </span> */}

            {!isLastPage && (
                <Link
                    href={createPageLink(current + 1)}
                    className={buttonClasses}
                >
                    Next
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4 ml-1">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
                    </svg>
                </Link>
            )}
        </div>
    );
}