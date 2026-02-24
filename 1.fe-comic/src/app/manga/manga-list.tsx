"use client";

import QuickFilter from '@/component/quick-filter';
import { getTypeIcon } from '@/lib/common-util';
import { fira } from "@/lib/fonts";
import { MangaData } from '@/type/manga';
import axios from 'axios';
import Image from "next/image";
import Link from 'next/link';
import { useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import Pagination from '../../component/pagination';
import StarRating from '../../component/star-rating';

// 1. Định nghĩa Interface chuẩn cho Response
interface MangaListResponse {
    timestamp: string;
    status: number;
    data: MangaData[];
    paging: {
        pageNum: number;
        pageSize: number;
        totalRecords: number;
        totalPages: number;
        validPageNum: boolean;
        validPageSize: boolean;
    };
}

export default function MangaList({ fieldSort }: { fieldSort?: string | string[], page: number }) {
    const searchParams = useSearchParams();

    // 2. Lấy toàn bộ filter từ URL
    const pageNum = Number(searchParams.get('page')) || 1;
    const type = searchParams.get('type') || '';
    const title = searchParams.get('title') || '';
    const author = searchParams.get('author') || '';
    const genre_in = searchParams.get('genre_in') || '';
    const genre_not = searchParams.get('genre_not') || '';
    const status = searchParams.get('status') || '';
    // const order = searchParams.get('order') || '';

    // 3. React Query với đầy đủ Dependencies
    const { data: response, isLoading } = useQuery<MangaListResponse>({
        queryKey: ['manga-list', pageNum, type, title, author, genre_in, genre_not, status, fieldSort],
        queryFn: async () => {
            // Tạo object chứa tất cả params dự kiến
            const rawParams = {
                pageNum,
                pageSize: 20,
                type,
                title,
                author,
                genre_in,
                genre_not,
                status,
                fieldSort
            };

            // Lọc bỏ các key có giá trị falsy (chuỗi rỗng, null, undefined)
            // Lưu ý: Chúng ta giữ lại số 0 nếu pageNum có thể là 0
            const cleanParams = Object.fromEntries(
                Object.entries(rawParams).filter(([, value]) => value !== '' && value !== null && value !== undefined)
            );

            const res = await axios.get(`/api-remote/manga/retrieveWithParam`, {
                params: cleanParams // Chỉ gửi những gì có giá trị
            });
            return res.data;
        },
        staleTime: 1000 * 60 * 5,
    });

    // 4. Trích xuất dữ liệu an toàn
    const pageItems = response?.data || [];
    const paging = response?.paging;

    // Logic cho Pagination: Lấy trực tiếp từ API trả về
    const totalRecords = paging?.totalRecords || 0;
    // const totalPages = paging?.totalPages || 2;
    const pageSize = paging?.pageSize || 20;

    return (
        <div className="bg-[#222222]">
            {/* Header */}
            <div className="release flex items-center justify-between">
                <h2 className="font-semibold">
                    Manga Lists
                </h2>
            </div>

            {/* Filter Bar */}
            <div className="flex flex-col py-2">
                <div className="flex justify-center transition-all duration-200" role="tablist">
                    <QuickFilter order={Array.isArray(fieldSort) ? fieldSort[0] : fieldSort} page={pageNum} />
                </div>
                <div className="flex justify-end pr-5" role="tablist">
                    <Link href="/manga/list-mode/" className="px-2 py-1 text-[11px] font-medium transition-colors rounded-sm bg-[#333] text-gray-300 hover:text-white">
                        TEXT MODE
                    </Link>
                </div>
            </div>

            {/* Main Content: Grid List */}
            {isLoading && pageItems.length === 0 ? (
                <div className="h-64 flex items-center justify-center text-gray-500">
                    <span className="animate-pulse">Loading manga list...</span>
                </div>
            ) : !isLoading && pageItems.length === 0 ? (
                <div className="h-auto pb-10 flex items-center justify-center text-gray-300">
                    <span>-- No Post Found --</span>
                </div>
            ) : (
                <div className="grid grid-cols-3 min-[670px]:grid-cols-5 gap-4 p-5">
                    {pageItems.map((it) => {
                        const iconSrc = getTypeIcon(it.mangaCategory || "Manga");
                        return (
                            <article key={it.id} className="w-full group cursor-pointer">
                                {/* Cover Image Container */}
                                <Link href={`/manga/${it.id}`} className="relative w-full aspect-3/4 block overflow-hidden rounded-sm">
                                    <Image
                                        src={it.mangaAvatarUrl}
                                        fill
                                        alt={it.title}
                                        className="object-cover transition-transform duration-300 group-hover:scale-105"
                                        sizes="(max-width: 670px) 33vw, 20vw"
                                    />

                                    {/* Type Icon */}
                                    {iconSrc && (
                                        <div className="absolute top-0 right-0 z-10 p-1 bg-transparent rounded-bl-sm">
                                            <Image src={iconSrc} alt={it.mangaCategory || ""} width={22} height={15} />
                                        </div>
                                    )}

                                    {/* Colored Tag */}
                                    {it.colored && (
                                        <div className="absolute bottom-1 left-1 z-10">
                                            <span className="bg-[#ebcf04] text-black font-bold text-[9px] py-0.5 px-1 rounded-sm uppercase">
                                                Color
                                            </span>
                                        </div>
                                    )}
                                </Link>

                                {/* Info */}
                                <div className="mt-2">
                                    <Link href={`/manga/${it.id}`} className="block">
                                        <h3 className="text-sm font-semibold text-gray-100 leading-tight line-clamp-2 group-hover:text-sky-400 transition-colors">
                                            {it.title}
                                        </h3>
                                    </Link>

                                    <div className="flex flex-col gap-0.5 mt-1">
                                        {it.lastChapter && (
                                            <span className={`text-[12px] text-gray-400 ${fira.className}`}>
                                                {it.lastChapter}
                                            </span>
                                        )}
                                        <div className="flex items-center gap-1.5">
                                            <StarRating score={it.rating} />
                                            <span className="text-[11px] text-gray-500">{it.rating}</span>
                                        </div>
                                    </div>
                                </div>
                            </article>
                        );
                    })}
                </div>
            )}

            {/* Pagination Section */}
            {totalRecords > 0 && (
                <div className="py-8 border-t border-[#312f40] mx-5">
                    <Pagination
                        total={totalRecords}
                        page={pageNum}
                        pageSize={pageSize}
                        basePath={"/manga"}
                    />
                </div>
            )}
        </div>
    );
}