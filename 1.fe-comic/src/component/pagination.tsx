import Link from "next/link";


export default function Pagination({ total, pageSize, page, basePath }: { total: number, pageSize: number, page: number, basePath: string }) {
    const totalPages = Math.max(1, Math.ceil(total / pageSize));
    const current = Math.min(Math.max(1, page), totalPages);
    const isFirstPage = current <= 1;
    const isLastPage = current >= totalPages;

    const previousPage = current - 1;
    const nextPage = current + 1;

    // Class Styling... (Giữ nguyên)
    const buttonClasses = "flex items-center px-8 py-1 text-sm font-medium bg-black rounded-xs transition-colors duration-500 whitespace-nowrap";
    const textColorClasses = "text-[#ddd] hover:text-white";

    return (
        <div className="flex justify-center items-center w-full gap-1 py-6">
            {!isFirstPage && (
                <Link
                    href={`${basePath}/${previousPage}`} // Dùng basePath
                    className={`${buttonClasses} ${textColorClasses}`}
                >
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
                    </svg>
                    Previous
                </Link>
            )}
            {!isLastPage && (
                <Link
                    href={`${basePath}/${nextPage}`} // Dùng basePath
                    className={`${buttonClasses} ${textColorClasses} `}
                >
                    Next
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
                    </svg>
                </Link>
            )}
        </div>
    );
}