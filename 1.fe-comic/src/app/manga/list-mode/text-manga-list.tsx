import Link from 'next/link';
import React from 'react';
import QuickFilter from '@/component/quick-filter';
import { SERIES_DATA, SeriesItem } from '@/type/comic-info';
import { Constants } from '@/constants';
import Pagination from '@/component/pagination';
import AlphabeticalNav from '@/component/alphabet-nav';



export default function TextMangaList() {

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
                <QuickFilter />
            </div>
            <AlphabeticalNav />
            <div
                className="flex justify-end rounded transition-all duration-200 pr-[15px]"
                role="tablist"
                aria-label="Popular series range"
            >
                <Link href="/manga" className="px-2 py-1 text-[13px] font-medium transition-colors rounded-sm bg-[#333] text-white">
                    Image mode
                </Link>
            </div>
            <div className="space-y-6 p-4 bg-transparent">

                {/* div.lxx có vẻ là một dải phân cách/placeholder trống rỗng, tôi sẽ bỏ qua hoặc thay bằng một dải phân cách nhỏ */}
                {/* <div className="h-1 bg-gray-700 w-full mb-4"></div> */}

                {SERIES_DATA.map((group) => (
                    // div.blix: Nhóm các mục theo chữ cái
                    <div
                        key={group.letter}
                        className="bg-transparent"
                    >
                        {/* span: Chứa Anchor Name (ID) */}
                        <span
                            id={group.letter.replace(/[^a-zA-Z0-9]/g, '')} // ID phải là ký tự hợp lệ
                            className="
                            block text-md font-extrabold text-white mb-3 
                            border-b border-[#333] pb-1
                        "
                        >
                            {group.letter}
                        </span>

                        {/* ul: Danh sách các truyện tranh trong nhóm */}
                        <ul className="list-none space-y-2 pl-0">
                            {group.items.map((item) => (
                                <li key={item.id} className="text-gray-300 hover:text-white transition-colors">
                                    <Link
                                        // class="series tip" rel="..."
                                        href={item.url}
                                        rel={item.id.toString()}
                                        className="
                                        text-xs md:text-sm hover:text-white 
                                    "
                                    >
                                        {item.title}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
        </div>
    );
}