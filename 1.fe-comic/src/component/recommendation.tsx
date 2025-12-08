// components/PopularSeriesWidget.tsx
"use client";

import React, { JSX, useState } from "react";
import StarRating from "./star-rating";
import Link from "next/link";
import Image from "next/image";
import { Fira_Sans } from "next/font/google";


type SeriesItem = {
    id: number;
    rank: number;
    chapter: string;
    title: string;
    href: string;
    img: string;
    genres: string[];
    score: number; // 0-10
};

const sampleComedy: SeriesItem[] = [
    { id: 1, rank: 1, chapter: "Chapter 1", title: "Magic Emperor", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    { id: 2, rank: 2, chapter: "Chapter 2", title: "Tales of Demons and Gods", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Fantasy"], score: 7 },
    { id: 3, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    { id: 3, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    { id: 3, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
];

const sampleHorror: SeriesItem[] = [
    { id: 4, rank: 1, chapter: "Chapter 1", title: "Monthly A", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Comedy"], score: 6 },
    { id: 5, rank: 2, chapter: "Chapter 2", title: "Monthly B", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Drama"], score: 8 },
];

const sampleSchoolLife: SeriesItem[] = [
    { id: 6, rank: 1, chapter: "Chapter 1", title: "Alltime A", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action"], score: 9 },
    { id: 7, rank: 2, chapter: "Chapter 2", title: "Alltime B", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Fantasy"], score: 8 },
];

const sampleShotacon: SeriesItem[] = [
    { id: 8, rank: 1, chapter: "Chapter 1", title: "Alltime A", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action"], score: 9 },
    { id: 9, rank: 2, chapter: "Chapter 2", title: "Alltime B", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Fantasy"], score: 8 },
];

const sampleWebComic: SeriesItem[] = [
    { id: 10, rank: 1, chapter: "Chapter 1", title: "Alltime A", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action"], score: 9 },
    { id: 11, rank: 2, chapter: "Chapter 2", title: "Alltime B", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Fantasy"], score: 8 },
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
    // active: black background + white text
    // inactive: gray text, hover lighter
    return (
        `px-10 py-1 text-[14px] font-medium transition-colors rounded-sm ` +
        (isActive ? "bg-black text-white" : "text-gray-400 hover:text-gray-200")
    ).trim();
}

const fira = Fira_Sans({
    subsets: ["latin"],
    weight: ["300", "400", "500", "600", "700"],
    variable: "--font-fira",
});

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
                        className="bg-[#333] m-[2px] py-[5px] flex justify-center rounded gap-4"
                        role="tablist"
                        aria-label="Popular series range"
                    >
                        {TABS.map((tab) => {
                            const isActive = tab.id === active;
                            return (
                                <button
                                    key={tab.id}
                                    id={`tab-${tab.id}`}
                                    role="tab"
                                    aria-selected={isActive}
                                    aria-controls={`panel-${tab.id}`}
                                    onClick={() => setActive(tab.id)}
                                    className={tabClass(isActive)}
                                >
                                    {tab.label}
                                </button>
                            );
                        })}
                    </div>
                </div>

                {/* Content */}
                <div className="flex justify-center sm:justify-start">
                    {items.map((it) => {
                        return (
                            <article key={it.id} className="w-[165px] styletwo bg-transparent rounded-md p-3 flex flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer">
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
