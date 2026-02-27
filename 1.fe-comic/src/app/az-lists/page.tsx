import Genre from '@/component/genre/genre';
import SearchBox from '@/component/common/search-box';
import SerialPopular from '@/component/common/serial-popular';
import AZLists from '../../component/az-lists/az-lists';
// Vì trang này sử dụng searchParams, nên cần wrap trong Suspense nếu build static
// Hoặc đơn giản là component trang
export default async function AZListsPage({
    searchParams,
}: {
    searchParams: Promise<{ [key: string]: string | string[] | undefined }>;
}) {
    // Lấy params order từ URL (ví dụ: ?order=popular)
    // Lưu ý: trong Next.js 15, searchParams có thể là Promise, cần check version.
    // Tuy nhiên code cơ bản như sau thường hoạt động tốt ở server component.

    // Với Next.js 15, access searchParams trực tiếp có thể cần await nếu nó là promise (trong tương lai), 
    // nhưng hiện tại ta cứ viết đơn giản. Nếu lỗi thì sửa sau.
    const { show } = await searchParams;

    return (
        <div className="w-full">
            <div className="max-w-7xl m-[35px_auto] mb-[160px] min-[800px]:mb-[35px] min-[800px]:px-5 min-[1226px]:px-12 flex flex-col gap-4">
                <div className="listupd grid grid-cols-1 lg:grid-cols-7 min-[880px]:grid-cols-17 gap-4">
                    {/* LatestUpdate */}
                    <div className="flex flex-col col-span-1 lg:col-span-5 min-[880px]:col-span-12 gap-4">
                        <AZLists show={show} />
                    </div>

                    {/* SerialPopular */}
                    <div className="flex flex-col gap-5 col-span-1 lg:col-span-2 min-[880px]:col-span-5">
                        <SearchBox />
                        <SerialPopular />
                        <Genre />
                    </div>
                </div>

            </div>
        </div>
    );
}
