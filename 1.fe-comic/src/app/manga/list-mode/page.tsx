import Genre from '@/component/genre';
import SearchBox from '@/component/search-box';
import SerialPopular from '@/component/serial-popular';
import { SAMPLE_GENRES } from '@/type/comic-info';
import TextMangaList from './text-manga-list';
// Vì trang này sử dụng searchParams, nên cần wrap trong Suspense nếu build static
// Hoặc đơn giản là component trang
export default function MangaPage() {
    // Lấy params order từ URL (ví dụ: ?order=popular)
    // Lưu ý: trong Next.js 15, searchParams có thể là Promise, cần check version.
    // Tuy nhiên code cơ bản như sau thường hoạt động tốt ở server component.

    // Với Next.js 15, access searchParams trực tiếp có thể cần await nếu nó là promise (trong tương lai), 
    // nhưng hiện tại ta cứ viết đơn giản. Nếu lỗi thì sửa sau.
    // const order = searchParams?.order; 

    return (
        <div className="w-full">
            <div className="max-w-7xl m-[35px_auto] min-[800px]:px-5 min-[1226px]:px-12 flex flex-col gap-4">
                <div className="listupd grid grid-cols-1 lg:grid-cols-7 min-[880px]:grid-cols-17 gap-4">
                    {/* LatestUpdate */}
                    <div className="flex flex-col col-span-1 lg:col-span-5 min-[880px]:col-span-12 gap-4">
                        <TextMangaList />
                    </div>

                    {/* SerialPopular */}
                    <div className="flex flex-col gap-5 col-span-1 lg:col-span-2 min-[880px]:col-span-5">
                        <SearchBox />
                        <SerialPopular />
                        <Genre genres={SAMPLE_GENRES} />
                    </div>
                </div>

            </div>
        </div>
    );
}
