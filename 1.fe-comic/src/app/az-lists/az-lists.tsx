import PaginationNumber from '@/component/common/pagination-number';
import StarRating from '@/component/star-rating';
import { Constants } from '@/constants';
import { fira } from '@/lib/fonts';
import { SeriesItem } from '@/type/comic-info';
import Image from "next/image";
import Link from 'next/link';


// --- Dữ liệu tĩnh cho A-Z List ---
const ALPHABET = [
    { char: '#', param: '.' },
    { char: '0-9', param: '0-9' },
    ...Array.from({ length: 26 }, (_, i) => String.fromCharCode(65 + i)).map(char => ({
        char,
        param: char,
    })),
];

export default function AZLists({ showLetter }: { showLetter?: string | string[] }) {
    const BASE_URL = "/az-lists";

    const bookmarks: SeriesItem[] = [
        { id: 1, rank: 1, chapter: "Chapter 1", title: "Magic Emperor", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 2, rank: 2, chapter: "Chapter 2", title: "Tales of Demons and Gods", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Fantasy"], score: 7 },
        { id: 3, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 4, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 5, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 6, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 7, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 8, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 9, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 10, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 11, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 12, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
        { id: 13, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    ];

    const total = bookmarks.length;
    const totalPages = Math.max(1, Math.ceil(total / 10));
    const current = Math.min(Math.max(1, Constants.DEFAULT_PAGE), totalPages);
    const start = (current - 1) * 10;
    const pageItems = bookmarks.slice(start, start + 10);

    return (
        // Thay thế div.bixbox
        <div className="bg-[#222222]">

            {/* Thay thế div.releases.blog */}
            <div className="release flex items-center justify-between">
                <h2 className="font-semibold">
                    AZ Lists
                </h2>
            </div>
            {/* Danh sách A-Z (ul.az-list) */}
            {/* Sử dụng flex-wrap để cuộn ngang trên di động nếu cần, hoặc grid để cố định */}
            <ul className="flex flex-wrap justify-center sm:justify-center gap-3 p-4 list-none">
                {ALPHABET.map((item) => (
                    <li key={item.char} className="shrink-0">
                        <Link
                            href={`${BASE_URL}/?show=${item.param}`}
                            className="
                  block 
                  w-8 h-8 
                  leading-8 
                  text-center 
                  text-[14px] 
                  font-semibold 
                  bg-black text-white
                  hover:text-[#999] 
                  transition-colors 
                  duration-200
                "
                        >
                            {item.char}
                        </Link>
                    </li>
                ))}
            </ul>
            <div className="grid grid-cols-3 sm:grid-cols-4 lg:grid-cols-5 gap-2 sm:gap-4 p-5">
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
            <PaginationNumber total={bookmarks.length} page={Constants.DEFAULT_PAGE} pageSize={10} basePath={"/az-lists"} param={`?show=${showLetter}`} />
        </div>

    );
}