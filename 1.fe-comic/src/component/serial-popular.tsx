// components/PopularSeriesWidget.tsx
"use client";

import React, { JSX, useState } from "react";
import StarRating from "./star-rating";
import Link from "next/link";
import Image from "next/image";
import { fira } from "@/lib/fonts";


type SeriesItem = {
    id: number;
    rank: number;
    title: string;
    href: string;
    img: string;
    genres: string[];
    score: number; // 0-10
};

const sampleWeekly: SeriesItem[] = [
    { id: 1, rank: 1, title: "Magic Emperor", href: "#", img: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
    { id: 2, rank: 2, title: "Tales of Demons and Gods", href: "#", img: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop", genres: ["Action", "Fantasy"], score: 7 },
    { id: 3, rank: 3, title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7 },
];

const sampleMonthly: SeriesItem[] = [
    { id: 4, rank: 1, title: "Monthly A", href: "#", img: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop", genres: ["Comedy"], score: 6 },
    { id: 5, rank: 2, title: "Monthly B", href: "#", img: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop", genres: ["Drama"], score: 8 },
];

const sampleAlltime: SeriesItem[] = [
    { id: 6, rank: 1, title: "Alltime A", href: "#", img: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop", genres: ["Action"], score: 9 },
    { id: 7, rank: 2, title: "Alltime B", href: "#", img: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop", genres: ["Fantasy"], score: 8 },
];

type TabId = "weekly" | "monthly" | "alltime";

const TABS: { id: TabId; label: string }[] = [
    { id: "weekly", label: "Weekly" },
    { id: "monthly", label: "Monthly" },
    { id: "alltime", label: "All" },
];

function tabClass(isActive: boolean) {
    // active: black background + white text
    // inactive: gray text, hover lighter
    return (
        `px-1 py-1 text-[12px] font-medium transition-colors rounded-sm w-1/3 ` +
        (isActive ? "bg-black text-white" : "text-gray-400 cursor-pointer hover:text-gray-200")
    ).trim();
}

export default function SerialPopular(): JSX.Element {
    const [active, setActive] = useState<TabId>("weekly");

    const items = active === "weekly" ? sampleWeekly : active === "monthly" ? sampleMonthly : sampleAlltime;

    return (
        <section aria-labelledby="popular-series-heading" className="w-full">
            <div className="release px-4 py-2">
                <h2 id="popular-series-heading" className="font-semibold">Serial Popular</h2>
            </div>

            <div className="bg-[#222] max-w-7xl mx-auto text-[12px] rounded">
                {/* Tabs */}
                <div className="p-2">
                    <div
                        className="bg-[#333] m-[2px] py-[5px] px-[6px] flex justify-center rounded"
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
                <div className="grid grid-cols-1 gap-4 p-3">
                    {items.length > 0 ? (
                        items.map((it) => (
                            <article
                                key={it.id}
                                className="flex gap-4 items-start border-b border-[#312f40] last:border-b-0 pb-4"
                                aria-labelledby={`series-${it.id}`}
                            >
                                <div className="w-[25px] h-[25px] text-center self-center flex items-center justify-center text-[1em] text-[#888] border border-[#888] border-[0.5px] rounded-[3px] ">
                                    {it.rank}
                                </div>

                                <div className="w-[58px] h-[77px] flex-shrink-0 overflow-hidden rounded">
                                    {/* If using Next.js Image, replace with next/image for optimization */}
                                    <Link href={it.href} className="w-full block">
                                        <article className="w-[100px]">
                                            <div className="relative w-full aspect-[1/1] rounded overflow-hidden">
                                                <Image
                                                    src={it.img}
                                                    fill
                                                    alt={it.title}
                                                    className="object-cover"
                                                />
                                            </div>
                                        </article>

                                    </Link>
                                </div>

                                <div className="flex-1 text-[12px]">
                                    <Link href={it.href} className="w-full block hover:text-black transition-colors duration-300">
                                        <div className="text-[13.3px] my-[8px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-2">{it.title}</div>
                                    </Link>

                                    <div className="text-xs mt-1">
                                        <span className={`text-[#999] font-medium ${fira.className}`}>Genres</span>:{" "}
                                        {it.genres.map((g, idx) => (
                                            <span key={g}>
                                                <Link
                                                    href={`/genre/${g.toLowerCase()}`}
                                                    className={`text-xs text-gray-300 hover:text-black transition-colors duration-300 ${fira.className}`}
                                                >
                                                    {g}
                                                </Link>
                                                {idx < it.genres.length - 1 && ", "}
                                            </span>
                                        ))}
                                    </div>

                                    {/* Stars */}
                                    <div className="flex items-center gap-1 mt-1">
                                        <div className="flex items-center">
                                            <StarRating score={it.score} />
                                        </div>
                                        <div className="text-xs text-[#999]">
                                            {it.score}
                                        </div>
                                    </div>
                                </div>
                            </article>
                        ))
                    ) : (
                        <div className="text-gray-400">No items</div>
                    )}
                </div>
            </div>
        </section>
    );
}
