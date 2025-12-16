// components/PopularSeriesWidget.tsx
"use client";

import React, { JSX, useState } from "react";
import StarRating from "./star-rating";
import Link from "next/link";
import Image from "next/image";
import { SeriesItem } from "@/type/comic-info";
import { fira } from "@/lib/fonts";


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

const sampleComedy: SeriesItem[] = [
    { id: 1, rank: 1, chapter: "Chapter 1", title: "Magic Emperor", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    { id: 2, rank: 2, chapter: "Chapter 2", title: "Tales of Demons and Gods", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Fantasy"], score: 7 },
    { id: 3, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    { id: 4, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    { id: 5, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
];

const sampleHorror: SeriesItem[] = [
    { id: 6, rank: 1, chapter: "Chapter 1", title: "Monthly A", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Comedy"], score: 6 },
    { id: 7, rank: 2, chapter: "Chapter 2", title: "Monthly B", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Drama"], score: 8 },
];

const sampleSchoolLife: SeriesItem[] = [
    { id: 8, rank: 1, chapter: "Chapter 1", title: "Alltime A", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action"], score: 9 },
    { id: 9, rank: 2, chapter: "Chapter 2", title: "Alltime B", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Fantasy"], score: 8 },
];

const sampleShotacon: SeriesItem[] = [
    { id: 10, rank: 1, chapter: "Chapter 1", title: "Alltime A", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action"], score: 9 },
    { id: 11, rank: 2, chapter: "Chapter 2", title: "Alltime B", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Fantasy"], score: 8 },
];

const sampleWebComic: SeriesItem[] = [
    { id: 12, rank: 1, chapter: "Chapter 1", title: "Alltime A", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action"], score: 9 },
    { id: 13, rank: 2, chapter: "Chapter 2", title: "Alltime B", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Fantasy"], score: 8 },
];

type TabId = "comedy" | "horror" | "school-life" | "shotacon" | "web-comic";

const TABS: { id: TabId; label: string }[] = [
    { id: "comedy", label: "Comedy" },
    { id: "horror", label: "Horror" },
    { id: "school-life", label: "School Life" },
    { id: "shotacon", label: "Shotacon" },
    { id: "web-comic", label: "Web Comic" },
];

function tabClass(isActive: boolean) {
    return (
        `px-1 py-1 text-[12px] sm:text-[14px] font-medium transition-colors rounded-sm whitespace-nowrap w-[50%] ` +
        (isActive ? "bg-black text-white" : "text-gray-400 hover:text-gray-200")
    ).trim();
}



export default function Recommendation(): JSX.Element {
    const [active, setActive] = useState<TabId>("comedy");

    const items = active === "comedy" ? sampleComedy : active === "horror" ? sampleHorror : active === "school-life" ? sampleSchoolLife : active === "shotacon" ? sampleShotacon : sampleWebComic;

    return (
        <section aria-labelledby="popular-series-heading" className="w-full">
            <div className="release px-4 py-2">
                <h2 id="popular-series-heading" className="font-semibold">Recommendation</h2>
            </div>

            <div className="bg-[#222] max-w-7xl mx-auto text-[12px] rounded pb-8">
                {/* Tabs */}
                <div className="p-2">
                    <div
                        className="bg-[#333] m-[2px] py-[5px] px-[6px] flex justify-center rounded transition-all duration-200"
                        role="tablist"
                        aria-label="Popular series range"
                    >
                        {TABS.map((tab, index) => {
                            const isActive = tab.id === active;
                            const isHiddenOnSmallScreen = index >= 3;
                            const transitionClasses = isHiddenOnSmallScreen
                                ? "transition-all duration-300 ease-in-out max-[590px]:opacity-0 max-[590px]:max-w-0 max-[590px]:p-0 max-[590px]:invisible"
                                : "transition-all duration-300 ease-in-out";
                            // Hide 4th and 5th items (index 3 and 4) on screens <= 590px
                            const hiddenClass = index >= 3 ? "max-[590px]:hidden" : "w-[33%]";

                            return (
                                <button
                                    key={tab.id}
                                    id={`tab-${tab.id}`}
                                    role="tab"
                                    aria-selected={isActive}
                                    aria-controls={`panel-${tab.id}`}
                                    onClick={() => setActive(tab.id)}
                                    className={`${tabClass(isActive)} ${hiddenClass} ${transitionClasses}`}
                                >
                                    {tab.label}
                                </button>
                            );
                        })}
                    </div>
                </div>

                {/* Content */}
                <div className="grid grid-cols-2 min-[480px]:grid-cols-3 min-[650px]:grid-cols-4 min-[768px]:grid-cols-5">
                    {items.map((it, index) => {
                        // Logic ẩn hiện:
                        // - Luôn hiện 3 item đầu
                        // - Item thứ 4 (index 3): chỉ hiện khi màn hình > 480px
                        // - Item thứ 5 (index 4): chỉ hiện khi màn hình > 600px
                        const visibilityClass =
                            index === 2 ? "hidden min-[480px]:flex" :
                                index === 3 ? "hidden min-[650px]:flex" :
                                    index === 4 ? "hidden min-[768px]:flex" :
                                        index === 5 ? "hidden min-[1024px]:flex" : "flex"; // Dự phòng nếu có nhiều hơn 5 item

                        return (
                            <article key={it.id} className={`w-full ${visibilityClass} bg-transparent rounded-md p-3 flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer`}>
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
                                    <div className={`text-sm text-[#999] dark:text-[#999] ${fira.className}`}>{it.chapter}</div>
                                )}

                                {/* Stars */}
                                <div className="flex items-center gap-1 mt-1">
                                    <div className="flex items-center">
                                        <StarRating score={it.score} />
                                    </div>
                                    <div className="text-xs text-[#999] dark:text-[#999]">
                                        {it.score}
                                    </div>
                                </div>
                            </article>
                        );
                    })}
                </div>
            </div>
        </section>
    );
}
