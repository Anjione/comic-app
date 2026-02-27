import GenreMangaList from '../../component/genre/genre-manga-list';
// Vì trang này sử dụng searchParams, nên cần wrap trong Suspense nếu build static
// Hoặc đơn giản là component trang
export default async function GenrePage() {
    // Lấy params order từ URL (ví dụ: ?order=popular)
    // Lưu ý: trong Next.js 15, searchParams có thể là Promise, cần check version.
    // Tuy nhiên code cơ bản như sau thường hoạt động tốt ở server component.

    // Với Next.js 15, access searchParams trực tiếp có thể cần await nếu nó là promise (trong tương lai), 
    // nhưng hiện tại ta cứ viết đơn giản. Nếu lỗi thì sửa sau.
    // const { show } = await searchParams;

    return (
        <div className="w-full">
            <div className="max-w-7xl m-[35px_auto] mb-[160px] min-[800px]:mb-[35px] min-[800px]:px-5 min-[1226px]:px-12 flex flex-col gap-4">
                <div className="listupd">
                    {/* LatestUpdate */}
                    <div className="flex flex-col">
                        <GenreMangaList />
                    </div>
                </div>

            </div>
        </div>
    );
}
