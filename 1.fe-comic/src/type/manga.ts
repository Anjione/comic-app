export interface Chapter {
    id: number;
    timeAgo?: string;
    chapterName: string;
    chapterNumber: number;
}

export interface MangaData {
    id: number;
    title: string;
    mangaAvatarUrl: string;
    lastChapter: string;
    rating: number;
    totalView: number;
    href: string;
    // type?: string;
    mangaCategory?: string;
    colored?: boolean;
    chapters: Chapter[];
    status?: string;
    isNew?: boolean;
}

export interface MangaResponse {
    timestamp: string;
    status: number;
    data: MangaData;
}

export interface Paging {
    pageNum: number;
    pageSize: number;
    totalRecords: number;
    totalPages: number;
    validPageNum: boolean;
    validPageSize: boolean;
}

export interface LatestMangaResponse {
    timestamp: string;
    status: number;
    data: MangaData[];
    paging: Paging;
}

export interface MangaListResponse {
    timestamp: string;
    status: number;
    data: MangaData[];
    paging: Paging;
}