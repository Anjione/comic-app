// components/LatestTwoColWithStars.tsx
import Image from "next/image";
import Link from "next/link";
import React from "react";

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

// Chuyển điểm số 0..10 sang 0..5 sao, có half star
function calcStars(score?: number): ('full' | 'half' | 'empty')[] {
  if (typeof score !== 'number') return Array(5).fill('empty');

  const rating = Math.max(0, Math.min(score, 10)); // clamp 0..10
  const fullStars = Math.floor(rating / 2); // số sao đầy
  const halfStar = rating % 2 >= 1 ? 1 : 0; // nửa sao nếu phần dư >=1
  const emptyStars = 5 - fullStars - halfStar;

  const stars: ('full' | 'half' | 'empty')[] = [];

  for (let i = 0; i < fullStars; i++) stars.push('full');
  if (halfStar) stars.push('half');
  for (let i = 0; i < emptyStars; i++) stars.push('empty');

  return stars;
}


function Star({ type }: { type: 'full' | 'half' | 'empty' }) {
  if (type === 'full') {
    return (
      <svg className="w-4 h-4 text-amber-400" viewBox="0 0 20 20" fill="currentColor">
        <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955a1 1 0 00.95.69h4.163c.969 0 1.371 1.24.588 1.81l-3.37 2.449a1 1 0 00-.364 1.118l1.287 3.955c.3.92-.755 1.688-1.54 1.118l-3.37-2.449a1 1 0 00-1.176 0l-3.37 2.449c-.784.57-1.84-.197-1.54-1.118l1.287-3.955a1 1 0 00-.364-1.118L2.07 9.382c-.783-.57-.38-1.81.588-1.81h4.163a1 1 0 00.95-.69l1.286-3.955z" />
      </svg>
    );
  } else if (type === 'half') {
    return (
      <svg className="w-4 h-4" viewBox="0 0 20 20">
        <defs>
          <clipPath id="half-left">
            <rect x="0" y="0" width="10" height="20" />
          </clipPath>
        </defs>

        {/* Empty star */}
        <path
          d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955a1 1 0 00.95.69h4.163c.969 0 1.371 1.24.588 1.81l-3.37 2.449a1 1 0 00-.364 1.118l1.287 3.955c.3.92-.755 1.688-1.54 1.118l-3.37-2.449a1 1 0 00-1.176 0l-3.37 2.449c-.784.57-1.84-.197-1.54-1.118l1.287-3.955a1 1 0 00-.364-1.118L2.07 9.382c-.783-.57-.38-1.81.588-1.81h4.163a1 1 0 00.95-.69l1.286-3.955z"
          fill="transparent"
          stroke="#555b57"
          strokeWidth="2"
        />

        {/* Half filled */}
        <path
          d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955a1 1 0 00.95.69h4.163c.969 0 1.371 1.24.588 1.81l-3.37 2.449a1 1 0 00-.364 1.118l1.287 3.955c.3.92-.755 1.688-1.54 1.118l-3.37-2.449a1 1 0 00-1.176 0l-3.37 2.449c-.784.57-1.84-.197-1.54-1.118l1.287-3.955a1 1 0 00-.364-1.118L2.07 9.382c-.783-.57-.38-1.81.588-1.81h4.163a1 1 0 00.95-.69l1.286-3.955z"
          fill="#ffc700"
          strokeWidth="1"
          clipPath="url(#half-left)"
        />
      </svg>
    );
  } else {
    return (
      <svg className="w-4 h-4" viewBox="0 0 20 20">
        <path
          d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955a1 1 0 00.95.69h4.163c.969 0 1.371 1.24.588 1.81l-3.37 2.449a1 1 0 00-.364 1.118l1.287 3.955c.3.92-.755 1.688-1.54 1.118l-3.37-2.449a1 1 0 00-1.176 0l-3.37 2.449c-.784.57-1.84-.197-1.54-1.118l1.287-3.955a1 1 0 00-.364-1.118L2.07 9.382c-.783-.57-.38-1.81.588-1.81h4.163a1 1 0 00.95-.69l1.286-3.955z"
          fill="transparent"
          stroke="#555b57"
          strokeWidth="2"
        />
      </svg>
    );
  }
}

function StarRating({ score }: { score?: number }) {
  const stars = calcStars(score);

  return (
    <div className="flex">
      {stars.map((type, idx) => (
        <Star key={idx} type={type} />
      ))}
    </div>
  );
}

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
          className={`listupd flex justify-center sm:justify-start ${needsScroll ? "overflow-x-scroll" : ""

            } [&::-webkit-scrollbar]:h-[10px]
              [&::-webkit-scrollbar-track]:bg-[#111]
              [&::-webkit-scrollbar-thumb]:bg-[#333]
              [&::-webkit-scrollbar-track]:rounded-[2px]
              [&::-webkit-scrollbar-thumb]:rounded-[2px]`}
          // apply max-height only if needs scroll
          style={needsScroll ? { maxHeight: `${maxH}px` } : undefined}
        >
          {items.map((c) => {
            const stars = calcStars(c.score);
            return (
              <article key={c.id} className="utao styletwo bg-transparent rounded-md p-3 flex flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer">
                {/* Cover */}
                <Link href={c.url} className="w-full block">
                  <article className="w-[140px]">
                    <div className="relative w-full aspect-[3/4] rounded overflow-hidden">
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
                  <div className="text-[13.3px] my-[8px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-2">{c.title}</div>
                </Link>

                {/* Chapter count */}
                {c.chapter && (
                  <div className="text-xs text-[#999] dark:text-[#999]">{c.chapter}</div>
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
