"use client";
import Blog from "@/component/blog";
import Genre from "@/component/genre";
import LatestUpdate from "@/component/last-update";
import PopularToday from "@/component/popular-today";
import Recommendation from "@/component/recommendation";
import SearchBox from "@/component/search-box";
import SerialPopular from "@/component/serial-popular";
import { PopularMangaGroups } from "@/type/popular-comic";
import { popularMangasSample, sampleItems } from "@/type/sample-data";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";

// 1. Tách hàm fetch ra ngoài để code sạch sẽ hơn
// const fetchPopularMangas = async (): Promise<PopularMangaGroups> => {
//     const response = await axios.get('/api-remote/manga/popular');
//     console.log("Dữ liệu API:", response.data); // Kiểm tra xem có đúng response.data.data không
//     return response.data.data;
// };

export default function Home() {
    // 2. Sử dụng useQuery
    const { data: popularMangas } = useQuery({
        queryKey: ['popular-manga'],
        // queryFn vẫn phải khai báo để React Query có thể fetch lại khi dữ liệu cũ (stale)
        queryFn: async () => {
            const response = await axios.get('/api-remote/manga/popular');
            return response.data.data;
        },
        staleTime: 1000 * 60 * 5,
    });

    return (
        <div className="w-full">
            <div className="max-w-7xl m-[35px_auto] mb-[160px] min-[800px]:mb-[35px] min-[800px]:px-5 min-[1226px]:px-12 flex flex-col gap-4">

                <div className="">
                    <PopularToday items={popularMangas?.day && popularMangas.day.length > 0 ? popularMangas.day : popularMangasSample} maxVisible={7} />
                </div>

                <div className="listupd grid grid-cols-1 lg:grid-cols-7 min-[880px]:grid-cols-17 gap-4">
                    <div className="flex flex-col col-span-1 lg:col-span-5 min-[880px]:col-span-12 gap-4">
                        <LatestUpdate items={sampleItems} nextPageUrl="/manga?order=update&page=2" />
                        <Recommendation />
                        <Blog />
                    </div>

                    <div className="flex flex-col gap-5 col-span-1 lg:col-span-2 min-[880px]:col-span-5">
                        <SearchBox />
                        {/* 4. Bên trong SerialPopular và Genre bạn cũng nên dùng useQuery tương tự */}
                        <SerialPopular />
                        <Genre />
                    </div>
                </div>
            </div>
        </div>
    );
}