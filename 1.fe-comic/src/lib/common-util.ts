import mangaSrc from "@/asset/manga.png";
import manhuaSrc from "@/asset/manhua.png";
import manhwaSrc from "@/asset/manhwa.png";


// --- HÀM HELPER ĐỂ XỬ LÝ ICON ---
export const getTypeIcon = (type: string | undefined) => {
    if (!type) return null;
    const lowerType = type.toLowerCase();
    switch (lowerType) {
        case 'manga':
            return mangaSrc;
        case 'manhwa':
            return manhwaSrc;
        case 'manhua':
            return manhuaSrc;
        default:
            return null;
    }
};

export const getTypeColor = (type: string | undefined) => {
    if (!type) return "text-black"; // Màu mặc định
    const lowerType = type.toLowerCase();
    switch (lowerType) {
        case 'manga':
            return "text-black";
        case 'manhwa':
            return "text-[#009688]"; // Bạn có thể dùng text-[#00aaff] nếu muốn màu xanh cụ thể
        case 'manhua':
            return "text-[#9d4942]";
        default:
            return "text-black";
    }
};

// utils/pagination.ts
export const generatePagination = (currentPage: number, totalPages: number) => {
    if (totalPages <= 7) {
        return Array.from({ length: totalPages }, (_, i) => i + 1);
    }

    const pages: number[] = [];
    pages.push(1); // Luôn hiện trang đầu

    let start = Math.max(2, currentPage - 2);
    let end = Math.min(totalPages - 1, currentPage + 2);

    // Điều chỉnh để giữ độ dài ổn định khi ở gần biên
    if (currentPage <= 3) end = 4;
    if (currentPage >= totalPages - 2) start = totalPages - 3;

    for (let i = start; i <= end; i++) {
        pages.push(i);
    }

    if (totalPages > 1) pages.push(totalPages); // Luôn hiện trang cuối

    return Array.from(new Set(pages)).sort((a, b) => a - b);
};