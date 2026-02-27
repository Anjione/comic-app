import Genre from '@/component/genre/genre';
import MangaList from '@/component/manga/manga-list';
import SearchBox from '@/component/common/search-box';
import SerialPopular from '@/component/common/serial-popular';
// Vì trang này sử dụng searchParams, nên cần wrap trong Suspense nếu build static
// Hoặc đơn giản là component trang
export default async function MangaPage({
    searchParams,
}: {
    searchParams: Promise<{ [key: string]: string | string[] | undefined }>;
}) {
    // Lấy params order từ URL (ví dụ: ?order=popular)
    // Lưu ý: trong Next.js 15, searchParams có thể là Promise, cần check version.
    // Tuy nhiên code cơ bản như sau thường hoạt động tốt ở server component.

    // Với Next.js 15, access searchParams trực tiếp có thể cần await nếu nó là promise (trong tương lai), 
    // nhưng hiện tại ta cứ viết đơn giản. Nếu lỗi thì sửa sau.
    // const order = searchParams?.order; 
    // Await searchParams theo chuẩn Next.js 15
    const params = await searchParams;

    // Chuẩn bị object tham số để truyền xuống Client Component
    const initialParams = {
        page: Number(params.page) || 1,
        category: (params.type as string) || '',
        title: (params.title as string) || '',
        author: (params.author as string) || '',
        genre_in: (params.genre_in as string) || '',
        genre_not: (params.genre_not as string) || '',
        status: (params.status as string) || '',
        fieldSort: (params.order as string) || '',
    };

    return (
        <div className="w-full">
            <div className="max-w-7xl m-[35px_auto] mb-[160px] min-[800px]:mb-[35px] min-[800px]:px-5 min-[1226px]:px-12 flex flex-col gap-4">
                <div className="listupd grid grid-cols-1 lg:grid-cols-7 xl:grid-cols-17 gap-4">
                    {/* LatestUpdate */}
                    <div className="flex flex-col col-span-1 lg:col-span-5 xl:col-span-12 gap-4">
                        <MangaList
                            initialParams={initialParams}
                        />
                    </div>

                    {/* SerialPopular */}
                    <div className="flex flex-col gap-5 col-span-1 lg:col-span-2 xl:col-span-5">
                        <SearchBox />
                        <SerialPopular />
                        <Genre />
                    </div>
                </div>

            </div>
        </div>
    );
}
