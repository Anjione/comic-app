"use client"
import PaginationNumber from '@/component/common/pagination-number';
import StarRating from '@/component/common/star-rating';
import { getTypeIcon } from '@/lib/common-util';
import { fira } from '@/lib/fonts';
import { MangaListResponse } from '@/type/manga';
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';
import Image from "next/image";
import Link from 'next/link';
import { usePathname } from 'next/navigation';


// --- Dữ liệu tĩnh cho A-Z List ---
const ALPHABET = [
    { char: '#', param: '.' },
    { char: '0-9', param: '0-9' },
    ...Array.from({ length: 26 }, (_, i) => String.fromCharCode(65 + i)).map(char => ({
        char,
        param: char,
    })),
];

export default function AZLists({ show }: { show: string | string[] | undefined }) {
    const BASE_URL = "/az-lists";

    const pathname = usePathname(); // Đảm bảo đã import từ next/navigation
    // const searchParams = useSearchParams();

    // 1. Lấy page từ Pathname (dạng /az-lists/page/2)
    const match = pathname.match(/\/page\/(\d+)/);
    const pageNum = match ? Number(match[1]) : 1;

    // 2. Lấy các filter khác từ searchParams (dạng ?show=A)
    // const show = searchParams.get('show') || '';

    // 3. React Query với đầy đủ Dependencies
    const { data: response } = useQuery<MangaListResponse>({
        queryKey: ['az-list', pageNum, show],
        queryFn: async () => {
            // Tạo object chứa tất cả params dự kiến
            const rawParams = {
                pageNum,
                pageSize: 20,
                show,
            };

            // Lọc bỏ các key có giá trị falsy (chuỗi rỗng, null, undefined)
            // Lưu ý: Chúng ta giữ lại số 0 nếu pageNum có thể là 0
            const cleanParams = Object.fromEntries(
                Object.entries(rawParams).filter(([, value]) => value !== '' && value !== null && value !== undefined)
            );

            const res = await axios.get(`/api-remote/manga/retrieveWithParam`, {
                params: cleanParams // Chỉ gửi những gì có giá trị
            });
            return res.data;
        },
        staleTime: 1000 * 60 * 5,
    });

    // 4. Trích xuất dữ liệu an toàn
    const pageItems = response?.data || [];
    const paging = response?.paging;

    // Logic cho Pagination: Lấy trực tiếp từ API trả về
    const totalPages = paging?.totalPages || 2;

    // const bookmarks: SeriesItem[] = [
    //     { id: 1, rank: 1, chapter: "Chapter 1", title: "Magic Emperor", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manga", colored: true },
    //     { id: 2, rank: 2, chapter: "Chapter 2", title: "Tales of Demons and Gods", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 3, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 4, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 5, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 6, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 7, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 8, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 9, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 10, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 11, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 12, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    //     { id: 13, rank: 3, chapter: "Chapter 3", title: "Swordmaster’s Youngest Son", href: "#", img: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop", genres: ["Action", "Adventure", "Fantasy"], score: 7, type: "Manhwa", colored: true },
    // ];

    // const total = bookmarks.length; 

    return (
        // Thay thế div.bixbox
        <div className="bg-[#222222]">

            {/* Thay thế div.releases.blog */}
            <div className="release flex items-center justify-between">
                <h2 className="font-semibold">
                    AZ Lists
                </h2>
            </div>
            {/* Danh sách A-Z (ul.az-list) */}
            {/* Sử dụng flex-wrap để cuộn ngang trên di động nếu cần, hoặc grid để cố định */}
            <ul className="flex flex-wrap justify-center sm:justify-center gap-3 p-4 list-none">
                {ALPHABET.map((item) => (
                    <li key={item.char} className="shrink-0">
                        <Link
                            href={`${BASE_URL}/?show=${item.param}`}
                            className="
                  block 
                  w-8 h-8 
                  leading-8 
                  text-center 
                  text-[14px] 
                  font-semibold 
                  bg-black text-white
                  hover:text-[#999] 
                  transition-colors 
                  duration-200
                "
                        >
                            {item.char}
                        </Link>
                    </li>
                ))}
            </ul>
            <div className="grid grid-cols-3 min-[670px]:grid-cols-5 gap-4 p-5">
                {pageItems.map((it) => {
                    const iconSrc = getTypeIcon(it.mangaCategory || "Manga");
                    return (
                        <article key={it.id} className={`w-full bg-transparent rounded-md flex-col items-start gap-0 transition-colors duration-500 hover:text-[#000000] cursor-pointer`}>
                            {/* Cover */}
                            <Link href={`/manga/${it.id}`} className="w-full block">
                                <article className="w-full">
                                    <div className="relative w-full aspect-3/4">
                                        <Image
                                            src={it.mangaAvatarUrl}
                                            fill
                                            alt={it.title}
                                            className="object-cover"
                                        />
                                        {/* 💥 THAY THẾ/THÊM ICON 💥 */}
                                        {iconSrc ? (
                                            // Hiển thị Icon ảnh ở góc trên bên trái
                                            <div className="absolute top-0 right-0 z-10 p-[5px] drop-shadow-[0_1px_1px_rgba(0,0,0,0.5)]">
                                                <Image
                                                    src={iconSrc}
                                                    alt={it.mangaCategory || "Manga"}
                                                    width={25} // Điều chỉnh kích thước icon tại đây
                                                    height={17} // Điều chỉnh kích thước icon tại đây
                                                    className="opacity-90"
                                                />
                                            </div>
                                        ) : (
                                            // Nếu không có icon ảnh, hiển thị text cũ (hoặc không hiển thị gì)
                                            // Tôi giữ lại span text cũ nếu không tìm thấy icon để đảm bảo tính an toàn
                                            <span className="absolute top-2 left-2 bg-indigo-600 text-white text-xs px-2 py-0.5 rounded">
                                                {it.mangaCategory ?? "Manga"}
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
                                </article>

                            </Link>

                            {/* Title */}
                            <Link href={`/manga/${it.id}`} className="w-full block">
                                <div className="text-sm my-[8px] mb-[3px] font-semibold leading-[20px] text-left overflow-hidden text-ellipsis line-clamp-2">{it.title}</div>
                            </Link>

                            {/* Chapter count */}
                            {it.lastChapter && (
                                <div className={`text-sm text-[#999] ${fira.className}`}>{it.lastChapter}</div>
                            )}

                            {/* Stars */}
                            <div className="flex items-center gap-1 mt-1">
                                <div className="flex items-center">
                                    <StarRating score={it.rating} />
                                </div>
                                <div className="text-xs text-[#999]">
                                    {it.rating}
                                </div>
                            </div>
                        </article>
                    );
                })}
            </div>
            {/* Pagination */}
            <PaginationNumber totalPages={totalPages} />
        </div>

    );
}