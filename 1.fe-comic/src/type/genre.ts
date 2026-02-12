// Định nghĩa Interface cho mỗi mục Thể loại
interface GenreItem {
    id: number;
    code: string;
}

interface GenreResponse {
    timestamp: string;
    status: number;
    data: GenreItem[];
}