"use client"
import AlphabeticalNav from '@/component/common/alphabet-nav';
import QuickFilter from '@/component/common/quick-filter';
import { TextModeComicResponse } from '@/type/comic-info';
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';
import Link from 'next/link';



export default function TextMangaList() {

    const { data: response } = useQuery<TextModeComicResponse>({
        queryKey: ['manga-text-list'],
        queryFn: async () => {
            const res = await axios.get(`/api-remote/manga/groupAlphabet`);
            return res.data;
        },
        // Giữ staleTime để tránh refetch liên tục khi chuyển qua lại giữa các trang
        staleTime: 1000 * 60 * 5,
    });

    const pageItems = response?.data || [];

    return (
        // Thay thế div.bixbox
        <div className="bg-[#222222]">

            {/* Thay thế div.releases.blog */}
            <div className="release flex items-center justify-between">
                <h2 className="font-semibold">
                    Manga Lists
                </h2>
            </div>
            <div
                className="flex justify-center rounded transition-all duration-200"
                role="tablist"
                aria-label="Popular series range"
            >
                <QuickFilter />
            </div>
            <AlphabeticalNav />
            <div
                className="flex justify-end rounded transition-all duration-200 pr-[15px]"
                role="tablist"
                aria-label="Popular series range"
            >
                <Link href="/manga" className="px-2 py-1 text-[13px] font-medium transition-colors rounded-sm bg-[#333] text-white">
                    Image mode
                </Link>
            </div>
            <div className="space-y-6 p-4 bg-transparent">

                {/* div.lxx có vẻ là một dải phân cách/placeholder trống rỗng, tôi sẽ bỏ qua hoặc thay bằng một dải phân cách nhỏ */}
                {/* <div className="h-1 bg-gray-700 w-full mb-4"></div> */}

                {pageItems.map((group) => (
                    // div.blix: Nhóm các mục theo chữ cái
                    <div
                        key={group.letter}
                        className="bg-transparent"
                    >
                        {/* span: Chứa Anchor Name (ID) */}
                        <span
                            id={group.letter.replace(/[^a-zA-Z0-9]/g, '')} // ID phải là ký tự hợp lệ
                            className="
                            block text-md font-extrabold text-white mb-3 
                            border-b border-[#333] pb-1
                        "
                        >
                            {group.letter}
                        </span>

                        {/* ul: Danh sách các truyện tranh trong nhóm */}
                        <ul className="list-none space-y-2 pl-0 grid min-[570px]:grid-cols-2">
                            {group.items.map((item) => (
                                <li key={item.id} className="text-gray-300 hover:text-white transition-colors">
                                    <span className={`mr-2 shrink-0 text-lg leading-none text-black`}>•</span>
                                    <Link
                                        // class="series tip" rel="..."
                                        href={`/manga/list-mode/`}
                                        rel={item.id.toString()}
                                        className="
                                        text-xs md:text-sm hover:text-white 
                                    "
                                    >
                                        {item.title}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
        </div>
    );
}