// Thêm Image vào import
import Image from "next/image";
import Link from "next/link";
import mangaSrc from "@/asset/manga.png";
import manhwaSrc from "@/asset/manhwa.png";
import manhuaSrc from "@/asset/manhua.png";
import { Fira_Sans } from "next/font/google";

// ... (các imports khác)

// --- HÀM HELPER ĐỂ XỬ LÝ ICON ---
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
// ---------------------------------

// (Các interface và logic phân trang giữ nguyên)
interface Chapter {
  url: string;
  title: string;
  timeAgo?: string;
  timeago?: string;
}

interface ComicItem {
  id: string;
  title: string;
  url: string;
  cover: string;
  type?: string;
  isNew?: boolean;
  status?: string;
  chapters?: Chapter[];
}

interface LatestUpdateProps {
  items: ComicItem[];
  nextPageUrl?: string;
  page?: number;
  pageSize?: number;
  basePath?: string;
}

const fira = Fira_Sans({
  subsets: ["latin"],
  weight: ["300", "400", "500", "600", "700"],
  variable: "--font-fira",
});

export default function LatestUpdate({ items, nextPageUrl, page = 1,
  pageSize = 20,
  basePath = "/page" }: LatestUpdateProps) {

  // Logic phân trang... (Giữ nguyên)
  const total = items.length;
  const totalPages = Math.max(1, Math.ceil(total / pageSize));
  const current = Math.min(Math.max(1, page), totalPages);
  const start = (current - 1) * pageSize;
  const pageItems = items.slice(start, start + pageSize);
  const isFirstPage = current <= 1;
  const isLastPage = current >= totalPages;

  const previousPage = current - 1;
  const nextPage = current + 1;

  // Class Styling... (Giữ nguyên)
  const buttonClasses = "flex items-center px-8 py-1 text-sm font-medium bg-black rounded-xs transition-colors duration-500 whitespace-nowrap";
  const textColorClasses = "text-[#ddd] hover:text-white";

  return (
    <section className="bixbox bg-[#222222] dark:bg-[#222222] shadow">
      <div className="release flex items-center justify-between">
        <h2 className="font-semibold">
          Latest Update
        </h2>
        <Link href="/manga?order=update" className="px-2 py-1 text-[8px] font-medium transition-colors rounded-sm bg-black text-white uppercase">
          View All
        </Link>
      </div>

      {/* Grid 2 cột */}
      <div className="listupd grid grid-cols-1 sm:grid-cols-2 gap-4">
        {pageItems.map((c: ComicItem) => {

          // 1. Lấy đường dẫn icon
          const iconSrc = getTypeIcon(c.type);

          return (
            <article key={c.id} className="utao styletwo flex gap-3 p-2 rounded-md hover:bg-slate-50 dark:hover:bg-slate-800 transition">
              <Link href={c.url} className="imgu relative w-[95px] sm:w-[120px] md:w-[140px] h-[140px] sm:h-[170px] rounded overflow-hidden shrink-0">

                {/* Ảnh bìa (Giữ nguyên) */}
                <Image
                  src={c.cover}
                  alt={c.title}
                  fill
                  sizes="(max-width:640px) 30vw, (max-width:1024px) 25vw, 140px"
                  className="object-cover"
                  priority={false}
                />

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

                {/* Cờ NEW (Giữ nguyên)
                {c.isNew && <span className="absolute top-2 right-2 bg-red-500 text-white text-xs px-2 py-0.5 rounded z-10">N</span>} */}
              </Link>

              {/* Phần thông tin truyện còn lại (Giữ nguyên) */}
              <div className="luf flex-1 min-w-0">
                <Link href={c.url} className="block">
                  <h3 className="text-sm my-[8px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-1">
                    {c.title}
                  </h3>
                </Link>

                {c.chapters && (
                  <ul className="mt-2 space-y-1 text-sm text-slate-700 dark:text-slate-300">
                    {c.chapters.slice(0, 3).map((ch: Chapter, idx: number) => (
                      <li key={idx} className="flex justify-between items-center">
                        <Link href={ch.url} className={`text-sm chapter-font text-[#999] dark:text-[#999] hover:text-white transition-colors duration-300 ${fira.className}`}>
                          {ch.title}
                        </Link>
                        <span className="text-xs text-[#999]">{ch.timeAgo ?? ch.timeago}</span>
                      </li>
                    ))}
                  </ul>
                )}

                <div className="mt-3">
                  <span className={`relative inline-block bg-[#333] px-[10px] py-[3px] rounded-[5px] text-xs ${c.status === "on-going" ? "text-emerald-500" : "text-[#999]"}`}>
                    <i className="fas fa-circle mr-[5px] text-[0.6rem]" aria-hidden />
                  </span>
                </div>
              </div>
            </article>
          );
        })}
      </div>

      {/* Pagination (Giữ nguyên) */}
      <div className="flex justify-center items-center w-full gap-1 py-6">
        {!isFirstPage && (
          <Link
            href={`${basePath}/${previousPage}`} // Dùng basePath
            className={`${buttonClasses} ${textColorClasses}`}
          >
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4">
              <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
            </svg>
            Previous
          </Link>
        )}
        {!isLastPage && (
          <Link
            href={`${basePath}/${nextPage}`} // Dùng basePath
            className={`${buttonClasses} ${textColorClasses} `}
          >
            Next
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-4 h-4">
              <path strokeLinecap="round" strokeLinejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
            </svg>
          </Link>
        )}
      </div>
    </section>
  );
}