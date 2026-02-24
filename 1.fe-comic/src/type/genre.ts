// Định nghĩa Interface cho mỗi mục Thể loại
export interface GenreItem {
    id: number;
    code: string;
}

export interface GenreResponse {
    timestamp: string;
    status: number;
    data: GenreItem[];
}