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