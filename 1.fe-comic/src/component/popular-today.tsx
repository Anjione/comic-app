// components/LatestTwoColWithStars.tsx
import Image from "next/image";
import Link from "next/link";
import React from "react";
import StarRating from "./star-rating";
import { fira } from "@/lib/fonts";
import mangaSrc from "@/asset/manga.png";
import manhwaSrc from "@/asset/manhwa.png";
import manhuaSrc from "@/asset/manhua.png";

export type Chapter = { title: string; url: string; timeAgo?: string };
export type PopularManga = {
  id: string | number;
  url: string;
  title: string;
  cover: string;
  type?: string;
  chapter?: string;   // e.g. "Chapter 783" or "Chapter 16"
  ratingPct?: number;      // optional 0-100
  score?: number;          // optional 0-10 or 0-5
  isNew?: boolean;
  colored?: boolean;
};



const getTypeIcon = (type: string | undefined) => {
  if (!type) return null;
  const lowerType = type.toLowerCase();
  switch (lowerType) {
    case 'manga':
      return mangaSrc;
    case 'manhwa':
      return manhwaSrc;
    case 'manhua':
      return manhuaSrc;
    default:
      return null;
  }
};

export default function PopularToday({
  items,
  maxVisible = 7, // max visible per page before scroll appears
}: {
  items: PopularManga[];
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
      <section className="bixbox bg-[#222222] p-2 shadow">
        <div
          className={`flex justify-start sm:justify-start ${needsScroll ? "overflow-x-scroll" : ""

            } [&::-webkit-scrollbar]:h-[10px]
              [&::-webkit-scrollbar-track]:bg-[#111]
              [&::-webkit-scrollbar-thumb]:bg-[#333]
              [&::-webkit-scrollbar-track]:rounded-[2px]
              [&::-webkit-scrollbar-thumb]:rounded-[2px]`}
          // apply max-height only if needs scroll
          style={needsScroll ? { maxHeight: `${maxH}px` } : undefined}
        >
          {items.map((c, index) => {
            const uniqueKey = `${c.id}-${index}`;
            const iconSrc = getTypeIcon(c.type);
            return (
              <article key={uniqueKey} className="styletwo bg-transparent rounded-md p-3 flex flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer">
                {/* Cover */}
                <Link href={c.url} className="relative w-full rounded overflow-hidden shrink-0">
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
                  {/* 💥 THAY THẾ/THÊM ICON 💥 */}
                  {iconSrc ? (
                    // Hiển thị Icon ảnh ở góc trên bên trái
                    <div className="absolute top-0 right-0 z-10 p-1">
                      <Image
                        src={iconSrc}
                        alt={c.type || "Manga"}
                        width={30} // Điều chỉnh kích thước icon tại đây
                        height={15} // Điều chỉnh kích thước icon tại đây
                        className="opacity-90"
                      />
                    </div>
                  ) : (
                    // Nếu không có icon ảnh, hiển thị text cũ (hoặc không hiển thị gì)
                    // Tôi giữ lại span text cũ nếu không tìm thấy icon để đảm bảo tính an toàn
                    <span className="absolute top-2 left-2 bg-indigo-600 text-white text-xs px-2 py-0.5 rounded">
                      {c.type ?? "Manga"}
                    </span>
                  )}
                  {c.colored && (
                    <div className="absolute bottom-0 left-0 z-10 p-1">
                      <span className="
                          absolute z-10 
                          bottom-[5px] left-[5px] 
                          bg-[#ebcf04] text-[rgba(0,0,0,0.7)] 
                          font-bold text-[10px] 
                          py-[2px] px-[5px] 
                          rounded-[3px] uppercase">
                        Colored
                      </span>
                    </div>
                  )}

                </Link>


                {/* Title */}
                <Link href={c.url} className="w-full block">
                  <div className="text-sm my-[8px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-2">{c.title}</div>
                </Link>

                {/* Chapter count */}
                {c.chapter && (
                  <div className={`text-sm text-[#999] ${fira.className}`}>{c.chapter}</div>
                )}

                {/* Stars */}
                <div className="flex items-center gap-1 mt-1">
                  <div className="flex items-center">
                    <StarRating score={c.score} />
                  </div>
                  <div className="text-xs text-[#999]">
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
