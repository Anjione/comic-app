// Thêm Image vào import
import { fira } from "@/lib/fonts";
import Image from "next/image";
import Link from "next/link";
import Pagination from "./pagination";
import { getTypeIcon, getTypeColor } from "@/lib/common-util";

// ... (các imports khác)
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

export default function LatestUpdate({ items, page = 1,
  pageSize = 20,
  basePath = "/page" }: LatestUpdateProps) {

  // Logic phân trang... (Giữ nguyên)
  const total = items.length;
  const totalPages = Math.max(1, Math.ceil(total / pageSize));
  const current = Math.min(Math.max(1, page), totalPages);
  const start = (current - 1) * pageSize;
  const pageItems = items.slice(start, start + pageSize);

  return (
    <section className="bixbox bg-[#222222] shadow">
      <div className="release flex items-center justify-between">
        <h2 className="font-semibold">
          Latest Update
        </h2>
        <Link href="/manga?order=update" className="px-2 py-1 text-[8px] font-medium transition-colors rounded-sm bg-black text-white uppercase">
          View All
        </Link>
      </div>
      {/* Grid 2 cột */}
      <div className="listupd grid grid-cols-1 min-[670px]:grid-cols-2 gap-0 pt-2 px-2">
        {pageItems.map((c: ComicItem) => {

          // 1. Lấy đường dẫn icon
          const iconSrc = getTypeIcon(c.type);

          return (
            <article key={c.id} className="utao styletwo flex gap-3 px-2 py-4 transition border-b border-[#333]">
              <Link href={c.url} className="imgu relative w-[110px] h-[150px] rounded overflow-hidden shrink-0">

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
                  <div className="absolute top-0 right-0 z-10 p-[5px] drop-shadow-[0_1px_1px_rgba(0,0,0,0.5)]">
                    <Image
                      src={iconSrc}
                      alt={c.type || "Manga"}
                      width={25} // Điều chỉnh kích thước icon tại đây
                      height={17} // Điều chỉnh kích thước icon tại đây
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
                <Link href={c.url} className="block hover:text-black transition-colors duration-300">
                  <h3 className="text-[15px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-1">
                    {c.title}
                  </h3>
                </Link>

                {c.chapters && (
                  <ul className="mt-2 space-y-1">
                    {c.chapters.slice(0, 3).map((ch: Chapter, idx: number) => {
                      // Lấy màu dựa trên type truyện
                      const bulletColor = getTypeColor(c.type);

                      return (
                        <li key={idx} className="flex justify-between items-center gap-2 text-sm">
                          <div className="flex items-center min-w-0">
                            {/* Dấu chấm giả */}
                            <span className={`mr-2 shrink-0 text-lg leading-none ${bulletColor}`}>•</span>

                            <Link
                              href={ch.url}
                              className={`chapter-font text-[#999] hover:text-white transition-colors truncate ${fira.className}`}
                            >
                              {ch.title}
                            </Link>
                          </div>

                          <span className={`text-[0.8rem] text-[#999] shrink-0 ${fira.className}`}>
                            {ch.timeAgo ?? ch.timeago}
                          </span>
                        </li>
                      );
                    })}
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

      {/* Pagination */}
      <Pagination total={items.length} page={page} pageSize={pageSize} basePath={basePath} />
    </section>
  );
}