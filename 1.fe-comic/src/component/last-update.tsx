import Image from "next/image";
import Link from "next/link";

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

export default function LatestUpdate({ items, nextPageUrl, page = 1,
    pageSize = 10,
    basePath = "/page" }: LatestUpdateProps) {
    const total = items.length;
    const totalPages = Math.max(1, Math.ceil(total / pageSize));
    const current = Math.min(Math.max(1, page), totalPages);
    const start = (current - 1) * pageSize;
    const pageItems = items.slice(start, start + pageSize);
    return (
        <section className="bixbox bg-[#222222] dark:bg-[#222222] p-4 shadow">
          <div className="releases flex items-center justify-between mb-4">
            <h2 className="text-xl font-semibold">Latest Update</h2>
            <Link href="/manga?order=update" className="text-indigo-600 hover:underline">
              View All
            </Link>
          </div>
    
          {/* Grid 2 cột */}
          <div className="listupd grid grid-cols-1 sm:grid-cols-2 gap-4">
            {pageItems.map((c: ComicItem) => (
              <article key={c.id} className="utao styletwo flex gap-3 p-2 rounded-md hover:bg-slate-50 dark:hover:bg-slate-800 transition">
                <Link href={c.url} className="imgu relative w-[95px] sm:w-[120px] md:w-[140px] h-[140px] sm:h-[170px] rounded overflow-hidden shrink-0">
                  <Image
                    src={c.cover}
                    alt={c.title}
                    fill
                    sizes="(max-width:640px) 30vw, (max-width:1024px) 25vw, 140px"
                    className="object-cover"
                    priority={false}
                  />
                  <span className="absolute top-2 left-2 bg-indigo-600 text-white text-xs px-2 py-0.5 rounded">{c.type ?? "Manga"}</span>
                  {c.isNew && <span className="absolute top-2 right-2 bg-red-500 text-white text-xs px-2 py-0.5 rounded">N</span>}
                </Link>
    
                <div className="luf flex-1 min-w-0">
                  <Link href={c.url} className="block">
                    <h3 className="text-sm md:text-base font-medium text-slate-900 dark:text-white line-clamp-2">
                      {c.title}
                    </h3>
                  </Link>
    
                  {c.chapters && (
                    <ul className="mt-2 space-y-1 text-sm text-slate-700 dark:text-slate-300">
                      {c.chapters.slice(0, 3).map((ch: Chapter, idx: number) => (
                        <li key={idx} className="flex justify-between items-center">
                          <Link href={ch.url} className="text-indigo-600 hover:underline">
                            {ch.title}
                          </Link>
                          <span className="text-[11px] text-slate-500 dark:text-slate-400 ml-2">{ch.timeAgo ?? ch.timeago}</span>
                        </li>
                      ))}
                    </ul>
                  )}
    
                  <div className="mt-3">
                    <span className={`statusind inline-flex items-center gap-2 text-xs ${c.status === "on-going" ? "text-emerald-500" : "text-slate-500"}`}>
                      <i className="fas fa-circle" aria-hidden /> <span className="sr-only">Status</span>
                    </span>
                  </div>
                </div>
              </article>
            ))}
          </div>
    
          {/* Pagination */}
          <div className="mt-4 flex items-center justify-between">
            <div className="text-sm text-slate-600 dark:text-slate-300">
              Showing {(start + 1)}–{Math.min(start + pageSize, total)} of {total}
            </div>
    
            <div className="flex items-center gap-2">
              <Link
                href={current > 1 ? `${basePath}/${current - 1}` : '#'}
                className={`px-3 py-1 rounded ${current === 1 ? 'opacity-40 pointer-events-none' : 'bg-slate-100 hover:bg-slate-200'}`}
                aria-disabled={current === 1}
              >
                Prev
              </Link>
    
              <span className="text-sm">Page {current} / {totalPages}</span>
    
              <Link
                href={current < totalPages ? `${basePath}/${current + 1}` : '#'}
                className={`px-3 py-1 rounded ${current === totalPages ? 'opacity-40 pointer-events-none' : 'bg-slate-100 hover:bg-slate-200'}`}
                aria-disabled={current === totalPages}
              >
                Next
              </Link>
            </div>
          </div>
        </section>
      );
}
