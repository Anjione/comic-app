import Link from 'next/link';
import React from 'react';
import Image from "next/image";
import StarRating from '../../component/star-rating';
import { SeriesItem } from '../../type/comic-info';
import { fira } from "@/lib/fonts";
import Pagination from '../../component/pagination';
import { Constants } from '../../constants';
import QuickFilter from '@/component/quick-filter';



export default function MangaList({ order }: { order?: string | string[] }) {

    const bookmarks: SeriesItem[] = [
        { id: 1, rank: 1, chapter: "Chapter 1", title: "Magic Emperor", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 2, rank: 2, chapter: "Chapter 2", title: "Tales of Demons and Gods", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Fantasy"], score: 7 },
        { id: 3, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 4, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 5, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 6, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 7, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 8, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    ];

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
            <div className="grid grid-cols-2 xs:grid-cols-3 sm:grid-cols-4 lg:grid-cols-5 gap-2 sm:gap-4 p-5">
                {pageItems.map((it) => {
                    return (
                        <article key={it.id} className={`w-full bg-transparent rounded-md flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer`}>
                            {/* Cover */}
                            <Link href={it.href} className="w-full block">
                                <article className="w-full">
                                    <div className="relative w-full aspect-[3/4]">
                                        <Image
                                            src={it.img}
                                            fill
                                            alt={it.title}
                                            className="object-cover"
                                        />
                                    </div>
                                </article>

                            </Link>

                            {/* Title */}
                            <Link href={it.href} className="w-full block">
                                <div className="text-sm my-[8px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-2">{it.title}</div>
                            </Link>

                            {/* Chapter count */}
                            {it.chapter && (
                                <div className={`text-sm text-[#999] ${fira.className}`}>{it.chapter}</div>
                            )}

                            {/* Stars */}
                            <div className="flex items-center gap-1 mt-1">
                                <div className="flex items-center">
                                    <StarRating score={it.score} />
                                </div>
                                <div className="text-xs text-[#999]">
                                    {it.score}
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