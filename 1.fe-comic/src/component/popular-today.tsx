// components/LatestTwoColWithStars.tsx
import Image from "next/image";
import Link from "next/link";
import React from "react";
import StarRating from "./star-rating";
import { Fira_Sans } from "next/font/google";

export type Chapter = { title: string; url: string; timeAgo?: string };
export type Comic = {
  id: string | number;
  url: string;
  title: string;
  cover: string;
  type?: string;
  chapter?: string;   // e.g. "Chapter 783" or "Chapter 16"
  ratingPct?: number;      // optional 0-100
  score?: number;          // optional 0-10 or 0-5
  isNew?: boolean;
};

const fira = Fira_Sans({
  subsets: ["latin"],
  weight: ["300", "400", "500", "600", "700"],
  variable: "--font-fira",
});

export default function PopularToday({
  items,
  maxVisible = 7, // max visible per page before scroll appears
}: {
  items: Comic[];
  maxVisible?: number;
}) {
  const needsScroll = (items?.length ?? 0) > maxVisible;

  // Two-column responsive: grid-cols-1 on mobile, sm:grid-cols-2 on >=640px
  // If more than maxVisible, limit container height and allow scroll
  // We estimate height per row and compute max-height for showing maxVisible items:
  // rows = ceil(maxVisible / 2)
  const rows = Math.ceil(maxVisible / 2);
  // approximate row height in px (adjust if your card size different)
  const rowHeightPx = 160; // each card ~160px tall (cover + text)
  const maxH = rows * rowHeightPx; // px

  return (
    <div>
      <div className="relative bg-[#000] flex justify-between items-baseline px-[15px]">
        <h2 className="mt-[15px] px-[20px] py-[5px] bg-[#222] rounded-tl-[5px] rounded-tr-[5px] text-[15px] text-white leading-[20px] font-semibold relative">
          Popular Today
        </h2>
      </div>
      <section className="bixbox bg-[#222222] dark:bg-[#222222] p-2 shadow">
        <div
          className={`flex justify-center sm:justify-start ${needsScroll ? "overflow-x-scroll" : ""

            } [&::-webkit-scrollbar]:h-[10px]
              [&::-webkit-scrollbar-track]:bg-[#111]
              [&::-webkit-scrollbar-thumb]:bg-[#333]
              [&::-webkit-scrollbar-track]:rounded-[2px]
              [&::-webkit-scrollbar-thumb]:rounded-[2px]`}
          // apply max-height only if needs scroll
          style={needsScroll ? { maxHeight: `${maxH}px` } : undefined}
        >
          {items.map((c) => {
            return (
              <article key={c.id} className="styletwo bg-transparent rounded-md p-3 flex flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer">
                {/* Cover */}
                <Link href={c.url} className="w-full block">
                  <article className="w-[140px]">
                    <div className="relative w-full aspect-[3/4]">
                      <Image
                        src={c.cover}
                        fill
                        alt={c.title}
                        className="object-cover"
                      />
                    </div>
                  </article>

                </Link>

                {/* Title */}
                <Link href={c.url} className="w-full block">
                  <div className="text-sm my-[8px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-2">{c.title}</div>
                </Link>

                {/* Chapter count */}
                {c.chapter && (
                  <div className={`text-sm text-[#999] dark:text-[#999] ${fira.className}`}>{c.chapter}</div>
                )}

                {/* Stars */}
                <div className="flex items-center gap-1 mt-1">
                  <div className="flex items-center">
                    <StarRating score={c.score} />
                  </div>
                  <div className="text-xs text-[#999] dark:text-[#999]">
                    {c.score}
                  </div>
                </div>
              </article>
            );
          })}
        </div>
      </section>
    </div>
  );
}
