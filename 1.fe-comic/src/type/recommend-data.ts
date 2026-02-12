interface MangaRecommendation {
    id: number;
    title: string;
    mangaAvatarUrl: string;
    author: string;
    totalView: number;
    rating: number;
    lastChapter: string;
    modifiedDate: string;
}

interface GenreRecommendation {
    genreId: number;
    genreCode: string;
    mangas: MangaRecommendation[];
}

interface RecommendationResponse {
    data: GenreRecommendation[];
}