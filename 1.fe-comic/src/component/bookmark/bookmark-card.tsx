"use client";
import Link from 'next/link';
import React, { useState } from 'react';
import Image from "next/image";
import StarRating from '../common/star-rating';
import { SeriesItem } from '../../type/comic-info';
import { fira } from "@/lib/fonts";
import Pagination from '../common/pagination';
import { Constants } from '../../constants';
import { getTypeIcon } from '@/lib/common-util';



export default function BookmarkCard() {

    const bookmarks: SeriesItem[] = [
        { id: 1, rank: 1, chapter: "Chapter 1", title: "Magic Emperor", href: "#", img: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manga", colored: true },
        { id: 2, rank: 2, chapter: "Chapter 2", title: "Tales of Demons and Gods", href: "#", img: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop", genres: ["Action", "Fantasy"], score: 7, type: "Manga", colored: true },
        { id: 3, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manga", colored: true },
        { id: 4, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manga", colored: true },
        { id: 5, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manga", colored: true },
        { id: 6, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manga", colored: true },
        { id: 7, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manga", colored: true },
        { id: 8, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manga", colored: true },
    ];

    const [list, setList] = useState(bookmarks); // Giả định bookmarks là mảng mẫu của bạn
    const [isEditMode, setIsEditMode] = useState(false);

    const handleDeleteItem = (e: React.MouseEvent, id: number) => {
        e.preventDefault(); // Ngăn việc nhấn nút xóa bị nhảy vào Link truyện
        setList(list.filter(item => item.id !== id));
    };


    const total = bookmarks.length;
    const totalPages = Math.max(1, Math.ceil(total / Constants.DEFAULT_PAGE_SIZE));
    const current = Math.min(Math.max(1, Constants.DEFAULT_PAGE), totalPages);
    const start = (current - 1) * Constants.DEFAULT_PAGE_SIZE;
    const pageItems = list.slice(start, start + Constants.DEFAULT_PAGE_SIZE);

    return (
        // Thay thế div.bixbox
        <div className="bg-[#222222]">

            {/* Thay thế div.releases.blog */}
            <div className="release flex items-center justify-between">
                <h2 className="font-semibold">
                    Bookmark
                </h2>
                <button onClick={() => setIsEditMode(!isEditMode)} className="px-5 py-[2px] text-[13px] font-medium transition-colors rounded-sm bg-[#e53327] text-white cursor-pointer">
                    Delete
                </button>
            </div>
            <div
                className="bg-[#333] m-2 py-2 px-2 flex justify-center rounded transition-all duration-200"
                role="tablist"
                aria-label="Popular series range"
            >
                <p className="text-xs font-medium text-gray-300">You can save a list of manga titles here up to 100. The list approves based on the latest update date. The list of manga is stored in a browser that you can use right now.</p>
            </div>
            <div className="grid grid-cols-3 min-[670px]:grid-cols-5 gap-4 p-5">
                {pageItems.map((it) => {
                    const iconSrc = getTypeIcon(it.type);
                    return (
                        <article key={it.id} className={`relative w-full bg-transparent rounded-md flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer`}>
                            {/* Nút X nhỏ hiển thị khi nhấn Delete chính */}
                            {isEditMode && (
                                <div
                                    onClick={(e) => handleDeleteItem(e, it.id)}
                                    className="absolute z-11 cursor-pointer top-0 right-0 text-white text-[13px] px-[5px] py-[2px] bg-[#e53427]"
                                >
                                    Delete
                                </div>
                            )}

                            {/* Cover Area */}
                            <Link href={it.href} className="w-full block">
                                <div className="relative w-full aspect-[3/4]">
                                    <Image
                                        src={it.img}
                                        fill
                                        alt={it.title}
                                        className="object-cover rounded-sm"
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