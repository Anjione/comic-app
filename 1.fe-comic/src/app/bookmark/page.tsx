import BookmarkCard from '@/app/bookmark/bookmark-card';
import Genre from '@/component/genre';
import SearchBox from '@/component/search-box';
import SerialPopular from '@/component/serial-popular';
import { SAMPLE_GENRES } from '@/type/comic-info';

export default function BookmarkPage() {
    return (
        <div className="w-full">
            <div className="max-w-7xl m-[35px_auto] min-[800px]:px-13 flex flex-col gap-4">
                <div className="listupd grid grid-cols-1 lg:grid-cols-7 xl:grid-cols-17 gap-4">
                    {/* LatestUpdate */}
                    <div className="flex flex-col col-span-1 lg:col-span-5 xl:col-span-12 gap-4">
                        <BookmarkCard />
                    </div>

                    {/* SerialPopular */}
                    <div className="flex flex-col gap-5 col-span-1 lg:col-span-2 xl:col-span-5">
                        <SearchBox />
                        <SerialPopular />
                        <Genre genres={SAMPLE_GENRES} />
                    </div>
                </div>

            </div>
        </div>
    );
}
