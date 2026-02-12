// components/PopularSeriesWidget.tsx
"use client";

import { fira } from "@/lib/fonts";
import { SeriesItem } from "@/type/comic-info";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import Image from "next/image";
import Link from "next/link";
import { JSX, useEffect, useMemo, useState } from "react";
import StarRating from "./star-rating";


// type SeriesItem = {
//     id: number;
//     rank: number;
//     chapter: string;
//     title: string;
//     href: string;
//     img: string;
//     genres: string[];
//     score: number; // 0-10
// };

type TabId = "comedy" | "horror" | "school-life" | "shotacon" | "web-comic";


function tabClass(isActive: boolean) {
    return (
        `px-1 py-1 text-[12px] sm:text-[14px] font-medium transition-colors rounded-sm whitespace-nowrap w-[50%] ` +
        (isActive ? "bg-black text-white" : "text-gray-400 hover:text-gray-200")
    ).trim();
}



export default function Recommendation(): JSX.Element {
    const [activeGenreId, setActiveGenreId] = useState<number | null>(null);

    const { data: recoData, isLoading } = useQuery<RecommendationResponse>({
        queryKey: ['recommendations'],
        queryFn: async () => {
            const response = await axios.get('/api-remote/manga/suggest'); // Thay bằng endpoint thật của bạn
            return response.data;
        },
        staleTime: 1000 * 60 * 5,
    });

    // 2. Sử dụng useMemo để cố định tham chiếu của genres
    const genres = useMemo(() => {
        return recoData?.data || [];
    }, [recoData?.data]);

    // 3. Bây giờ useEffect sẽ chỉ chạy khi dữ liệu thực sự thay đổi
    useEffect(() => {
        if (genres.length > 0 && !activeGenreId) {
            setActiveGenreId(genres[0].genreId);
        }
    }, [genres, activeGenreId]);

    // Lấy danh sách truyện của thể loại đang được chọn
    const activeGroup = genres.find(g => g.genreId === activeGenreId);
    const displayItems = activeGroup?.mangas || [];

    if (isLoading) return <div>Loading...</div>;

    return (
        <section aria-labelledby="popular-series-heading" className="w-full">
            <div className="release px-4 py-2">
                <h2 id="popular-series-heading" className="font-semibold">Recommendation</h2>
            </div>

            <div className="bg-[#222] max-w-7xl mx-auto text-[12px] rounded pb-8">
                {/* Tabs */}
                <div className="p-2">
                    <div className="bg-[#333] m-[2px] py-[5px] px-[6px] flex justify-center rounded overflow-x-auto" role="tablist">
                        {genres.map((genre, index) => {
                            const isActive = genre.genreId === activeGenreId;
                            return (
                                <button
                                    key={genre.genreId}
                                    onClick={() => setActiveGenreId(genre.genreId)}
                                    className={`${tabClass(isActive)} ${index >= 3 ? "max-[590px]:hidden" : "w-[33%]"}`}
                                >
                                    {genre.genreCode}
                                </button>
                            );
                        })}
                    </div>
                </div>

                {/* Content */}
                <div className="grid grid-cols-3 min-[650px]:grid-cols-4 min-[768px]:grid-cols-5">
                    {displayItems.map((it, index) => {
                        const visibilityClass =
                            index === 2 ? "hidden min-[480px]:flex" :
                                index === 3 ? "hidden min-[650px]:flex" :
                                    index === 4 ? "hidden min-[768px]:flex" : "flex";

                        return (
                            <article key={it.id} className={`w-full ${visibilityClass} p-3 flex-col cursor-pointer`}>
                                <Link href={`/manga/${it.id}`} className="w-full block relative">
                                    <div className="relative w-full aspect-3/4">
                                        <Image
                                            src={it.mangaAvatarUrl || ""}
                                            fill
                                            alt={it.title}
                                            className="object-cover rounded-sm"
                                            sizes="(max-width: 768px) 33vw, 20vw"
                                        />
                                    </div>
                                </Link>

                                <Link href={`/manga/${it.id}`} className="w-full block hover:text-indigo-400 transition-colors">
                                    <div className="text-sm mt-2 font-semibold line-clamp-2 min-h-[40px] text-gray-100">
                                        {it.title}
                                    </div>
                                </Link>

                                <div className={`text-xs text-[#999] mt-1 ${fira.className}`}>
                                    {it.lastChapter}
                                </div>

                                <div className="flex items-center gap-1 mt-1">
                                    <StarRating score={it.rating} />
                                    <span className="text-xs text-[#999]">{it.rating}</span>
                                </div>
                            </article>
                        );
                    })}
                </div>
            </div>
        </section>
    );
}
