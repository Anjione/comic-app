export interface MangaRecommendation {
    id: number;
    title: string;
    mangaAvatarUrl: string;
    author: string;
    totalView: number;
    rating: number;
    lastChapter: string;
    modifiedDate: string;
}

export interface GenreRecommendation {
    genreId: number;
    genreCode: string;
    mangas: MangaRecommendation[];
}

export interface RecommendationResponse {
    data: GenreRecommendation[];
}