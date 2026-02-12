// 1. Định nghĩa cấu trúc của một mục truyện tranh lẻ
export interface PopularManga {
    id: number;
    title: string;
    url: string | null;
    mangaAvatarUrl: string;
    author: string;
    totalView: number;
    rating: number;
    createdDate: string | null;
    lastChapter: string;
    modifiedBy: string | null;
    createdBy: string | null;
    mangaCategory: string[] // Có thể thay any bằng interface Category nếu có
    chapters: string | null;
    type?: string;
    colored?: boolean;
    rank: number;
}

// 2. Định nghĩa cấu trúc object "data" chứa các danh sách theo thời gian
export interface PopularMangaGroups {
    total: PopularManga[];
    week: PopularManga[];
    month: PopularManga[];
    year: PopularManga[];
    day: PopularManga[];
}

// 3. Định nghĩa cấu trúc phản hồi tổng quát từ API
export interface PopularMangaResponse {
    status: number;
    message?: string; // Tùy chọn nếu API có trả về thông báo
    data: PopularMangaGroups;
}