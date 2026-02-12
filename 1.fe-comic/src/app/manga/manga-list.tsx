"use client";
import QuickFilter from '@/component/quick-filter';
import { getTypeIcon } from '@/lib/common-util';
import { fira } from "@/lib/fonts";
import { MangaData } from '@/type/manga';
import axios from 'axios';
import Image from "next/image";
import Link from 'next/link';
import { useEffect, useState } from 'react';
import Pagination from '../../component/pagination';
import StarRating from '../../component/star-rating';
import { Constants } from '../../constants';


export default function MangaList({ order }: { order?: string | string[] }) {
    // const NoSSRComponent = dynamic(() => import('./manga-list'), { ssr: false });
    const [bookmarks, setMangas] = useState<MangaData[]>([]);
    // const [loading, setLoading] = useState<boolean>(true);
    useEffect(() => {
        const fetchMangaList = async () => {
            try {
                const response = await axios.get('/api-remote/manga');
                if (response['data']['status'] === 200) {
                    setMangas(response['data']['data']); // Map mảng danh sách truyện vào state
                }
            } catch (error) {
                console.error("Lỗi fetch danh sách truyện:", error);
            } finally {
                // setLoading(false);
            }
        };

        fetchMangaList();
    }, []);

    const total = bookmarks.length;
    const totalPages = Math.max(1, Math.ceil(total / Constants.DEFAULT_PAGE_SIZE));
    const current = Math.min(Math.max(1, Constants.DEFAULT_PAGE), totalPages);
    const start = (current - 1) * Constants.DEFAULT_PAGE_SIZE;
    const pageItems = bookmarks.slice(start, start + Constants.DEFAULT_PAGE_SIZE);

    return (
        // Thay thế div.bixbox
        <div className="bg-[#222222]">

            {/* Thay thế div.releases.blog */}
            <div className="release flex items-center justify-between">
                <h2 className="font-semibold">
                    Manga Lists
                </h2>
            </div>
            <div
                className="flex justify-center rounded transition-all duration-200"
                role="tablist"
                aria-label="Popular series range"
            >
                <QuickFilter order={Array.isArray(order) ? order[0] : order} />
            </div>
            <div
                className="flex justify-end rounded transition-all duration-200 pr-[15px]"
                role="tablist"
                aria-label="Popular series range"
            >
                <Link href="/manga/list-mode/" className="px-2 py-1 text-[13px] font-medium transition-colors rounded-sm bg-[#333] text-white">
                    Text mode
                </Link>
            </div>
            <div className="grid grid-cols-3 min-[670px]:grid-cols-5 gap-4 p-5">
                {pageItems.map((it) => {
                    const iconSrc = getTypeIcon(it.type || "Manga");
                    return (
                        <article key={it.id} className={`w-full bg-transparent rounded-md flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer`}>
                            {/* Cover */}
                            <Link href="#" className="w-full block">
                                <article className="w-full">
                                    <div className="relative w-full aspect-3/4">
                                        <Image
                                            src={it.mangaAvatarUrl}
                                            fill
                                            alt={it.title}
                                            className="object-cover"
                                        />
                                        {/* 💥 THAY THẾ/THÊM ICON 💥 */}
                                        {iconSrc ? (
                                            // Hiển thị Icon ảnh ở góc trên bên trái
                                            <div className="absolute top-0 right-0 z-10 p-[5px] drop-shadow-[0_1px_1px_rgba(0,0,0,0.5)]">
                                                <Image
                                                    src={iconSrc}
                                                    alt={it.type || "Manga"}
                                                    width={25} // Điều chỉnh kích thước icon tại đây
                                                    height={17} // Điều chỉnh kích thước icon tại đây
                                                    className="opacity-90"
                                                />
                                            </div>
                                        ) : (
                                            // Nếu không có icon ảnh, hiển thị text cũ (hoặc không hiển thị gì)
                                            // Tôi giữ lại span text cũ nếu không tìm thấy icon để đảm bảo tính an toàn
                                            <span className="absolute top-2 left-2 bg-indigo-600 text-white text-xs px-2 py-0.5 rounded">
                                                {it.type ?? "Manga"}
                                            </span>
                                        )}
                                        {it.colored && (
                                            <div className="absolute bottom-0 left-0 z-10 p-1">
                                                <span className="
                                                                                                  absolute z-10 
                                                                                                  bottom-[5px] left-[5px] 
                                                                                                  bg-[#ebcf04] text-[rgba(0,0,0,0.7)] 
                                                                                                  font-bold text-[10px] 
                                                                                                  py-[2px] px-[5px] 
                                                                                                  rounded-[3px] uppercase
                                                                                                  flex items-center gap-1">
                                                    <i className="fas fa-palette" aria-hidden="true"></i>
                                                    <span>Color</span>
                                                </span>
                                            </div>
                                        )}
                                    </div>
                                </article>

                            </Link>

                            {/* Title */}
                            <Link href="#" className="w-full block">
                                <div className="text-sm my-[8px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-2">{it.title}</div>
                            </Link>

                            {/* Chapter count */}
                            {it.lastChapter && (
                                <div className={`text-sm text-[#999] ${fira.className}`}>{it.lastChapter}</div>
                            )}

                            {/* Stars */}
                            <div className="flex items-center gap-1 mt-1">
                                <div className="flex items-center">
                                    <StarRating score={it.rating} />
                                </div>
                                <div className="text-xs text-[#999]">
                                    {it.rating}
                                </div>
                            </div>
                        </article>
                    );
                })}
            </div>
            {/* Pagination */}
            <Pagination total={bookmarks.length} page={Constants.DEFAULT_PAGE} pageSize={Constants.DEFAULT_PAGE_SIZE} basePath={"/bookmark"} />
        </div>
    );
}